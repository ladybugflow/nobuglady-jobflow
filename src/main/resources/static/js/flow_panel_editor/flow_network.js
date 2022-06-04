var network = null;
var nodes = null;
var edges = null;

////////////////////////////////////////
// vis network
////////////////////////////////////////

function createNetwork(data,manipulation){
	console.log(data);
	
	network = null;
	
	if(!manipulation){
		manipulation = false;
	}
	
	if(!network){
      // create an array with nodes
      nodes = new vis.DataSet(data.nodes);

      // create an array with edges
      edges = new vis.DataSet(data.edges);

      // create a network
      var container = document.getElementById("mynetwork");
      var data = {
        nodes: nodes,
        edges: edges,
      };
	  var width = 650;
	  var height = 500;
      var options = {
/*
		  layout: {
            hierarchical: {
              direction: "LR",
            },
          },
*/
	    width: width + 'px',
	    height: height + 'px',
		layout: { randomSeed: 2 },
        physics: {
          enabled: false,
        },
        interaction: { hover: true },
        manipulation: {
          enabled: manipulation,
		  addEdge: function (data, callback) {
			  data.arrows = "to";
              callback(data);
            },
		  editEdge: function (data, callback) {
              callback(data);
            }

        },
  	  };

      network = new vis.Network(container, data, options);
      network.moveTo({
	    position: {x: 0, y: 0},
	    offset: {x: 0, y: 0},
	    scale: 1,
	})
      setupEvents(network);
	}else{
        nodes = new vis.DataSet(data.nodes);
        edges = new vis.DataSet(data.edges);
        network.setData({ nodes: nodes, edges: edges });
	}
}

function setupEvents(network){
	network.on("click", function (params) {
		var nodeName = this.getNodeAt(params.pointer.DOM);
		if(nodeName){
			
			var node = nodes.get(params.nodes[0]);
			
			$("#node_info_id").text(node.id);
			$("#node_info_name").val(node.label);
			
			request_node_info(currentFlowId,node.id);
			
			$("#edgePropDiv").hide();
			$("#nodePropDiv").show();
			$("#logDiv").show();
			
			$("#request-tab-li").show();
			$("#response-tab-li").show();
			$("#request").show();
			$("#response").show();
				

		}else{
			
			if(params.edges && params.edges.length == 1){
				console.log("click edge:"+params.edges[0]);
				console.log(edges.get(params.edges[0]));
				var edge = edges.get(params.edges[0]);
				
//				$("#edge_info_from").text(edge.from);
//				$("#edge_info_to").text(edge.to);
				
				request_edge_info(currentFlowId,edge.id,edge.from,edge.to);
				
				$("#nodePropDiv").hide();
				//$("#edgePropDiv").show();
				
			}else{
				
				console.log("click flow.");
				
				$("#nodePropDiv").hide();
				$("#edgePropDiv").hide();

			}
			
		}
	});
	
	network.on("dragEnd", function (params) {
		$("#node_info_layout_x").val(params.pointer.canvas.x);
		$("#node_info_layout_y").val(params.pointer.canvas.y);
		
		var nodeIds = params.nodes;
		
		var node = nodes.get(nodeIds[0]);
		node.layoutX = params.pointer.canvas.x;
		node.layoutY = params.pointer.canvas.y;
		/*
		nodeIds.forEach(
			element => {
				var node = nodes.get(element);
				node.layoutX = params.pointer.DOM.x;
				node.layoutY = params.pointer.DOM.y;
			}
		);
		*/
	
	});
	
	network.on("click", function (params) {
		$("#node_info_layout_x").val(params.pointer.canvas.x);
		$("#node_info_layout_y").val(params.pointer.canvas.y);
		
		var nodeIds = params.nodes;
		
		var node = nodes.get(nodeIds[0]);
		node.layoutX = params.pointer.canvas.x;
		node.layoutY = params.pointer.canvas.y;
		/*
		nodeIds.forEach(
			element => {
				var node = nodes.get(element);
				node.layoutX = params.pointer.DOM.x;
				node.layoutY = params.pointer.DOM.y;
			}
		);
		*/
	
	});
}

