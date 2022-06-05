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

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.FlowStatus;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryFlowMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishFlowMapper;
import io.github.nobuglady.jobflow.security.AuthHolder;

/**
 * FlowHistory table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryFlowDao {

	@Autowired
	private HistoryFlowMapper flowHistoryMapper;

	@Autowired
	private PublishFlowMapper publishFlowMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @return
	 */
	public HistoryFlowEntity selectByKey(String flowId, String historyId) {

		return flowHistoryMapper.selectByKey(flowId, historyId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(HistoryFlowEntity entity) {

		flowHistoryMapper.save(entity);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param from
	 * @param fetchCount
	 * @return
	 */
	public List<HistoryFlowEntity> selectAll(int from, int fetchCount) {

		return flowHistoryMapper.selectAll(from, fetchCount);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectAllError() {

		return flowHistoryMapper.selectAllError(FlowStatus.COMPLETE);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectTodayComplete() {

		return flowHistoryMapper.selectTodayComplete$CustomFunction(FlowStatus.COMPLETE);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectRunningFlow() {

		return flowHistoryMapper.selectRunningFlow(FlowStatus.READY);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param status
	 */
	public void updateFlowStatus(String flowId, String historyId, int status) {

		flowHistoryMapper.updateStatus(flowId, historyId, status, AuthHolder.getUser().email);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public String createHistory(String flowId) {

		PublishFlowEntity flowEntity = publishFlowMapper.selectByKey(flowId);

		HistoryFlowEntity flowHistoryEntity = new HistoryFlowEntity();
		flowHistoryEntity.setFlowId(flowId);
		flowHistoryEntity.setCategoryId(flowEntity.getCategoryId());
		flowHistoryEntity.setFlowName(flowEntity.getFlowName());
		flowHistoryEntity.setDisableFlag(flowEntity.getDisableFlag());
		flowHistoryEntity.setFlowDesc(flowEntity.getFlowDesc());
		flowHistoryEntity.setStartTime(new Date());
		flowHistoryEntity.setCreateUser(AuthHolder.getUser().email);
		flowHistoryEntity.setUpdateUser(AuthHolder.getUser().email);
		saveWithId(flowHistoryEntity);

		String historyId = flowHistoryEntity.getHistoryId();

		/*
		 * insert history_node
		 */
		flowHistoryMapper.createHistoryNode(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_http
		 */
		flowHistoryMapper.createHistoryNodeHttp(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_shell
		 */
		flowHistoryMapper.createHistoryNodeShell(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_roles
		 */
		flowHistoryMapper.createHistoryNodeRoles(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_edge
		 */
		flowHistoryMapper.createHistoryEdge(flowId, historyId, AuthHolder.getUser().email);

		return historyId;

	}

	/**
	 * 
	 * @param flowHistoryEntity
	 */
	private synchronized void saveWithId(HistoryFlowEntity flowHistoryEntity) {

		int maxHistoryId = flowHistoryMapper.selectMaxHistoryId$CustomFunction();

		maxHistoryId++;
		flowHistoryEntity.setHistoryId(String.valueOf(maxHistoryId));

		flowHistoryMapper.save(flowHistoryEntity);
	}
}
