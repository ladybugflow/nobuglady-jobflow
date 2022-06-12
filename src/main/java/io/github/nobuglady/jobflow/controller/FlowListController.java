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

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nobuglady.jobflow.controller.dto.CommonResponse;
import io.github.nobuglady.jobflow.controller.dto.PagingDto;
import io.github.nobuglady.jobflow.service.flowstatic.FlowListBusiness;
import io.github.nobuglady.jobflow.service.flowstatic.dto.FlowListResponseDto;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class FlowListController {

	@Autowired
	private FlowListBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "/request_flow_list", method = RequestMethod.POST)
	@ResponseBody
	public PagingDto requestFlowList(@RequestParam(value = "curPage") int curPage) {

		int count = flowBusiness.requestFlowListCount();
		List<FlowListResponseDto> result = flowBusiness.requestFlowList(curPage);

		return new PagingDto(count, result);
	}

	/**
	 * 
	 * @param flowId
	 * @param flowName
	 * @return
	 */
	@RequestMapping(value = "/request_create_flow", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse requestCreateFlow(@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "flowName") String flowName) {

		flowBusiness.requestCreateFlow(flowId, flowName);

		return new CommonResponse();
	}
}
