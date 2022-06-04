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
