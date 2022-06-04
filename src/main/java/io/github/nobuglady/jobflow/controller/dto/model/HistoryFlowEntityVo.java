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
package io.github.nobuglady.jobflow.controller.dto.model;

import java.util.Date;

import io.github.nobuglady.jobflow.persistance.db.entity.HistoryFlowEntity;
import io.github.nobuglady.jobflow.util.DateUtil;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
public class HistoryFlowEntityVo extends HistoryFlowEntity{

	public String getStartTimeVo() {
		return DateUtil.dateToString(getStartTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}
	public String getFinishTimeVo() {
		return DateUtil.dateToString(getFinishTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}
	public String getErrorTimeVo() {
		return DateUtil.dateToString(getErrorTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}
	public String getCreateTimeVo() {
		return DateUtil.dateToString(getCreateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}
	public String getUpdateTimeVo() {
		return DateUtil.dateToString(getUpdateTime(), DateUtil.FMT_YYYYMMDD_HHMMSS);
	}
	public String getSpendTimeVo() {
		Date finishTime = getFinishTime();
		Date startTime = getStartTime();
		
		if(finishTime == null) {
			finishTime = new Date();
		}
		
		long spend = finishTime.getTime() - startTime.getTime();
		
		long oneDay = 1000 * 60 * 60 * 24;
		long oneHour = 1000 * 60 * 60;
		long oneMinute = 1000 * 60;
		long oneSecond = 1000;
		
		long days    =    spend / oneDay;
		long hours   = (  spend % oneDay) / oneHour;
		long minutes = (( spend % oneDay) % oneHour) / oneMinute;
		long seconds = (((spend % oneDay) % oneHour) % oneMinute) / oneSecond;
//		long millis  = (((spend % oneDay) % oneHour) % oneMinute) % oneSecond;
		
		return days+" days "
		+" "+StringUtil.padding(hours, "0", 2)
		+":"+StringUtil.padding(minutes, "0", 2)
		+":"+StringUtil.padding(seconds, "0", 2);
	}
	
}
