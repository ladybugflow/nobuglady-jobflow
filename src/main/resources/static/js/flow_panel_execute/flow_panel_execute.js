
var console_editor = null;
var terminal_editor = null;
var markId = null;
var currentPath = "";
var selectNode = false;
var selectEdge = false;
var selectFlow = true;

////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	
	// register event
	$("#btn_start").on("click", startFlowClick);
	$("#btn_restart").on("click", startFlowClick);
	$("#btn_refresh_log").on("click", logRefreshClick);
	$("#btn_clear_log").on("click", logClearClick);
	$("#btn_clear_terminal").on("click", terminalClearClick);
	$("#customSwitch1").on("click", onOff1Click);
	$("#btn_refresh").on("click", refreshFlowClick);
	$("#btn_stop").on("click", stopFlowClick);
	$('input[name="node_info_type"]').on("click",nodeInfoTypeClick);
	$("#node_info_start_type_1").on("click", nodeInfoStartTypeClick);
	$("#node_info_start_type_2").on("click", nodeInfoStartTypeClick);
	$("#node_info_start_type_3").on("click", nodeInfoStartTypeClick);
	$("#node_info_execute_type_1").on("click", nodeInfoExecuteTypeClick);
	$("#node_info_execute_type_2").on("click", nodeInfoExecuteTypeClick);
	$("#node_info_execute_type_3").on("click", nodeInfoExecuteTypeClick);
	$("#node_info_shell_sync_flag_1").on("click", nodeInfoShellSyncFlagClick);
	$("#node_info_shell_sync_flag_2").on("click", nodeInfoShellSyncFlagClick);
	$("#node_info_http_sync_flag_1").on("click", nodeInfoHttpSyncFlagClick);
	$("#node_info_http_sync_flag_2").on("click", nodeInfoHttpSyncFlagClick);

	$("input").attr('readonly', 'readonly');
	$("input[type=radio]").attr('disabled', 'disabled');
	$("textarea").attr('readonly', 'readonly');
	$("select").attr('disabled', 'disabled');
	$("select").attr('readonly', 'readonly');
	
	console_editor = createEditor_log("console_div","text");
	terminal_editor = createEditor_terminal("terminal_div","text");
	
	startWebSocketClient();
	
	var flowId = getUrlParameter("flow_id");
	var flowName = getUrlParameter("flow_name");
	var historyId = getUrlParameter("history_id");
	dispalyFlowClick(flowId,flowName);
	if(historyId){
		if(!$("#btn_start").hasClass("fa-disabled")){
			$("#btn_start").addClass("fa-disabled");
		}
		flowHistoryClick(historyId);
	}else{
		if(!$("#btn_restart").hasClass("fa-disabled")){
			$("#btn_restart").addClass("fa-disabled");
		}
	}
	$("#customSwitch1").prop("disabled",true);
});

////////////////////////////////////////
// Click event
////////////////////////////////////////

////////////////////////////////////////
// Send request
////////////////////////////////////////

function post(requestUrl, requestData, callBack){
    $.ajax({
        url:requestUrl,
        type:'POST',
        data:requestData
    }).done( (data, status, xhr) => {
        console.log(data);
		var ct = xhr.getResponseHeader("content-type") || "";
		if (ct.indexOf('html') > -1) {
		    window.location.replace("/login");
		}

        callBack(data);

    }).fail( (data) => {
        console.log(data);
    }).always( (data) => {

    });
}

function postJson(requestUrl, requestData, callBack){
    $.ajax({
        url:requestUrl,
        type:'POST',
		contentType: 'application/json',
		dataType: 'json',
        data:JSON.stringify(requestData)
    }).done( (data, status, xhr) => {
        console.log(data);
		var ct = xhr.getResponseHeader("content-type") || "";
		if (ct.indexOf('html') > -1) {
		    window.location.replace("/login");
		}

        callBack(data);

    }).fail( (data) => {
        console.log(data);
    }).always( (data) => {

    });
}

////////////////////////////////////////
// Helper function
////////////////////////////////////////
function getFormatTime(date) {
	var res = "" + date.getFullYear() + "/" + padZero(date.getMonth() + 1) + 
		"/" + padZero(date.getDate()) + " " + padZero(date.getHours()) + ":" + 
		padZero(date.getMinutes()) + ":" + padZero(date.getSeconds());
	return res;
}

function padZero(num) {
	var result;
	if (num < 10) {
		result = "0" + num;
	} else {
		result = "" + num;
	}
	return result;
}

function getUniqueStr(myStrong){
 var strong = 1000;
 if (myStrong) strong = myStrong;
 return new Date().getTime().toString(16)  + Math.floor(strong*Math.random()).toString(16)
}


function getSingleCheckedValue(checkBoxName){
	if($('input[name='+checkBoxName+']:checked').val()){
		return $('input[name='+checkBoxName+']:checked').val();
	}else{
		return '0';
	}
}

function getRadioValue(radioName){
	if($('input[name='+radioName+']:checked').val()){
		return $('input[name='+radioName+']:checked').val();
	}else{
		return '0';
	}
}


function getCheckedValue(checkBoxName){
	var result = "";
	$('input[name='+checkBoxName+']:checked').each(function() {
		result = result + "," + this.value;
	});
	if(result.length > 0){
		result = result.slice(1);
	}
	return result;
}

function getUrlParameter(sParam) {
    var sPageURL = window.location.search.substring(1),
        sURLVariables = sPageURL.split('&'),
        sParameterName,
        i;

    for (i = 0; i < sURLVariables.length; i++) {
        sParameterName = sURLVariables[i].split('=');

        if (sParameterName[0] === sParam) {
            return sParameterName[1] === undefined ? true : decodeURIComponent(sParameterName[1]);
        }
    }
    return false;
}

