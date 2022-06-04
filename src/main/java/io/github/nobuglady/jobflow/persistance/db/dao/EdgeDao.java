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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.persistance.db.entity.EdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.mapper.EdgeMapper;

/**
 * Edge table operation class
 * 
 * @author NoBugLady
 *
 */
@Component
public class EdgeDao {

	@Autowired
	private EdgeMapper edgeMapper;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param edgeId
	 * @return
	 */
	public EdgeEntity selectByKey(String flowId, String edgeId) {

		return edgeMapper.selectByKey(flowId, edgeId);
	}

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @return
	 */
	public List<EdgeEntity> selectByFlowId(String flowId) {

		return edgeMapper.selectByFlowId(flowId);
	}

	/**
	 * 
	 * @param flowId
	 * @param from_node_id
	 * @param to_node_id
	 * @param condition
	 */
	public void saveOrUpdateEdge(String flowId, String edgeId, String from_node_id, String to_node_id,
			String condition) {

		EdgeEntity edgeEntity = edgeMapper.selectByKey(flowId, edgeId);
		if (edgeEntity != null) {
			edgeEntity.setFlowId(flowId);
			edgeEntity.setEdgeId(edgeId);
			edgeEntity.setFromNodeId(from_node_id);
			edgeEntity.setToNodeId(to_node_id);
			edgeEntity.setEdgeCondition(condition);
			edgeEntity.setUpdateUser(AuthHolder.getUser().email);

			edgeMapper.update(edgeEntity);
		} else {
			edgeEntity = new EdgeEntity();
			edgeEntity.setFlowId(flowId);
			edgeEntity.setEdgeId(edgeId);
			edgeEntity.setFromNodeId(from_node_id);
			edgeEntity.setToNodeId(to_node_id);
			edgeEntity.setEdgeCondition(condition);
			edgeEntity.setCreateUser(AuthHolder.getUser().email);
			edgeEntity.setUpdateUser(AuthHolder.getUser().email);

			edgeMapper.save(edgeEntity);
		}

	}

	/**
	 * 
	 * @param flowId
	 * @param fromToList
	 */
	public void deleteEdgeNotInKeys(String flowId, List<String[]> fromToList) {

		List<String> fromToStrList = new ArrayList<String>();
		for (String[] fromTo : fromToList) {
			fromToStrList.add(fromTo[0] + "," + fromTo[1]);
		}

		String fromToArray = String.join("','", fromToStrList);

		String sql = "delete from edge " + " where " + " flow_id = '" + flowId + "'  "
				+ " and concat(from_node_id,',',to_node_id) not in ('" + fromToArray + "') ";

		jdbcTemplate.execute(sql);
	}

}
