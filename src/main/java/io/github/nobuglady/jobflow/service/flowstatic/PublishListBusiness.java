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
package io.github.nobuglady.jobflow.service.flowstatic;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.constant.NodeStartType;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishFlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowPublishCatagoryEntity;
import io.github.nobuglady.jobflow.service.flowstatic.dto.PublishListResponseDto;
import io.github.nobuglady.jobflow.util.DataUtil;
import io.github.nobuglady.jobflow.util.PagingUtil;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class PublishListBusiness {

	@Autowired
	private PublishFlowDao publishFlowDao;

	@Autowired
	private PublishNodeDao publishNodeDao;

	/**
	 * 
	 * @param curPage
	 * @return
	 */
	public List<PublishListResponseDto> requestPublishList(int curPage) {

		List<PublishListResponseDto> resultList = new ArrayList<>();

		List<FlowPublishCatagoryEntity> entityList = publishFlowDao
				.selectFlowPublishCatagoryList(PagingUtil.getFrom(curPage), PagingUtil.getFetchCount(curPage));
		if (entityList != null) {
			for (FlowPublishCatagoryEntity entity : entityList) {

				List<PublishNodeEntity> nodeEntityList = publishNodeDao.selectStartByFlowId(entity.getFlowId());

				PublishNodeEntity nodeStartEntity = null;
				if (nodeEntityList != null && nodeEntityList.size() > 0) {
					nodeStartEntity = nodeEntityList.get(0);
				}

				PublishListResponseDto responseDto = new PublishListResponseDto();

				responseDto.flow_id = entity.getFlowId();
				responseDto.file_id = entity.getCategoryId();
				responseDto.flow_name = entity.getFlowName();
				responseDto.disable_flag = entity.getDisableFlag();
				responseDto.update_time = entity.getUpdateTime();

				if (nodeStartEntity != null) {
					responseDto.flow_start_type = nodeStartEntity.getStartType();
					responseDto.cron = nodeStartEntity.getStartCron();

					if (DataUtil.getNodeStartType(nodeStartEntity.getStartType()) == NodeStartType.NODE_START_TYPE_TIMER
							&& StringUtil.isNotEmpty(nodeStartEntity.getStartCron())) {
						CronExpression cronTrigger = CronExpression.parse(nodeStartEntity.getStartCron());

						LocalDateTime next = cronTrigger.next(LocalDateTime.now());
						responseDto.nextStartTime = next.format(DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"));
					}
				}

				resultList.add(responseDto);

			}
		}

		return resultList;
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
