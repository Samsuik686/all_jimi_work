function tabs(tabTit,on,tabCon,callback){
	var index =0;
	$(tabCon).each(function(){
	  $(this).children().eq(0).show();
	  });
	$(tabTit).each(function(){
	  $(this).children().eq(0).addClass(on);
	  });
     $(tabTit).children().click(function(){
        $(this).addClass(on).siblings().removeClass(on);
         index = $(tabTit).children().index(this);
         $(tabCon).children().eq(index).show().siblings().hide();
         if(callback && typeof(callback)=="function"){
         	callback(index);
         }
    });
}
	