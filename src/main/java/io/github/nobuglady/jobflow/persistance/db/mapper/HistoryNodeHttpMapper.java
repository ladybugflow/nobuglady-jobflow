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

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface HistoryNodeHttpMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM history_node_http " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and node_id = #{param2}"
			+ " and history_id = #{param3}")
	// @formatter:on
	public HistoryNodeHttpEntity selectByKey(String flowId, String nodeId, String historyId);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Update("update history_node_http " 
			+ "  set http_request = #{param4} , " 
			+ "  update_user = #{param5}, "
			+ "  update_time = now() " 
			+ " where" 
			+ " flow_id = #{param1} " 
			+ " and history_id = #{param2} "
			+ " and node_id = #{param3} ")
	// @formatter:on
	public void updateNodehitoryRequest(String flowId, String historyId, String nodeId, String httpRequest,
			String userId);

	// @formatter:off
	@Update("update history_node_http " 
			+ "  set http_res_status = #{param4}, " 
			+ " http_response = #{param5}, "
			+ "  update_user = #{param6}, " 
			+ "  update_time = now() " 
			+ " where" 
			+ " flow_id = #{param1} "
			+ " and history_id = #{param2} " 
			+ " and node_id = #{param3} ")
	// @formatter:on
	public void updateNodehitoryReponse(String flowId, String historyId, String nodeId, String httpResStatus,
			String httpResponse, String userId);

	// @formatter:off
	@Delete("DELETE FROM history_node_http " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and history_id = #{param2}")
	// @formatter:on
	public int deleteByFlowHistoryId(String flowId, String historyId);

}
