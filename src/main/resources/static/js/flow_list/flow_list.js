////////////////////////////////////////
// Initialize
////////////////////////////////////////

var curPageStatic = 1;
var pageCountStatic = 1;
var pageSizeStatic = 10;

$(document).ready(function() {
	$("#btn_create_flow").on("click", createFlowClick);
	fetch_data(1);
});

function createFlowClick(){
	var flowName = $("#flowName").val();
	
	if(!flowName){
		alert("please input flowName");
		return;
	}
	
	var flowId = getUniqueStr();
	
	request_create_flow(flowId, flowName);
	
}

function getUniqueStr(myStrong){
 var strong = 1000;
 if (myStrong) strong = myStrong;
 return new Date().getTime().toString(16)  + Math.floor(strong*Math.random()).toString(16)
}


function request_flow_list(curPage){
	post(
		'/request_flow_list',
		{
			curPage:curPage
		},
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


function request_create_flow(flowId, flowName){
	post(
		'/request_create_flow',
		{
			flowId:flowId,
			flowName:flowName
		},
		function (data) {
			var responseObj = data;
			request_flow_list();
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
	request_flow_list(curPage);
}
/////////////////////////////
// paging end
/////////////////////////////

