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

import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeRolesEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface PublishNodeRolesMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM publish_node_roles " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and node_id = #{param2}")
	// @formatter:on
	public List<PublishNodeRolesEntity> selectList(String flowId, String nodeId);

	// @formatter:off
	@Delete("DELETE FROM publish_node_roles " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ "")
	// @formatter:on
	public int deleteByFlowId(String flowId);
}
