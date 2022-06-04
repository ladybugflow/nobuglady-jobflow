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

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.github.nobuglady.jobflow.security.AuthHolder;

/**
 * Url controller
 * 
 * @author NoBugLady
 *
 */
@Controller
public class UrlController {

	@RequestMapping("/")
	public String index() {

		return "redirect:/home";
	}

	@GetMapping("/login")
	public String login() {

		return "login";
	}

	@GetMapping("/logout")
	public String logout(HttpSession session) {

		AuthHolder.removeUser();

		return "redirect:/login";
	}

	@RequestMapping("/home")
	public String home() {

		return "home";
	}

	@RequestMapping("/flow_list")
	public String flowList() {

		return "flow_list";
	}

	@RequestMapping("/publish_list")
	public String publishList() {

		return "publish_list";
	}

	@RequestMapping("/instance_list")
	public String instanceList() {

		return "instance_list";
	}

	@RequestMapping("/log_list")
	public String logList() {

		return "log_list";
	}

	@RequestMapping("/log_list_file")
	public String logListFile() {

		return "log_list_file";
	}

	@RequestMapping("/flow_panel_execute")
	public String flowPanelExecute() {

		return "flow_panel_execute";
	}

	@RequestMapping("/flow_panel_editor")
	public String flowPanelEditor() {

		return "flow_panel_editor";
	}

	@RequestMapping("/admin")
	public String admin() {

		return "admin";
	}
}
