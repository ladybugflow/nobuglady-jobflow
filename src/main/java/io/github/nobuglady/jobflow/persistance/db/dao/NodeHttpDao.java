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

import io.github.nobuglady.jobflow.constant.NodeTimeOutType;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeHttpMapper;

/**
 * Node table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class NodeHttpDao {

	@Autowired
	private NodeHttpMapper nodeHttpMapper;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
	public NodeHttpEntity selectByKey(String flowId, String nodeId) {

		return nodeHttpMapper.selectByKey(flowId, nodeId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(NodeHttpEntity entity) {
		nodeHttpMapper.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	public void update(NodeHttpEntity entity) {
		nodeHttpMapper.update(entity);
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
	 * @param location
	 * @param httpMethod
	 * @param httpContentType
	 * @param httpHeader
	 * @param httpBody
	 */
	public void saveOrUpdateNode(String flowId, String nodeId, String successCode, String errorType, String syncFlag,
			String location, String httpMethod, String httpContentType, String httpHeader, String httpBody) {

		NodeHttpEntity nodeHttpEntity = nodeHttpMapper.selectByKey(flowId, nodeId);

		/*
		 * save or update nodes
		 */
		if (nodeHttpEntity != null) {
			nodeHttpEntity.setFlowId(flowId);
			nodeHttpEntity.setNodeId(nodeId);
			nodeHttpEntity.setSuccessCode(successCode);
			nodeHttpEntity.setErrorType(Integer.valueOf(errorType));
			nodeHttpEntity.setSyncFlag(Integer.valueOf(syncFlag));
			nodeHttpEntity.setHttpUrl(location);
			nodeHttpEntity.setHttpMethod(httpMethod);
			nodeHttpEntity.setHttpContentType(httpContentType);
			nodeHttpEntity.setHttpHeader(httpHeader);
			nodeHttpEntity.setHttpBody(httpBody);

			nodeHttpEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeHttpMapper.update(nodeHttpEntity);
		} else {

			nodeHttpEntity = new NodeHttpEntity();
			nodeHttpEntity.setFlowId(flowId);
			nodeHttpEntity.setNodeId(nodeId);
			nodeHttpEntity.setTimeoutType(NodeTimeOutType.NODE_TIMEOUT_TYPE_STOP);
			nodeHttpEntity.setSuccessCode(successCode);
			nodeHttpEntity.setErrorType(Integer.valueOf(errorType));
			nodeHttpEntity.setSyncFlag(Integer.valueOf(syncFlag));
			nodeHttpEntity.setHttpUrl(location);
			nodeHttpEntity.setHttpMethod(httpMethod);
			nodeHttpEntity.setHttpContentType(httpContentType);
			nodeHttpEntity.setHttpHeader(httpHeader);
			nodeHttpEntity.setHttpBody(httpBody);
			nodeHttpEntity.setCreateUser(AuthHolder.getUser().email);
			nodeHttpEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeHttpMapper.save(nodeHttpEntity);
		}

	}

}
