
var markId = null;
var currentPath = "";

////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	// register event
	$("#btn_save").on("click", flowSaveClick);
	$("#btn_publish").on("click", flowPublishClick);
	$("#customSwitch1").on("click", onOff1Click);
	$("#customSwitch2").on("click", onOff2Click);
	$("#btn_node_info_save").on("click", nodeInfoSaveClick);
	$("#btn_edge_info_save").on("click", edgeInfoSaveClick);
	$("#btn_edit").on("click", flowEditClick);
	$('input[name="node_info_type"]').on("click",nodeInfoTypeClick);
	$("#node_info_start_type_1").on("click", nodeInfoStartTypeClick);
	$("#node_info_start_type_2").on("click", nodeInfoStartTypeClick);
	$("#node_info_start_type_3").on("click", nodeInfoStartTypeClick);
	$("#node_info_execute_type_1").on("click", nodeInfoExecuteTypeClick);
	$("#node_info_execute_type_2").on("click", nodeInfoExecuteTypeClick);
	$("#node_info_execute_type_3").on("click", nodeInfoExecuteTypeClick);
	$("#updateCron").on("click", updateCronClick);
	$("#node_info_shell_sync_flag_1").on("click", nodeInfoShellSyncFlagClick);
	$("#node_info_shell_sync_flag_2").on("click", nodeInfoShellSyncFlagClick);
	$("#node_info_http_sync_flag_1").on("click", nodeInfoHttpSyncFlagClick);
	$("#node_info_http_sync_flag_2").on("click", nodeInfoHttpSyncFlagClick);


	var flowId = getUrlParameter("flow_id");
	var flowName = getUrlParameter("flow_name");
	dispalyFlowClick(flowId,flowName);

});

////////////////////////////////////////
// Click event
////////////////////////////////////////

/**
 * load menu
 */
function loadmenu(){
	request_load_menu();
}

////////////////////////////////////////
// Send request
////////////////////////////////////////

function request_load_menu(){
	post(
		'/menu',
		{},
		function (data) {
			var res = data;
			$("#menu").html(res);
		}
	);
}

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

function request_flow_list(){
	post(
		'/request_flow_list',
		{},
		function (data) {
			var responseObj = data;
			$("#flowListBody").html("");
			var html = "";
			html += "";
			responseObj.forEach(element => {
				html += "<tr>";
				html += "	<td><i class=\"fas fa-cog\"></i></td>";
				html += "	<td>"+element.flow_id+"</td>";
				html += "	<td>"+element.flow_name+"</td>";
				html += "	<td>"+nodeStartTypeMap[element.flow_start_type]+"<br/>";
				html += "   " +element.cron+"<br/>";
				html += "   " +element.nextStartTime+"<br/>";
				html += "   </td>"
				html += "	<td>"+element.update_time.split(".")[0].split("T").join(" ")+"</td>";
				html += "	<td>"+"<a href='flow_panel_editor?flow_id="+element.flow_id+"&flow_name="+element.flow_name+"' target='_tab'>Detail</a>"+"</td>";
				html += "</tr>";
			});
			html += "";
			$("#flowListBody").html(html);
		}
	);
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


