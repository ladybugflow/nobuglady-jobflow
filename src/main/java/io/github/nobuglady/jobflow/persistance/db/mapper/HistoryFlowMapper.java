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

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface HistoryFlowMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	@Select("SELECT * FROM history_flow " + " WHERE" + " flow_id = #{param1}" + " and history_id = #{param2}")
	public HistoryFlowEntity selectByKey(String flowId, String historyId);

	@Insert("insert into history_flow" + " (" + "flow_id," + "history_id," + "category_id," + "flow_name,"
			+ "flow_desc," + "flow_status," + "flow_result," + "start_time," + "finish_time," + "error_time,"
			+ "disable_flag," + "create_user," + "update_user," + "create_time," + "update_time" + " )" + " values"
			+ " (" + "#{flowId}," + "#{historyId}," + "#{categoryId}," + "#{flowName}," + "#{flowDesc},"
			+ "#{flowStatus}," + "#{flowResult}," + "#{startTime}," + "#{finishTime}," + "#{errorTime},"
			+ "#{disableFlag}," + "#{createUser}," + "#{updateUser}," + "now()," + "now()" + " ) ")
	public void save(HistoryFlowEntity entity);

	@Delete("DELETE FROM history_flow " + " WHERE" + " flow_id = #{param1}" + " and history_id = #{param2}")
	public int deleteByKey(String flowId, String historyId);
	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
//	@Select("select * from (SELECT * FROM history_flow" + " order by update_time desc) t1 LIMIT #{param1}, #{param2} ")
//	public List<HistoryFlowEntity> selectAll(int from, int fetchCount);

	@Select("SELECT * FROM history_flow " + " WHERE" + " flow_status <> #{param1} " + " order by update_time desc ")
	public List<HistoryFlowEntity> selectAllError(int completeStatus);

	@Select("SELECT * FROM history_flow " + " WHERE" + " flow_status = #{param1}"
			+ " and DATE(`update_time`) = CURDATE()" + " order by update_time desc ")
	public List<HistoryFlowEntity> selectTodayComplete$CustomFunction(int completeStatus);

	@Select("SELECT * FROM history_flow " + " WHERE" + " flow_status = #{param1}")
	public List<HistoryFlowEntity> selectRunningFlow(int readyStatus);

	@Select("SELECT IfNull(max(cast(history_id as signed)),0) " + " FROM history_flow")
	public int selectMaxHistoryId$CustomFunction();

	@Update("update history_flow" + " set finish_time=now()," + " flow_status=#{param3}, " + " update_user=#{param4}, "
			+ " update_time=now() " + " where" + " flow_id=#{param1} " + " and history_id = #{param2} ")
	public void updateStatus(String flowId, String historyId, int status, String userId);

	@Update(" insert into history_node( " + " 		flow_id, " + " 		node_id, " + " 		history_id, "
			+ " 		node_name, " + " 		ref_name, " + " 		node_type, " + " 		start_type, "
			+ " 		execute_type, " + " 		start_cron, " + " 		sub_flow_id, " + " 		sub_node_id, "
			+ " 		layout_x, " + " 		layout_y, " + " 		skip_flag, " + " 		skip_value, "
			+ " 		node_status, " + " 		node_status_detail, " + " 		start_time, " + " 		finish_time, "
			+ " 		node_result_message, " + " 		disable_flag, " + " 		create_user, "
			+ " 		update_user, " + " 		create_time, " + " 		update_time " + " 		) " + " 		select "
			+ " 		flow_id, " + " 		node_id, " + " 		#{param2} as history_id, " + " 		node_name, "
			+ " 		ref_name, " + " 		node_type, " + " 		start_type, " + " 		execute_type, "
			+ " 		start_cron, " + " 		sub_flow_id, " + " 		sub_node_id, " + " 		layout_x, "
			+ " 		layout_y, " + " 		skip_flag, " + " 		skip_value, " + " 		0, " + " 		0, "
			+ " 		null, " + " 		null, " + " 		null, " + " 		0, " + " 		#{param3},"
			+ " 		#{param3}," + " 		now()," + " 		now()"
			+ " 		from publish_node where flow_id = #{param1} ")
	public void createHistoryNode(String flowId, String historyId, String userId);

	@Update(" insert into history_node_http( " + " 		flow_id, " + " 		node_id, " + " 		history_id, "
			+ " 		timeout, " + " 		timeout_type, " + " 		success_code, " + " 		error_type, "
			+ " 		sync_flag, " + " 		http_url, " + " 		http_method, " + " 		http_header, "
			+ " 		http_body, " + " 		http_content_type, " + " 		create_user, " + " 		update_user, "
			+ " 		create_time, " + " 		update_time " + " 		) " + " 		select " + " 		flow_id, "
			+ " 		node_id, " + " 		#{param2} as history_id, " + " 		timeout, " + " 		timeout_type, "
			+ " 		success_code, " + " 		error_type, " + " 		sync_flag, " + " 		http_url, "
			+ " 		http_method, " + " 		http_header, " + " 		http_body, " + " 		http_content_type, "
			+ " 		#{param3}," + " 		#{param3}," + " 		now()," + " 		now()"
			+ " 		from publish_node_http where flow_id = #{param1} ")
	public void createHistoryNodeHttp(String flowId, String historyId, String userId);

	@Update(" insert into history_node_shell( " + " 		flow_id, " + " 		node_id, " + " 		history_id, "
			+ " 		timeout, " + " 		timeout_type, " + " 		success_code, " + " 		error_type, "
			+ " 		sync_flag, " + " 		shell_location, " + " 		shell_param, " + " 		create_user, "
			+ " 		update_user, " + " 		create_time, " + " 		update_time " + " 		) " + " 		select "
			+ " 		flow_id, " + " 		node_id, " + " 		#{param2} as history_id, " + " 		timeout, "
			+ " 		timeout_type, " + " 		success_code, " + " 		error_type, " + " 		sync_flag, "
			+ " 		shell_location, " + " 		shell_param, " + " 		#{param3}," + " 		#{param3},"
			+ " 		now()," + " 		now()" + " 		from publish_node_shell where flow_id = #{param1} ")
	public void createHistoryNodeShell(String flowId, String historyId, String userId);

	@Update(" insert into history_node_roles( " + " 		flow_id, " + " 		node_id, " + " 		history_id, "
			+ " 		roles_id, " + " 		create_user, " + " 		update_user, " + " 		create_time, "
			+ " 		update_time " + " 		) " + " 		select " + " 		flow_id, " + " 		node_id, "
			+ " 		#{param2} as history_id, " + " 		roles_id, " + " 		#{param3}," + " 		#{param3},"
			+ " 		now()," + " 		now()" + " 		from publish_node_roles where flow_id = #{param1} ")
	public void createHistoryNodeRoles(String flowId, String historyId, String userId);

	@Update("insert into history_edge( " + "		flow_id, " + "		edge_id, " + "		history_id, "
			+ "		from_node_id, " + "		to_node_id, " + "		edge_condition, "
			+ "		edge_exception_return_type, " + " 		skip_flag, " + " 		skip_value, "
			+ " 		disable_flag, " + " 		create_user, " + " 		update_user, " + "		create_time, "
			+ "		update_time " + "		) " + "		select " + "		flow_id, " + "		edge_id, "
			+ " 		#{param2} as history_id, " + "		from_node_id, " + "		to_node_id, "
			+ "		edge_condition, " + "		edge_exception_return_type, " + " 		skip_flag, "
			+ " 		skip_value, " + " 		disable_flag, " + " 		#{param3}," + " 		#{param3},"
			+ " 		now()," + " 		now()" + "		from publish_edge where flow_id = #{param1} ")
	public void createHistoryEdge(String flowId, String historyId, String userId);

}
