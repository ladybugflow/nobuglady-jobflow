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
package io.github.nobuglady.jobflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeDao;
import io.github.nobuglady.jobflow.service.flowdynamic.FlowDynamicBusiness;
import io.github.nobuglady.jobflow.service.flowmanager.FlowManager;
import io.github.nobuglady.jobflow.service.timer.FlowTimerTriger;

/**
 * 
 * @author NoBugLady
 *
 */
@SpringBootApplication
public class NobugladyJobflowApplication {

	@Autowired
	private FlowDynamicBusiness flowBusiness;
	
	@Autowired
	private PublishNodeDao publishNodeDao;
	
	@Autowired
	private FlowManager flowManager;

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SpringApplication.run(NobugladyJobflowApplication.class, args);
	}

	/**
	 * 
	 */
	@EventListener(ApplicationReadyEvent.class)
	public void doSomethingAfterStartup() {
	    flowBusiness.initFlowOnStartup();
	    new Thread(new FlowTimerTriger(publishNodeDao, flowManager, new ThreadPoolTaskScheduler())).start();
	}

}
