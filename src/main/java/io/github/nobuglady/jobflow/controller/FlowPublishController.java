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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nobuglady.jobflow.service.flowpublish.FlowPublishBusiness;
import io.github.nobuglady.jobflow.service.flowpublish.dto.EdgeInfoPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.EdgeInfoPublishResponseDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.FlowStaticPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.FlowStaticPublishResponseDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.NodeInfoPublishRequestDto;
import io.github.nobuglady.jobflow.service.flowpublish.dto.NodeInfoPublishResponseDto;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class FlowPublishController {

	@Autowired
	private FlowPublishBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value = "/request_flow_static_publish", method = RequestMethod.POST)
	@ResponseBody
	public FlowStaticPublishResponseDto requestFlowStatic(@RequestParam(value = "flowid") String flowid) {

		FlowStaticPublishRequestDto requestDto = new FlowStaticPublishRequestDto();
		FlowStaticPublishResponseDto responseDto = new FlowStaticPublishResponseDto();

		requestDto.flowId = flowid;

		flowBusiness.requestFlowStaticPublish(requestDto, responseDto);

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
	@RequestMapping(value = "/request_node_info_publish", method = RequestMethod.POST)
	@ResponseBody
	public NodeInfoPublishResponseDto requestNodeInfo(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "nodeid") String nodeid) {

		NodeInfoPublishRequestDto requestDto = new NodeInfoPublishRequestDto();
		NodeInfoPublishResponseDto responseDto = new NodeInfoPublishResponseDto();
		requestDto.flowId = flowid;
		requestDto.nodeId = nodeid;

		flowBusiness.requestNodeInfoPublish(requestDto, responseDto);

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
	@RequestMapping(value = "/request_edge_info_publish", method = RequestMethod.POST)
	@ResponseBody
	public EdgeInfoPublishResponseDto requestEdgeInfo(@RequestParam(value = "flowid") String flowid,
			@RequestParam(value = "edgeid") String edgeid, @RequestParam(value = "from") String from,
			@RequestParam(value = "to") String to) {

		EdgeInfoPublishRequestDto requestDto = new EdgeInfoPublishRequestDto();
		EdgeInfoPublishResponseDto responseDto = new EdgeInfoPublishResponseDto();

		requestDto.flowId = flowid;
		requestDto.edgeId = edgeid;
		requestDto.from = from;
		requestDto.to = to;

		flowBusiness.requestEdgeInfoPublish(requestDto, responseDto);

		return responseDto;
	}

}
