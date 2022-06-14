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

import io.github.nobuglady.jobflow.persistance.db.entity.NodeEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface NodeMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM node " 
			+ " WHERE" 
			+ " flow_id = #{param1}" 
			+ " and node_id = #{param2}")
	// @formatter:on
	public NodeEntity selectByKey(String flowId, String nodeId);

	// @formatter:off
	@Insert("insert into node" 
			+ " (" 
			+ " flow_id, " 
			+ " node_id, " 
			+ " node_name, " 
			+ " ref_name, " 
			+ " node_type, "
			+ " start_type, " 
			+ " execute_type, " 
			+ " start_cron, " 
			+ " sub_flow_id, " 
			+ " sub_node_id, "
			+ " layout_x, " 
			+ " layout_y, " 
			+ " skip_flag, " 
			+ " skip_value, " 
			+ " disable_flag, " 
			+ " create_user, "
			+ " update_user, " 
			+ " create_time, " 
			+ " update_time " 
			+ " )" 
			+ " values" 
			+ " (" 
			+ "#{flowId},"
			+ "#{nodeId}," 
			+ "#{nodeName}," 
			+ "#{refName}," 
			+ "#{nodeType}," 
			+ "#{startType}," 
			+ "#{executeType},"
			+ "#{startCron}," 
			+ "#{subFlowId}," 
			+ "#{subNodeId}," 
			+ "#{layoutX}," 
			+ "#{layoutY}," 
			+ "#{skipFlag},"
			+ "#{skipValue}," 
			+ "#{disableFlag}," 
			+ "#{createUser}," 
			+ "#{updateUser}," 
			+ "now()," 
			+ "now()" 
			+ " ) ")
	// @formatter:on
	public void save(NodeEntity entity);

	// @formatter:off
	@Update("update node " 
			+ " set" 
			+ " flow_id=#{flowId}" 
			+ " ,node_id=#{nodeId}" 
			+ " ,node_name=#{nodeName}"
			+ " ,ref_name=#{nodeName}" 
			+ " ,node_type=#{nodeType}" 
			+ " ,start_type=#{startType}"
			+ " ,execute_type=#{executeType}" 
			+ " ,start_cron=#{startCron}" 
			+ " ,sub_flow_id=#{subFlowId}"
			+ " ,sub_node_id=#{subNodeId}" 
			+ " ,layout_x=#{layoutX}" 
			+ " ,layout_y=#{layoutY}"
			+ " ,skip_flag=#{skipFlag}" 
			+ " ,skip_value=#{skipValue}" 
			+ " ,disable_flag=#{disableFlag}"
			+ " ,update_user=#{updateUser}" 
			+ " ,update_time=now()" 
			+ " where" 
			+ " flow_id=#{flowId}"
			+ " and node_id=#{nodeId}")
	// @formatter:on
	public void update(NodeEntity entity);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM node " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public List<NodeEntity> selectByFlowId(String flowId);

	// @formatter:off
	@Select("SELECT * FROM node " 
			+ " where flow_id = #{param1} " 
			+ " and node_type = #{param2}")
	// @formatter:on
	public List<NodeEntity> selectStartByFlowId(String flowIds, int nodeType);

	// @formatter:off
	@Delete("DELETE FROM node " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public int deleteByFlowId(String flowId);

}
