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
package io.github.nobuglady.jobflow.logger;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import io.github.nobuglady.jobflow.constant.Const;

/**
 * ConsoleLogger
 * 
 * @author NoBugLady
 *
 */
public class ConsoleLogger {

	private static Map<String, ConsoleLogger> instanceMap = new HashMap<>();

//	private String flowId;
//	private String historyId;

	File logFile;
	private PrintWriter pw;

	/**
	 * constructor
	 */
	private ConsoleLogger(String flowId, String historyId) {

//		this.flowId = flowId;
//		this.historyId = historyId;

		File dir = new File(Const.LOG_ROOT_DIR + flowId + "/");
		dir.mkdirs();

		logFile = new File(Const.LOG_ROOT_DIR + flowId + "/" + historyId + ".log");

		if (!logFile.exists()) {
			try {
				System.out.println("create new file" + logFile.toString());
				logFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		try {
			FileOutputStream fos = new FileOutputStream(logFile, true);
			OutputStreamWriter osw = new OutputStreamWriter(fos, Charset.forName("UTF-8"));
			pw = new PrintWriter(osw, true);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}

		Resource resource = new ClassPathResource("/application.properties");
		Integer logSize = 64;
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			logSize = Integer.parseInt(props.getOrDefault("log.size", 64).toString());
			System.out.println("log size:" + logSize);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * get instance
	 * 
	 * @param method method
	 * @param path   path
	 * @return instance
	 */
	public static synchronized ConsoleLogger getInstance(String flowId, String historyId) {
		String instanceId = flowId + "," + historyId;
		ConsoleLogger instance = instanceMap.get(instanceId);
		if (instance == null) {
			instance = new ConsoleLogger(flowId, historyId);
			instanceMap.put(instanceId, instance);
		}
		return instance;
	}

	/**
	 * info
	 * 
	 * @param message message
	 */
	public void info(String message) {
		pw.println(message);
	}

	/**
	 * error
	 * 
	 * @param message message
	 */
	public void error(String message, Throwable e) {
		pw.println(message);
		pw.println(e.getMessage());
	}

	/**
	 * get message list
	 * 
	 * @return message list
	 */
	public List<String> getMessages() {

		try {
			return Files.readAllLines(logFile.toPath());
		} catch (IOException e) {
			e.printStackTrace();
		}

		return new ArrayList<>();
	}

	/**
	 * clear log
	 */
	public void clear() {
		System.out.println("clear log:" + logFile.toString());
		logFile.delete();
	}

}
