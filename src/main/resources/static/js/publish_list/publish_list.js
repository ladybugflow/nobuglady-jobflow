////////////////////////////////////////
// Initialize
////////////////////////////////////////

var curPageStatic = 1;
var pageCountStatic = 1;
var pageSizeStatic = 10;

$(document).ready(function() {
	request_publish_list(1);
});


function request_publish_list(curPage){
	post(
		'/request_publish_list',
		{
			curPage:curPage
		},
		function (data) {
			var responseObj = data;
			$("#publishListBody").html("");
			var html = "";
			html += "";
			responseObj.result.forEach(element => {
				html += "<tr>";
				html += "	<td><i class=\"fas fa-cog\"></i></td>";
				html += "	<td>"+element.flow_id+"</td>";
				html += "	<td>"+element.flow_name+"</td>";
				html += "	<td>"+nodeStartTypeMap[element.flow_start_type]+"<br/>";
				if(element.flow_start_type == 3){
					html += "   " +element.cron+"<br/>";
					html += "   " +element.nextStartTime+"<br/>";
				}				html += "   </td>"
				html += "	<td>"+element.update_time.split(".")[0].split("T").join(" ")+"</td>";
				html += "	<td>"+"<a href='flow_panel_execute?flow_id="+element.flow_id+"&flow_name="+element.flow_name+"' target='_tab'>Detail</a>"+"</td>";
				html += "</tr>";
			});
			html += "";
			$("#publishListBody").html(html);
			
			/////////////////////////////
			// paging start
			/////////////////////////////
			var rowCount = responseObj.rowCount;
			
			var pageCount = parseInt(rowCount / pageSizeStatic);
			if(rowCount % pageSizeStatic > 0){
				pageCount ++;
			}
			
			curPageStatic = curPage;
			pageCountStatic = pageCount;
			
			$(".paging_item_class").remove();
			for(var i = 1; i <= pageCount; i ++){
				
				var html = '<li class="page-item paging_item_class"><a class="page-link" href="javascript:fetch_data('+i+')">'+i+'</a></li>';
				
				if(i == curPageStatic){
					html = '<li class="page-item paging_item_class active"><span class="page-link">'+i+'<span class="sr-only">(current)</span></span></li>';
				}
				
				$($(".paging_item_next")).before(html);
			}
			/////////////////////////////
			// paging end
			/////////////////////////////
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

/////////////////////////////
// paging start
/////////////////////////////
function fetch_pre(){
	if(curPageStatic > 1){
		fetch_data(curPageStatic -1);
	}
}

function fetch_next(){
	if(curPageStatic < pageCountStatic){
		fetch_data(curPageStatic +1);
	}
}

function fetch_data(curPage){
	request_publish_list(curPage);
}
/////////////////////////////
// paging end
/////////////////////////////

