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
package io.github.nobuglady.jobflow.service.flowpublish.dto;

import java.util.Date;

/**
 * 
 * @author NoBugLady
 *
 */
public class PublishListResponseDto {
	public String flow_id;
	public String file_id;
	public String flow_name;
	public Integer flow_start_type;
	public Integer disable_flag;
	public Date update_time;

	public String cron;
	public String nextStartTime;
}
