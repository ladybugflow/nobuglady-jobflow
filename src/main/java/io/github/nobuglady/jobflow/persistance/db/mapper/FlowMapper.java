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

import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowCatagoryEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface FlowMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM flow " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public FlowEntity selectByKey(String flowId);

	// @formatter:off
	@Insert("insert into flow" 
			+ " (" 
			+ "flow_id," 
			+ "category_id," 
			+ "flow_name," 
			+ "flow_desc," 
			+ "publish_time,"
			+ "disable_flag," 
			+ "create_user," 
			+ "update_user," 
			+ "create_time," 
			+ "update_time" 
			+ " )" 
			+ " values"
			+ " (" 
			+ "#{flowId}," 
			+ "#{categoryId}," 
			+ "#{flowName}," 
			+ "#{flowDesc}," 
			+ "#{publishTime},"
			+ "#{disableFlag}," 
			+ "#{createUser}," 
			+ "#{updateUser}," 
			+ "now()," 
			+ "now()" 
			+ " ) ")
	// @formatter:on
	public void save(FlowEntity entity);

	// @formatter:off
	@Update("update flow " 
			+ " set" 
			+ " category_id=#{categoryId}" 
			+ " ,flow_name=#{flowName}"
			+ " ,flow_desc=#{flowDesc}" 
			+ " ,publish_time=#{publishTime}" 
			+ " ,disable_flag=#{disableFlag}"
			+ " ,update_user=#{updateUser}" 
			+ " ,update_time=now()" 
			+ " where" 
			+ " flow_id=#{flowId}")
	// @formatter:on
	public void update(FlowEntity entity);

	// @formatter:off
	@Delete("DELETE FROM flow " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public int deleteByKey(String flowId);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("select count(1) from (SELECT * FROM flow " 
			+ " order by update_time desc) t1 ")
	// @formatter:on
	public int selectFlowCatagoryListCount();

	// @formatter:off
	@Select("select * from (SELECT * FROM flow " 
			+ " order by update_time desc) t1 LIMIT #{param1}, #{param2} ")
	// @formatter:on
	public List<FlowCatagoryEntity> selectFlowCatagoryList(int from, int fetchCount);

	// @formatter:off
	@Update("delete from publish_flow where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishFlow(String flowId);

	// @formatter:off
	@Update("delete from publish_node where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishNode(String flowId);

	// @formatter:off
	@Update("delete from publish_node_http where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishNodeHttp(String flowId);

	// @formatter:off
	@Update("delete from publish_node_shell where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishNodeShell(String flowId);

	// @formatter:off
	@Update("delete from publish_node_roles where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishRoles(String flowId);

	// @formatter:off
	@Update("delete from publish_edge where flow_id = #{param1}")
	// @formatter:on
	public void deletePublishEdge(String flowId);

	// @formatter:off
	@Update(" insert into publish_flow( " 
			+ " 		flow_id, " 
			+ " 		category_id, " 
			+ " 		flow_name, "
			+ " 		flow_desc, " 
			+ " 		disable_flag, " 
			+ " 		create_user," 
			+ " 		update_user,"
			+ " 		create_time, " 
			+ " 		update_time " 
			+ " 		) " 
			+ " 		select " 
			+ " 		flow_id, "
			+ " 		category_id, " 
			+ " 		flow_name, " 
			+ " 		flow_desc, " 
			+ " 		disable_flag, "
			+ " 		#{param2}, " 
			+ " 		#{param2}, " 
			+ " 		now(), " 
			+ " 		now() "
			+ " 		from flow where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishFlow(String flowId, String userId);

	// @formatter:off
	@Update(" insert into publish_node( " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		node_name, "
			+ " 		ref_name, " 
			+ " 		node_type, " 
			+ " 		start_type, " 
			+ " 		execute_type, "
			+ " 		start_cron, " 
			+ " 		sub_flow_id, " 
			+ " 		sub_node_id, " 
			+ " 		layout_x, "
			+ " 		layout_y, " 
			+ " 		skip_flag, " 
			+ " 		skip_value, " 
			+ " 		disable_flag, "
			+ " 		create_user, " 
			+ " 		update_user, " 
			+ " 		create_time, " 
			+ " 		update_time "
			+ " 		) " 
			+ " 		select " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		node_name, "
			+ " 		ref_name, " 
			+ " 		node_type, " 
			+ " 		start_type, " 
			+ " 		execute_type, "
			+ " 		start_cron, " 
			+ " 		sub_flow_id, " 
			+ " 		sub_node_id, " 
			+ " 		layout_x, "
			+ " 		layout_y, " 
			+ " 		skip_flag, " 
			+ " 		skip_value, " 
			+ " 		disable_flag, "
			+ " 		#{param2}, " 
			+ " 		#{param2}, " 
			+ " 		create_time, " 
			+ " 		update_time "
			+ " 		from node where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishNode(String flowId, String userId);

	// @formatter:off
	@Update(" insert into publish_node_http( " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		timeout, "
			+ " 		timeout_type, " 
			+ " 		success_code, " 
			+ " 		error_type, " 
			+ " 		sync_flag, "
			+ " 		http_url, " 
			+ " 		http_method, " 
			+ " 		http_header, " 
			+ " 		http_body, "
			+ " 		http_content_type, " 
			+ " 		create_user, " 
			+ " 		update_user, " 
			+ " 		create_time, "
			+ " 		update_time " 
			+ " 		) " 
			+ " 		select " 
			+ " 		flow_id, " 
			+ " 		node_id, "
			+ " 		timeout, " 
			+ " 		timeout_type, " 
			+ " 		success_code, " 
			+ " 		error_type, "
			+ " 		sync_flag, " 
			+ " 		http_url, " 
			+ " 		http_method, " 
			+ " 		http_header, "
			+ " 		http_body, " 
			+ " 		http_content_type, " 
			+ " 		#{param2}, " 
			+ " 		#{param2}, "
			+ " 		create_time, " 
			+ " 		update_time " 
			+ " 		from node_http where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishNodeHttp(String flowId, String userId);

	// @formatter:off
	@Update(" insert into publish_node_shell( " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		timeout, "
			+ " 		timeout_type, " 
			+ " 		success_code, " 
			+ " 		error_type, " 
			+ " 		sync_flag, "
			+ " 		shell_location, " 
			+ " 		shell_param, " 
			+ " 		create_user, " 
			+ " 		update_user, "
			+ " 		create_time, " 
			+ " 		update_time " 
			+ " 		) " 
			+ " 		select " 
			+ " 		flow_id, "
			+ " 		node_id, " 
			+ " 		timeout, " 
			+ " 		timeout_type, " 
			+ " 		success_code, "
			+ " 		error_type, " 
			+ " 		sync_flag, " 
			+ " 		shell_location, " 
			+ " 		shell_param, "
			+ " 		#{param2}, " 
			+ " 		#{param2}, " 
			+ " 		create_time, " 
			+ " 		update_time "
			+ " 		from node_shell where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishNodeShell(String flowId, String userId);

	// @formatter:off
	@Update(" insert into publish_node_roles( " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		roles_id, "
			+ " 		create_user, " 
			+ " 		update_user, " 
			+ " 		create_time, " 
			+ " 		update_time "
			+ " 		) " 
			+ " 		select " 
			+ " 		flow_id, " 
			+ " 		node_id, " 
			+ " 		roles_id, "
			+ " 		#{param2}, " 
			+ " 		#{param2}, " 
			+ " 		create_time, " 
			+ " 		update_time "
			+ " 		from node_roles where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishRoles(String flowId, String userId);

	// @formatter:off
	@Update("insert into publish_edge( " 
			+ "		flow_id, " 
			+ "		edge_id, " 
			+ "		from_node_id, "
			+ "		to_node_id, " 
			+ "		edge_condition, " 
			+ "		edge_exception_return_type, "
			+ " 	skip_flag, " 
			+ " 	skip_value, " 
			+ " 	disable_flag, " 
			+ " 	create_user, "
			+ " 	update_user, " 
			+ "		create_time, " 
			+ "		update_time " 
			+ "		) " 
			+ "		select "
			+ "		flow_id, " 
			+ "		edge_id, " 
			+ "		from_node_id, " 
			+ "		to_node_id, "
			+ "		edge_condition, " 
			+ "		edge_exception_return_type, " 
			+ " 	skip_flag, "
			+ " 	skip_value, " 
			+ " 	disable_flag, " 
			+ " 	#{param2}, " 
			+ " 	#{param2}, "
			+ "		create_time, " 
			+ "		update_time " 
			+ "		from edge where flow_id = #{param1} ")
	// @formatter:on
	public void createPublishEdge(String flowId, String userId);

}
