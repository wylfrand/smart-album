/*
  simpleFade (a jQuery plugin)
  http://helloworld.free.bg/simpleFade/
  m2k 2010
*/
(function( $ ){
	$.fn.simpleFade = function(options) {

	// Fields
	var item  = this;
	var timer = null;
	var index = 0;
	var animated = false;
	
	// Properties
	var s = $.extend({
		speed    : 400, 
		duration : 5000,
		auto     : true,
		effect   : null,
		animate  : {opacity: 'hide'},
		easing   : 'swing',
		init   : null,
		onFade : null, 
		onAfterFade : null, 
		fadeTo : FadeToT,
		next   : Next,
		prev   : Prev,
		stop   : StopT,
		start  : StartT
	}, options || {});
	
	// Init
	if($.isFunction(s.init)) s.init();
	SetActive();
	OnFade(0);
	OnAfterFade(0);
	Start();
	
	// Public
	function Next() {
		if (animated) return;
		FadeTo(index == item.length-1 ? 0 : index+1);
	}
	function Prev() {
		if (animated) return;
		FadeTo(index-1 < 0 ? item.length-1 : index-1);
	}
	function FadeToT(idx) {
		if (animated || idx == index) return;
		FadeTo(idx);
	}
	
	function StartT() {
		s.auto = true;
		Start();
	}
	
	function StopT() {
		s.auto = false;
		Stop();
	}
	
	// Private
	function SetActive() {
		item.css('z-index', 1).show().eq(index).css('z-index', item.length);
	}
	
	function PrepareActive(idx) {
		item.eq(idx).css('z-index', item.length-1);
	}
	
	function Start() {
		if (!s.auto) return;
		
		timer = setInterval(function () {
			FadeTo(index == item.length-1 ? 0 : index+1);
		}, s.duration);
	}
	
	function Stop() {
		clearInterval(timer);
	}
	
	function FadeTo(idx) {
		Stop();
		PrepareActive(idx);
		Fade(idx);
	}
	
	function Fade(idx) {
		animated = true;
		if (s.effect != null) {
			item.eq(index).effect(s.effect, s.speed, function () {
				AfterFade(idx);
			});
		} else {
			var style = item.length ? item.eq(index)[0].style.cssText : '';
			item.eq(index).animate(s.animate, s.speed, s.easing, function () {
				this.style.cssText = style;
				AfterFade(idx);
			});
		}
		OnFade(idx);
	}
	
	function AfterFade(idx) {
		index = idx;
		SetActive();
		animated = false;
		OnAfterFade(idx);
		Start();
	}
	
	function OnFade(idx) {
		if($.isFunction(s.onFade)) s.onFade(idx);
	}
	function OnAfterFade(idx) {
		if($.isFunction(s.onAfterFade)) s.onAfterFade(idx);
	}
	
	return this;
	};
})( jQuery );