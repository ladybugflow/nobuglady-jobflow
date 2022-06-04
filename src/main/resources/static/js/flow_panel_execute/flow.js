
/**
 * Click FlowMenuId
 */
function dispalyFlowClick(flowId,flowName){
	
	setFlowId(flowId);
	setHistoryId("");
	
	$("#flowName").text(flowName);
	$("#flowPanel").show();
	
	$("#folderMessagePanel").hide();
	
	request_flow_static(flowId);
}

////////////////////////////////////////
// Flow Bar status
////////////////////////////////////////
var flowStatus = "STOPED"; // STARTED
var currentFlowId ="";
var currentHistoryId = "";

function setFlowId(flowId){
	if(currentFlowId != flowId){
		currentFlowId = flowId;
		$("#flowId").text(flowId);
	}
}

function setHistoryId(historyId){
	if(currentHistoryId != historyId){
		if(!$("#btn_start").hasClass("fa-disabled")){
			$("#btn_start").addClass("fa-disabled");
		}
		if(flowStatus == "STARTED"){
			if(!$("#btn_restart").hasClass("fa-disabled")){
				$("#btn_restart").addClass("fa-disabled");
			}
		}else{
			if($("#btn_restart").hasClass("fa-disabled")){
				$("#btn_restart").removeClass("fa-disabled");
			}
		}
		

		currentHistoryId = historyId;
		$("#instanceId").text("/"+historyId);
		sendFlowHistoryId(currentFlowId, currentHistoryId)
	}
}

/**
 * refresh flow bar
 */
function refreshFlowBar(){
	if(flowStatus == "STARTED"){
		if(!$("#btn_start").hasClass("fa-disabled")){
			$("#btn_start").addClass("fa-disabled");
		}
		
		if(!$("#btn_restart").hasClass("fa-disabled")){
			$("#btn_restart").addClass("fa-disabled");
		}
		
		if($("#btn_stop").hasClass("fa-disabled")){
			$("#btn_stop").removeClass("fa-disabled");
		}
		
	}else if(flowStatus == "STOPED"){
//		if($("#btn_start").hasClass("fa-disabled")){
//			$("#btn_start").removeClass("fa-disabled");
//		}
		
		if($("#btn_restart").hasClass("fa-disabled")){
			$("#btn_restart").removeClass("fa-disabled");
		}
		
		if(!$("#btn_stop").hasClass("fa-disabled")){
			$("#btn_stop").addClass("fa-disabled");
		}
		
	}
}

/**
 * change status
 */
function changeStatus(status){
	if(status == "STARTED"){
		if(flowStatus == "STOPED"){
			
			flowStatus = status;
			refreshFlowBar();
			return true;
		}else{
			return false;
		}
	}else if(status == "STOPED"){
		if(flowStatus == "STARTED"){
			
			flowStatus = status;
			refreshFlowBar();
			return true;
		}else{
			return false;
		}
	}

	return false;
}

////////////////////////////////////////
// Click event(Flow tab)
////////////////////////////////////////
/**
 * flow on-off click
 */
function onOff1Click(){
	
	var onOff = "off";
	
	if($("#customSwitch1").is(":checked")){
		onOff = "on";
	}
	
	if(selectNode){
		request_node_save_on_off(
			currentFlowId,
			$("#node_info_id").text(),
			onOff
		);
	}else if(selectEdge){
		
		
		
	}else if(selectFlow){
		request_flow_save_on_off(
			currentFlowId,
			onOff
		);
	}

}

/**
 * start flow
 */
function startFlowClick(){
	if(!changeStatus("STARTED")){
		return;
	}
	$('.loader-inner').show();
	
	if(selectNode){
		if(currentHistoryId != ""){
			request_restart_node(currentFlowId,currentHistoryId,$("#node_info_id").text());
		}else{
			request_start_node(currentFlowId,$("#node_info_id").text());
		}
	}else if(selectEdge){
		
		
		
	}else if(selectFlow){
	
		if(currentHistoryId != ""){
			request_restart_flow(currentFlowId,currentHistoryId);
		}else{
			request_start_flow(currentFlowId);
		}
	
	}

}

/**
 * stop flow
 */
function stopFlowClick(){
	if(!changeStatus("STOPED")){
		return;
	}
	$('.loader-inner').hide();
	request_stop_flow(currentFlowId,currentHistoryId);
}

/**
 * refresh flow
 */
function refreshFlowClick(){
	if(currentHistoryId != ""){
		request_flow_dynamic(currentFlowId,currentHistoryId);
	}
}

////////////////////////////////////////
// Click event(Flow tab -node)
////////////////////////////////////////

/**
 * node on-off click
 */
function onOff2Click(){
	
	var onOff = "off";
	
	if($("#customSwitch2").is(":checked")){
		onOff = "on";
	}
	
	request_node_save_on_off(
		currentFlowId,
		$("#node_info_id").text(),
		onOff
	);
}

/**
 * node info type click
 */
function nodeInfoTypeClick(){
	if($('input[name=node_info_type]:checked').val() == "1"){
		
	}else{
		
	}
}

/**
 * start type click
 */
function nodeInfoStartTypeClick(){
	if($('input[name=node_info_start_type]:checked').val() == "2"){
		$("#node_info_start_type_1_div").hide();
		$("#node_info_start_type_2_div").show();
		$("#node_info_start_type_3_div").hide();
	}else if($('input[name=node_info_start_type]:checked').val() == "3"){
		$("#node_info_start_type_1_div").hide();
		$("#node_info_start_type_2_div").hide();
		$("#node_info_start_type_3_div").show();
	}else{
		$("#node_info_start_type_1_div").show();
		$("#node_info_start_type_2_div").hide();
		$("#node_info_start_type_3_div").hide();
	}
}

/**
 * execute type click
 */
function nodeInfoExecuteTypeClick(){
	if($('input[name=node_info_execute_type]:checked').val() == "2"){
		$("#node_info_execute_type_1_div").hide();
		$("#node_info_execute_type_2_div").show();
		$("#node_info_execute_type_3_div").hide();
	}else if($('input[name=node_info_execute_type]:checked').val() == "3"){
		$("#node_info_execute_type_1_div").hide();
		$("#node_info_execute_type_2_div").hide();
		$("#node_info_execute_type_3_div").show();
	}else{
		$("#node_info_execute_type_1_div").show();
		$("#node_info_execute_type_2_div").hide();
		$("#node_info_execute_type_3_div").hide();
	}
}

/**
 * sync flag click
 */
function nodeInfoShellSyncFlagClick(){
	if($('input[name=node_info_shell_sync_flag]:checked').val() == "1"){
		$("#node_info_shell_sync_flag_2_div").hide();
	}else if($('input[name=node_info_shell_sync_flag]:checked').val() == "2"){
		$("#node_info_shell_sync_flag_2_div").show();
	}
}

/**
 * sync flag click
 */
function nodeInfoHttpSyncFlagClick(){
	if($('input[name=node_info_http_sync_flag]:checked').val() == "1"){
		$("#node_info_http_sync_flag_2_div").hide();
	}else if($('input[name=node_info_http_sync_flag]:checked').val() == "2"){
		$("#node_info_http_sync_flag_2_div").show();
	}
}

////////////////////////////////////////
// Click event(Flow tab -edge)
////////////////////////////////////////

////////////////////////////////////////
// Click event(Console tab)
////////////////////////////////////////
/**
 * log refresh
 */
function logRefreshClick(){
	
	request_log_refresh(
		currentFlowId,currentHistoryId
	);
}

/**
 * log clear
 */
function logClearClick(){
	
	request_log_clear(
		currentFlowId,currentHistoryId
	);
}

////////////////////////////////////////
// Click event(History tab)
////////////////////////////////////////

/**
 * show history flow
 */
function flowHistoryClick(historyId){
	setHistoryId(historyId);
	
	$("#history-tab").removeClass("active");
	$("#flow-tab").addClass("active");
	$("#flow-tab").tab("show");
	request_flow_dynamic(currentFlowId,historyId);
}

////////////////////////////////////////
// Click event(Terminal tab)
////////////////////////////////////////
/**
 * terminal clear
 */
function terminalClearClick(){
	
	terminal_editor.setValue(currentPath + ">",1);
}
