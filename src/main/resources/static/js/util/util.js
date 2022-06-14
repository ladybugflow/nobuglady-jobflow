var flowStatusMap = {
	"0":"READY",
	"1":"PROCESSING",
	"2":"COMPLETE",
	"-1":"ERROR",
	"-2":"CANCEL"
};

var nodeStatusMap = {
    "999":"SKIPED",
    "0":"WAIT",
    "2":"READY",
    "4":"ARRIVED",
    "10":"OPENNING",
    "12":"CLOSED",
    "20":"READY_RUN",
    "21":"RUNNING",
    "22":"COMPLETE",
    "100":"GO"
};

var nodeStatusDetailMap = {
	"1":"COMPLETE_SUCCESS",
	"-1":"COMPLETE_ERROR",
	"-2":"COMPLETE_TIMEOUT",
	"-3":"COMPLETE_CANCEL"
};

var nodeTypeMap = {
	"1":"NODE_TYPE_START",
	"2":"NODE_TYPE_NOMARL"
};

var nodeExecuteTypeMap = {
	"1":"NODE_EXECUTE_TYPE_NONE",
	"2":"NODE_EXECUTE_TYPE_SHELL",
	"3":"NODE_EXECUTE_TYPE_HTTP"
};

var nodeStartTypeMap = {
	"1":"NODE_START_TYPE_DEFAULT",
	"2":"NODE_START_TYPE_WAIT_REQUEST",
	"3":"NODE_START_TYPE_TIMER"
};

var labelMap = {
	
	"READY":"READY",
	"PROCESSING":"PROCESSING",
	"COMPLETE":"<i class='fas fa-check-circle' style='color:green'></i>COMPLETE",
	"ERROR":"<i class='fas fa-times-circle' style='color:red'></i>ERROR",
	"CANCEL":"CANCEL",

    "SKIPED":"SKIPED",
    "WAIT":"WAIT",
    "READY":"READY",
    "ARRIVED":"ARRIVED",
    "OPENNING":"OPENNING",
    "CLOSED":"CLOSED",
    "READY_RUN":"READY_RUN",
    "RUNNING":"RUNNING",
    "COMPLETE":"<i class='fas fa-check-circle' style='color:green'></i>COMPLETE",
    "GO":"GO",

	"COMPLETE_SUCCESS":"COMPLETE_SUCCESS",
	"COMPLETE_ERROR":"COMPLETE_ERROR",
	"COMPLETE_TIMEOUT":"COMPLETE_TIMEOUT",
	"COMPLETE_CANCEL":"COMPLETE_CANCEL",

	"NODE_TYPE_START":"NODE_TYPE_START",
	"NODE_TYPE_NOMARL":"NODE_TYPE_NOMARL",

	"NODE_EXECUTE_TYPE_NONE":"NODE_EXECUTE_TYPE_NONE",
	"NODE_EXECUTE_TYPE_SHELL":"NODE_EXECUTE_TYPE_SHELL",
	"NODE_EXECUTE_TYPE_HTTP":"NODE_EXECUTE_TYPE_HTTP",

	"NODE_START_TYPE_DEFAULT":"DEFAULT",
	"NODE_START_TYPE_WAIT_REQUEST":"WAIT_REQUEST",
	"NODE_START_TYPE_TIMER":"<i class='fas fa-clock' style='color:blue'></i>TIMER",

}

function getLabel(name){
	
	if(labelMap[name]){
		return labelMap[name];
	}
	
	return name;
}
