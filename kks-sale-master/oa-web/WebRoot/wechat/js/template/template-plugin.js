template.helper('dateFormat', function(date, format) {
	if(date){
		date = new Date(date);
		
		var map = {
				"M" : date.getMonth() + 1, // 月份
				"d" : date.getDate(), // 日
				"h" : date.getHours(), // 小时
				"m" : date.getMinutes(), // 分
				"s" : date.getSeconds(), // 秒
				"q" : Math.floor((date.getMonth() + 3) / 3), // 季度
				"S" : date.getMilliseconds()
				// 毫秒
		};
		format = format.replace(/([yMdhmsqS])+/g, function(all, t) {
			var v = map[t];
			if (v !== undefined) {
				if (all.length > 1) {
					v = '0' + v;
					v = v.substr(v.length - 2);
				}
				return v;
			} else if (t === 'y') {
				return (date.getFullYear() + '').substr(4 - all.length);
			}
			return all;
		});
		return format;
	}
});

template.helper('dateCompare', function(date, date2) {

	if (!date || !date2) {
		return false;
	}

	var r = /^[0-9]+.?[0-9]*$/;

	if (r.test(date) == false) {
		return false;
	}
	
	if (r.test(date2) == false) {
		return false;
	}

	if (date > date2) {
		return true;
	}

	return false;
});

template.helper('numberFormat', function(number, form) {
	
	if(!number || !form){
		return "";
	}

	var forms = form.split('.'), number = '' + number, numbers = number
			.split('.'), leftnumber = numbers[0].split(''), exec = function(
			lastMatch) {
		if (lastMatch == '0' || lastMatch == '#') {
			if (leftnumber.length) {
				return leftnumber.pop();
			} else if (lastMatch == '0') {
				return lastMatch;
			} else {
				return '';
			}
		} else {
			return lastMatch;
		}
	}, string

	string = forms[0].split('').reverse().join('').replace(/./g, exec)
			.split('').reverse().join('');
	string = leftnumber.join('') + string;

	if (forms[1] && forms[1].length) {
		leftnumber = (numbers[1] && numbers[1].length) ? numbers[1].split('')
				.reverse() : [];
		string += '.' + forms[1].replace(/./g, exec);
	}
	return string.replace(/ .$/, '');

});
