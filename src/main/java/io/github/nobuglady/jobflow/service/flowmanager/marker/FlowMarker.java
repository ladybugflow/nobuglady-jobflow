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
package io.github.nobuglady.jobflow.service.flowmanager.marker;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.NodeErrorType;
import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.constant.NodeStatus;
import io.github.nobuglady.jobflow.constant.NodeStatusDetail;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryEdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeShellEntity;
import io.github.nobuglady.jobflow.service.flowmanager.marker.helper.FlowHelper;
import io.github.nobuglady.jobflow.service.flowmanager.marker.helper.model.Flow;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteNodeResult;

/**
 * 
 * @author NoBugLady
 *
 */
@Component
public class FlowMarker {

	@Autowired
	private FlowHelper flowHelper;

	@Autowired
	private HistoryNodeDao historyNodeDao;

	@Autowired
	private HistoryNodeHttpDao historyNodeHttpDao;

	@Autowired
	private HistoryNodeShellDao historyNodeShellDao;

	/**
	 * 
	 * @param nodeResult
	 */
	public void onNodeComplete(CompleteNodeResult nodeResult) {

		String flowId = nodeResult.getFlowId();
		String historyId = nodeResult.getHistoryId();
		String nodeId = nodeResult.getNodeId();

		HistoryNodeEntity historyNodeEntity = historyNodeDao.selectByKey(flowId, nodeId, historyId);

		if (NodeStatus.COMPLETE != historyNodeEntity.getNodeStatus()) {
			return;
		}

		if (NodeStatus.COMPLETE == historyNodeEntity.getNodeStatus()
				&& NodeStatusDetail.COMPLETE_SUCCESS != historyNodeEntity.getNodeStatusDetail()) {
			// has error
			int nodeErrorType = NodeErrorType.NODE_ERROR_TYPE_STOP;
			if (NodeExecuteType.NODE_EXECUTE_TYPE_HTTP == historyNodeEntity.getExecuteType()) {

				HistoryNodeHttpEntity historyNodeHttpEntity = historyNodeHttpDao.selectByKey(flowId, nodeId, historyId);
				nodeErrorType = historyNodeHttpEntity.getErrorType();

			} else if (NodeExecuteType.NODE_EXECUTE_TYPE_SHELL == historyNodeEntity.getExecuteType()) {

				HistoryNodeShellEntity historyNodeShellEntity = historyNodeShellDao.selectByKey(flowId, nodeId,
						historyId);
				nodeErrorType = historyNodeShellEntity.getErrorType();
			}

			if (NodeErrorType.NODE_ERROR_TYPE_STOP == nodeErrorType) {
				return;
			}
		}

		historyNodeDao.updateStatusByNodeId(flowId, historyId, nodeId, NodeStatus.GO);

		// mark next
		markNext(flowId, historyId, nodeId);

	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param nodeId
	 */
	private void markNext(String flowId, String historyId, String nodeId) {

		Flow flow = flowHelper.getFlow(flowId, historyId);

		Map<String, List<HistoryEdgeEntity>> edgesMap = flow.getEdgesMap();
		Map<String, HistoryNodeEntity> nodeMap = flow.getNodeMap();

		List<HistoryEdgeEntity> edgeList = edgesMap.get(nodeId);
		if (edgeList != null && edgeList.size() > 0) {
			for (HistoryEdgeEntity edge : edgeList) {
				HistoryNodeEntity nodeTo = nodeMap.get(edge.getToNodeId());

				if (nodeTo == null) {
					continue;
				}

//                if (NodeStatus.WAIT == nodeTo.getStatus()) {

				boolean needWait = false;
				Map<String, List<HistoryEdgeEntity>> edgesBackMap = flow.getEdgesBackMap();
				List<HistoryEdgeEntity> edgeBackList = edgesBackMap.get(nodeTo.getNodeId());
				for (HistoryEdgeEntity flowEdgeBack : edgeBackList) {
					HistoryNodeEntity nodeFrom = nodeMap.get(flowEdgeBack.getFromNodeId());
					if (!(NodeStatus.GO == nodeFrom.getNodeStatus())) {
						needWait = true;
					}
				}

				if (!needWait) {
					historyNodeDao.updateStatusByNodeId(flowId, historyId, nodeTo.getNodeId(), NodeStatus.READY);
				}

//                }
			}
		}
	}

}
