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
package io.github.nobuglady.jobflow.websocket;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.websocket.Session;

import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
public class WebSocketManager {

	private static WebSocketManager instance = new WebSocketManager();

	private List<Session> sessionList = new ArrayList<Session>();
	Map<String, String[]> sessionFlowHistoryMap = new HashMap<String, String[]>();

	private WebSocketManager() {

	}

	/**
	 * 
	 * @return
	 */
	public static WebSocketManager getInstance() {
		return instance;
	}

	/**
	 * 
	 * @param session
	 */
	public void saveSession(Session session) {
		sessionList.add(session);
	}

	/**
	 * 
	 * @param session
	 */
	public void removeSession(Session session) {
		sessionList.remove(session);
	}

	/**
	 * 
	 * @param session
	 * @param message
	 */
	public void sendMessage(Session session, String message) {
		try {
			session.getBasicRemote().sendText(message);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param flowIdParam
	 * @param historyIdParam
	 */
	public void notifyFlowUpdate(String flowIdParam, String historyIdParam) {
		for (Map.Entry<String, String[]> entry : sessionFlowHistoryMap.entrySet()) {
			String sessionId = entry.getKey();
			String flowId = entry.getValue()[0];
			String historyId = entry.getValue()[1];

			if (flowIdParam.equals(flowId) && historyIdParam.equals(historyId)) {
				Session session = getSession(sessionId);
				if (session != null && session.isOpen()) {
					String message = "update#" + flowId + "," + historyId;
					System.out.println("send:" + message);
					try {
						sendMessage(session, message);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	/**
	 * 
	 * @param flowIdParam
	 * @param historyIdParam
	 */
	public void notifyFlowComplete(String flowIdParam, String historyIdParam) {
		for (Map.Entry<String, String[]> entry : sessionFlowHistoryMap.entrySet()) {
			String sessionId = entry.getKey();
			String flowId = entry.getValue()[0];
			String historyId = entry.getValue()[1];

			if (flowIdParam.equals(flowId) && historyIdParam.equals(historyId)) {
				Session session = getSession(sessionId);
				if (session != null && session.isOpen()) {
					String message = "complete#" + flowId + "," + historyId;
					System.out.println("send:" + message);
					try {
						sendMessage(session, message);
					} catch (Exception e) {
						e.printStackTrace();
					}

				}
			}
		}
	}

	/**
	 * 
	 * @param sessionId
	 * @return
	 */
	private Session getSession(String sessionId) {

		for (Session session : sessionList) {
			if (session.getId().equals(sessionId)) {
				return session;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param session
	 * @param flowId
	 * @param historyId
	 */
	public void updateFlowHistory(Session session, String flowId, String historyId) {
		if (StringUtil.isEmpty(flowId) || StringUtil.isEmpty(historyId)) {
			sessionFlowHistoryMap.remove(session.getId());
		} else {
			sessionFlowHistoryMap.put(session.getId(), new String[] { flowId, historyId });
		}
	}
}
