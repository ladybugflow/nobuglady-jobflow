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
package io.github.nobuglady.jobflow.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nobuglady.jobflow.controller.dto.ControllerFlowSaveEdge;
import io.github.nobuglady.jobflow.controller.dto.ControllerFlowSaveNode;
import io.github.nobuglady.jobflow.controller.dto.ControllerFlowSaveRequestDto;
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
import io.github.nobuglady.jobflow.service.flowstatic.FlowStaticBusiness;
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

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class FlowStaticController {

	@Autowired
	private FlowStaticBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value = "/request_flow_static", method = RequestMethod.POST)
	@ResponseBody
	public FlowStaticResponseDto requestFlowStatic(@RequestParam(value = "flowid") String flowid) {

		FlowStaticRequestDto requestDto = new FlowStaticRequestDto();
		FlowStaticResponseDto responseDto = new FlowStaticResponseDto();

		requestDto.flowId = flowid;

		flowBusiness.requestFlowStatic(requestDto, responseDto);

		return responseDto;
	}

	/**
	 * 
	 * @param flowId
	 * @param onOff
	 * @return
	 */
	@RequestMapping(value = "/request_flow_save_on_off", method = RequestMethod.POST)
	@ResponseBody
	public FlowSaveOnOffResponseDto requestFlowSaveOnOff(@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "onOff") String onOff) {

		FlowSaveOnOffRequestDto requestDto = new FlowSaveOnOffRequestDto();
		FlowSaveOnOffResponseDto responseDto = new FlowSaveOnOffResponseDto();

		requestDto.flowId = flowId;
		requestDto.onOff = onOff;

		flowBusiness.requestFlowSaveOnOff(requestDto, responseDto);

		return responseDto;
	}

	/**
	 * 
	 * @param reqDto
	 * @return
	 */
	@RequestMapping(value = "/request_flow_save", method = RequestMethod.POST)
	@ResponseBody
	public FlowSaveResponseDto requestFlowSave(@RequestBody ControllerFlowSaveRequestDto reqDto) {

		FlowSaveRequestDto requestDto = new FlowSaveRequestDto();
		FlowSaveResponseDto responseDto = new FlowSaveResponseDto();
		requestDto.flowid = reqDto.flowid;
		requestDto.nodes = new ArrayList<Node>();
		requestDto.edges = new ArrayList<Edge>();
		for (ControllerFlowSaveNode node : reqDto.nodes) {
			Node newNode = new Node();
			newNode.id = node.id;
			newNode.label = node.label;
			newNode.layoutX = node.layoutX;
			newNode.layoutY = node.layoutY;
			requestDto.nodes.add(newNode);
		}
		for (ControllerFlowSaveEdge edge : reqDto.edges) {
			Edge newEdge = new Edge();
			newEdge.id = edge.id;
			newEdge.from = edge.from;
			newEdge.to = edge.to;
			newEdge.arrows = edge.arrows;
			requestDto.edges.add(newEdge);
		}

		flowBusiness.requestFlowSave(requestDto, responseDto);
		return responseDto;

	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	@RequestMapping(value = "/request_flow_publish", method = RequestMethod.POST)
	@ResponseBody
	public FlowPublishResponseDto requestPublishFlow(@RequestParam(value = "flowId") String flowId) {

		FlowPublishRequestDto requestDto = new FlowPublishRequestDto();
		FlowPublishResponseDto responseDto = new FlowPublishResponseDto();
		requestDto.flowId = flowId;

		flowBusiness.requestFlowPublish(requestDto, responseDto);
		return responseDto;

	}

	///////////////////////////////////////
	// node
	///////////////////////////////////////
	/**
	 * 
	 * @param flowid
	 * @param nodeid
	 * @return
	 */
	@RequestMapping(value = "/request_node_info", method = RequestMethod.POST)
	@ResponseBody
	public NodeInfoResponseDto requestNodeInfo(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "nodeid") String nodeid) {

		NodeInfoRequestDto requestDto = new NodeInfoRequestDto();
		NodeInfoResponseDto responseDto = new NodeInfoResponseDto();
		requestDto.flowId = flowid;
		requestDto.nodeId = nodeid;

		flowBusiness.requestNodeInfo(requestDto, responseDto);

		return responseDto;
	}

	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param onOff
	 * @return
	 */
	@RequestMapping(value = "/request_node_save_on_off", method = RequestMethod.POST)
	@ResponseBody
	public NodeSaveOnOffResponseDto requestNodeSaveOnOff(@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "nodeId") String nodeId, @RequestParam(value = "onOff") String onOff) {

		NodeSaveOnOffRequestDto requestDto = new NodeSaveOnOffRequestDto();
		NodeSaveOnOffResponseDto responseDto = new NodeSaveOnOffResponseDto();

		requestDto.flowId = flowId;
		requestDto.nodeId = nodeId;
		requestDto.onOff = onOff;

		flowBusiness.requestNodeSaveOnOff(requestDto, responseDto);

		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @param nodeid
	 * @param node_type
	 * @param cron
	 * @param start_type
	 * @param execute_type
	 * @param shell_location
	 * @param noderefname
	 * @param nodename
	 * @param layoutX
	 * @param layoutY
	 * @param location
	 * @param http_method
	 * @param http_content_type
	 * @param http_header
	 * @param http_body
	 * @param roles
	 * @return
	 */
	@RequestMapping(value = "/request_node_save", method = RequestMethod.POST)
	@ResponseBody
	public NodeSaveResponseDto requestNodeSave(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "nodeid") String nodeid, @RequestParam(value = "node_type") String node_type,
			@RequestParam(value = "cron") String cron, @RequestParam(value = "start_type") String start_type,
			@RequestParam(value = "execute_type") String execute_type,
			@RequestParam(value = "shell_success_code") String shell_success_code,
			@RequestParam(value = "shell_error_type") String shell_error_type,
			@RequestParam(value = "shell_sync_flag") String shell_sync_flag,
			@RequestParam(value = "shell_location") String shell_location,
			@RequestParam(value = "noderefname") String noderefname, @RequestParam(value = "nodename") String nodename,
			@RequestParam(value = "layout_x") String layoutX, @RequestParam(value = "layout_y") String layoutY,
			@RequestParam(value = "http_success_code") String http_success_code,
			@RequestParam(value = "http_error_type") String http_error_type,
			@RequestParam(value = "http_sync_flag") String http_sync_flag,
			@RequestParam(value = "location") String location, @RequestParam(value = "http_method") String http_method,
			@RequestParam(value = "http_content_type") String http_content_type,
			@RequestParam(value = "http_header") String http_header,
			@RequestParam(value = "http_body") String http_body, @RequestParam(value = "roles") String roles) {

		NodeSaveRequestDto requestDto = new NodeSaveRequestDto();
		NodeSaveResponseDto responseDto = new NodeSaveResponseDto();
		requestDto.flowId = flowid;
		requestDto.nodeId = nodeid;
		requestDto.nodeType = node_type;
		requestDto.cron = cron;
		requestDto.startType = start_type;
		requestDto.executeType = execute_type;
		requestDto.shellSuccessCode = shell_success_code;
		requestDto.shellErrorType = shell_error_type;
		requestDto.shellSyncFlag = shell_sync_flag;
		requestDto.shellLocation = shell_location;
		requestDto.nodeRefName = noderefname;
		requestDto.nodeName = nodename;
		requestDto.layoutX = layoutX;
		requestDto.layoutY = layoutY;
		requestDto.httpSuccessCode = http_success_code;
		requestDto.httpErrorType = http_error_type;
		requestDto.httpSyncFlag = http_sync_flag;
		requestDto.location = location;
		requestDto.httpMethod = http_method;
		requestDto.httpContentType = http_content_type;
		requestDto.httpHeader = http_header;
		requestDto.httpBody = http_body;
		requestDto.roles = roles;

		flowBusiness.requestNodeSave(requestDto, responseDto);
		return responseDto;

	}

	///////////////////////////////////////
	// edge
	///////////////////////////////////////
	/**
	 * 
	 * @param flowid
	 * @param edgeid
	 * @param from
	 * @param to
	 * @return
	 */
	@RequestMapping(value = "/request_edge_info", method = RequestMethod.POST)
	@ResponseBody
	public EdgeInfoResponseDto requestEdgeInfo(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "edgeid") String edgeid, @RequestParam(value = "from") String from,
			@RequestParam(value = "to") String to) {

		EdgeInfoRequestDto requestDto = new EdgeInfoRequestDto();
		EdgeInfoResponseDto responseDto = new EdgeInfoResponseDto();

		requestDto.flowId = flowid;
		requestDto.edgeId = edgeid;
		requestDto.from = from;
		requestDto.to = to;

		flowBusiness.requestEdgeInfo(requestDto, responseDto);

		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @param from
	 * @param to
	 * @param condition
	 * @return
	 */
	@RequestMapping(value = "/request_edge_save", method = RequestMethod.POST)
	@ResponseBody
	public EdgeSaveResponseDto requestEdgeSave(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "edgeid") String edgeid, @RequestParam(value = "from") String from,
			@RequestParam(value = "to") String to, @RequestParam(value = "condition") String condition) {

		EdgeSaveRequestDto requestDto = new EdgeSaveRequestDto();
		EdgeSaveResponseDto responseDto = new EdgeSaveResponseDto();
		requestDto.flowId = flowid;
		requestDto.edgeId = edgeid;
		requestDto.from = from;
		requestDto.to = to;
		requestDto.content = condition;

		flowBusiness.requestEdgeSave(requestDto, responseDto);
		return responseDto;

	}

}
