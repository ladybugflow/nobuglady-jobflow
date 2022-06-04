	var socket;
	
	function startWebSocketClient(){
	    
	    if(typeof(WebSocket) == "undefined") {
	        console.log("WebSocket not supported.");
	    }else{
		
	        socket = new WebSocket("ws://localhost:8080/websocket/123");
	        
	        socket.onopen = function() {
	            console.log("Socket opened.");
	            //socket.send("From client:" + location.href + new Date());
	        };
	        
	        socket.onmessage = function(msg) {
	            console.log(msg.data);

				var msgHeader = msg.data.split("#")[0];
				var msgBody = msg.data.split("#")[1];
				
				if("update" == msgHeader){
					var flowId = msgBody.split(",")[0];
					var historyId = msgBody.split(",")[1];
					request_flow_dynamic(flowId,historyId);
				}else if("complete" == msgHeader){
					var flowId = msgBody.split(",")[0];
					var historyId = msgBody.split(",")[1];
					request_flow_dynamic(flowId,historyId);
					
					if(!changeStatus("STOPED")){
						return;
					}
					$('.loader-inner').hide();
				}
	            
				
	        };
	        
	        socket.onclose = function() {
	            console.log("Socket closed");
	        };
	        
	        socket.onerror = function() {
	            alert("Socket error");
	        }

	        // $(window).unload(function(){
	        //     socket.close();
	        //});
	    }

	}
	
	function sendFlowHistoryId(flowId, historyId){
		console.log("setFlowHistory call:"+socket.readyState);
		if(socket != null && socket.readyState === 1){
			console.log("setFlowHistory send");
			socket.send("setFlowHistory#"+flowId+","+historyId);
		}else{
			setTimeout(function(){
				sendFlowHistoryId(flowId, historyId);
			},1*1000);
		}
	}
