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

import io.github.nobuglady.jobflow.persistance.db.dao.HistoryNodeShellDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryNodeShellEntity;
import io.github.nobuglady.jobflow.service.flowexecutor.NodeInf;

/**
 * 
 * @author NoBugLady
 *
 */
public class NodeDelegatorShell implements NodeInf {

	private HistoryNodeShellEntity historyNodeShellEntity;
	@SuppressWarnings("unused")
	private HistoryNodeShellDao historyNodeShellDao;

	/**
	 * 
	 * @param historyNodeShellEntity
	 * @param historyNodeShellDao
	 */
	public NodeDelegatorShell(HistoryNodeShellEntity historyNodeShellEntity, HistoryNodeShellDao historyNodeShellDao) {
		this.historyNodeShellEntity = historyNodeShellEntity;
		this.historyNodeShellDao = historyNodeShellDao;
	}

	/**
	 * 
	 */
	@Override
	public String execute() throws Throwable {

		String shellPath = historyNodeShellEntity.getShellLocation();

		Runtime runtime = Runtime.getRuntime();
		Process process = runtime.exec(shellPath);

		RuntimeStreamTracer tracer_normal = new RuntimeStreamTracer(process.getInputStream(),
				historyNodeShellEntity.getFlowId(), historyNodeShellEntity.getHistoryId());

		RuntimeStreamTracer tracer_error = new RuntimeStreamTracer(process.getErrorStream(),
				historyNodeShellEntity.getFlowId(), historyNodeShellEntity.getHistoryId());

		new Thread(tracer_normal).start();
		new Thread(tracer_error).start();

		process.waitFor();

		tracer_normal.doStop();
		tracer_error.doStop();

		return String.valueOf(process.exitValue());
	}

}
