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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.NodeType;
import io.github.nobuglady.jobflow.persistance.db.mapper.FlowMapper;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeRolesMapper;
import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeRolesEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.util.DataUtil;
import io.github.nobuglady.jobflow.util.StringUtil;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.NodeMapper;

/**
 * Node table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class NodeDao {

	@Autowired
	private NodeMapper nodeMapper;

	@Autowired
	private FlowMapper flowMapper;

	@Autowired
	private NodeRolesMapper nodeRolesMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
	public NodeEntity selectByKey(String flowId, String nodeId) {

		return nodeMapper.selectByKey(flowId, nodeId);
	}

	/**
	 * 
	 * @param entity
	 */
	public void save(NodeEntity entity) {
		nodeMapper.save(entity);
	}

	/**
	 * 
	 * @param entity
	 */
	public void update(NodeEntity entity) {
		nodeMapper.update(entity);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public List<NodeEntity> selectByFlowId(String flowId) {
		return nodeMapper.selectByFlowId(flowId);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public List<NodeEntity> selectStartByFlowId(String flowId) {
		return nodeMapper.selectStartByFlowId(flowId, NodeType.NODE_TYPE_START);
	}

	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param nodeType
	 * @param cron
	 * @param startType
	 * @param executeType
	 * @param nodeRefName
	 * @param nodeName
	 * @param layoutX
	 * @param layoutY
	 * @param roles
	 */
	public void saveOrUpdateNode(String flowId, String nodeId, String nodeType, String cron, String startType,
			String executeType, String nodeRefName, String nodeName, String layoutX, String layoutY, String roles) {

		NodeEntity nodeEntity = nodeMapper.selectByKey(flowId, nodeId);

		/*
		 * save or update nodes
		 */
		if (nodeEntity != null) {
			nodeEntity.setFlowId(flowId);
			nodeEntity.setNodeId(nodeId);
			if (StringUtil.isNotEmpty(nodeType)) {
				nodeEntity.setNodeType(Integer.valueOf(nodeType));
			}
			nodeEntity.setStartCron(cron);
			if (StringUtil.isNotEmpty(startType)) {
				nodeEntity.setStartType(Integer.valueOf(startType));
			}
			if (StringUtil.isNotEmpty(executeType)) {
				nodeEntity.setExecuteType(Integer.valueOf(executeType));
			}
			nodeEntity.setRefName(nodeRefName);
			nodeEntity.setNodeName(nodeName);
			nodeEntity.setLayoutX(layoutX);
			nodeEntity.setLayoutY(layoutY);

			nodeEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeMapper.update(nodeEntity);
		} else {

			nodeEntity = new NodeEntity();
			nodeEntity.setFlowId(flowId);
			nodeEntity.setNodeId(nodeId);
			nodeEntity.setNodeType(NodeType.NODE_TYPE_NOMARL);
			nodeEntity.setStartCron(cron);
			if (StringUtil.isNotEmpty(startType)) {
				nodeEntity.setStartType(Integer.valueOf(startType));
			}
			if (StringUtil.isNotEmpty(executeType)) {
				nodeEntity.setExecuteType(Integer.valueOf(executeType));
			}
			nodeEntity.setRefName(nodeRefName);
			nodeEntity.setNodeName(nodeName);
			nodeEntity.setLayoutX(layoutX);
			nodeEntity.setLayoutY(layoutY);

			nodeEntity.setCreateUser(AuthHolder.getUser().email);
			nodeEntity.setUpdateUser(AuthHolder.getUser().email);
			nodeMapper.save(nodeEntity);
		}

		/*
		 * update flow
		 */
		if (DataUtil.getNodeType(nodeEntity.getNodeType()) == NodeType.NODE_TYPE_START) {
			FlowEntity flowEntity = flowMapper.selectByKey(nodeEntity.getFlowId());
			flowEntity.setUpdateUser(AuthHolder.getUser().email);
			flowMapper.update(flowEntity);
		}

		/*
		 * update roles
		 */
		nodeRolesMapper.deleteRoles(flowId, nodeId);
		if (roles != null && !"".equals(roles)) {
			String[] rolesArray = roles.split(",");
			for (int i = 0; i < rolesArray.length; i++) {
				NodeRolesEntity entity = new NodeRolesEntity();
				entity.setFlowId(flowId);
				entity.setNodeId(nodeId);
				entity.setRolesId(rolesArray[i]);
				entity.setCreateUser(AuthHolder.getUser().email);
				entity.setUpdateUser(AuthHolder.getUser().email);

				nodeRolesMapper.save(entity);
			}
		}
	}

	/**
	 * 
	 * @param flowId
	 * @param nodeIdList
	 */
	public void deleteNodesNotInKeys(String flowId, List<String> nodeIdList) {

		String nodeIdArray = String.join("','", nodeIdList);

		String sql = "delete from node " + " where " + " flow_id = '" + flowId + "'  " + " and node_id not in ('"
				+ nodeIdArray + "') ";

		jdbcTemplate.execute(sql);

	}

}
