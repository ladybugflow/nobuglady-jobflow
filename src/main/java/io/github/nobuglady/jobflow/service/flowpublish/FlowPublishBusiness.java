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
package io.github.nobuglady.jobflow.service.flowpublish;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.persistance.db.dao.PublishEdgeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishFlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeRolesDao;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishEdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishFlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeShellEntity;
import io.github.nobuglady.jobflow.service.flowpublish.dto.EdgeInfoPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.EdgeInfoPublishResponseDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.FlowStaticPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.FlowStaticPublishResponseDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.NodeInfoPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.NodeInfoPublishResponseDto;
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
public class FlowPublishBusiness {

	@Autowired
	private PublishFlowDao publishFlowDao;

	@Autowired
	private PublishNodeDao publishNodeDao;

	@Autowired
	private PublishNodeShellDao publishNodeShellDao;

	@Autowired
	private PublishNodeHttpDao publishNodeHttpDao;

	@Autowired
	private PublishEdgeDao publishEdgeDao;

	@Autowired
	private PublishNodeRolesDao publishNodeRolesDao;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowStaticPublish(FlowStaticPublishRequestDto requestDto,
			FlowStaticPublishResponseDto responseDto) {

		String flowId = requestDto.flowId;

		/*
		 * flow
		 */
		PublishFlowEntity flowEntity = publishFlowDao.selectByKey(flowId);

		FlowDto nodeFlowDto = new FlowDto();
		if (flowEntity.getUpdateTime() != null) {
			nodeFlowDto.updateTime = DateUtil.dateToString(flowEntity.getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
		}

		/*
		 * node
		 */
		List<PublishNodeEntity> nodeEntityList = publishNodeDao.selectByFlowId(flowId);
		for (PublishNodeEntity item : nodeEntityList) {

			NodeDto nodeNodeDto = new NodeDto();
			nodeNodeDto.id = item.getNodeId();
			nodeNodeDto.label = item.getNodeName();
			nodeNodeDto.layoutX = item.getLayoutX();
			nodeNodeDto.layoutY = item.getLayoutY();
			nodeNodeDto.disabled = DataUtil.getDisabedFlag(item.getDisableFlag());
			nodeNodeDto.type = DataUtil.getNodeType(item.getNodeType());

			nodeFlowDto.nodes.add(nodeNodeDto);
		}

		/*
		 * edge
		 */
		List<PublishEdgeEntity> edgeEntityList = publishEdgeDao.selectByFlowId(flowId);
		for (PublishEdgeEntity item : edgeEntityList) {

			EdgeDto nodeEdgeDto = new EdgeDto();
			nodeEdgeDto.id = item.getEdgeId();
			nodeEdgeDto.from = item.getFromNodeId();
			nodeEdgeDto.to = item.getToNodeId();

			nodeFlowDto.edges.add(nodeEdgeDto);
		}

		/*
		 * convert entity to json
		 */
		responseDto.flowData = nodeFlowDto.toJson();
		responseDto.disable_flag = DataUtil.getDisabedFlag(flowEntity.getDisableFlag());
	}

	///////////////////////////////////////
	// node
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestNodeInfoPublish(NodeInfoPublishRequestDto requestDto, NodeInfoPublishResponseDto responseDto) {

		String flowId = requestDto.flowId;
		String nodeId = requestDto.nodeId;

		PublishNodeEntity nodeEntity = publishNodeDao.selectByKey(flowId, nodeId);
		PublishNodeHttpEntity nodeHttpEntity = publishNodeHttpDao.selectByKey(flowId, nodeId);
		PublishNodeShellEntity nodeShellEntity = publishNodeShellDao.selectByKey(flowId, nodeId);

		responseDto.nodeEntity = nodeEntity;
		responseDto.nodeHttpEntity = nodeHttpEntity;
		responseDto.nodeShellEntity = nodeShellEntity;
		responseDto.rolesIdList = publishNodeRolesDao.selectRolesIdList(flowId, nodeId);
	}

	///////////////////////////////////////
	// edge
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestEdgeInfoPublish(EdgeInfoPublishRequestDto requestDto, EdgeInfoPublishResponseDto responseDto) {

		String flowId = requestDto.flowId;
		String edgeId = requestDto.edgeId;

		PublishEdgeEntity edgeEntity = publishEdgeDao.selectByKey(flowId, edgeId);
		PublishNodeEntity fromNodeEntity = publishNodeDao.selectByKey(flowId, edgeEntity.getFromNodeId());
		PublishNodeEntity toNodeEntity = publishNodeDao.selectByKey(flowId, edgeEntity.getToNodeId());

		responseDto.flowid = edgeEntity.getFlowId();
		responseDto.edgeid = edgeEntity.getEdgeId();
		responseDto.from = edgeEntity.getFromNodeId();
		responseDto.to = edgeEntity.getToNodeId();
		responseDto.condition = edgeEntity.getEdgeCondition();
		responseDto.fromNodeName = fromNodeEntity.getNodeName();
		responseDto.toNodeName = toNodeEntity.getNodeName();
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
