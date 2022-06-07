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
package io.github.nobuglady.jobflow.service.modal;

import java.util.ArrayList;
import java.util.List;

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.NodeStatus;
import io.github.nobuglady.jobflow.constant.NodeStatusDetail;
import io.github.nobuglady.jobflow.util.StringUtil;

/**
 * 
 * @author NoBugLady
 *
 */
public class FlowDto {

	public String updateTime;

	public List<NodeDto> nodes = new ArrayList<NodeDto>();
	public List<EdgeDto> edges = new ArrayList<EdgeDto>();

	public String toJson() {

		EdgeDto startEdge = null;
		for (EdgeDto edge : edges) {
			if (edge.from.equals("start")) {
				startEdge = edge;
				break;
			}
		}

		if (startEdge != null) {
			edges.remove(startEdge);
			edges.add(0, startEdge);
		}

		StringBuffer json = new StringBuffer();
		json.append("{");
		json.append("\"updateTime\":\"" + updateTime + "\",");
		json.append("\"nodes\":[");

		for (NodeDto jobNodeDto : nodes) {
			json.append("{");
			// id
			json.append("\"id\": \"" + jobNodeDto.id + "\",");
			// position
			if (StringUtil.isNotEmpty(jobNodeDto.layoutX)) {
				json.append("\"x\": " + jobNodeDto.layoutX + ", ");
				json.append("\"layoutX\": " + jobNodeDto.layoutX + ", ");
			}
			if (StringUtil.isNotEmpty(jobNodeDto.layoutY)) {
				json.append("\"y\": " + jobNodeDto.layoutY + ", ");
				json.append("\"layoutY\": " + jobNodeDto.layoutY + ", ");
			}
			// label
			json.append("\"label\": \"" + jobNodeDto.label + "\" ");

			// color

			if (Const.FLAG_ON == jobNodeDto.disabled) {
				json.append(",");
				json.append("\"color\": \"#999999\"");
			} else {

				// wait E8F9FD
				// ready 79DAE8
				// open 187498
				// running F9D923
				// success 36AE7C
				// error EB5353

				if (NodeStatus.SKIPED == jobNodeDto.status) {
					json.append(",");
					json.append("\"color\": \"#999999\"");
				} else if (NodeStatus.WAIT == jobNodeDto.status) {
					json.append(",");
					json.append("\"color\": \"#E8F9FD\"");
				} else if (NodeStatus.READY == jobNodeDto.status) {
					json.append(",");
					json.append("\"color\": \"#79DAE8\"");
				} else if (NodeStatus.OPENNING == jobNodeDto.status) {
					json.append(",");
					json.append("\"color\": \"#187498\"");
				} else if (NodeStatus.RUNNING == jobNodeDto.status) {
					json.append(",");
					json.append("\"color\": \"#F9D923\"");
				} else if (NodeStatus.COMPLETE == jobNodeDto.status || NodeStatus.GO == jobNodeDto.status) {

					if (NodeStatusDetail.COMPLETE_SUCCESS == jobNodeDto.status_detail) {
						json.append(",");
						json.append("\"color\": \"#36AE7C\"");
					} else if (NodeStatusDetail.COMPLETE_ERROR == jobNodeDto.status_detail) {
						json.append(",");
						json.append("\"color\": \"#EB5353\"");
					} else if (NodeStatusDetail.COMPLETE_TIMEOUT == jobNodeDto.status_detail) {
						json.append(",");
						json.append("\"color\": \"#EB5353\"");
					} else if (NodeStatusDetail.COMPLETE_CANCEL == jobNodeDto.status_detail) {
						json.append(",");
						json.append("\"color\": \"#EB5353\"");
					}

				}

			}

			json.append("}");
			json.append(",");
		}

		if (nodes.size() > 0) {
			// delete last ","
			json.deleteCharAt(json.length() - 1);
		}

		json.append("],\"edges\":[");

		for (EdgeDto jobEdgeDto : edges) {
			json.append("{");
			json.append("\"id\": \"" + jobEdgeDto.id + "\",");
			json.append("\"from\": \"" + jobEdgeDto.from + "\",");
			json.append("\"to\": \"" + jobEdgeDto.to + "\",");
			json.append("\"arrows\": \"to\"");
			json.append("}");
			json.append(",");
		}

		if (edges.size() > 0) {
			// delete last ","
			json.deleteCharAt(json.length() - 1);
		}

		json.append("]}");
		return json.toString();
	}
}
