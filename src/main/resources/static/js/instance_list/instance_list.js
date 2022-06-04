////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	request_instance_list();
});


function request_instance_list(){
	post(
		'/request_instance_list',
		{},
		function (data) {
			var responseObj = data;
			$("#instanceListBody").html("");
			var html = "";
			html += "";
			responseObj.forEach(element => {
				
				html += "<tr>";
				
				html += "	<td><i class=\"fas fa-cogs\"></i></td>";
				html += "	<td>"+element.flowId+"</td>";
				html += "	<td>"+element.flowName+"</td>";
				html += "	<td>"+element.historyId+"</td>";
				html += "	<td>"+element.createTimeVo+"</td>";
				if(element.finishTimeVo){
					html += "	<td>"+element.finishTimeVo+"</td>";
				}else{
					html += "<td></td>";
				}
				html += "	<td>"+element.spendTimeVo+"</td>";
				html += "	<td>"+flowStatusMap[element.flowStatus]+"</td>";
				html += "	<td>"+"<a href='flow_panel_execute?flow_id="+element.flowId+"&history_id="+element.historyId+"&flow_name="+element.flowName+"' target='_tab'>Detail</a>"+"</td>";
				html += "</tr>";
				
			});
			html += "";
			$("#instanceListBody").html(html);
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
