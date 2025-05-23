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

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryEdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryEdgeMapper;

/**
 * NodeHistory table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryEdgeDao {

	@Autowired
	private HistoryEdgeMapper edgeHistoryMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @return
	 */
	public List<HistoryEdgeEntity> selectByFlowHistoryId(String flowId, String historyId) {

		return edgeHistoryMapper.selectByFlowHistoryId(flowId, historyId);
	}

}
