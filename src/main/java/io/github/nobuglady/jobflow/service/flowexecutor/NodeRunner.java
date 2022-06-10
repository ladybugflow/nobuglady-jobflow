/*
 * Copyright (c) 2021-present, NoBugLady-jobflow Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package io.github.nobuglady.jobflow.service.flowexecutor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.CancellationException;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.constant.NodeStartType;
import io.github.nobuglady.jobflow.constant.NodeStatus;
import io.github.nobuglady.jobflow.constant.NodeStatusDetail;
import io.github.nobuglady.jobflow.logger.ConsoleLogger;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryFlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeShellEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.flowexecutor.delegator.NodeDelegator;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteQueueManager;
import io.github.nobuglady.jobflow.util.DataUtil;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
public class NodeRunner implements Runnable {

	private String flowId;
	private String historyId;
	private String nodeId;

	private String nodeName;

	private HistoryFlowDao historyFlowDao;
	private HistoryNodeDao historyNodeDao;
	private HistoryNodeHttpDao historyNodeHttpDao;
	private HistoryNodeShellDao historyNodeShellDao;

	private NodeDelegator nodeDelegator;

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param nodeId
	 * @param historyFlowDao
	 * @param historyNodeDao
	 * @param historyNodeHttpDao
	 * @param historyNodeShellDao
	 * @param nodeDelegator
	 */
	public NodeRunner(String flowId, String historyId, String nodeId, HistoryFlowDao historyFlowDao,
			HistoryNodeDao historyNodeDao, HistoryNodeHttpDao historyNodeHttpDao,
			HistoryNodeShellDao historyNodeShellDao, NodeDelegator nodeDelegator) {

		this.flowId = flowId;
		this.historyId = historyId;
		this.nodeId = nodeId;
		this.historyFlowDao = historyFlowDao;
		this.historyNodeDao = historyNodeDao;
		this.historyNodeHttpDao = historyNodeHttpDao;
		this.historyNodeShellDao = historyNodeShellDao;
		this.nodeDelegator = nodeDelegator;
	}

	/**
	 * 
	 */
	public void run() {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(Const.USER_SYS);

		AuthHolder.setUser(userEntity);

		// start log
		ConsoleLogger consoleLogger = ConsoleLogger.getInstance(flowId, historyId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");

		consoleLogger.info("-------------------------------------------------------------------");
		consoleLogger.info("[FLOW]" + flowId + ",[HISTORY]" + historyId + ",[NODE]" + nodeId + "");
		consoleLogger.info("-------------------------------------------------------------------");
		try {

			/*
			 * Get node instance
			 */
			HistoryNodeEntity historyNodeEntity = historyNodeDao.selectByKey(flowId, nodeId, historyId);
			nodeName = historyNodeEntity.getNodeName();

			HistoryFlowEntity flowEntity = historyFlowDao.selectByKey(flowId, historyId);
			String flowId = flowEntity.getFlowId();

			if (NodeStatus.READY != historyNodeEntity.getNodeStatus()) {
				consoleLogger.info(sdf.format(new Date()) + " [NOT READY]" + historyNodeEntity.getNodeStatus());
				return;
			}

			/*
			 * check node start type
			 */
			if (NodeStartType.NODE_START_TYPE_WAIT_REQUEST == DataUtil
					.getNodeStartType(historyNodeEntity.getStartType())) {

				consoleLogger.info(sdf.format(new Date()) + " [NODE OPENING]" + historyNodeEntity.getNodeName());
				historyNodeDao.updateStatusByNodeId(flowId, historyId, nodeId, NodeStatus.OPENNING);
				consoleLogger.info(sdf.format(new Date()) + " [NODE OPENED]" + historyNodeEntity.getNodeName());

			} else {

				String returnValue = null;

				/*
				 * check node execute type
				 */
				if (NodeExecuteType.NODE_EXECUTE_TYPE_NONE != DataUtil
						.getNodeExecuteType(historyNodeEntity.getExecuteType())) {

					historyNodeDao.updateStatusByNodeId(flowId, historyId, nodeId, NodeStatus.RUNNING);

					NodeInf nodeInf = nodeDelegator.getNodeDelegator(flowId, nodeId, historyId);
					if (nodeInf != null) {
						consoleLogger
								.info(sdf.format(new Date()) + " [NODE RUNNING]" + historyNodeEntity.getNodeName());
						returnValue = nodeInf.execute();
					}

					// check return value
					String expectReturnCode = null;

					if (NodeExecuteType.NODE_EXECUTE_TYPE_HTTP == historyNodeEntity.getExecuteType()) {

						HistoryNodeHttpEntity historyNodeHttpEntity = historyNodeHttpDao.selectByKey(flowId, nodeId,
								historyId);
						expectReturnCode = historyNodeHttpEntity.getSuccessCode();

					} else if (NodeExecuteType.NODE_EXECUTE_TYPE_SHELL == historyNodeEntity.getExecuteType()) {

						HistoryNodeShellEntity historyNodeShellEntity = historyNodeShellDao.selectByKey(flowId, nodeId,
								historyId);
						expectReturnCode = historyNodeShellEntity.getSuccessCode();

					}

					if (StringUtil.isNotEmpty(expectReturnCode) && !expectReturnCode.equals(returnValue)) {

						consoleLogger.info(sdf.format(new Date()) + " [NODE RETRUN VALUE ERROR][" + returnValue + "]"
								+ historyNodeEntity.getNodeName());
						historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
								NodeStatusDetail.COMPLETE_ERROR);
						CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);

					} else {

						consoleLogger.info(sdf.format(new Date()) + " [NODE COMPLETE][" + returnValue + "]"
								+ historyNodeEntity.getNodeName());
						historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
								NodeStatusDetail.COMPLETE_SUCCESS);
						CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);
					}

				} else {

					consoleLogger.info(sdf.format(new Date()) + " [NODE COMPLETE][" + returnValue + "]"
							+ historyNodeEntity.getNodeName());
					historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
							NodeStatusDetail.COMPLETE_SUCCESS);
					CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);
				}

			}

		} catch (InterruptedException e) {

			consoleLogger.error(sdf.format(new Date()) + " [NODE INTERRUPTED]" + nodeName, e);

			historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
					NodeStatusDetail.COMPLETE_CANCEL);

			CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);

		} catch (CancellationException e) {

			consoleLogger.error(sdf.format(new Date()) + " [NODE CANCEL]" + nodeName, e);

			historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
					NodeStatusDetail.COMPLETE_CANCEL);

			CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);

		} catch (Throwable e) {
			e.printStackTrace();
			consoleLogger.error(sdf.format(new Date()) + " [NODE ERROR]" + nodeName, e);

			historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE,
					NodeStatusDetail.COMPLETE_ERROR);

			CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);

		} finally {

		}

	}

}
