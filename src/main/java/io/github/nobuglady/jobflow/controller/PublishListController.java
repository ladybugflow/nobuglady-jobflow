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
import io.github.nobuglady.jobflow.service.flowpublish.PublishListBusiness;
import io.github.nobuglady.jobflow.service.flowpublish.dto.PublishListResponseDto;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class PublishListController {

	@Autowired
	private PublishListBusiness flowBusiness;

	///////////////////////////////////////
	// flow
	///////////////////////////////////////
	/**
	 * 
	 * @param curPage
	 * @return
	 */
	@RequestMapping(value = "/request_publish_list", method = RequestMethod.POST)
	@ResponseBody
	public PagingDto requestPublishList(@RequestParam(value = "curPage") int curPage) {

		int count = flowBusiness.requestPublishListCount();
		List<PublishListResponseDto> result = flowBusiness.requestPublishList(curPage);

		return new PagingDto(count, result);
	}

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	@RequestMapping(value = "/request_publish_remove", method = RequestMethod.POST)
	@ResponseBody
	public CommonResponse requestPublishRemove(@RequestParam(value = "flowId") String flowId) {

		flowBusiness.requestPublishRemove(flowId);

		return new CommonResponse();
	}
}
