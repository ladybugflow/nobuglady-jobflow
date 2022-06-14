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
import io.github.nobuglady.jobflow.controller.dto.model.HistoryFlowEntityVo;
import io.github.nobuglady.jobflow.service.flowdynamic.InstanceListBusiness;
import io.github.nobuglady.jobflow.util.BeanUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class InstanceListController {

	@Autowired
	private InstanceListBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param curPage
	 * @param flowName
	 * @param flowName
	 * @param flowStartDate
	 * @return
	 */
	@RequestMapping(value = "/request_instance_list", method = RequestMethod.POST)
	@ResponseBody
	public PagingDto requestInstanceList(@RequestParam(value = "curPage") int curPage,
			@RequestParam(value = "flowName") String flowName, @RequestParam(value = "flowStatus") String flowStatus,
			@RequestParam(value = "flowStartDate") String flowStartDate) {

		int count = flowBusiness.requestInstanceListCount(flowName, flowStatus, flowStartDate);
		List<HistoryFlowEntityVo> result = BeanUtil.copyList(
				flowBusiness.requestInstanceList(curPage, flowName, flowStatus, flowStartDate),
				HistoryFlowEntityVo.class);

		return new PagingDto(count, result);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/request_processing_instance_list", method = RequestMethod.POST)
	@ResponseBody
	public List<HistoryFlowEntityVo> requestProcessingInstanceList() {

		return BeanUtil.copyList(flowBusiness.requestProcessingInstanceList(), HistoryFlowEntityVo.class);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/request_error_instance_list", method = RequestMethod.POST)
	@ResponseBody
	public List<HistoryFlowEntityVo> requestErrorInstanceList() {

		return BeanUtil.copyList(flowBusiness.requestErrorInstanceList(), HistoryFlowEntityVo.class);
	}

	/**
	 * 
	 * @return
	 */
	@RequestMapping(value = "/request_complete_instance_list", method = RequestMethod.POST)
	@ResponseBody
	public List<HistoryFlowEntityVo> requestCompleteInstanceList() {

		return BeanUtil.copyList(flowBusiness.requestCompleteInstanceList(), HistoryFlowEntityVo.class);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	@RequestMapping(value = "/request_flow_history_remove", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse requestFlowRemove(@RequestParam(value = "flowId") String flowId,
			@RequestParam(value = "historyId") String historyId) {

		flowBusiness.requestFlowHistoryRemove(flowId, historyId);

		return new CommonResponse();
	}
}
