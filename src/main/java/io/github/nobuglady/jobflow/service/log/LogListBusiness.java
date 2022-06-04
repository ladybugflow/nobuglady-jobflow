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
import java.util.List;

import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.service.log.dto.LogFileDto;
import io.github.nobuglady.jobflow.service.log.dto.LogFolderDto;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class LogListBusiness {

	/**
	 * 
	 * @return
	 */
	public List<LogFolderDto> requestLogList() {
		
		File root = new File(Const.LOG_ROOT_DIR);
		File[] files = root.listFiles();
		
		List<LogFolderDto> resultList = new ArrayList<>();
		if(files != null) {
			for(File file:files) {
				LogFolderDto logFolderDto = new LogFolderDto();
				logFolderDto.flowId = file.getName();
				resultList.add(logFolderDto);
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
		
		File root = new File(Const.LOG_ROOT_DIR + flowId);
		File[] files = root.listFiles();
		
		List<LogFileDto> resultList = new ArrayList<>();
		if(files != null) {
			for(File file:files) {
				LogFileDto logFolderDto = new LogFileDto();
				logFolderDto.flowId = flowId;
				logFolderDto.fileId = file.getName();
				resultList.add(logFolderDto);
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
		
		File file = new File(Const.LOG_ROOT_DIR + flowId + "/" + fileId);
		if(file.exists()) {
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

		File file = new File(Const.LOG_ROOT_DIR + flowId + "/" + fileId);
		if(file.exists()) {
			file.delete();
		}
	}

}
