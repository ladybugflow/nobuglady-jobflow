////////////////////////////////////////
// Initialize
////////////////////////////////////////
$(document).ready(function() {
	request_log_list();
});


function request_log_list(){
	post(
		'/request_log_list',
		{},
		function (data) {
			var responseObj = data;
			$("#logListBody").html("");
			var html = "";
			html += "";
			responseObj.forEach(element => {
				
				html += "<tr>";
				
				html += "	<td><i class=\"fas fa-folder\"></i></td>";
				html += "	<td>"+element.flowId+"</td>";
				html += "	<td><a href='log_list_file?flowId="+element.flowId+"'>show files</a></td>";
				html += "</tr>";
				
			});
			html += "";
			$("#logListBody").html(html);
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
