/**
 *  tab窗口大小改变
 * @author wg.he
 * @date 2013/12/20
 */
	var n=0;
	$(window).resize(function() {
	    if(n%2==0){
	   		window.setTimeout("datagridInit()",500);
    	}
    	n++;
	});

	 

 