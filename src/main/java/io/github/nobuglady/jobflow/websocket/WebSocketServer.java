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

import javax.websocket.*;
import javax.websocket.server.*;

import org.springframework.stereotype.Component;

/**
 * 
 * @author NoBugLady
 *
 */
@ServerEndpoint("/websocket/{sid}")
@Component
public class WebSocketServer {

	/**
	 * 
	 * @param session
	 * @param sid
	 */
	@OnOpen
	public void onOpen(Session session, @PathParam("sid") String sid) {
		System.out.println("websocket open:" + session.getId());
		WebSocketManager.getInstance().saveSession(session);
	}

	/**
	 * 
	 * @param session
	 */
	@OnClose
	public void onClose(Session session) {
		System.out.println("websocket close:" + session.getId());
		WebSocketManager.getInstance().removeSession(session);
	}

	/**
	 * 
	 * @param message
	 * @param session
	 */
	@OnMessage
	public void onMessage(String message, Session session) {
		System.out.println(message);
		try {
			String header = message.split("#")[0];
			String body = message.split("#")[1];

			if ("setFlowHistory".equals(header)) {
				String flowId = body.split(",")[0];

				String historyId = null;
				if (body.split(",").length >= 2) {
					historyId = body.split(",")[1];
				}

				WebSocketManager.getInstance().updateFlowHistory(session, flowId, historyId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param session
	 * @param error
	 */
	@OnError
	public void onError(Session session, Throwable error) {
		error.printStackTrace();
	}

}