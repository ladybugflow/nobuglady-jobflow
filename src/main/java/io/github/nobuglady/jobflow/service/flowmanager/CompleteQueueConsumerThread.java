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
package io.github.nobuglady.jobflow.service.flowmanager;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteNodeResult;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteQueueManager;

/**
 * 
 * @author NoBugLady
 *
 */
public class CompleteQueueConsumerThread extends Thread {

	private volatile boolean stopFlag = false;

	private FlowManager flowManager;

	/**
	 * 
	 * @param flowManager
	 */
	public CompleteQueueConsumerThread(FlowManager flowManager) {
		this.flowManager = flowManager;
	}

	/**
	 * 
	 */
	public void run() {

		UserEntity userEntity = new UserEntity();
		userEntity.setEmail(Const.USER_SYS);

		AuthHolder.setUser(userEntity);

		while (true) {
			try {
				CompleteNodeResult nodeResult = CompleteQueueManager.getInstance().takeCompleteNode();
				flowManager.onNodeComplete(nodeResult);
			} catch (InterruptedException e) {
				if (!this.stopFlag) {
					e.printStackTrace();
				} else {
					break;
				}
			}
		}
	}

	/**
	 * 
	 */
	public void shutdown() {

		this.stopFlag = true;
		this.interrupt();
	}

}
