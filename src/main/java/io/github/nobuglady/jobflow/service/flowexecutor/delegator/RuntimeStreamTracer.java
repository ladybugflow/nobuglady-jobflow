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
package io.github.nobuglady.jobflow.service.flowexecutor.delegator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import io.github.nobuglady.jobflow.logger.ConsoleLogger;

/**
 * 
 * @author NoBugLady
 *
 */
public class RuntimeStreamTracer implements Runnable{

	private BufferedReader br;
	private ConsoleLogger consoleLogger;
	private volatile boolean stopFlag = false;
	
	/**
	 * 
	 * @param is
	 * @param flowId
	 * @param historyId
	 */
	public RuntimeStreamTracer(InputStream is, String flowId, String historyId) {
		
		InputStreamReader isr = new InputStreamReader(is);
		br = new BufferedReader(isr);
		consoleLogger = ConsoleLogger.getInstance(flowId,historyId);
	}
	
	/**
	 * 
	 */
	public void doStop() {
		try {
			Thread.sleep(200);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		stopFlag = true;
	}
	
	/**
	 * 
	 */
	public void run() {
		
		try {
			String temp = null;
			while(!stopFlag) {
				temp = br.readLine();
				if(temp != null) {
					consoleLogger.info(temp);
				}else {
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						break;
					}
				}
				
			}
			
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
