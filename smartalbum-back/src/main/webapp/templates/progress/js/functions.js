//Custom Font
Cufon.replace('#location h2, .slider1-content .text-holder h2, h2.title-cnt', {fontFamily: 'Helvetica',color: '-linear-gradient(#fff, #b4b4b4)'});
Cufon.replace('.service-text h3, .project-item h3, .under-construction-bg h2', {fontFamily: 'Helvetica'});
Cufon.replace('.request-btn', {fontFamily: 'Myriad Pro', hover: true, textShadow: '1px 1px #fff'});

//Twitter Username
var TWITTER_USERNAME = 'chocotemplates';

//Home Style 1 Carousel Callback
function mycarousel_initCallback(carousel) {
	$('#next, .project-slider-next').bind('click', function() {
        carousel.next();
        return false;
    });

    $('#prev, .project-slider-prev').bind('click', function() {
        carousel.prev();
        return false;
    });
    
    $('.slider1-nav a').bind('click', function() {
        carousel.scroll(jQuery.jcarousel.intval(jQuery(this).text()));
        return false;
    });

}
    
function mycarousel_itemFirstInCallback(carousel, item, idx, state) {
	$('.slider1-nav a').removeClass('active');
	$('.slider1-nav a').eq(idx-1).addClass('active');
	
};

$(function(){ 
	
		
	//IE6 PNG fix
	if($.browser.msie && $.browser.version.substr(0,1) == 6){
		DD_belatedPNG.fix('.mark-featured, .pointer, #prev, #next, #screen, .close, .social-links a');
	}
	
	// Login Popup
	
	$('.close').click(function(){
		$('.login').fadeOut(function(){ $('#screen').hide(); });
	});
	
	$('.login-link').click(function(){
		
		var h = $( 'body' ).height() > $( window ).height() ? $( 'body' ).height() : $( window ).height();
		$('#screen').css({ 'height': h });	
			
		$('#screen').show();
		$('.login').center();
		$('.login').fadeIn();
		return false;
	});
	
	//Set height for Error page
	var wrapper_height = $('#wrapper2').height();
	$('.error-404').css('height',(wrapper_height - 119));
	
	//Navigation
	$('#navigation ul li').hover(function(){
		$(this).find('.dd-holder:eq(0)').show();
		$(this).find('a:eq(0)').addClass('hover');
	},
	function(){
		$(this).find('.dd-holder:eq(0)').hide();
		$(this).find('a:eq(0)').removeClass('hover');
	});
	
	//Tweets
	if( $('#tweets').length > 0 ) {
		$('#tweets').tweet({
			count: 2,
			username: TWITTER_USERNAME,
			loading_text: 'Loading twitter...'
		});
	};
	
	//Blink Fields
	 $('.blink').
    focus(function() {
        if(this.title==this.value) {
            this.value = '';
        }
    }).
    blur(function(){
        if(this.value=='') {
            this.value = this.title;
        }
    });
    
    //Animate Home Style 1 Controls 
    $('#prev').hover(function(){
    	$(this).animate({
    		left: '0'
    	},{
    		queue:false,
    		duration:200
    	});
    },
    function(){  
    	$(this).animate({
    		left: '-15px'
    	},{
    		queue:false,
    		duration:200
    	});
    });
    
     $('#next').hover(function(){
    	$(this).animate({
    		right: '0'
    	},{
    		queue:false,
    		duration:200
    	});
    },
    function(){  
    	$(this).animate({
    		right: '-14px'
    	},{
    		queue:false,
    		duration:200
    	});
    });
    
    //Set Width Carousel's Item
    var slider1_width = $('.slider1-content').width();
    $('.slider1-content ul li, slider1-content .jcarousel-clip').css("width",slider1_width);
    
    //Home Style 1 Carousel
    $('.slider1-content ul').jcarousel({
    	auto: 5,
		wrap: "last",
		scroll: 1,
		visible: 1,
		initCallback: mycarousel_initCallback,
        buttonNextHTML: null,
        buttonPrevHTML: null,
        itemFirstInCallback: mycarousel_itemFirstInCallback
	});
	
	//Project Page Carousel
	 $('.project-slider-content ul').jcarousel({
    	auto: 5,
		wrap: "last",
		scroll: 1,
		visible: 1,
		initCallback: mycarousel_initCallback,
        buttonNextHTML: null,
        buttonPrevHTML: null
	});
	
	//FancyBox
	$(".gallery li a, .project-gallery li a.image, #service-image").fancybox({
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'easingIn'      : 'easeOutBack',
		'easingOut'     : 'easeInBack',
		'overlayColor'	: '#000',
		'overlayOpacity' : '0.7'
	});
	
	 //Contact Form Input Focus 
 	  $('.row .field').focus(function(){ 
 	  	$(this).addClass('field-focus');
 	   }).blur(function(){
 	   	$(this).removeClass('field-focus'); 	  
 	 });
	    
	// Contact Form
	$('.validate-form').submit(function(){
		var form = $(this);
		form.find('.required').parents('.row:eq(0)').removeClass('field-error');
		
		var field, v, id, msg, t, field_holder;
		var alert_msg = form.find('.msg-alert');
		var error = false;
		var textarea = form.find('textarea');
		var num_errors = 0;
		
		form.find('.required').each(function(){
			field = $(this);
			field_holder = field.parents('.row:eq(0)');
			v = $(this).val();
			t = $(this).attr('title');
			id = $(this).attr('id');
			
			error = false;
			
			if( $(this).hasClass('valid-email') ){
				if( /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,4})+$/.test(v) == false ) {
					error = true;
				}
			}else {
				if( v == '' || v == t ){
					error = true;
				}
			}
			
			if( error ) {
				field.addClass('field-error');
				alert_msg.fadeIn();
				num_errors++;
			}else {
				field.removeClass('field-error');
			}
		});
		
		form.find('.field-error:eq(0)').focus();
		
		if( num_errors == 0 ) {
		
			form.find('.required').removeClass('field-error');
			var data = {}
			
			form.find('.text-field').each(function(){ data[ $(this).attr('name') ] = $(this).val(); });
			form.find('.text-field').each(function(){ $(this).val( $(this).attr('title') ); });
			
			textarea.val( 'Loading...' );
			
			$.post('php/send.php', data, function(){
				alert_msg.fadeOut();
				form.find('.msg-thanks').fadeIn(function(){
					
					textarea.val( textarea.attr('title') );
					
					window.setTimeout(function(){
						form.find('.msg-thanks').fadeOut();
					}, 5000);
					
				});
			});
		}
		
		return false;
	}); 
	
	
	
	//Home Style 2 Carousel
	$("#featureCarousel").featureCarousel({
    	largeFeatureWidth: 499,
    	largeFeatureHeight: 304,
    	smallFeatureWidth: 348,
    	smallFeatureHeight: 209,
    	carouselSpeed: 1000,
    	counterStyle: 3,
    	sidePadding: 66,
    	smallFeatureOffset: 62,
    	animationEasing: 'easeInOutSine'
    });
    
    
    //Social Tooltips
	tooltip();	
	
  	

});

this.tooltip = function(){	
	/* CONFIG */		
		xOffset = -25;
		yOffset = -80;		
	/* END CONFIG */		
	$("a.tooltip").hover(function(e){											  
		this.t = this.title;
		this.title = "";									  
		$("body").append("<p id='tooltip'>"+ this.t +"<span class='tooltip-pointer'></span></p>");
		$("#tooltip")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");		
    },
	function(){
		this.title = this.t;		
		$("#tooltip").remove();
    });	
	$("a.tooltip").mousemove(function(e){
		$("#tooltip")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};

jQuery.fn.center = function(loaded) {
    var obj = this;
    body_width = parseInt($(window).width());
    body_height = parseInt($(window).height());
    block_width = parseInt(obj.width());
    block_height = parseInt(obj.height());
    
    left_position = parseInt((body_width/2) - (block_width/2)  + $(window).scrollLeft());
    if (body_width<block_width) { left_position = 0 + $(window).scrollLeft(); };
    
    top_position = parseInt((body_height/2) - (block_height/2) + $(window).scrollTop());
    if (body_height<block_height) { top_position = 0 + $(window).scrollTop(); };
    
    if(!loaded) {
        
        obj.css({'position': 'absolute'});
        obj.css({ 'top': top_position });
        $(window).bind('resize', function() { 
            obj.center(!loaded);
        });
        $(window).bind('scroll', function() { 
            obj.center(!loaded);
        });
        
    } else {
        obj.stop();
        obj.css({'position': 'absolute'});
        obj.animate({ 'top': top_position }, 200, 'linear');
    }
}




