////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	var flowId = getUrlParameter("flowId");
	request_log_list_file(flowId);
});


function request_log_list_file(flowId){
	post(
		'/request_log_list_file',
		{
			flowId:flowId
		},
		function (data) {
			var responseObj = data;
			$("#logListFileBody").html("");
			var html = "";
			html += "";
			responseObj.forEach(element => {
				
				html += "<tr>";
				
				html += "	<td><i class=\"fas fa-cogs\"></i></td>";
				html += "	<td>"+element.fileId+"</td>";
				html += "	<td><a href='request_log_list_file_download?flowId="+flowId+"&fileId="+element.fileId+"'>download</a> | <a href='javascript:doDelete(\""+flowId+"\",\""+element.fileId+"\");'>delete</a></td>";
				html += "</tr>";
				
			});
			html += "";
			$("#logListFileBody").html(html);
		}
	);
}

function doDelete(flowId, fileId){
	if(window.confirm("Are you shure Delete?")){
		post(
			'/request_log_list_file_delete',
			{
				flowId:flowId,
				fileId:fileId
			},
			function (data) {
				var responseObj = data;
				request_log_list_file(flowId);
			}
		);
	}
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

