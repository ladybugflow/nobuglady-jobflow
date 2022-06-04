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

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nobuglady.jobflow.controller.dto.WebLoginRequestDto;
import io.github.nobuglady.jobflow.controller.dto.WebLoginResponseDto;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.home.LoginBusiness;

/**
 * Login controller
 * 
 * @author NoBugLady
 *
 */
@Controller
public class LoginController {

	@Autowired
	private LoginBusiness loginBusiness;

	/**
	 * 
	 * @param requestDto
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@ResponseBody
	public WebLoginResponseDto login(@RequestBody WebLoginRequestDto requestDto, HttpSession session) {

		WebLoginResponseDto responseDto = new WebLoginResponseDto();

		responseDto.result = loginBusiness.login(session, requestDto.username, requestDto.password);

		return responseDto;
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/menu", method = RequestMethod.POST)
	@ResponseBody
	public String getMenu(HttpSession session) {

		return AuthHolder.getUserMenu();
	}
}
