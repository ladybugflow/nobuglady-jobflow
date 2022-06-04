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
package io.github.nobuglady.jobflow.service.flowexecutor.delegator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeShellEntity;
import io.github.nobuglady.jobflow.service.flowexecutor.NodeInf;

/**
 * 
 * @author NoBugLady
 *
 */
@Component
public class NodeDelegator {

	@Autowired
	private HistoryNodeDao historyNodeDao;

	@Autowired
	private HistoryNodeHttpDao historyNodeHttpDao;

	@Autowired
	private HistoryNodeShellDao historyNodeShellDao;

	/**
	 *
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
	public NodeInf getNodeDelegator(String flowId, String nodeId, String historyId) {

		System.out.println("get node:" + flowId + "," + nodeId + "," + historyId);
		HistoryNodeEntity historyNodeEntity = historyNodeDao.selectByKey(flowId, nodeId, historyId);

		if (NodeExecuteType.NODE_EXECUTE_TYPE_HTTP == historyNodeEntity.getExecuteType()) {

			HistoryNodeHttpEntity historyNodeHttpEntity = historyNodeHttpDao.selectByKey(flowId, nodeId, historyId);
			return new NodeDelegatorHttp(historyNodeHttpEntity, historyNodeHttpDao);

		} else if (NodeExecuteType.NODE_EXECUTE_TYPE_SHELL == historyNodeEntity.getExecuteType()) {

			HistoryNodeShellEntity historyNodeShellEntity = historyNodeShellDao.selectByKey(flowId, nodeId, historyId);
			return new NodeDelegatorShell(historyNodeShellEntity, historyNodeShellDao);

		} else {

			return null;
		}
	}
}
