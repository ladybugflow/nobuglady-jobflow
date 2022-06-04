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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.nobuglady.jobflow.persistance.db.entity.NodeRolesEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface NodeRolesMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	@Insert("insert into node_roles"
			+ " ("
			+ "flow_id,"
			+ "node_id,"
			+ "roles_id,"
	    	+ "create_user, "
	    	+ "update_user, "
			+ "create_time,"
			+ "update_time"
			+ " )"
			+ " values"
			+ " ("
			+ "#{flowId},"
			+ "#{nodeId},"
			+ "#{rolesId},"
			+ "#{createUser},"
			+ "#{updateUser},"
			+ "now(),"
			+ "now()"
			+ " ) ")
	public void save(NodeRolesEntity entity);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	@Select("SELECT * FROM node_roles "
			+ " WHERE"
			+ " flow_id = #{param1}"
			+ " and node_id = #{param2}")
	public List<NodeRolesEntity> selectList(String flowId, String nodeId);

	@Update("delete FROM node_roles "
			+ " WHERE"
			+ " flow_id = #{param1}"
			+ " and node_id = #{param2}")
	public void deleteRoles(String flowId, String nodeId);


}
