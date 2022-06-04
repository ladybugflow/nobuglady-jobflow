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
package io.github.nobuglady.jobflow.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.util.WebUtils;

import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;

/**
 * Url interceptor
 * 
 * @author NoBugLady
 *
 */
@Component
public class UrlInterceptor implements AsyncHandlerInterceptor {

	/**
	 * pre handle
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		response.setHeader("Access-Control-Allow-Origin", "*");
		response.setHeader("Access-Control-Allow-Methods", "DELETE, POST, GET, PUT, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		
		if("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(200);
			return false;
		}
		
		String uri = getRequestUrl(request);
		String reqFullPath = uri.substring(request.getContextPath().length());

		AuthHolder.refreshUser(request.getSession());
		
		if (reqFullPath.startsWith("/home")) {
			UserEntity userEntity = AuthHolder.getUser();
			if (userEntity == null) {
				response.sendRedirect("/login");
				return false;
			}
			return true;
		} else if (reqFullPath.startsWith("/admin")) {
			UserEntity userEntity = AuthHolder.getUser();
			if (userEntity == null) {
				response.sendRedirect("/login");
				return false;
			}

			if (userEntity.getAdminFlag() == null || userEntity.getAdminFlag() != 1) {
				response.sendError(403);
				return false;
			}

			return true;
		} else {
			return true;
		}

	}

	/**
	 * get request url
	 * 
	 * @param request request
	 * @return request url
	 */
	private String getRequestUrl(HttpServletRequest request) {
		String uri = (String) request.getAttribute(WebUtils.INCLUDE_REQUEST_URI_ATTRIBUTE);
		if (uri == null) {
			uri = request.getRequestURI();
		}
		return uri;
	}
}
