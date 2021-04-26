/**
 * 
 * @authors lihfei ()
 * @date    2016-08-27 20:04:08
 * @version 0.01
 */

;(function($){
	$.fn.mobileModal=function(options,fn){
		var _this = this;
		if(options=="hide"){
			this.hide();
			fn && fn();
		}else {
			this.show();
		}
		$(this).find(".mobile-btn-cencal,.mobile-close").click(function(){
			$(_this).hide();
			fn && fn();
		});
		return this;
	};

	$.extend({
		mobileMsg: function(content,time){
			time ? time : time = 2000;
			$(".mobile-loading").length > 0 ? $(".mobile-msg").remove() : "";
			$("body").append("<div class='mobile-msg'>"+content+"</div>");
			setTimeout(function() {
				$(".mobile-msg").remove();
			}, time);
		},
		loading: function(flag,time){
			if(flag && flag == "close"){
				if(time && typeof(time) == "number"){
					setTimeout(function() {
						$(".mobile-loading").remove();
					}, time);
				}else{
					$(".mobile-loading").remove();
				}
			}else{
				$(".mobile-loading").length > 0 ? $(".mobile-loading").remove() : "";
				$("body").append("<div class='mobile-loading'></div>");
			}

		}/*,
		preventTouch: function(flag){
			if(flag != false){
				document.addEventListener('touchmove', preventDefault, false);
			}else{
				document.removeEventListener('touchmove', preventDefault, false);// 恢复
			}
			function preventDefault(e) { e.preventDefault(); };
		}*/

	});

})(jQuery);