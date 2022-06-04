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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.constant.NodeStartType;
import io.github.nobuglady.jobflow.constant.NodeType;
import io.github.nobuglady.jobflow.service.flowstatic.dto.Edge;
import io.github.nobuglady.jobflow.service.flowstatic.dto.EdgeSaveRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.EdgeSaveResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowPublishResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowSaveRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowSaveResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.Node;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeSaveRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeSaveResponseDto;
import io.github.nobuglady.jobflow.persistance.db.dao.EdgeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.FlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeRolesDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.EdgeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeHttpEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeShellEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.flowstatic.dto.EdgeInfoRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.EdgeInfoResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowSaveOnOffRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowSaveOnOffResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowStaticRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowStaticResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeInfoRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeInfoResponseDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeSaveOnOffRequestDto;
import io.github.nobuglady.jobflow.service.flowstatic.dto.NodeSaveOnOffResponseDto;
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
public class FlowStaticBusiness {

	@Autowired
	private FlowDao flowDao;
	
	@Autowired
	private NodeDao nodeDao;

	@Autowired
	private NodeShellDao nodeShellDao;
	
	@Autowired
	private NodeHttpDao nodeHttpDao;
	
	@Autowired
	private EdgeDao edgeDao;

	@Autowired
	private NodeRolesDao nodeRolesDao;
	
	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowStatic(FlowStaticRequestDto requestDto, 
			FlowStaticResponseDto responseDto) {

		String flowId = requestDto.flowId;
		
		/*
		 * flow
		 */
    	FlowEntity flowEntity = flowDao.selectByKey(flowId);
    	
        FlowDto nodeFlowDto = new FlowDto();
        if(flowEntity.getUpdateTime() != null) {
        	nodeFlowDto.updateTime = DateUtil.dateToString(flowEntity.getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
        }

        /*
         * node
         */
        List<NodeEntity> nodeEntityList = nodeDao.selectByFlowId(flowId);
        for (NodeEntity item : nodeEntityList) {
        	
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
        List<EdgeEntity> edgeEntityList = edgeDao.selectByFlowId(flowId);
        for (EdgeEntity item : edgeEntityList) {
            	
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

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowSaveOnOff(FlowSaveOnOffRequestDto requestDto, FlowSaveOnOffResponseDto responseDto) {

		FlowEntity entity = flowDao.selectByKey(requestDto.flowId);
		if (entity != null) {
			
			if(Const.STR_ON.equals(requestDto.onOff)) {
				entity.setDisableFlag(Const.FLAG_OFF);
			}else {
				entity.setDisableFlag(Const.FLAG_ON);
			}
			entity.setUpdateUser(AuthHolder.getUser().email);
			flowDao.update(entity);
			
			entity = flowDao.selectByKey(requestDto.flowId);
			responseDto.updateTime = DateUtil.dateToString(entity.getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
		} 
	}


	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowSave(FlowSaveRequestDto requestDto, FlowSaveResponseDto responseDto) {
		
		String flowId = requestDto.flowid;
		List<Node> nodes = requestDto.nodes;
		List<Edge> edges = requestDto.edges;
		
		List<String[]> fromToList = new ArrayList<>();
		for(Edge edge:edges) {
			fromToList.add(new String[] {edge.from, edge.to, edge.id});
		}
		
		/*
		 * update nodes
		 */

		Map<String,Node> nodeMap = new HashMap<String,Node>();
		List<String> nodeIdList = new ArrayList<String>();
		for(Node node:nodes) {
			nodeIdList.add(node.id);
			nodeMap.put(node.id, node);
		}
		
		nodeDao.deleteNodesNotInKeys(flowId, nodeIdList);
        
        for(String nodeId:nodeIdList) {
        	
        	Node node = nodeMap.get(nodeId);
        	
        	NodeEntity nodeEntity = nodeDao.selectByKey(flowId, nodeId);
        	if(nodeEntity != null) {
        		nodeEntity.setNodeName(node.label);
        		nodeEntity.setLayoutX(node.layoutX);
        		nodeEntity.setLayoutY(node.layoutY);
        		nodeEntity.setUpdateUser(AuthHolder.getUser().email);
        		nodeDao.update(nodeEntity);
        	}else {
        		nodeEntity = new NodeEntity();
            	nodeEntity.setFlowId(flowId);
            	nodeEntity.setNodeId(nodeId);
            	nodeEntity.setNodeName(node.label);
            	nodeEntity.setRefName(node.label);
            	nodeEntity.setLayoutX(node.layoutX);
            	nodeEntity.setLayoutY(node.layoutY);
            	nodeEntity.setStartType(NodeStartType.NODE_START_TYPE_DEFAULT);
            	nodeEntity.setDisableFlag(Const.FLAG_OFF);
            	nodeEntity.setNodeType(NodeType.NODE_TYPE_NOMARL);
            	nodeEntity.setExecuteType(NodeExecuteType.NODE_EXECUTE_TYPE_NONE);
            	nodeEntity.setSkipFlag(Const.FLAG_OFF);
            	nodeEntity.setDisableFlag(Const.FLAG_OFF);
	        	nodeEntity.setCreateUser(AuthHolder.getUser().email);
	        	nodeEntity.setUpdateUser(AuthHolder.getUser().email);
	        	
            	nodeDao.save(nodeEntity);
        	}
        	
        }
        
		
		/*
		 * update edges
		 */
		edgeDao.deleteEdgeNotInKeys(flowId, fromToList);
        
        for(String[] fromTo:fromToList) {
        	String from = fromTo[0];
        	String to = fromTo[1];
        	String edgeId = fromTo[2];
        	
        	edgeDao.saveOrUpdateEdge(flowId, edgeId, from, to, null);
			
        }
        
        /*
         * update flow
         */
		FlowEntity flowEntity = flowDao.selectByKey(flowId);
		flowEntity.setUpdateUser(AuthHolder.getUser().email);
		flowDao.update(flowEntity);

	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestFlowPublish(FlowPublishRequestDto requestDto, FlowPublishResponseDto responseDto) {
		FlowEntity flowEntity = flowDao.publishFlow(requestDto.flowId);
		responseDto.publishTime =  DateUtil.dateToString(flowEntity.getPublishTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}

	///////////////////////////////////////
	// node
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestNodeInfo(NodeInfoRequestDto requestDto, 
			NodeInfoResponseDto responseDto) {
		
		String flowId = requestDto.flowId;
		String nodeId = requestDto.nodeId;
		
		NodeEntity nodeEntity = nodeDao.selectByKey(flowId, nodeId);
		NodeHttpEntity nodeHttpEntity = nodeHttpDao.selectByKey(flowId, nodeId);
		NodeShellEntity nodeShellEntity = nodeShellDao.selectByKey(flowId, nodeId);
		
		responseDto.nodeEntity = nodeEntity;
		responseDto.nodeHttpEntity = nodeHttpEntity;
		responseDto.nodeShellEntity = nodeShellEntity;
		responseDto.rolesIdList = nodeRolesDao.selectRolesIdList(flowId, nodeId);
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestNodeSaveOnOff(NodeSaveOnOffRequestDto requestDto, NodeSaveOnOffResponseDto responseDto) {

		NodeEntity entity = nodeDao.selectByKey(requestDto.flowId,requestDto.nodeId);
		if (entity != null) {
			
			if(Const.STR_ON.equals(requestDto.onOff)) {
				entity.setDisableFlag(Const.FLAG_OFF);
			}else {
				entity.setDisableFlag(Const.FLAG_ON);
			}
			entity.setUpdateUser(AuthHolder.getUser().email);
			nodeDao.update(entity);
			
			entity = nodeDao.selectByKey(requestDto.flowId,requestDto.nodeId);
			responseDto.updateTime = DateUtil.dateToString(entity.getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
		} 
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestNodeSave(NodeSaveRequestDto requestDto, NodeSaveResponseDto responseDto) {
		
		nodeDao.saveOrUpdateNode(
				requestDto.flowId,
				requestDto.nodeId,
				requestDto.nodeType,
				requestDto.cron,
				requestDto.startType,
				requestDto.executeType,
				requestDto.nodeRefName,
				requestDto.nodeName,
				requestDto.layoutX,
				requestDto.layoutY,
				requestDto.roles
				);
		
		nodeHttpDao.saveOrUpdateNode(
				requestDto.flowId,
				requestDto.nodeId,
				requestDto.httpSuccessCode,
				requestDto.httpErrorType,
				requestDto.httpSyncFlag,
				requestDto.location,
				requestDto.httpMethod,
				requestDto.httpContentType,
				requestDto.httpHeader,
				requestDto.httpBody
				);
		
		nodeShellDao.saveOrUpdateNode(
				requestDto.flowId,
				requestDto.nodeId,
				requestDto.shellSuccessCode,
				requestDto.shellErrorType,
				requestDto.shellSyncFlag,
				requestDto.shellLocation
				);
	}

	///////////////////////////////////////
	// edge
	///////////////////////////////////////
	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestEdgeInfo(EdgeInfoRequestDto requestDto, EdgeInfoResponseDto responseDto) {
		
		String flowId = requestDto.flowId;
		String edgeId = requestDto.edgeId;
		
		EdgeEntity edgeEntity = edgeDao.selectByKey(flowId,edgeId);
		NodeEntity fromNodeEntity = nodeDao.selectByKey(flowId, edgeEntity.getFromNodeId());
		NodeEntity toNodeEntity = nodeDao.selectByKey(flowId, edgeEntity.getToNodeId());
		
		responseDto.flowid = edgeEntity.getFlowId();
		responseDto.edgeid = edgeEntity.getEdgeId();
		responseDto.from = edgeEntity.getFromNodeId();
		responseDto.to = edgeEntity.getToNodeId();
		responseDto.condition = edgeEntity.getEdgeCondition();
		responseDto.fromNodeName = fromNodeEntity.getNodeName();
		responseDto.toNodeName = toNodeEntity.getNodeName();
	}

	/**
	 * 
	 * @param requestDto
	 * @param responseDto
	 */
	public void requestEdgeSave(EdgeSaveRequestDto requestDto, EdgeSaveResponseDto responseDto) {
		
		edgeDao.saveOrUpdateEdge(
				requestDto.flowId,
				requestDto.edgeId,
				requestDto.from,
				requestDto.to,
				requestDto.content
				);
		
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
