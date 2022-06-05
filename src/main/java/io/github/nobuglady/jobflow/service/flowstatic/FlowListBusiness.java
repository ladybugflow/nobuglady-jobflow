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
import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.constant.NodeType;
import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.persistance.db.dao.FlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.custom.FlowCatagoryEntity;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowListResponseDto;
import io.github.nobuglady.jobflow.util.DataUtil;
import io.github.nobuglady.jobflow.util.PagingUtil;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class FlowListBusiness {

	@Autowired
	private FlowDao flowDao;

	@Autowired
	private NodeDao nodeDao;

	/**
	 * 
	 * @param curPage
	 * @return
	 */
	public List<FlowListResponseDto> requestFlowList(int curPage) {

		List<FlowListResponseDto> resultList = new ArrayList<>();

		List<FlowCatagoryEntity> entityList = flowDao.selectFlowCatagoryList(PagingUtil.getFrom(curPage),
				PagingUtil.getFetchCount(curPage));
		if (entityList != null) {
			for (FlowCatagoryEntity entity : entityList) {

				List<NodeEntity> nodeEntityList = nodeDao.selectStartByFlowId(entity.getFlowId());

				NodeEntity nodeStartEntity = null;
				if (nodeEntityList != null && nodeEntityList.size() > 0) {
					nodeStartEntity = nodeEntityList.get(0);
				}

				FlowListResponseDto responseDto = new FlowListResponseDto();

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

	/**
	 * 
	 * @param flowId
	 * @param flowName
	 * @return
	 */
	public void requestCreateFlow(String flowId, String flowName) {

		// check flow
		FlowEntity flowEntity = flowDao.selectByKey(flowId);
		if (flowEntity == null) {
			flowEntity = new FlowEntity();
			flowEntity.setCategoryId(flowId);
			flowEntity.setFlowId(flowId);
			flowEntity.setFlowName(flowName);
			flowEntity.setCreateUser(AuthHolder.getUser().email);
			flowEntity.setUpdateUser(AuthHolder.getUser().email);

			flowDao.save(flowEntity);

			NodeEntity nodeEntity = new NodeEntity();
			nodeEntity.setFlowId(flowEntity.getFlowId());
			nodeEntity.setNodeId("start");
			nodeEntity.setNodeName("start");
			nodeEntity.setRefName("start");
			nodeEntity.setDisableFlag(Const.FLAG_OFF);
			nodeEntity.setLayoutX("0");
			nodeEntity.setLayoutY("0");
			nodeEntity.setStartType(NodeStartType.NODE_START_TYPE_DEFAULT);
			nodeEntity.setNodeType(NodeType.NODE_TYPE_START);
			nodeEntity.setExecuteType(NodeExecuteType.NODE_EXECUTE_TYPE_NONE);
			nodeEntity.setSkipFlag(Const.FLAG_OFF);
			nodeEntity.setDisableFlag(Const.FLAG_OFF);
			nodeEntity.setCreateUser(AuthHolder.getUser().email);
			nodeEntity.setUpdateUser(AuthHolder.getUser().email);

			nodeDao.save(nodeEntity);
		}
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
