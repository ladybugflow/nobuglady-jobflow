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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeHttpMapper;
import io.github.nobuglady.jobflow.security.AuthHolder;

/**
 * NodeHistory table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryNodeHttpDao {

	@Autowired
	private HistoryNodeHttpMapper historyNodeHttpMapper;

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
	public HistoryNodeHttpEntity selectByKey(String flowId, String nodeId, String historyId) {

		return historyNodeHttpMapper.selectByKey(flowId, nodeId, historyId);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param nodeId
	 * @param httpRequest
	 */
	public void updateNodehitoryRequest(String flowId, String historyId, String nodeId, String httpRequest) {

		historyNodeHttpMapper.updateNodehitoryRequest(flowId, historyId, nodeId, httpRequest,
				AuthHolder.getUser().email);
	}

	/**
	 * 
	 * @param flowId
	 * @param instanceId
	 * @param nodeId
	 * @param httpResStatus
	 * @param httpResponse
	 */
	public void updateNodehitoryReponse(String flowId, String instanceId, String nodeId, String httpResStatus,
			String httpResponse) {

		historyNodeHttpMapper.updateNodehitoryReponse(flowId, instanceId, nodeId, httpResStatus, httpResponse,
				AuthHolder.getUser().email);
	}
}
