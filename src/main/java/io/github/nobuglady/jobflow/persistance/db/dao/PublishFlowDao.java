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

import io.github.nobuglady.jobflow.persistance.db.entity.PublishFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowPublishCatagoryEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishEdgeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishFlowMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishNodeHttpMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishNodeMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishNodeRolesMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.PublishNodeShellMapper;

/**
 * Flow table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class PublishFlowDao {

	@Autowired
	private PublishFlowMapper publishFlowMapper;

	@Autowired
	private PublishEdgeMapper publishEdgeMapper;

	@Autowired
	private PublishNodeHttpMapper publishNodeHttpMapper;

	@Autowired
	private PublishNodeMapper publishNodeMapper;

	@Autowired
	private PublishNodeRolesMapper publishNodeRolesMapper;

	@Autowired
	private PublishNodeShellMapper publishNodeShellMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public PublishFlowEntity selectByKey(String flowId) {
		return publishFlowMapper.selectByKey(flowId);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @return
	 */
	public int selectFlowPublishCatagoryListCount() {

		return publishFlowMapper.selectFlowPublishCatagoryListCount();
	}

	/**
	 * 
	 * @param from
	 * @param fetchCount
	 * @return
	 */
	public List<FlowPublishCatagoryEntity> selectFlowPublishCatagoryList(int from, int fetchCount) {

		return publishFlowMapper.selectFlowPublishCatagoryList(from, fetchCount);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public void deleteAllByKey(String flowId) {

		publishFlowMapper.deleteByKey(flowId);
		publishEdgeMapper.deleteByFlowId(flowId);
		publishNodeHttpMapper.deleteByFlowId(flowId);
		publishNodeMapper.deleteByFlowId(flowId);
		publishNodeRolesMapper.deleteByFlowId(flowId);
		publishNodeShellMapper.deleteByFlowId(flowId);
	}

}
