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
package io.github.nobuglady.jobflow.service.home;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.persistance.db.dao.UserDao;
import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
@Transactional
public class LoginBusiness {

	@Autowired
	private UserDao userDao;

	/**
	 * 
	 * @param session
	 * @param email
	 * @param password
	 * @return
	 */
	public int login(HttpSession session, String email, String password) {

		UserEntity userEntity = userDao.getByEmailPassword(email, password);

		if (userEntity == null) {
			return Const.LOGIN_FAILED;
		} else {
			AuthHolder.saveUser(session, userEntity);
			return Const.LOGIN_SUCCESS;
		}
	}
}
