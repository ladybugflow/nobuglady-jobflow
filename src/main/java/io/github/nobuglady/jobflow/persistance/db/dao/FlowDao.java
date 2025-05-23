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

import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowCatagoryEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.EdgeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.FlowMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeHttpMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeRolesMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeShellMapper;

/**
 * Flow table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class FlowDao {

	@Autowired
	private FlowMapper flowMapper;

	@Autowired
	private EdgeMapper edgeMapper;

	@Autowired
	private NodeHttpMapper nodeHttpMapper;

	@Autowired
	private NodeMapper nodeMapper;

	@Autowired
	private NodeRolesMapper nodeRolesMapper;

	@Autowired
	private NodeShellMapper nodeShellMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public FlowEntity selectByKey(String flowId) {
		return flowMapper.selectByKey(flowId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(FlowEntity entity) {
		flowMapper.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	public void update(FlowEntity entity) {
		flowMapper.update(entity);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @return
	 */
	public int selectFlowCatagoryListCount() {

		return flowMapper.selectFlowCatagoryListCount();
	}

	/**
	 * 
	 * @param from
	 * @param fetchCount
	 * @return
	 */
	public List<FlowCatagoryEntity> selectFlowCatagoryList(int from, int fetchCount) {

		return flowMapper.selectFlowCatagoryList(from, fetchCount);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public FlowEntity publishFlow(String flowId) {
		flowMapper.deletePublishFlow(flowId);
		flowMapper.deletePublishNode(flowId);
		flowMapper.deletePublishNodeHttp(flowId);
		flowMapper.deletePublishNodeShell(flowId);
		flowMapper.deletePublishRoles(flowId);
		flowMapper.deletePublishEdge(flowId);

		flowMapper.createPublishFlow(flowId, AuthHolder.getUser().email);
		flowMapper.createPublishNode(flowId, AuthHolder.getUser().email);
		flowMapper.createPublishNodeHttp(flowId, AuthHolder.getUser().email);
		flowMapper.createPublishNodeShell(flowId, AuthHolder.getUser().email);
		flowMapper.createPublishRoles(flowId, AuthHolder.getUser().email);
		flowMapper.createPublishEdge(flowId, AuthHolder.getUser().email);

		FlowEntity flowEntity = flowMapper.selectByKey(flowId);
		flowEntity.setPublishTime(new Date());
		flowEntity.setUpdateUser(AuthHolder.getUser().getEmail());
		flowMapper.update(flowEntity);

		return flowEntity;
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public void deleteAllByKey(String flowId) {

		flowMapper.deleteByKey(flowId);
		edgeMapper.deleteByFlowId(flowId);
		nodeHttpMapper.deleteByFlowId(flowId);
		nodeMapper.deleteByFlowId(flowId);
		nodeRolesMapper.deleteByFlowId(flowId);
		nodeShellMapper.deleteByFlowId(flowId);
	}
}
