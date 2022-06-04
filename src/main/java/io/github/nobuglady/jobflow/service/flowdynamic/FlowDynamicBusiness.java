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
package io.github.nobuglady.jobflow.service.flowdynamic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.logger.ConsoleLogger;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryEdgeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryFlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryEdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.FlowDynamicRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.FlowDynamicResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.LogClearRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.LogClearResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.LogRefreshRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.LogRefreshResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.ReStartFlowRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.ReStartFlowResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.ReStartNodeRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.ReStartNodeResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.StartFlowRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.StartFlowResponseDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.StartNodeRequestDto;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.StartNodeResponseDto;
import io.github.nobuglady.jobflow.service.flowmanager.FlowManager;
import io.github.nobuglady.jobflow.service.modal.EdgeDto;
import io.github.nobuglady.jobflow.service.modal.FlowDto;
import io.github.nobuglady.jobflow.service.modal.NodeDto;
import io.github.nobuglady.jobflow.util.DataUtil;
import io.github.nobuglady.jobflow.util.DateUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class FlowDynamicBusiness {

	@Autowired
	private HistoryFlowDao flowHistoryDao;

	@Autowired
	private HistoryNodeDao nodeHistoryDao;

	@Autowired
	private HistoryEdgeDao edgeHistoryDao;

	@Autowired
	private FlowManager flowManager;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowDynamic(FlowDynamicRequestDto requestDto, FlowDynamicResponseDto responseDto) {

		String flowId = requestDto.flowId;
		String historyId = requestDto.historyId;

		/*
		 * flow
		 */
		HistoryFlowEntity flowEntity = flowHistoryDao.selectByKey(flowId, historyId);

		FlowDto nodeFlowDto = new FlowDto();
		nodeFlowDto.updateTime = DateUtil.dateToString(flowEntity.getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);

		/*
		 * node
		 */
		List<HistoryNodeEntity> nodeEntityList = nodeHistoryDao.selectByFlowHistoryId(flowId, historyId);
		for (HistoryNodeEntity item : nodeEntityList) {

			NodeDto nodeNodeDto = new NodeDto();
			nodeNodeDto.id = item.getNodeId();
			nodeNodeDto.label = item.getNodeName();
			nodeNodeDto.layoutX = item.getLayoutX();
			nodeNodeDto.layoutY = item.getLayoutY();
			nodeNodeDto.status = item.getNodeStatus();
			nodeNodeDto.status_detail = item.getNodeStatusDetail();

			nodeNodeDto.disabled = DataUtil.getDisabedFlag(item.getDisableFlag());
			nodeNodeDto.type = DataUtil.getNodeType(item.getNodeType());

			nodeFlowDto.nodes.add(nodeNodeDto);
		}

		/*
		 * edge
		 */
		List<HistoryEdgeEntity> edgeEntityList = edgeHistoryDao.selectByFlowHistoryId(flowId, historyId);
		for (HistoryEdgeEntity item : edgeEntityList) {

			EdgeDto nodeEdgeDto = new EdgeDto();
			nodeEdgeDto.id = item.getEdgeId();
			nodeEdgeDto.from = item.getFromNodeId();
			nodeEdgeDto.to = item.getToNodeId();

			nodeFlowDto.edges.add(nodeEdgeDto);
		}

		/*
		 * convert flow to json
		 */
		responseDto.flowData = nodeFlowDto.toJson();
		HistoryFlowEntity flowHistoryEntity = flowHistoryDao.selectByKey(requestDto.flowId, requestDto.historyId);
		responseDto.disable_flag = DataUtil.getDisabedFlag(flowHistoryEntity.getDisableFlag());

	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestStartFlow(StartFlowRequestDto requestDto, StartFlowResponseDto responseDto) {

		String historyId = flowManager.startFlow(requestDto.flowId);
		HistoryFlowEntity flowHistoryEntity = flowHistoryDao.selectByKey(requestDto.flowId, historyId);
		responseDto.instanceId = historyId;
		responseDto.flow_id = flowHistoryEntity.getFlowId();
		responseDto.start_time = flowHistoryEntity.getStartTime();
		responseDto.create_time = flowHistoryEntity.getCreateTime();
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestReStartFlow(ReStartFlowRequestDto requestDto, ReStartFlowResponseDto responseDto) {

		String historyId = flowManager.reStartFlow(requestDto.flowId, requestDto.historyId);
		HistoryFlowEntity flowHistoryEntity = flowHistoryDao.selectByKey(requestDto.flowId, historyId);
		responseDto.instanceId = historyId;
		responseDto.flow_id = flowHistoryEntity.getFlowId();
		responseDto.start_time = flowHistoryEntity.getStartTime();
		responseDto.create_time = flowHistoryEntity.getCreateTime();
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestStartNode(StartNodeRequestDto requestDto, StartNodeResponseDto responseDto) {

		String historyId = flowManager.startNode(requestDto.flowId, requestDto.nodeId);
		HistoryNodeEntity nodeHistoryEntity = nodeHistoryDao.selectByKey(requestDto.flowId, requestDto.nodeId,
				historyId);
		responseDto.instanceId = historyId;
		responseDto.flow_id = nodeHistoryEntity.getFlowId();
		responseDto.node_id = nodeHistoryEntity.getNodeId();
		responseDto.start_time = nodeHistoryEntity.getStartTime();
		responseDto.create_time = nodeHistoryEntity.getCreateTime();
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestReStartNode(ReStartNodeRequestDto requestDto, ReStartNodeResponseDto responseDto) {

		String historyId = flowManager.reStartNode(requestDto.flowId, requestDto.instanceId, requestDto.nodeId);
		HistoryNodeEntity nodeHistoryEntity = nodeHistoryDao.selectByKey(requestDto.flowId, requestDto.nodeId,
				historyId);
		responseDto.instanceId = historyId;
		responseDto.flow_id = nodeHistoryEntity.getFlowId();
		responseDto.node_id = nodeHistoryEntity.getNodeId();
		responseDto.start_time = nodeHistoryEntity.getStartTime();
		responseDto.create_time = nodeHistoryEntity.getCreateTime();
	}

	///////////////////////////////////////
	// log
	///////////////////////////////////////
	/**
	 * refresh log
	 * 
	 * @param requestDto  request dto
	 * @param responseDto response dto
	 */
	public void refreshLog(LogRefreshRequestDto requestDto, LogRefreshResponseDto responseDto) {

		List<String> messageList = ConsoleLogger.getInstance(requestDto.flowId, requestDto.historyId).getMessages();
		StringBuffer logMsg = new StringBuffer();

		for (String message : messageList) {
			logMsg.append(message);
			logMsg.append("\n");
		}

		responseDto.logs = logMsg.toString();
	}

	/**
	 * clear log
	 * 
	 * @param requestDto  request dto
	 * @param responseDto response dto
	 */
	public void clearLog(LogClearRequestDto requestDto, LogClearResponseDto responseDto) {

		ConsoleLogger.getInstance(requestDto.flowId, requestDto.historyId).clear();

		responseDto.logs = "";
	}

	///////////////////////////////////////
	// other
	///////////////////////////////////////

	public void initFlowOnStartup() {

		List<HistoryFlowEntity> historyFlowEntityList = flowHistoryDao.selectRunningFlow();
		if (historyFlowEntityList != null) {
			for (HistoryFlowEntity historyFlowEntity : historyFlowEntityList) {
				flowManager.recoveryFlow(historyFlowEntity.getFlowId(),
						String.valueOf(historyFlowEntity.getHistoryId()));
			}
		}
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
