var operator = "=";

function getCookieValue(keyStr){
	var value = null;
	var s = window.document.cookie;
	var arr = s.split("; ");
	for(var i=0; i<arr.length; i++){
		var str = arr[i];
		var k = str.split(operator)[0];
		var v = str.split(operator)[1];
		if(k == keyStr){
			value = v;
			break;
		}
	}
	return value;
}

function setCookieValue(key,value){
	//默认为一天的过期时间
	var exp = new Date();
	exp.setTime(exp.getTime() + 24*60*60*1000);
	document.cookie = key+operator+value +";expires=" + exp.toGMTString();

}
