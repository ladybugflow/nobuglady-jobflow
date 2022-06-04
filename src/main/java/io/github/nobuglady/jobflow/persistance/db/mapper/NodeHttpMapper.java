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

import io.github.nobuglady.jobflow.persistance.db.entity.NodeHttpEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface NodeHttpMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	@Select("SELECT * FROM node_http "
			+ " WHERE"
			+ " flow_id = #{param1}"
			+ " and node_id = #{param2}")
	public NodeHttpEntity selectByKey(String flowId, String nodeId);


	@Insert("insert into node_http"
			+ " ("
	    	+ " flow_id, "
	    	+ " node_id, "
	    	+ " timeout, "
	    	+ " timeout_type, "
	    	+ " success_code, "
	    	+ " error_type, "
	    	+ " sync_flag, "
	    	+ " http_url, "
	    	+ " http_method, "
	    	+ " http_header, "
	    	+ " http_body, "
	    	+ " http_content_type, "
	    	+ " create_user, "
	    	+ " update_user, "
	    	+ " create_time, "
	    	+ " update_time "
			+ " )"
			+ " values"
			+ " ("
			+ "#{flowId},"
			+ "#{nodeId},"
			+ "#{timeout},"
			+ "#{timeoutType},"
			+ "#{successCode},"
			+ "#{errorType},"
			+ "#{syncFlag},"
			+ "#{httpUrl},"
			+ "#{httpMethod},"
			+ "#{httpHeader},"
			+ "#{httpBody},"
			+ "#{httpContentType},"
			+ "#{createUser},"
			+ "#{updateUser},"
			+ "now(),"
			+ "now()"
			+ " ) ")
	public void save(NodeHttpEntity entity);
	
	@Update("update node_http "
			+ " set"
			+ " flow_id=#{flowId}"
			+ " ,node_id=#{nodeId}"
			+ " ,timeout=#{timeout}"
			+ " ,timeout_type=#{timeoutType}"
			+ " ,success_code=#{successCode}"
			+ " ,error_type=#{errorType}"
			+ " ,sync_flag=#{syncFlag}"
			+ " ,http_url=#{httpUrl}"
			+ " ,http_method=#{httpMethod}"
			+ " ,http_header=#{httpHeader}"
			+ " ,http_body=#{httpBody}"
			+ " ,http_content_type=#{httpContentType}"
			+ " ,update_user=#{updateUser}"
			+ " ,update_time=now()"
			+ " where"
			+ " flow_id=#{flowId}"
			+ " and node_id=#{nodeId}")
	public void update(NodeHttpEntity entity);
	
	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
}
