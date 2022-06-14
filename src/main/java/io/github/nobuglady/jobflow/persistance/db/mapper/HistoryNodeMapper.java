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
package io.github.nobuglady.jobflow.persistance.db.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface HistoryNodeMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM history_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and node_id = #{param2}"
			+ " and history_id = #{param3}")
	// @formatter:on
	public HistoryNodeEntity selectByKey(String flowId, String nodeId, String historyId);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM history_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and history_id = #{param2}")
	// @formatter:on
	public List<HistoryNodeEntity> selectByFlowHistoryId(String flowId, String historyId);

	// @formatter:off
	@Select("SELECT * FROM history_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and history_id = #{param2}"
			+ " and node_status = #{param3}")
	// @formatter:on
	public List<HistoryNodeEntity> selectNodeListByStatus(String flowId, String historyId, int status);

	// @formatter:off
	@Select("SELECT * FROM history_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and history_id = #{param2}"
			+ " and node_status = #{param3}" 
			+ " and node_status_detail = #{param4}")
	// @formatter:on
	public List<HistoryNodeEntity> selectNodeListByStatusDetail(String flowId, String historyId, int status,
			int statusDetail);

	// @formatter:off
	@Update("update history_node " 
			+ "  set node_status = #{param1}, " 
			+ "  update_user = #{param4}, "
			+ "  update_time = now() " 
			+ " where" 
			+ " flow_id = #{param2} " 
			+ " and history_id = #{param3}")
	// @formatter:on
	public void updateStatusByHistoryId(int status, String flowId, String historyId, String userId);

	// @formatter:off
	@Update("update history_node " 
			+ "  set node_status = #{param1}, " 
			+ "  update_user = #{param5}, "
			+ "  update_time = now() " 
			+ " where" 
			+ " flow_id = #{param2} " 
			+ " and history_id = #{param3} "
			+ " and node_id = #{param4} ")
	// @formatter:on
	public void updateStatusByNodeId(int status, String flowId, String historyId, String nodeId, String userId);

	// @formatter:off
	@Update("update history_node " 
			+ "  set node_status = #{param1}, " 
			+ "  node_status_detail = #{param2}, "
			+ "  update_user = #{param6}, " 
			+ "  update_time = now() " 
			+ " where" 
			+ " flow_id = #{param3} "
			+ " and history_id = #{param4} " 
			+ " and node_id = #{param5} ")
	// @formatter:on
	public void updateStatusDetailByNodeId(int status, int detailStatus, String flowId, String historyId, String nodeId,
			String userId);

	// @formatter:off
	@Update("delete from history_node " 
			+ " where" 
			+ " node_id in "
			+ " ( select node_id from node where flow_id = #{param1})")
	// @formatter:on
	public void deleteByFlowId(String flowId);

	// @formatter:off
	@Delete("DELETE FROM history_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and history_id = #{param2}")
	// @formatter:on
	public int deleteByFlowHistoryId(String flowId, String historyId);
}
