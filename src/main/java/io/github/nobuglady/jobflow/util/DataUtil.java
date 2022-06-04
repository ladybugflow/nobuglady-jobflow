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

import io.github.nobuglady.jobflow.constant.Const;
import io.github.nobuglady.jobflow.constant.NodeExecuteType;
import io.github.nobuglady.jobflow.constant.NodeStartType;
import io.github.nobuglady.jobflow.constant.NodeType;

/**
 * 
 * @author NoBugLady
 *
 */
public class DataUtil {

	/**
	 * 
	 * @param disabeldFlag
	 * @return
	 */
	public static int getDisabedFlag(Integer disabeldFlag) {

		if (disabeldFlag == null) {
			return Const.FLAG_OFF;
		} else {
			return disabeldFlag.intValue();
		}
	}

	/**
	 * 
	 * @param nodeType
	 * @return
	 */
	public static int getNodeType(Integer nodeType) {

		if (nodeType == null) {
			return NodeType.NODE_TYPE_NOMARL;
		} else {
			return nodeType.intValue();
		}
	}

	/**
	 * 
	 * @param nodeExecuteType
	 * @return
	 */
	public static int getNodeExecuteType(Integer nodeExecuteType) {

		if (nodeExecuteType == null) {
			return NodeExecuteType.NODE_EXECUTE_TYPE_NONE;
		} else {
			return nodeExecuteType.intValue();
		}
	}

	/**
	 * 
	 * @param nodeExecuteType
	 * @return
	 */
	public static int getNodeStartType(Integer nodeStartType) {

		if (nodeStartType == null) {
			return NodeStartType.NODE_START_TYPE_DEFAULT;
		} else {
			return nodeStartType.intValue();
		}
	}
}
