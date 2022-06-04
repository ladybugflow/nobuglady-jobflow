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
package io.github.nobuglady.jobflow.persistance.db.entity;

import java.util.Date;

/**
 * 
 * @author NoBugLady
 *
 */
public class PublishNodeHttpEntity {

	private String flowId;
	private String nodeId;
	private String timeout;
	private int timeoutType;
	private String successCode;
	private int errorType;
	private int syncFlag;
	private String httpUrl;
	private String httpMethod;
	private String httpHeader;
	private String httpBody;
	private String httpContentType;
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	
	public String getFlowId() {
		return flowId;
	}
	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getTimeout() {
		return timeout;
	}
	public void setTimeout(String timeout) {
		this.timeout = timeout;
	}
	public int getTimeoutType() {
		return timeoutType;
	}
	public void setTimeoutType(int timeoutType) {
		this.timeoutType = timeoutType;
	}
	public String getSuccessCode() {
		return successCode;
	}
	public void setSuccessCode(String successCode) {
		this.successCode = successCode;
	}
	public int getErrorType() {
		return errorType;
	}
	public void setErrorType(int errorType) {
		this.errorType = errorType;
	}
	public int getSyncFlag() {
		return syncFlag;
	}
	public void setSyncFlag(int syncFlag) {
		this.syncFlag = syncFlag;
	}
	public String getHttpUrl() {
		return httpUrl;
	}
	public void setHttpUrl(String httpUrl) {
		this.httpUrl = httpUrl;
	}
	public String getHttpMethod() {
		return httpMethod;
	}
	public void setHttpMethod(String httpMethod) {
		this.httpMethod = httpMethod;
	}
	public String getHttpHeader() {
		return httpHeader;
	}
	public void setHttpHeader(String httpHeader) {
		this.httpHeader = httpHeader;
	}
	public String getHttpBody() {
		return httpBody;
	}
	public void setHttpBody(String httpBody) {
		this.httpBody = httpBody;
	}
	public String getHttpContentType() {
		return httpContentType;
	}
	public void setHttpContentType(String httpContentType) {
		this.httpContentType = httpContentType;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
