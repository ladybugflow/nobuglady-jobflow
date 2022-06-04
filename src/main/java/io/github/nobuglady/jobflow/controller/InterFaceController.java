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

import io.github.nobuglady.jobflow.service.flowdynamic.dto.RequestAsyncResponseDto;
import io.github.nobuglady.jobflow.service.flowmanager.InterFaceService;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class InterFaceController {

	@Autowired
	private InterFaceService interFaceService;
	

	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param historyId
	 * @param result
	 * @return
	 */
	@RequestMapping(value = "/api/async", method = RequestMethod.POST)
	@ResponseBody
	public RequestAsyncResponseDto requestAsync(
			@RequestParam(value="flowId") String flowId,
			@RequestParam(value="nodeId") String nodeId,
			@RequestParam(value="historyId") String historyId,
			@RequestParam(value="result") String result) {
		
		return interFaceService.requestAsync(flowId, nodeId, historyId, result);
	}
	
	
}
