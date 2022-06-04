
/**
 * Click FlowMenuId
 */
function dispalyFlowClick(flowId,flowName){
	
	setFlowId(flowId);
	
	$("#flowName").text(flowName);
	$("#flowPanel").show();
	
	$("#folderMessagePanel").hide();
	
	request_flow_static(flowId,true);
}

////////////////////////////////////////
// Flow Bar status
////////////////////////////////////////
var currentFlowId ="";

function setFlowId(flowId){
	if(currentFlowId != flowId){
		currentFlowId = flowId;
	}
}


////////////////////////////////////////
// Click event(Flow tab)
////////////////////////////////////////
/**
 * flow eidt
 */
function flowEditClick(){
	request_flow_static(currentFlowId,true);
}

/**
 * flow save
 */
function flowSaveClick(){
	var nodeIds = nodes.getIds();
	var edgeIds = edges.getIds();
	
	var nodesArray = [];
	nodeIds.forEach(
		element => {
			var node = nodes.get(element);
			nodesArray.push(node);
		}
	);
	
	var edgesArray = [];
	edgeIds.forEach(
		element => {
			var edge = edges.get(element);
			edgesArray.push(edge);
		}
	);
	
	request_flow_save(currentFlowId,nodesArray,edgesArray);
}

/**
 * flow publish
 */
function flowPublishClick(){
	$('#publishConfirmModal').modal('show');
}

function flowPublishConfirmClick(){
	$('#publishConfirmModal').modal('hide');
	request_flow_publish(currentFlowId);
}

/**
 * flow on-off click
 */
function onOff1Click(){
	
	var onOff = "off";
	
	if($("#customSwitch1").is(":checked")){
		onOff = "on";
	}
	
	request_flow_save_on_off(
		currentFlowId,
		onOff
	);
}

////////////////////////////////////////
// Click event(Flow tab -node)
////////////////////////////////////////
/**
 * node info save
 */
function nodeInfoSaveClick(){
	
	if(!$("#profile_form")[0].classList.contains('was-validated')){
		$("#profile_form")[0].classList.add('was-validated');
	}
	if(!$("#triger_form")[0].classList.contains('was-validated')){
		$("#triger_form")[0].classList.add('was-validated');
	}
	if(!$("#executor_form")[0].classList.contains('was-validated')){
		$("#executor_form")[0].classList.add('was-validated');
	}
	
	if(!$("#profile_form")[0].checkValidity()){
		console.log("not validited.");
		return;
	}
	
	if(!$("#triger_form")[0].checkValidity()){
		console.log("not validited.");
		return;
	}
	
	if(!$("#executor_form")[0].checkValidity()){
		console.log("not validited.");
		return;
	}
	
	
	if($("#profile_form")[0].classList.contains('was-validated')){
		$("#profile_form")[0].classList.remove('was-validated');
	}
	if($("#triger_form")[0].classList.contains('was-validated')){
		$("#triger_form")[0].classList.remove('was-validated');
	}
	if($("#executor_form")[0].classList.contains('was-validated')){
		$("#executor_form")[0].classList.remove('was-validated');
	}
	
	request_node_save(
		currentFlowId,
		$("#node_info_id").text(),
		$('input[name=node_info_type]:checked').val(),
		$("#node_info_cron").val(),
		getRadioValue("node_info_start_type"),
		getRadioValue("node_info_execute_type"),
		$("#node_info_shell_success_code").val(),
		getRadioValue("node_info_shell_error_type"),
		getRadioValue("node_info_shell_sync_flag"),
		$("#node_info_shell_location").val(),
		$("#node_info_ref_name").val(),
		$("#node_info_name").val(),
		$("#node_info_layout_x").val(),
		$("#node_info_layout_y").val(),
		$("#node_info_http_success_code").val(),
		getRadioValue("node_info_http_error_type"),
		getRadioValue("node_info_http_sync_flag"),
		$("#node_info_location").val(),
		$("#node_info_http_method").val(),
		$("#node_info_http_content_type").val(),
		$("#node_info_http_header").val(),
		$("#node_info_http_body").val(),
		getCheckedValue("rolesCheck"),
	);
}


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

function updateCronClick(){
	$("#node_info_cron").val($("#cron_command").text());
	$('#exampleModalCenter').modal('hide')

}

////////////////////////////////////////
// Click event(Flow tab -edge)
////////////////////////////////////////
/**
 * edge info save
 */
function edgeInfoSaveClick(){
	request_edge_save(
		currentFlowId,
		$("#edge_info_id").val(),
		$("#edge_info_from").text(),
		$("#edge_info_to").text(),
		$("#edge_condition").val()
	);
}

