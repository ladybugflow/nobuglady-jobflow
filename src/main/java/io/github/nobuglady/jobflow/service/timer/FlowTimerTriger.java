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
package io.github.nobuglady.jobflow.service.timer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.scheduling.support.CronTrigger;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.persistance.db.dao.PublishNodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.PublishNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.flowmanager.FlowManager;

/**
 * 
 * @author NoBugLady
 *
 */
public class FlowTimerTriger implements Runnable{

	private PublishNodeDao publishNodeDao;
	
	private FlowManager flowManager;
	
	private ThreadPoolTaskScheduler taskScheduler;
	
	private static Set<String> scheduledFlowIdSet = new HashSet<String>();
	
	/**
	 * 
	 * @param historyNodeDao
	 */
	public FlowTimerTriger(PublishNodeDao publishNodeDao, FlowManager flowManager, ThreadPoolTaskScheduler taskScheduler) {
		taskScheduler.initialize();
		
		this.publishNodeDao = publishNodeDao;
		this.flowManager = flowManager;
		this.taskScheduler = taskScheduler;
	}
	
	/**
	 * 
	 */
	@Override
	public void run() {
		
    	UserEntity userEntity = new UserEntity();
    	userEntity.setEmail(Const.USER_SYS);
    	
    	AuthHolder.setUser(userEntity);
    	
		while(true) {
			try {

				System.out.println("check flow.");
				
				// check flow
				List<PublishNodeEntity> publishNodeList = publishNodeDao.selectAllCronNode();
				
				// add to start list
				if(publishNodeList != null) {
					for(PublishNodeEntity historyNodeEntity:publishNodeList) {
						if(scheduledFlowIdSet.add(historyNodeEntity.getFlowId())) {
							register(historyNodeEntity.getStartCron(), historyNodeEntity.getFlowId());
						} else {
							System.out.println("already scheduled:"+historyNodeEntity.getFlowId());
						}
						
					}
				}
				
				Thread.sleep(1000*60);
				
			} catch (InterruptedException e) {
				e.printStackTrace();
				break;
			}
		}

	}
	
	/**
	 * 
	 * @param cron
	 * @param flowId
	 * @return
	 */
    private ScheduledFuture<?> register(String cron, String flowId) {

        if(!CronExpression.isValidExpression(cron)) {
        	System.out.println("not a valied expression:"+cron);
        	return null;
        }

        CronExpression exp = CronExpression.parse(cron);
        LocalDateTime nextTime = exp.next(LocalDateTime.now());
        
        if (nextTime != null) {
            System.out.println("["+flowId+"] next execute time:"+nextTime.format(DateTimeFormatter.ofPattern ("yyyy/MM/dd HH:mm:ss")));
        }

        return taskScheduler.schedule(new Runnable() {
            @Override
            public void run() {

            	UserEntity userEntity = new UserEntity();
            	userEntity.setEmail(Const.USER_SYS);
            	
            	AuthHolder.setUser(userEntity);
            	
            	flowManager.startFlow(flowId);
            }
        }, new CronTrigger(cron));
    }

}
