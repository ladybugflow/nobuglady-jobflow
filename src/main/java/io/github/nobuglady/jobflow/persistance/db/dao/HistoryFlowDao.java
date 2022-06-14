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
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.FlowStatus;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryEdgeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryFlowMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeHttpMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.HistoryNodeShellMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishFlowMapper;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * FlowHistory table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class HistoryFlowDao {

	@Autowired
	private HistoryFlowMapper historyFlowMapper;

	@Autowired
	private PublishFlowMapper publishFlowMapper;

	@Autowired
	private HistoryEdgeMapper historyEdgeMapper;

	@Autowired
	private HistoryNodeHttpMapper historyNodeHttpMapper;

	@Autowired
	private HistoryNodeMapper historyNodeMapper;

//	@Autowired
//	private HistoryNodeRolesMapper historyNodeRolesMapper;

	@Autowired
	private HistoryNodeShellMapper historyNodeShellMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

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

		return historyFlowMapper.selectByKey(flowId, historyId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(HistoryFlowEntity entity) {

		historyFlowMapper.save(entity);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////

	/**
	 * 
	 * @param flowName
	 * @param flowStatus
	 * @param flowStartDate
	 * @return
	 */
	public int selectAllCount(String flowName, String flowStatus, String flowStartDate) {

		String sql = "select count(1) from (SELECT * FROM history_flow" + " where 1=1 ";

		if (StringUtil.isNotEmpty(flowName)) {
			sql += " and flow_name like '%" + flowName + "%' ";
		}

		if (StringUtil.isNotEmpty(flowStatus)) {
			sql += " and flow_status = " + flowStatus + " ";
		}

		if (StringUtil.isNotEmpty(flowStartDate)) {
			sql += " and DATE_FORMAT(start_time,'%Y/%m/%d') = '" + flowStartDate + "' ";
		}

		sql += " order by start_time desc) t1 ";

		return jdbcTemplate.queryForObject(sql, Integer.class);
//		return flowHistoryMapper.selectAll(from, fetchCount);
	}

	/**
	 * 
	 * @param from
	 * @param fetchCount
	 * @param flowName
	 * @param flowStatus
	 * @param flowStartDate
	 * @return
	 */
	public List<HistoryFlowEntity> selectAll(int from, int fetchCount, String flowName, String flowStatus,
			String flowStartDate) {

		String sql = "select * from (SELECT * FROM history_flow" + " where 1=1 ";

		if (StringUtil.isNotEmpty(flowName)) {
			sql += " and flow_name like '%" + flowName + "%' ";
		}

		if (StringUtil.isNotEmpty(flowStatus)) {
			sql += " and flow_status = " + flowStatus + " ";
		}

		if (StringUtil.isNotEmpty(flowStartDate)) {
			sql += " and DATE_FORMAT(start_time,'%Y/%m/%d') = '" + flowStartDate + "' ";
		}

		sql += " order by start_time desc) t1 order by t1.start_time desc LIMIT #{param1}, #{param2} ";
		sql = sql.replace("#{param1}", String.valueOf(from));
		sql = sql.replace("#{param2}", String.valueOf(fetchCount));

		return jdbcTemplate.query(sql, new BeanPropertyRowMapper<HistoryFlowEntity>(HistoryFlowEntity.class));
//		return flowHistoryMapper.selectAll(from, fetchCount);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectAllError() {

		return historyFlowMapper.selectAllError(FlowStatus.COMPLETE);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectTodayComplete() {

		return historyFlowMapper.selectTodayComplete$CustomFunction(FlowStatus.COMPLETE);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> selectRunningFlow() {

		return historyFlowMapper.selectRunningFlow(FlowStatus.READY);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @param status
	 */
	public void updateFlowStatus(String flowId, String historyId, int status) {

		historyFlowMapper.updateStatus(flowId, historyId, status, AuthHolder.getUser().email);
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
		historyFlowMapper.createHistoryNode(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_http
		 */
		historyFlowMapper.createHistoryNodeHttp(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_shell
		 */
		historyFlowMapper.createHistoryNodeShell(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_node_roles
		 */
		historyFlowMapper.createHistoryNodeRoles(flowId, historyId, AuthHolder.getUser().email);

		/*
		 * insert history_edge
		 */
		historyFlowMapper.createHistoryEdge(flowId, historyId, AuthHolder.getUser().email);

		return historyId;

	}

	/**
	 * 
	 * @param flowHistoryEntity
	 */
	private synchronized void saveWithId(HistoryFlowEntity flowHistoryEntity) {

		int maxHistoryId = historyFlowMapper.selectMaxHistoryId$CustomFunction();

		maxHistoryId++;
		flowHistoryEntity.setHistoryId(String.valueOf(maxHistoryId));

		historyFlowMapper.save(flowHistoryEntity);
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 */
	public void deleteAllByKey(String flowId, String historyId) {

		historyFlowMapper.deleteByKey(flowId, historyId);
		historyEdgeMapper.deleteByFlowHistoryId(flowId, historyId);
		historyNodeHttpMapper.deleteByFlowHistoryId(flowId, historyId);
		historyNodeMapper.deleteByFlowHistoryId(flowId, historyId);
//		historyNodeRolesMapper.deleteByFlowHistoryId(flowId,historyId);
		historyNodeShellMapper.deleteByFlowHistoryId(flowId, historyId);
	}
}
