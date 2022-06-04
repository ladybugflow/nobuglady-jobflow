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
package io.github.nobuglady.jobflow.security;

import javax.servlet.http.HttpSession;

import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;

/**
 * AuthHolder
 * 
 * @author NoBugLady
 *
 */
public class AuthHolder {

	private static ThreadLocal<UserEntity> userHolder = new ThreadLocal<>();
	
	/**
	 * save user
	 * 
	 * @param sessionId session id
	 * @param userEntity user entity
	 */
	public static void saveUser(HttpSession session, UserEntity userEntity) {
		session.setAttribute("loginUser", userEntity);
		userHolder.set(userEntity);
	}
	
	/**
	 * save user
	 * 
	 * @param sessionId session id
	 * @param userEntity user entity
	 */
	public static void setUser(UserEntity userEntity) {
		userHolder.set(userEntity);
	}
	
	/**
	 * 
	 * @param session
	 */
	public static void refreshUser(HttpSession session) {
		UserEntity userEntity = (UserEntity)session.getAttribute("loginUser");
		userHolder.set(userEntity);
	}
	
	/**
	 * get user
	 * 
	 * @param sessionId session id
	 * @return user entity
	 */
	public static UserEntity getUser() {
		return userHolder.get();
	}
	
	/**
	 * remove user
	 * 
	 * @param seesionId session id
	 */
	public static void removeUser() {
		userHolder.remove();
	}
	
	/**
	 * get user menu
	 * 
	 * @param sessionId session id
	 * @return user menu
	 */
	public static String getUserMenu() {
		UserEntity userEntity = userHolder.get();
		if(userEntity == null) {
			return "";
		}
		
		if(userEntity.getAdminFlag() != null && userEntity.getAdminFlag() == 1) {
//			return ""+userEntity.email+" | <a href='/admin' target='_tab'>admin</a> | <a href='/logout'>logout</a>";
			return ""+userEntity.email+" | <a href='/logout'>logout</a>";
		}else {
			return ""+userEntity.email+" | <a href='/logout'>logout</a>";
		}
	}
}
