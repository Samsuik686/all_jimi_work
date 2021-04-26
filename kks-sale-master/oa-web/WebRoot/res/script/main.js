function loadMailCount(mailCountddress,mailAddress){
	if(null!=mailCountddress&&""!=mailCountddress){
		//$("#mailCount").attr("href",mailAddress);
		$.ajax({
	        dataType: 'jsonp',
	        async : true,
	        url: mailCountddress,
	        success: function(data){
	              result(data);
	        }
		});
	}
}
function result(data){
	if(null!=data.code)
		$("#mailCount").html(data.msg);
	else
		$("#mailCount").html(data.data);
	window.setTimeout("loadMailCount('"+mailCountddress+"','"+mailAddress+"')", 30000);
}