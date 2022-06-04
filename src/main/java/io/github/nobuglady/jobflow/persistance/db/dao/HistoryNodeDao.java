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
package io.github.nobuglady.jobflow.persistance.db.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeMapper;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.websocket.WebSocketManager;

/**
 * NodeHistory table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryNodeDao {

	@Autowired
	private HistoryNodeMapper nodeHistoryMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param historyId
	 * @return
	 */
	public HistoryNodeEntity selectByKey(String flowId, String nodeId, String historyId) {

		return nodeHistoryMapper.selectByKey(flowId, nodeId, historyId);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @return
	 */
	public List<HistoryNodeEntity> selectByFlowHistoryId(String flowId, String historyId) {

		return nodeHistoryMapper.selectByFlowHistoryId(flowId, historyId);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param status
	 * @return
	 */
	public List<HistoryNodeEntity> selectNodeListByStatus(String flowId, String historyId, int status) {

		return nodeHistoryMapper.selectNodeListByStatus(flowId, historyId, status);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param status
	 * @param statusDetail
	 * @return
	 */
	public List<HistoryNodeEntity> selectNodeListByStatusDetail(String flowId, String historyId, int status,
			int statusDetail) {

		return nodeHistoryMapper.selectNodeListByStatusDetail(flowId, historyId, status, statusDetail);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param status
	 */
	public void updateStatusByHistoryId(String flowId, String historyId, int status) {

		nodeHistoryMapper.updateStatusByHistoryId(status, flowId, historyId, AuthHolder.getUser().email);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param nodeId
	 * @param status
	 */
	public void updateStatusByNodeId(String flowId, String historyId, String nodeId, int status) {

		try {
			WebSocketManager.getInstance().notifyFlowUpdate(flowId, historyId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nodeHistoryMapper.updateStatusByNodeId(status, flowId, historyId, nodeId, AuthHolder.getUser().email);
	}

	/**
	 * 
	 * @param flowId
	 * @param instanceId
	 * @param nodeId
	 * @param status
	 * @param detailStatus
	 */
	public void updateStatusDetailByNodeId(String flowId, String instanceId, String nodeId, int status,
			int detailStatus) {

		try {
			WebSocketManager.getInstance().notifyFlowUpdate(flowId, instanceId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		nodeHistoryMapper.updateStatusDetailByNodeId(status, detailStatus, flowId, instanceId, nodeId,
				AuthHolder.getUser().email);
	}

	/**
	 * 
	 * @param flowId
	 */
	public void deleteByFlowId(String flowId) {

		nodeHistoryMapper.deleteByFlowId(flowId);
	}

}
