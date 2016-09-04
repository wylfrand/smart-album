//jCarousel Callback 
function mycarousel_initCallback(carousel) {
	$('#next, #next3').bind('click', function() {
        carousel.next();
        return false;
    });
    $('#prev, #prev3').bind('click', function() {
        carousel.prev();
        return false;
    });
    $('.slider-nav a').bind('click', function() {
        carousel.scroll(jQuery.jcarousel.intval(jQuery(this).text()));
        return false;
    });
    $('.slider-thumbs li, .project-slider-thumbs li').bind('click', function() {
    carousel.scroll(jQuery.jcarousel.intval($(this).index()+1));
    return false;
    });
}
function mycarousel_itemFirstInCallback(carousel, item, idx, state) {
	$('.slider-nav ul li a, .slider-thumbs a, .project-slider-thumbs li a').removeClass('active');
	$('.slider-nav ul li a, .slider-thumbs a, .project-slider-thumbs li a').eq(idx-1).addClass('active');
}

function mycarousel2_initCallback(carousel) {
	$('#next2').bind('click', function() {
        carousel.next();
        return false;
    });
    $('#prev2').bind('click', function() {
        carousel.prev();
        return false;
    });
}

$(function(){ 
	
	//Navigation
	$('#navigation ul li').hover(function(){ 
		$(this).find('.dd:eq(0)').show();
		$(this).find('a:eq(0)').addClass('hover');
	 },
	 function(){  
		$(this).find('.dd').hide();
		$(this).find('a:eq(0)').removeClass('hover');
 	});
 	
 	$('.dd ul li').hover(function(){ 
 		$(this).find('.dd').show();
 		$(this).find('a:eq(0)').addClass('hover');
 	 },
 	 function(){ 
 	 	$(this).find('.dd').hide();
 		$(this).find('a:eq(0)').removeClass('hover');
 	  });
	
	//Home 1 Carousel
	$('.slider-content ul, .project-slider-content ul').jcarousel({
		auto: 5,
		wrap: "last",
		scroll: 1,
		visible: 1,
		initCallback: mycarousel_initCallback,
        buttonNextHTML: null,
        buttonPrevHTML: null,
        itemFirstInCallback: mycarousel_itemFirstInCallback
	});
	
	//Home 2 Carousels
	$('.slider-content2 ul').jcarousel({
		wrap: "last",
		scroll: 1,
		visible: 1,
		initCallback: mycarousel_initCallback,
        buttonNextHTML: null,
        buttonPrevHTML: null,
        itemFirstInCallback: mycarousel_itemFirstInCallback
	});
	
	$('.slider-thumbs ul').jcarousel({
		wrap: "last",
		scroll: 1,
		visible: 9,
		initCallback: mycarousel2_initCallback,
        buttonNextHTML: null,
        buttonPrevHTML: null
	});
	
	//Fancy box 
	$(".gallery li a, .project-gallery li a, .service-main-image a").fancybox({
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic',
		'easingIn'      : 'easeOutBack',
		'easingOut'     : 'easeInBack',
		'overlayColor'	: '#000',
		'overlayOpacity' : '0.7'
	});

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
		var alert_msg = $('.msg-alert');
		var error = false;
		form.find('.required').each(function(){
			field = $(this);
			field_holder = field.parents('.row:eq(0)');
			v = $(this).val();
			t = $(this).attr('title');
			id = $(this).attr('id');
			
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
				//error_fields.push( field );
			}
		});
		
		if( !error ) {
		
			form.find('.required').removeClass('field-error');
			var data = {}
			
			form.find('.text-field').each(function(){
				data[ $(this).attr('name') ] = $(this).val();
			});
			
			form.find('.text-field').each(function(){
				$(this).val( $(this).attr('title') );
			});
			
			$('#message-field').val( 'Loading...' );
			
			$.post('php/send.php', data, function(){
				alert_msg.fadeOut();
				form.find('.msg-thanks').fadeIn(function(){
					
					$('#message-field').val( $('#message-field').attr('title') );
					
					window.setTimeout(function(){
						form.find('.msg-thanks').fadeOut();
					}, 5000);
					
				});
			});
		}
		
		return false;
	}); 
	
 });