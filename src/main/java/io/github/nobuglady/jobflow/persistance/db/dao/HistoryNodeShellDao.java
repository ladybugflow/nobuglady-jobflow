/*
 * Copyright (c) 2021-present, NoBugLady-jobflow Contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * Shell://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See
 * the License for the specific language governing permissions and limitations under the License.
 */
package io.github.nobuglady.jobflow.persistance.db.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeShellEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeShellMapper;

/**
 * Node table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryNodeShellDao {

	@Autowired
	private HistoryNodeShellMapper historyNodeShellMapper;

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
	public HistoryNodeShellEntity selectByKey(String flowId, String nodeId, String historyId) {

		return historyNodeShellMapper.selectByKey(flowId, nodeId, historyId);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////

}
