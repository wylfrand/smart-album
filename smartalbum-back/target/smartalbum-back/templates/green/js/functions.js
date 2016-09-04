var TWITTER_USERNAME = "chocotemplates"
$(function() {
	// Blinking Fields 
	$('.field, textarea').focus(function() {
        if(this.title==this.value) {
            this.value = '';
        }
    }).blur(function(){
        if(this.value=='') {
            this.value = this.title;
        }
    });

    // Img hover states
    $(document).on('mouseenter', 'img[data-hover]', function() {
    	if($(this).data('original_src') == undefined) {
    		$(this).data('original_src', $(this).attr('src'));
    	}
    	$(this).attr('src', $(this).attr('data-hover'));
    }).on('mouseleave', 'img[data-hover]', function() {
    	$(this).attr('src', $(this).data('original_src'));
    });

    // Fader call
	$('#featured-content .item').simpleFade({
		init : function () {
			fader = this;
		},
		onFade : function (index) {
			$('#featured-content .nav a').removeClass('active').eq(index).addClass('active');
		}
	});
	
	
	$('#featured-content .nav a').click(function () {
		fader.fadeTo($('#featured-content .nav a').index(this));
		return false;
	});

	$('#featured-content .container').mouseenter(function(){
		fader.stop();
	}).mouseleave(function(){
		fader.start();
	});

	centerFaderNav();

	// Insert the shadow
	$('<span class="shadow"></span>').prependTo('#gallery .nav li');

	$('#gallery .nav li').hover(function(){
		$(this).toggleClass('hover');
	});

	// Toggle gallery thumb info box
	$('#gallery li .box').mouseenter(function(){
		$(this).animate({
			'height': 227
		}, 300);
		$('.info', this).fadeIn(300);	
	}).mouseleave(function(){
		$('.info', this).fadeOut(300);
		$(this).animate({
			'height': 198
		}, 300);
	});

	// Toggle focus field
	$('.field').focus(function(){
		$(this).addClass('field-focus');
	}).blur(function(){
		$(this).removeClass('field-focus');
	});

	// Fancybox call
	$("a.fancybox").fancybox({
		helpers	: {
			overlay	: {
				opacity : 0.6
			}
		}
	});

	// Trigger homepage thumbs height adjustment
	$('.view-more').click(function(){
		viewMoreThumbs();
		return false;
	});


	// Filter thumbs 
	$('#gallery .nav a').click(function() {
		
		$('#gallery .nav li').removeClass('active');
		$(this).parent().addClass('active');
		galleryNavColors();
		
		var filterVal = $(this).text().toLowerCase().replace(' ','-');
				
		if(filterVal == 'all') {
			$('#gallery .container li.hidden').show().removeClass('hidden');
			$('#gallery .container').animate({
				height: '819px'
			},1000);

			$('.view-more').fadeIn();
		} else {
			
			$('#gallery .container li').each(function() {
				if(!$(this).hasClass(filterVal)) {
					$(this).hide().addClass('hidden');
				} else {
					$(this).show().removeClass('hidden');
				}
			});

			var listHeight = $('#gallery .list-thumbs').height();
			var visibleItems = ($('#gallery .container li').length - $('#gallery .container li.hidden').length);
		
			$('#gallery .container').animate({
				height: '100%'
			},1000);

			if (visibleItems < 10) {
				$('.view-more').fadeOut();
			};
		}

		return false;
	});


	// Valudation call
	var cf1 = new LiveValidation('cf1');
	cf1.add( Validate.Presence);
	cf1.add( Validate.Exclusion, { within: [ 'Name' ], failureMessage: "Please enter name!" });

	var cf2 = new LiveValidation('cf2');
	cf2.add( Validate.Email, { failureMessage: "Please enter email!" });

	var cf3 = new LiveValidation('cf3');
	cf3.add( Validate.Presence);
	cf3.add( Validate.Exclusion, { within: [ 'Your Message' ], failureMessage: "Please enter message!" } );

	$('.contact-form').submit(function(){
        var form = $('.contact-form');

        if (!$('.LV_invalid_field', form).length) {
        
        	$.post(form.attr('action'), form.serialize(), function(data){
                
                form.find('.success').html(data).fadeIn(function(){
                    
                    window.setTimeout(function(){
                        form.find('.success').fadeOut();
                    }, 5000);
                    
                });
            });

        }
        return false;
    });


	$('#sidebar .list-2 > li > a').click(function(){
		$('#sidebar .list-2 li a').removeClass('active');
		$(this).addClass('active');

		if ($(this).next().is(':visible')) {
			$('#sidebar .list-2 ul').slideUp();			
		} else {
			$('#sidebar .list-2 ul').slideUp();
			$(this).next().slideDown();			
		};

		return false;
	});

	// Tweets
    if( $('.tweets').length > 0 ) {
        $('.tweets').tweet({
            count: 2,
            username: TWITTER_USERNAME,
            template: "{text}{time}",
            loading_text: 'Loading latest tweets... please wait'
        });
    };

    var seeMoreTweets = "http://twitter.com/" + TWITTER_USERNAME;
    $('.footer-top .col-1 .btn-1').attr('href', seeMoreTweets);
});


function centerFaderNav(){
	var faderItems = $('#featured-content .nav li').length * 23;
	var faderPosition = (faderItems/2) * -1;

	$('#featured-content .nav').css({ 
		'width'       : faderItems,
		'margin-left' : faderPosition
	});

}

function galleryNavColors(){
	var itemOffset = $("#gallery .nav li.active").position().left;
	var itemWidth = $("#gallery .nav li.active").width();
	var pointerPos = Math.round(itemOffset + (itemWidth - 65)/2) - 1;
		$('#gallery .nav .colors').animate({
			left: pointerPos
		},{
			queue: false,
			duration: 300
		});
}

function viewMoreThumbs(){
	var itemsCount = $('#gallery .list-thumbs li').length;
	var rowHeight = parseInt((itemsCount * 273) / 3); // 273 - each li width
	var containerHeight = $('#gallery .container').height();
	var listHeight = $('#gallery .list-thumbs').height();
	var space = listHeight - containerHeight

		$('#gallery .container').animate({
			height: '+=' + space
			}, 1000, function(){
				$('.view-more').fadeOut();
			}
		);
		
	
}
