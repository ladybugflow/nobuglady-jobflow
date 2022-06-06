////////////////////////////////////////
// Initialize
////////////////////////////////////////

var curPageStatic = 1;
var pageCountStatic = 1;
var pageSizeStatic = 10;

$(document).ready(function() {
	$("#btn_search").on("click", searchClick);
	request_instance_list(1);
});

function searchClick(){
	request_instance_list(1);
}

function request_instance_list(curPage){

	var flowName = $("#flowName").val();
	var flowStatus = $("#flowStatus").val();
	var flowStartDate = $("#flowStartDate").val();
	
	post(
		'/request_instance_list',
		{
			curPage:curPage,
			flowName:flowName,
			flowStatus:flowStatus,
			flowStartDate:flowStartDate
		},
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
			
			/////////////////////////////
			// paging start
			/////////////////////////////
			var rowCount = responseObj.length;
			
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
	request_instance_list(curPage);
}
/////////////////////////////
// paging end
/////////////////////////////

