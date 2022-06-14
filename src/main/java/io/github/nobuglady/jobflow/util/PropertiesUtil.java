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
package io.github.nobuglady.jobflow.util;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

/**
 * 
 * @author NoBugLady
 *
 */
public class PropertiesUtil {

	public static String getLogDir() {

		String logDir = "c:/tmp/";

		Resource resource = new ClassPathResource("/application.properties");
		try {
			Properties props = PropertiesLoaderUtils.loadProperties(resource);
			logDir = props.getOrDefault("log.dir", logDir).toString();
			if (!logDir.endsWith("/") && !logDir.endsWith("\\")) {
				logDir = logDir + File.separator;
			}
			System.out.println("logDir:" + logDir);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return logDir;
	}
}
