///////////////////////////////////////
// flow
///////////////////////////////////////

function request_flow_dynamic(flowid,instanceid){
	post(
		'/request_flow_dynamic',
		{
			'flowid':flowid,
			'instanceid':instanceid
		},
		function (data) {
			var responseObj = data;
			
			setHistoryId(instanceid);
			$("#update_time").text(JSON.parse(responseObj.flowData).updateTime);
			
			if(responseObj.disable_flag == '0'){
				$("#customSwitch1").prop("checked",true);
			}else{
				$("#customSwitch1").prop("checked",false);
			}
			
			var flowDto = JSON.parse(responseObj.flowData);
			if(flowDto.status == "COMPLETE"){
				
			}
			
			createNetwork(flowDto);
		
		}
	);
}

function request_start_flow(flowid){
	post(
		'/request_start_flow',
		{
			'flowid':flowid
		},
		function (data) {
			var responseObj = data;
			setHistoryId(responseObj.instanceId);
			
			
		}
	);
}

function request_restart_flow(flowid,instanceid){
	post(
		'/request_restart_flow',
		{
			'flowid':flowid,
			'instanceid':instanceid
		},
		function (data) {
			var responseObj = data;
			setHistoryId(responseObj.instanceId);
			
		}
	);
}


function request_start_node(flowid,nodeid){
	post(
		'/request_start_node',
		{
			'flowid':flowid,
			'nodeid':nodeid
		},
		function (data) {
			var responseObj = data;
			setHistoryId(responseObj.instanceId);
			
			
		}
	);
}

function request_restart_node(flowid,instanceid,nodeid){
	post(
		'/request_restart_node',
		{
			'flowid':flowid,
			'instanceid':instanceid,
			'nodeid':nodeid
		},
		function (data) {
			var responseObj = data;
			setHistoryId(responseObj.instanceId);
			
		}
	);
}

///////////////////////////////////////
// flow history
///////////////////////////////////////


///////////////////////////////////////
// other
///////////////////////////////////////
function request_log_refresh(flowId,historyId){
	post(
		'/request_log_refresh',
		{
			'flowId':flowId,
			'historyId':historyId
		},
		function (data) {
			var res = data;
			console_editor.setValue(res.logs,1);
		}
	);
}

function request_log_clear(flowId,historyId){
	post(
		'/request_log_clear',
		{
			'flowId':flowId,
			'historyId':historyId
		},
		function (data) {
			var res = data;
			console_editor.setValue(res.logs,1);
		}
	);
}

