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
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import io.github.nobuglady.jobflow.persistance.db.entity.EdgeEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface EdgeMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	@Select("SELECT * FROM edge " + " WHERE" + " flow_id = #{param1}" + " and edge_id = #{param2}")
	public EdgeEntity selectByKey(String flowId, String edgeId);

	@Insert("insert into edge" + " (" + "flow_id," + "edge_id," + "from_node_id," + "to_node_id," + "edge_condition,"
			+ "edge_exception_return_type," + "skip_flag," + "skip_value," + "disable_flag," + "create_user,"
			+ "update_user," + "create_time," + "update_time" + " )" + " values" + " (" + "#{flowId}," + "#{edgeId},"
			+ "#{fromNodeId}," + "#{toNodeId}," + "#{edgeCondition}," + "#{edgeExceptionReturnType}," + "#{skipFlag},"
			+ "#{skipValue}," + "#{disableFlag}," + "#{createUser}," + "#{updateUser}," + "now()," + "now()" + " ) ")
	public void save(EdgeEntity entity);

	@Update("update edge " + " set" + " flow_id=#{flowId}" + " ,edge_id=#{edgeId}" + " ,from_node_id=#{fromNodeId}"
			+ " ,to_node_id=#{toNodeId}" + " ,edge_condition=#{edgeCondition}"
			+ " ,edge_exception_return_type=#{edgeExceptionReturnType}" + " ,skip_flag=#{skipFlag}"
			+ " ,skip_value=#{skipValue}" + " ,disable_flag=#{disableFlag}" + " ,update_user=#{updateUser}"
			+ " ,update_time=now()" + " where" + " flow_id=#{flowId}" + " and from_node_id=#{fromNodeId}"
			+ " and to_node_id=#{toNodeId}")
	public void update(EdgeEntity entity);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	@Select("SELECT * FROM edge " + " WHERE" + " flow_id = #{param1}")
	public List<EdgeEntity> selectByFlowId(String flowId);

	@Delete("DELETE FROM edge " + " WHERE" + " flow_id = #{param1}" + "")
	public int deleteByFlowId(String flowId);

}
