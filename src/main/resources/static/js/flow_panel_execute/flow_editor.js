///////////////////////////////////////
// flow
///////////////////////////////////////
function request_flow_static(flowid, manipulation){
	post(
		'/request_flow_static_publish',
		{
			'flowid':flowid
		},
		function (data) {
			var responseObj = data;
			$("#update_time").text(JSON.parse(responseObj.flowData).updateTime);
			
			if(responseObj.disable_flag == '0'){
				$("#customSwitch1").prop("checked",true);
			}else{
				$("#customSwitch1").prop("checked",false);
			}
			
			createNetwork(JSON.parse(responseObj.flowData),manipulation);
		
		}
	);
}

function request_flow_save_on_off(flowId,onOff){
	post(
		'/request_flow_save_on_off',
		{
			'flowId':flowId,
			'onOff':onOff,
		},
		function (data) {
			var res = data;
			$("#update_time").text(res.updateTime);
			console.log(res);
			
		}
	);
}

///////////////////////////////////////
// node
///////////////////////////////////////

function request_node_info(flowid,nodeid){
	post(
		'/request_node_info_publish',
		{
			'flowid':flowid,
			'nodeid':nodeid
		},
		function (data) {
			var responseObj = data;
			
			var node = responseObj.nodeEntity;
			var nodeShell = responseObj.nodeShellEntity;
			var nodeHttp = responseObj.nodeHttpEntity;
			
			if(!nodeShell){
				nodeShell = {};
			}
			
			if(!nodeHttp){
				nodeHttp = {};
			}
			
			if(node.disableFlag == '0'){
				$("#customSwitch1").prop("checked",true);
			}else{
				$("#customSwitch1").prop("checked",false);
			}
			
			if(node.nodeType != '1'){
				$("#triger-tab-li").hide();
				if($("#triger").hasClass("active")){
					$("#triger").removeClass("active");
					$("#triger-tab").removeClass("active");
					$('.nav-tabs a[href="#profile"]').tab('show');
					$('#profile-tab').addClass('active');
				}
				
			}else{
				$("#triger-tab-li").show();
				
			}
			
			$("#node_info_id").text(node.nodeId);
			if($("input[name=node_info_type][value=" + node.nodeType + "]")){
				$("input[name=node_info_type][value=" + node.nodeType + "]").prop('checked', true);
				nodeInfoTypeClick();
			}
			$("#node_info_cron").val(node.startCron);
			if(node.startType == "2"){
				$("#node_info_start_type_2").prop("checked",true);
				$("#node_info_start_type_2").click();
			}else if(node.startType == "3"){
				$("#node_info_start_type_3").prop("checked",true);
				$("#node_info_start_type_3").click();
			}else{
				$("#node_info_start_type_1").prop("checked",true);
				$("#node_info_start_type_1").click();
			}
			
			if(node.executeType == "2"){
				$("#node_info_execute_type_2").prop("checked",true);
				$("#node_info_execute_type_2").click();
			}else if(node.executeType == "3"){
				$("#node_info_execute_type_3").prop("checked",true);
				$("#node_info_execute_type_3").click();
			}else{
				$("#node_info_execute_type_1").prop("checked",true);
				$("#node_info_execute_type_1").click();
			}
			$("#node_info_shell_success_code").val(nodeShell.successCode);
			if(nodeShell.errorType == "1"){
				$("#node_info_shell_error_type_1").prop("checked",true);
				$("#node_info_shell_error_type_1").click();
			}else{
				$("#node_info_shell_error_type_2").prop("checked",true);
				$("#node_info_shell_error_type_2").click();
			}
			if(nodeShell.syncFlag == "2"){
				$("#node_info_shell_sync_flag_2").prop("checked",true);
				$("#node_info_shell_sync_flag_2").click();
			}else{
				$("#node_info_shell_sync_flag_1").prop("checked",true);
				$("#node_info_shell_sync_flag_1").click();
			}
			$("#node_info_shell_location").val(nodeShell.shellLocation);
			
			$("#node_info_ref_name").val(node.refName);
			$("#node_info_name").val(node.nodeName);
			$("#node_info_layout_x").val(node.layoutX);
			$("#node_info_layout_y").val(node.layoutY);
			$("#node_info_http_success_code").val(nodeHttp.successCode);
			if(nodeHttp.errorType == "1"){
				$("#node_info_http_error_type_1").prop("checked",true);
				$("#node_info_http_error_type_1").click();
			}else{
				$("#node_info_http_error_type_2").prop("checked",true);
				$("#node_info_http_error_type_2").click();
			}
			if(nodeHttp.syncFlag == "2"){
				$("#node_info_http_sync_flag_2").prop("checked",true);
				$("#node_info_http_sync_flag_2").click();
			}else{
				$("#node_info_http_sync_flag_1").prop("checked",true);
				$("#node_info_http_sync_flag_1").click();
			}
			$("#node_info_location").val(nodeHttp.httpUrl);
			$("#node_info_http_method").val(nodeHttp.httpMethod);
			$("#node_info_http_content_type").val(nodeHttp.httpContentType);
			
			$("#node_info_http_header").val(nodeHttp.httpHeader);
			$("#node_info_http_body").val(nodeHttp.httpBody);
			
			var rolesIdList = responseObj.rolesIdList;
			if(rolesIdList){
				rolesIdList.forEach(function(item){
					$("#rolesCheck"+item).prop("checked",true);
				});
			}

			
		}
	);
}

function request_node_save_on_off(flowId,nodeId,onOff){
	post(
		'/request_node_save_on_off',
		{
			'flowId':flowId,
			'nodeId':nodeId,
			'onOff':onOff,
		},
		function (data) {
			var res = data;
			console.log(res);
			
		}
	);
}

///////////////////////////////////////
// edge
///////////////////////////////////////
function request_edge_info(flowid,edgeid,from,to){
	post(
		'/request_edge_info_publish',
		{
			'flowid':flowid,
			'edgeid':edgeid,
			'from':from,
			'to':to
		},
		function (data) {
			var responseObj = data;
			$("#edge_info_id").val(responseObj.edgeid);
			$("#edge_info_from").text(responseObj.from);
			$("#edge_info_to").text(responseObj.to);
			$("#edge_info_from_name").text(responseObj.fromNodeName);
			$("#edge_info_to_name").text(responseObj.toNodeName);
			$("#edge_condition").val(responseObj.condition);

//			$("#eventSpan").text(responseObj.flowContent.split("#").join("\r\n"));
		}
	);
}

