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
package io.github.nobuglady.jobflow.service.flowdynamic;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.github.nobuglady.jobflow.persistance.db.dao.HistoryFlowDao;
import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.util.PagingUtil;

/**
 * 
 * @author NoBugLady
 *
 */
@Service
public class InstanceListBusiness {

	@Autowired
	private HistoryFlowDao flowHistoryDao;

	/**
	 * 
	 * @param curPage
	 * @param flowName
	 * @param flowStatus
	 * @param flowStartDate
	 * @return
	 */
	public List<HistoryFlowEntity> requestInstanceList(int curPage, String flowName, String flowStatus,
			String flowStartDate) {

		return flowHistoryDao.selectAll(PagingUtil.getFrom(curPage), PagingUtil.getFetchCount(curPage), flowName,
				flowStatus, flowStartDate);
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> requestProcessingInstanceList() {

		return flowHistoryDao.selectRunningFlow();
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> requestErrorInstanceList() {

		return flowHistoryDao.selectAllError();
	}

	/**
	 * 
	 * @return
	 */
	public List<HistoryFlowEntity> requestCompleteInstanceList() {

		return flowHistoryDao.selectTodayComplete();
	}

	///////////////////////////////////////
	// help function
	///////////////////////////////////////

}
