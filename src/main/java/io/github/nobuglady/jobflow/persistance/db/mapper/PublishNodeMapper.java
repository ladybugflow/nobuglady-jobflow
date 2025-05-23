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

import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface PublishNodeMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM publish_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and node_id = #{param2}")
	// @formatter:on
	public PublishNodeEntity selectByKey(String flowId, String nodeId);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM publish_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public List<PublishNodeEntity> selectByFlowId(String flowId);

	// @formatter:off
	@Select("SELECT * FROM publish_node " 
			+ " where flow_id = #{param1} " 
			+ " and node_type = #{param2}")
	// @formatter:on
	public List<PublishNodeEntity> selectStartByFlowId(String flowIds, int nodeType);

	// @formatter:off
	@Select("SELECT * FROM publish_node " 
			+ " where start_type = #{param1}")
	// @formatter:on
	public List<PublishNodeEntity> selectAllCronNode(int nodeStartType);

	// @formatter:off
	@Delete("DELETE FROM publish_node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ "")
	// @formatter:on
	public int deleteByFlowId(String flowId);
}
