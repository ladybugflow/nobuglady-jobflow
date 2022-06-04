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

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.nobuglady.jobflow.persistance.db.entity.NodeShellEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface NodeShellMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	@Select("SELECT * FROM node_shell " + " WHERE" + " flow_id = #{param1}" + " and node_id = #{param2}")
	public NodeShellEntity selectByKey(String flowId, String nodeId);

	@Insert("insert into node_shell" + " (" + " flow_id, " + " node_id, " + " timeout, " + " timeout_type, "
			+ " success_code, " + " error_type, " + " sync_flag, " + " shell_location, " + " shell_param, "
			+ " create_user, " + " update_user, " + " create_time, " + " update_time " + " )" + " values" + " ("
			+ "#{flowId}," + "#{nodeId}," + "#{timeout}," + "#{timeoutType}," + "#{successCode}," + "#{errorType},"
			+ "#{syncFlag}," + "#{shellLocation}," + "#{shellParam}," + "#{createUser}," + "#{updateUser}," + "now(),"
			+ "now()" + " ) ")
	public void save(NodeShellEntity entity);

	@Update("update node_shell " + " set" + " flow_id=#{flowId}" + " ,node_id=#{nodeId}" + " ,timeout=#{timeout}"
			+ " ,timeout_type=#{timeoutType}" + " ,success_code=#{successCode}" + " ,error_type=#{errorType}"
			+ " ,sync_flag=#{syncFlag}" + " ,shell_location=#{shellLocation}" + " ,shell_param=#{shellParam}"
			+ " ,update_user=#{updateUser}" + " ,update_time=now()" + " where" + " flow_id=#{flowId}"
			+ " and node_id=#{nodeId}")
	public void update(NodeShellEntity entity);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
}
