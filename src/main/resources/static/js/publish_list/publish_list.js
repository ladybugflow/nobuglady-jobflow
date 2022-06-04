////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	request_publish_list();
});


function request_publish_list(){
	post(
		'/request_publish_list',
		{},
		function (data) {
			var responseObj = data;
			$("#publishListBody").html("");
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
				html += "	<td>"+"<a href='flow_panel_execute?flow_id="+element.flow_id+"&flow_name="+element.flow_name+"' target='_tab'>Detail</a>"+"</td>";
				html += "</tr>";
			});
			html += "";
			$("#publishListBody").html(html);
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
