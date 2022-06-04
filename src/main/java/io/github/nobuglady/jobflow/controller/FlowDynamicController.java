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

import io.github.nobuglady.jobflow.service.flowdynamic.FlowDynamicBusiness;
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

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class FlowDynamicController {

	@Autowired
	private FlowDynamicBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param flowid
	 * @param instanceid
	 * @return
	 */
	@RequestMapping(value="/request_flow_dynamic", method=RequestMethod.POST)
	@ResponseBody
	public FlowDynamicResponseDto requestFlowDynamic(
			@RequestParam(value="flowid") String flowid,
			@RequestParam(value="instanceid") String historyId) {

		FlowDynamicRequestDto requestDto = new FlowDynamicRequestDto();
		FlowDynamicResponseDto responseDto = new FlowDynamicResponseDto();
		
		requestDto.historyId = historyId;
		requestDto.flowId = flowid;
		
		flowBusiness.requestFlowDynamic(requestDto, responseDto);
		
		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @return
	 */
	@RequestMapping(value="/request_start_flow", method=RequestMethod.POST)
	@ResponseBody
	public StartFlowResponseDto requestStartFlow(
			@RequestParam(value="flowid") String flowid) {

		StartFlowRequestDto requestDto = new StartFlowRequestDto();
		StartFlowResponseDto responseDto = new StartFlowResponseDto();
		
		requestDto.flowId = flowid;

		flowBusiness.requestStartFlow(requestDto, responseDto);
		
		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @param instanceid
	 * @return
	 */
	@RequestMapping(value="/request_restart_flow", method=RequestMethod.POST)
	@ResponseBody
	public ReStartFlowResponseDto requestReStartFlow(
			@RequestParam(value="flowid") String flowid,
			@RequestParam(value="instanceid") String historyId) {

		ReStartFlowRequestDto requestDto = new ReStartFlowRequestDto();
		ReStartFlowResponseDto responseDto = new ReStartFlowResponseDto();
		
		requestDto.flowId = flowid;
		requestDto.historyId = historyId;

		flowBusiness.requestReStartFlow(requestDto, responseDto);
		
		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @param nodeid
	 * @return
	 */
	@RequestMapping(value="/request_start_node", method=RequestMethod.POST)
	@ResponseBody
	public StartNodeResponseDto requestStartNode(
			@RequestParam(value="flowid") String flowid,
			@RequestParam(value="nodeid") String nodeid) {

		StartNodeRequestDto requestDto = new StartNodeRequestDto();
		StartNodeResponseDto responseDto = new StartNodeResponseDto();
		
		requestDto.flowId = flowid;
		requestDto.nodeId = nodeid;

		flowBusiness.requestStartNode(requestDto, responseDto);
		
		return responseDto;
	}

	/**
	 * 
	 * @param flowid
	 * @param instanceid
	 * @param nodeid
	 * @return
	 */
	@RequestMapping(value="/request_restart_node", method=RequestMethod.POST)
	@ResponseBody
	public ReStartNodeResponseDto requestReStartNode(
			@RequestParam(value="flowid") String flowid,
			@RequestParam(value="instanceid") String instanceid,
			@RequestParam(value="nodeid") String nodeid) {

		ReStartNodeRequestDto requestDto = new ReStartNodeRequestDto();
		ReStartNodeResponseDto responseDto = new ReStartNodeResponseDto();
		
		requestDto.flowId = flowid;
		requestDto.nodeId = nodeid;
		requestDto.instanceId = instanceid;

		flowBusiness.requestReStartNode(requestDto, responseDto);
		
		return responseDto;
	}
	
	///////////////////////////////////////
	// flow history
	///////////////////////////////////////

	///////////////////////////////////////
	// log
	///////////////////////////////////////
	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @return
	 */
	@RequestMapping(value = "/request_log_refresh", method = RequestMethod.POST)
	@ResponseBody
	public LogRefreshResponseDto requestRefreshLog(
			@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "historyId") String historyId) {

		LogRefreshRequestDto requestDto = new LogRefreshRequestDto();
		LogRefreshResponseDto responseDto = new LogRefreshResponseDto();
		
		requestDto.flowId = flowId;
		requestDto.historyId = historyId;

		flowBusiness.refreshLog(requestDto, responseDto);
		
		return responseDto;
	}

	/**
	 * 
	 * @param flowId
	 * @param historyId
	 * @return
	 */
	@RequestMapping(value = "/request_log_clear", method = RequestMethod.POST)
	@ResponseBody
	public LogClearResponseDto requestClearLog(
			@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "historyId") String historyId) {

		LogClearRequestDto requestDto = new LogClearRequestDto();
		LogClearResponseDto responseDto = new LogClearResponseDto();
		
		requestDto.flowId = flowId;
		requestDto.historyId = historyId;

		flowBusiness.clearLog(requestDto, responseDto);
		
		return responseDto;
	}

}
