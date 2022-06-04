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

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import io.github.nobuglady.jobflow.logger.ConsoleLogger;
import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeHttpDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeHttpEntity;
import io.github.nobuglady.jobflow.service.flowexecutor.NodeInf;

/**
 * 
 * @author NoBugLady
 *
 */
public class NodeDelegatorHttp implements NodeInf {

	private HistoryNodeHttpEntity historyNodeHttpEntity;
	private HistoryNodeHttpDao historyNodeHttpDao;

	/**
	 * 
	 * @param historyNodeHttpEntity
	 * @param historyNodeHttpDao
	 */
	public NodeDelegatorHttp(HistoryNodeHttpEntity historyNodeHttpEntity, HistoryNodeHttpDao historyNodeHttpDao) {
		this.historyNodeHttpEntity = historyNodeHttpEntity;
		this.historyNodeHttpDao = historyNodeHttpDao;
	}

	/**
	 * 
	 */
	@Override
	public String execute() throws Throwable {

		/*
		 * check body
		 */
		String body = historyNodeHttpEntity.getHttpBody();
		if (body != null && !"".equals(body)) {
			body = body.replace("\r", "").replace("\n", "");
		}

		// variable replace

		StringBuffer responseStr = new StringBuffer();

		/*
		 * open connection
		 */
		URL urlObj = new URL(historyNodeHttpEntity.getHttpUrl());
		HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();

		/*
		 * set header
		 */
		httpConnection.setRequestMethod(historyNodeHttpEntity.getHttpMethod());

		/*
		 * send content
		 */
		if ("GET".equalsIgnoreCase(historyNodeHttpEntity.getHttpMethod())) {
			printRequestInfo(httpConnection);
			httpConnection.connect();
		} else {
			httpConnection.setDoOutput(true);
			if (historyNodeHttpEntity.getHttpContentType() != null
					&& !"".equals(historyNodeHttpEntity.getHttpContentType())) {
				httpConnection.setRequestProperty("Content-Type", historyNodeHttpEntity.getHttpContentType());
			}

			httpConnection.setRequestProperty("Content-Length", String.valueOf(body.length()));

			printRequestInfo(httpConnection);

			updateRequest(body);

			OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(httpConnection.getOutputStream()));
			if (body != null && !"".equals(body)) {
				out.write(body);
			}
			out.close();
		}

		/*
		 * read response
		 */

		int responseCode = httpConnection.getResponseCode();

		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream inputStream = httpConnection.getInputStream();
			String encoding = httpConnection.getContentEncoding();
			if (null == encoding) {
				encoding = "UTF-8";
			}

			InputStreamReader isr = new InputStreamReader(inputStream, encoding);
			BufferedReader br = new BufferedReader(isr);

			String line = null;
			while ((line = br.readLine()) != null) {
				responseStr.append(line);
			}

			br.close();
			isr.close();
			inputStream.close();

		} else {
			throw new Exception("response code error:" + responseCode);
		}

		/*
		 * update to database
		 */
		updateResponse(String.valueOf(responseCode), responseStr.toString());

		return String.valueOf(responseCode);
	}

	/**
	 * 
	 * @param httpConnection
	 */
	private void printRequestInfo(HttpURLConnection httpConnection) {

		ConsoleLogger consoleLogger = ConsoleLogger.getInstance(historyNodeHttpEntity.getFlowId(),
				historyNodeHttpEntity.getHistoryId());

		consoleLogger.info("------------");
		consoleLogger.info("URL:" + httpConnection.getURL());
		consoleLogger.info("METHOD:" + httpConnection.getRequestMethod());

		Map<String, List<String>> headerMap = httpConnection.getHeaderFields();
		for (Map.Entry<String, List<String>> entry : headerMap.entrySet()) {
			consoleLogger.info(entry.getKey() + ":" + entry.getValue());
		}
		consoleLogger.info("------------");
	}

	/**
	 * 
	 * @param http_body
	 */
	private void updateRequest(String http_body) {
		ConsoleLogger consoleLogger = ConsoleLogger.getInstance(historyNodeHttpEntity.getFlowId(),
				historyNodeHttpEntity.getHistoryId());
		consoleLogger.info(http_body);
		historyNodeHttpDao.updateNodehitoryRequest(historyNodeHttpEntity.getFlowId(),
				historyNodeHttpEntity.getHistoryId(), historyNodeHttpEntity.getNodeId(), cutString(http_body, 1500));
	}

	/**
	 * 
	 * @param http_res_status
	 * @param http_response
	 */
	private void updateResponse(String http_res_status, String http_response) {
		ConsoleLogger consoleLogger = ConsoleLogger.getInstance(historyNodeHttpEntity.getFlowId(),
				historyNodeHttpEntity.getHistoryId());
		consoleLogger.info("------------");
		consoleLogger.info("RESPONSE:" + http_res_status);
		consoleLogger.info(http_response);
		consoleLogger.info("------------");
		historyNodeHttpDao.updateNodehitoryReponse(historyNodeHttpEntity.getFlowId(),
				historyNodeHttpEntity.getHistoryId(), historyNodeHttpEntity.getNodeId(), http_res_status,
				cutString(http_response, 1500));
	}

	/**
	 * 
	 * @param str
	 * @param max
	 * @return
	 */
	private String cutString(String str, int max) {
		if (str.length() > max) {
			return str.substring(0, max - 1);
		} else {
			return str;
		}
	}
}
