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
package io.github.nobuglady.jobflow.service.log;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.persistance.db.dao.FlowDao;
import io.github.nobuglady.jobflow.persistance.db.entity.FlowEntity;
import io.github.nobuglady.jobflow.service.log.dto.LogFileDto;
import io.github.nobuglady.jobflow.service.log.dto.LogFolderDto;
import io.github.nobuglady.jobflow.util.DateUtil;
import io.github.nobuglady.jobflow.util.PropertiesUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class LogListBusiness {

	private static String logDir = PropertiesUtil.getLogDir();

	@Autowired
	private FlowDao flowDao;

	/**
	 * 
	 * @return
	 */
	public List<LogFolderDto> requestLogList() {

		File root = new File(logDir);
		File[] files = root.listFiles();

		List<LogFolderDto> resultList = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				if (file.isDirectory()) {

					FlowEntity flowEntity = flowDao.selectByKey(file.getName());

					LogFolderDto logFolderDto = new LogFolderDto();
					logFolderDto.flowId = file.getName();
					if (flowEntity != null) {
						logFolderDto.flowName = flowEntity.getFlowName();
					}

					resultList.add(logFolderDto);
				}
			}
		}
		return resultList;
	}

	/**
	 * 
	 * @param flowId
	 * @param fileId
	 * @return
	 */
	public List<LogFileDto> requestLogListFile(String flowId) {

		File root = new File(logDir + flowId);
		File[] files = root.listFiles();

		List<LogFileDto> resultList = new ArrayList<>();
		if (files != null) {
			for (File file : files) {
				if (file.isFile() && !file.isHidden()) {
					LogFileDto logFileDto = new LogFileDto();
					logFileDto.flowId = flowId;
					logFileDto.fileId = file.getName();
					logFileDto.updateTime = DateUtil.dateToString(new Date(file.lastModified()),
							DateUtil.FMT_YYYYMMDD_HHMMSS);
					logFileDto.size = file.length() / 1024 + "kb";
					resultList.add(logFileDto);
				}
			}
		}
		return resultList;
	}

	/**
	 * 
	 * @param flowId
	 * @param fileId
	 */
	public File requestLogListFileDownload(String flowId, String fileId) {

		File file = new File(logDir + flowId + "/" + fileId);
		if (file.exists()) {
			return file;
		}

		return null;
	}

	/**
	 * 
	 * @param flowId
	 * @param fileId
	 */
	public void requestLogListFileDelete(String flowId, String fileId) {

		File file = new File(logDir + flowId + "/" + fileId);
		if (file.exists()) {
			file.delete();
		}
	}

}
