
	// Adapted from http://indiegamr.com/generate-repeatable-random-numbers-in-js/
	var _seed = Date.now();
	
	function valueOrDefault(val,def){
		if(val){
			return val;
		}else{
			return def;
		}
	}
var Utils = {

	
	srand:function (seed) {
	  _seed = seed;
	},
	
	rand: function (min, max) {
	  min = (min, 0);
	  max = valueOrDefault(max, 0);
	  _seed = (_seed * 9301 + 49297) % 233280;
	  return min + (_seed / 233280) * (max - min);
	},
	
	numbers:function (config) {
	  var cfg = config || {};
	  var min = valueOrDefault(cfg.min, 0);
	  var max = valueOrDefault(cfg.max, 100);
	  var from = valueOrDefault(cfg.from, []);
	  var count = valueOrDefault(cfg.count, 8);
	  var decimals = valueOrDefault(cfg.decimals, 8);
	  var continuity = valueOrDefault(cfg.continuity, 1);
	  var dfactor = Math.pow(10, decimals) || 0;
	  var data = [];
	  var i, value;
	
	  for (i = 0; i < count; ++i) {
	    value = (from[i] || 0) + this.rand(min, max);
	    if (this.rand() <= continuity) {
	      data.push(Math.round(dfactor * value) / dfactor);
	    } else {
	      data.push(null);
	    }
	  }
	
	  return data;
	},
	
	points:function (config) {
	  const xs = this.numbers(config);
	  const ys = this.numbers(config);
	  return xs.map((x, i) => ({x, y: ys[i]}));
	},
	
	bubbles:function (config) {
	  return this.points(config).map(pt => {
	    pt.r = this.rand(config.rmin, config.rmax);
	    return pt;
	  });
	},
	
	labels:function (config) {
	  var cfg = config || {};
	  var min = cfg.min || 0;
	  var max = cfg.max || 100;
	  var count = cfg.count || 8;
	  var step = (max - min) / count;
	  var decimals = cfg.decimals || 8;
	  var dfactor = Math.pow(10, decimals) || 0;
	  var prefix = cfg.prefix || '';
	  var values = [];
	  var i;
	
	  for (i = min; i < max; i += step) {
	    values.push(prefix + Math.round(dfactor * i) / dfactor);
	  }
	
	  return values;
	},
	
	MONTHS: [
	  'January',
	  'February',
	  'March',
	  'April',
	  'May',
	  'June',
	  'July',
	  'August',
	  'September',
	  'October',
	  'November',
	  'December'
	],
	
	months:function (config) {
	  var cfg = config || {};
	  var count = cfg.count || 12;
	  var section = cfg.section;
	  var values = [];
	  var i, value;
	
	  for (i = 0; i < count; ++i) {
	    value = Utils.MONTHS[Math.ceil(i) % 12];
	    values.push(value.substring(0, section));
	  }
	
	  return values;
	},
	
	COLORS: [
	  '#4dc9f6',
	  '#f67019',
	  '#f53794',
	  '#537bc4',
	  '#acc236',
	  '#166a8f',
	  '#00a950',
	  '#58595b',
	  '#8549ba'
	],
	
	color:function (index) {
	  return Utils.COLORS[index % Utils.COLORS.length];
	},
	
	CHART_COLORS: {
	  red: 'rgb(255, 99, 132)',
	  orange: 'rgb(255, 159, 64)',
	  yellow: 'rgb(255, 205, 86)',
	  green: 'rgb(75, 192, 192)',
	  blue: 'rgb(54, 162, 235)',
	  purple: 'rgb(153, 102, 255)',
	  grey: 'rgb(201, 203, 207)'
	},
	
	NAMED_COLORS: [
	  'rgb(255, 99, 132)',
	  'rgb(255, 159, 64)',
	  'rgb(255, 205, 86)',
	  'rgb(75, 192, 192)',
	  'rgb(54, 162, 235)',
	  'rgb(153, 102, 255)',
	  'rgb(201, 203, 207)',
	],
	
	namedColor:function (index) {
	  return Utils.NAMED_COLORS[index % Utils.NAMED_COLORS.length];
	},
	
	newDate:function (days) {
	  return DateTime.now().plus({days}).toJSDate();
	},
	
	newDateString:function (days) {
	  return DateTime.now().plus({days}).toISO();
	},
	
	transparentize:function(color,num){
		return color;
	},
	
	parseISODate:function (str) {
	  return DateTime.fromISO(str);
	}
}