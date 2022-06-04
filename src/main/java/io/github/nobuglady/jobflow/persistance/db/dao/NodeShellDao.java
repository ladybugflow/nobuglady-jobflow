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

import io.github.nobuglady.jobflow.constant.NodeTimeOutType;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeShellEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeShellMapper;

/**
 * Node table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class NodeShellDao {

	@Autowired
	private NodeShellMapper nodeShellMapper;
	
	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
	public NodeShellEntity selectByKey(String flowId, String nodeId) {
		
        return nodeShellMapper.selectByKey(flowId, nodeId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(NodeShellEntity entity) {
		nodeShellMapper.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	public void update(NodeShellEntity entity) {
		nodeShellMapper.update(entity);
	}
	
	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param successCode
	 * @param errorType
	 * @param syncFlag
	 * @param shellLocation
	 */
	public void saveOrUpdateNode(
			String flowId, 
			String nodeId, 
			String successCode, 
			String errorType, 
			String syncFlag,
			String shellLocation) {

		NodeShellEntity nodeShellEntity = nodeShellMapper.selectByKey(flowId, nodeId);
		
		/*
		 * save or update nodes
		 */
		if(nodeShellEntity != null) {
			nodeShellEntity.setFlowId(flowId);
			nodeShellEntity.setNodeId(nodeId);
			nodeShellEntity.setSuccessCode(successCode);
			nodeShellEntity.setErrorType(Integer.valueOf(errorType));
			nodeShellEntity.setSyncFlag(Integer.valueOf(syncFlag));
			nodeShellEntity.setShellLocation(shellLocation);
			nodeShellEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeShellMapper.update(nodeShellEntity);
		} else {

			nodeShellEntity = new NodeShellEntity();
			nodeShellEntity.setFlowId(flowId);
			nodeShellEntity.setNodeId(nodeId);
			nodeShellEntity.setTimeoutType(NodeTimeOutType.NODE_TIMEOUT_TYPE_STOP);
			nodeShellEntity.setSuccessCode(successCode);
			nodeShellEntity.setErrorType(Integer.valueOf(errorType));
			nodeShellEntity.setSyncFlag(Integer.valueOf(syncFlag));
			nodeShellEntity.setShellLocation(shellLocation);
			nodeShellEntity.setCreateUser(AuthHolder.getUser().email);
			nodeShellEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeShellMapper.save(nodeShellEntity);
		}
		
	}

	
}
