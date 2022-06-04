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
package io.github.nobuglady.jobflow.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import io.github.nobuglady.jobflow.service.log.LogListBusiness;
import io.github.nobuglady.jobflow.service.log.dto.FileDeleteResponseDto;
import io.github.nobuglady.jobflow.service.log.dto.LogFileDto;
import io.github.nobuglady.jobflow.service.log.dto.LogFolderDto;

/**
 * 
 * @author NoBugLady
 *
 */
@Controller
public class LogListController {

	@Autowired
	private LogListBusiness logListBusiness;

	/**
	 * 
	 * @param flowId
	 * @return
	 */
	@RequestMapping(value="/request_log_list", method=RequestMethod.POST)
	@ResponseBody
	public List<LogFolderDto> requestLogList() {
		
		return logListBusiness.requestLogList();
	}
	
	/**
	 * 
	 * @param flowId
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value="/request_log_list_file", method=RequestMethod.POST)
	@ResponseBody
	public List<LogFileDto> requestLogListFile(
			@RequestParam(value="flowId") String flowId) {
		
		return logListBusiness.requestLogListFile(flowId);
	}
	
	/**
	 * 
	 * @param flowId
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value="/request_log_list_file_download", method=RequestMethod.GET)
	public ResponseEntity<Resource> requestLogListFileDownload(
			@RequestParam(value="flowId") String flowId,
			@RequestParam(value="fileId") String fileId) {
		
		File file = logListBusiness.requestLogListFileDownload(flowId,fileId);
		
		if(file == null) {
			return null;
		}
		
		try {
		    InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
	
		    HttpHeaders headers = new HttpHeaders(); headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+file.getName());
		    
		    return ResponseEntity.ok()
		            .headers(headers)
		            .contentLength(file.length())
		            .contentType(MediaType.APPLICATION_OCTET_STREAM)
		            .body(resource);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param flowId
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value="/request_log_list_file_delete", method=RequestMethod.POST)
	@ResponseBody
	public FileDeleteResponseDto requestLogListFileDelete(
			@RequestParam(value="flowId") String flowId,
			@RequestParam(value="fileId") String fileId) {
		
		FileDeleteResponseDto responseDto = new FileDeleteResponseDto();
		
		logListBusiness.requestLogListFileDelete(flowId,fileId);
		
		return responseDto;
	}
}
