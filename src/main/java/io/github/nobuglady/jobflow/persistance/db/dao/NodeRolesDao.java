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


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.persistance.db.entity.NodeRolesEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeRolesMapper;

/**
 * Node table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class NodeRolesDao {

	@Autowired
	private NodeRolesMapper nodeRolesMapper;
	
	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	
	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
	public List<String> selectRolesIdList(String flowId, String nodeId) {
		
		List<String> resultList = new ArrayList<String>();
		
		List<NodeRolesEntity> nodeRolesEntityList = nodeRolesMapper.selectList(flowId, nodeId);
		
		if (nodeRolesEntityList != null) {
			for(NodeRolesEntity entity:nodeRolesEntityList) {
				resultList.add(entity.getRolesId());
			}
		}
		
		return resultList;
	}
}
