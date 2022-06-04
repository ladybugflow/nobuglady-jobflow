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

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.constant.NodeStatus;
import io.github.nobuglady.jobflow.constant.NodeStatusDetail;
import io.github.nobuglady.jobflow.logger.ConsoleLogger;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.service.flowdynamic.dto.RequestAsyncResponseDto;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteQueueManager;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class InterFaceService {

	@Autowired
	private HistoryNodeDao historyNodeDao;
	
	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @param historyId
	 * @param result
	 * @return
	 */
	public RequestAsyncResponseDto requestAsync(String flowId, String nodeId, String historyId, String result) {

		RequestAsyncResponseDto responseDto = new RequestAsyncResponseDto();
		
		ConsoleLogger consoleLogger = ConsoleLogger.getInstance(flowId,historyId);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss.SSS");
		
		HistoryNodeEntity historyNodeEntity = historyNodeDao.selectByKey(flowId, nodeId, historyId);

        /*
         * Complete
         */
		if("0".equals(result)) {
			historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE, NodeStatusDetail.COMPLETE_SUCCESS);
	        CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);
	        consoleLogger.info(sdf.format(new Date()) + " [NODE COMPLETE]["+result+"]" + historyNodeEntity.getNodeName());
		}else {
			consoleLogger.error(sdf.format(new Date()) + " [NODE ERROR]" + historyNodeEntity.getNodeName(), new Exception(result));
            historyNodeDao.updateStatusDetailByNodeId(flowId, historyId, nodeId, NodeStatus.COMPLETE, NodeStatusDetail.COMPLETE_ERROR);
            CompleteQueueManager.getInstance().putCompleteNode(flowId, historyId, nodeId);
		}
        
        responseDto.result = "ok";
		return responseDto;
	}
	
}
