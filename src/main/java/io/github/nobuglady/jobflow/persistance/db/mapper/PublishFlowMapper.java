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

import io.github.nobuglady.jobflow.persistance.db.entity.PublishFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowPublishCatagoryEntity;

/**
 * 
 * @author NoBugLady
 *
 */
@Mapper
public interface PublishFlowMapper {

	//////////////////////////////////////
	// Base
	//////////////////////////////////////
	// @formatter:off
	@Select("SELECT * FROM publish_flow " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public PublishFlowEntity selectByKey(String flowId);

	// @formatter:off
	@Delete("DELETE from publish_flow " 
			+ " WHERE" 
			+ " flow_id = #{param1}")
	// @formatter:on
	public int deleteByKey(String flowId);

	//////////////////////////////////////
	// Extends
	//////////////////////////////////////
	// @formatter:off
	@Select("select count(1) from (SELECT * FROM publish_flow " 
			+ " order by update_time desc) t1 ")
	// @formatter:on
	public int selectFlowPublishCatagoryListCount();

	// @formatter:off
	@Select("select * from (SELECT * FROM publish_flow " 
			+ " order by update_time desc) t1 LIMIT #{param1}, #{param2} ")
	// @formatter:on
	public List<FlowPublishCatagoryEntity> selectFlowPublishCatagoryList(int from, int fetchCount);

}
