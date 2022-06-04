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

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.FlowStatus;
import io.github.nobuglady.jobflow.constant.NodeStatus;
import io.github.nobuglady.jobflow.constant.NodeStatusDetail;
import io.github.nobuglady.jobflow.constant.NodeType;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryFlowDao;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeDao;
import io.github.nobuglady.jobflow.persistance.db.dao.NodeDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.NodeEntity;
import io.github.nobuglady.jobflow.persistance.db.entity.UserEntity;
import io.github.nobuglady.jobflow.security.AuthHolder;
import io.github.nobuglady.jobflow.service.flowmanager.marker.FlowMarker;
import io.github.nobuglady.jobflow.service.flowqueue.complete.CompleteNodeResult;
import io.github.nobuglady.jobflow.service.flowqueue.ready.ReadyQueueManager;
import io.github.nobuglady.jobflow.websocket.WebSocketManager;

/**
 * 
 * @author NoBugLady
 *
 */
@Component
public class FlowManager {

    @Autowired
    private FlowMarker flowMarker;
    
	@Autowired
	private HistoryFlowDao historyFlowDao;

	@Autowired
	private HistoryNodeDao historyNodeDao;

	@Autowired
	private NodeDao nodeDao;

	public FlowManager() {

        CompleteQueueConsumerThread statusNotifyThread = new CompleteQueueConsumerThread(this);
        statusNotifyThread.start();
        System.out.println("Complete queue thread started.");

	}
	
	////////////////////////////////////////////////////////
	// call back
	////////////////////////////////////////////////////////

    /**
     * 
     * @param nodeResult
     */
    public void onNodeComplete(CompleteNodeResult nodeResult) {

    	String flowId = nodeResult.getFlowId();
    	String historyId = nodeResult.getHistoryId();

        try {
        	flowMarker.onNodeComplete(nodeResult);

            List<HistoryNodeEntity> readyNodeList = getReadyNode(flowId, historyId);

            if (readyNodeList.size() > 0) {
                for (HistoryNodeEntity readyNode : readyNodeList) {
                    startNode(flowId, historyId, readyNode.getNodeId());
                }
            } else {
            	
                List<HistoryNodeEntity> runningNodeList = getRunningNode(flowId, historyId);
                List<HistoryNodeEntity> openingNodeList = getOpenningNode(flowId, historyId);
                List<HistoryNodeEntity> errorNodeList = getErrorNode(flowId, historyId);
                
                if (runningNodeList.size() == 0 && openingNodeList.size() == 0) {
                    System.out.println("Complete.");
                    if(errorNodeList.size() > 0) {
                    	updateFlowStatus(flowId, historyId, true);
                    }else {
                    	updateFlowStatus(flowId, historyId, false);
                    }
                    
                    WebSocketManager.getInstance().notifyFlowComplete(flowId, historyId);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            updateFlowStatus(flowId, historyId, true);
        }
        
        WebSocketManager.getInstance().notifyFlowUpdate(flowId, historyId);
    }


	////////////////////////////////////////////////////////
	// start
	////////////////////////////////////////////////////////

	/**
	 * 
	 * @param flowId
	 * @return
	 */
    public String startFlow(String flowId) {

        // create instance
        String historyId = String.valueOf(historyFlowDao.createHistory(flowId));
        
        // initialize flow
        historyNodeDao.updateStatusByHistoryId(flowId,historyId, NodeStatus.WAIT);

        // start first node
        startNode(flowId, historyId, getFirstNodeId(flowId));
        
        return historyId;
    }

    /**
     * 
     * @param flowId
     * @param historyId
     * @return
     */
    public String recoveryFlow(
    		String flowId, 
    		String historyId) {
    	
    	UserEntity userEntity = new UserEntity();
    	userEntity.setEmail(Const.USER_SYS);
    	
    	AuthHolder.setUser(userEntity);
    	
    	reStartFlow(flowId, historyId);
        
        return historyId;
    }
    

    /**
     * 
     * @param flowId
     * @param historyIdParam
     * @return
     */
    public String reStartFlow(
    		String flowId, 
    		String historyId) {

        // initialize flow
        historyNodeDao.updateStatusByHistoryId(flowId,historyId, NodeStatus.WAIT);

        // start first node
        startNode(flowId, historyId, getFirstNodeId(flowId));
        
        return historyId;
    }
    

	/**
	 * 
	 * @param flowId
	 * @param nodeId
	 * @return
	 */
    public String startNode(
    		String flowId, 
    		String nodeId) {

        // create instance
        String historyId = String.valueOf(historyFlowDao.createHistory(flowId));
        
        // initialize flow
        historyNodeDao.updateStatusByHistoryId(flowId,historyId, NodeStatus.WAIT);

        // start node
        startNode(flowId, historyId, nodeId);
        
        return historyId;
    }

    /**
     * 
     * @param flowId
     * @param historyId
     * @param nodeId
     * @return
     */
    public String reStartNode(
    		String flowId, 
    		String historyId, 
    		String nodeId) {

    	historyNodeDao.updateStatusByNodeId(flowId, historyId, nodeId, NodeStatus.READY);
    	
        // start node
        startNode(flowId, historyId, nodeId);
        
        return historyId;
    }
    
    /**
     * 
     * @param flowId
     * @param personId
     * @param personName
     * @param personParam
     * @return
     */
	public String startFlowByInterface(
			String flowId, 
			String personId, 
			String personName, 
			String personParam) {

        // create instance
        String historyId = String.valueOf(historyFlowDao.createHistory(flowId));
        
        // initialize flow
        historyNodeDao.updateStatusByHistoryId(flowId,historyId, NodeStatus.WAIT);

        // start first node
        startNode(flowId, historyId, getFirstNodeId(flowId));
        
        return historyId;
	}

	////////////////////////////////////////////////////////
	// shutdown
	////////////////////////////////////////////////////////

	/**
	 * 
	 * @param flowId
	 * @param instanceId
	 * @param hasError
	 */
    public void updateFlowStatus(String flowId, String historyId, boolean hasError) {
    	
    	if(hasError) {
    		historyFlowDao.updateFlowStatus(flowId, historyId, FlowStatus.ERROR);
    	} else {
    		historyFlowDao.updateFlowStatus(flowId, historyId, FlowStatus.COMPLETE);
    	}
    }


    //////////////////////////////
    // help function
    //////////////////////////////

    /**
     * 
     * @param flowId
     * @return
     */
    private String getFirstNodeId(String flowId) {
    	
    	List<NodeEntity> nodeEntityList = nodeDao.selectByFlowId(flowId);
    	if(nodeEntityList != null) {
    		for(NodeEntity entity:nodeEntityList) {
    			if(entity.getNodeType() == NodeType.NODE_TYPE_START ) {
    				return entity.getNodeId();
    			}
    		}
    	}
		return null;
	}

    /**
     * 
     * @param flowId
     * @param historyId
     * @param nodeId
     */
    private void startNode(String flowId, String historyId, String nodeId) {
    	
    	ReadyQueueManager.getInstance().putReadyNode(flowId, historyId, nodeId);
	}
    
    /**
     * 
     * @param flowId
     * @param historyId
     * @return
     */
    private List<HistoryNodeEntity> getReadyNode(String flowId, String historyId) {
    	
    	List<HistoryNodeEntity> result = new ArrayList<>();
    	
    	List<HistoryNodeEntity> result_ready = historyNodeDao.selectNodeListByStatus(flowId, historyId, NodeStatus.READY);
    	
    	if(result_ready != null) {
    		result.addAll(result_ready);
    	}
    	return result;
    }

    /**
     * 
     * @param flowId
     * @param historyId
     * @return
     */
    private List<HistoryNodeEntity> getRunningNode(String flowId, String historyId) {
    	List<HistoryNodeEntity> result = historyNodeDao.selectNodeListByStatus(flowId, historyId, NodeStatus.RUNNING);
    	if(result == null) {
    		return new ArrayList<>();
    	}
    	return result;
    }
    
    /**
     * 
     * @param flowId
     * @param historyId
     * @return
     */
    private List<HistoryNodeEntity> getOpenningNode(String flowId, String historyId) {
    	List<HistoryNodeEntity> result = historyNodeDao.selectNodeListByStatus(flowId, historyId, NodeStatus.OPENNING);
    	if(result == null) {
    		return new ArrayList<>();
    	}
    	return result;
    }
    
    /**
     * 
     * @param flowId
     * @param historyId
     * @return
     */
    private List<HistoryNodeEntity> getErrorNode(String flowId, String historyId) {
    	List<HistoryNodeEntity> result = historyNodeDao.selectNodeListByStatusDetail(flowId, historyId, NodeStatus.COMPLETE, NodeStatusDetail.COMPLETE_ERROR);
    	if(result == null) {
    		return new ArrayList<>();
    	}
    	return result;
    }
}
