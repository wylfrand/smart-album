$(function() {
	$(document).on('focusin', '.field', function() {
		if(this.title==this.value) {
			this.value = '';
		}
	}).on('focusout', '.field', function(){
		if(this.value=='') {
			this.value = this.title;
		}
	}).on('click', '#to-top a', function() {
		$('html, body').animate({
			scrollTop: 0
		}, {
			duration: 900,
			easing: 'easeInOutQuart'
		});
		return false;
	}).on('click', 'a.reply', function() {
		$('#comment-form').slideDown(400);
		$('html, body').animate({
			scrollTop: $('#comment-form').offset().top
		}, 500);
		return false;
	}).on('mouseenter', '#project-slider .flex-control-nav a', function() {
		if(!$(this).hasClass('active')) {
			$(this).find('.popout').show(200, function() {
				$(this).find('img').fadeIn(200);
			});
		}
	}).on('mouseleave', '#project-slider .flex-control-nav a', function() {
		$(this).find('.popout').stop(true,true).hide(300, function() {
			$(this).find('img').hide();
		});
	}).on('click', '#project-slider .flex-control-nav a', function() {
		$(this).find('.popout').stop(true,true).hide(300, function() {
			$(this).find('img').hide();
		});
	});

	$(window).load(function() {
		if($('#tweets').length) {
			$('#tweets').tweet({
				username: 'chocotemplates',
				count: 3,
				loading_text: 'Loading latest tweets',
				template: '{text}{time}'
			}).bind('loaded', function() {
				$('.tweet_list').addClass('slides');
				$('#tweets').flexslider({
					slideshowSpeed: 10000,
					directionNav: false,
					controlNav: false,
					keyboardNav: false
				});
			});
		}

		if($('#large-slider').length) {
			$('#large-slider').flexslider({
				animation: 'slide',
				animationDuration: 900,
				slideshowSpeed: 10000,
				keyboardNav: false,
				prevText: '',
				nextText: '',
				start: function(slider) {
					$('#large-slider .flex-control-nav').css({
						width: (slider.count * 20),
						marginLeft: (0 - (slider.count * 10))
					});
				}
			})
		}

		if($('#large-slider2').length) {
			$('#large-slider2 .text').flexslider({
				animation: 'slide',
				slideDirection: 'vertical',
				slideshowSpeed: 10000,
				animationDuration: 800,
				directionNav: false,
				keyboardNav: false
			});

			$('#large-slider2 .images').flexslider({
				animation: 'slide',
				slideshowSpeed: 10000,
				animationDuration: 800,
				directionNav: false,
				keyboardNav: false,
				controlsContainer: '#large-slider2 .thumbs',
				start: function(slider) {
					$('#large-slider2 .thumbs li').each(function() {
						var large_src = $('#large-slider2 .images .slides li').eq($(this).index() + 1).find('img').attr('src');
						var img_name = large_src.split('/');
						var th_name = 'th-'+ img_name[img_name.length-1];
						large_src = large_src.replace(img_name[img_name.length-1], th_name);
						$(this).find('a').html('<img src="' + large_src + '" />');
					});

					$('#large-slider2 .thumbs li:eq(0) a').animate({
						padding: 5,
						marginLeft: 6,
						marginRight: 6
					}, {
						duration: 200,
						easing: 'linear'
					});
				},
				before: function(slider) {
					$('#large-slider2 .text .flex-control-nav li').eq(slider.animatingTo).find('a').trigger('click');

					$('#large-slider2 .thumbs li:eq(' + slider.animatingTo + ') a').animate({
						padding: 5,
						marginLeft: 6,
						marginRight: 6
					}, {
						duration: 200,
						easing: 'linear'
					});

					$('#large-slider2 .thumbs li:eq(' + slider.currentSlide + ') a').animate({
						padding: 0,
						marginLeft: 12,
						marginRight: 12
					}, {
						duration: 200,
						easing: 'linear'
					});
				}
			});
		}

		if($('#project-slider').length) {
			$('#project-slider .container').flexslider({
				animation: 'slide',
				animationDuration: 800,
				slideshow: false,
				controlsContainer: '#project-slider nav',
				start: function(slider) {
					$('#project-slider .flex-control-nav').css({
						width: (slider.count * 20)
					}).find('li').each(function() {
						var this_idx = $(this).index();

						$(this).find('a').append('<span class="popout"><img src="' + slider.container.find('li').eq(this_idx+1).find('img').attr('data-thumb') + '" /></span>');
					});
				}
			})
		}

		if($('.slider').length) {
			$('.slider').flexslider({
				animation: 'slide',
				slideshow: false,
				prevText: '',
				nextText: '',
				controlNav: false
			})
		}
	});

	if($('.tabs').length) {
		init_tabs('.tabs');
	}

	$('a[data-popup]').colorbox({
		opacity: .8,
		initialWidth: 300,
		initialHeight: 150
	});

	$('a[data-inline]').colorbox({
		opacity: .8,
		inline: true,
		initialWidth: 300,
		initialHeight: 150
	});

	if($('#contact').length) {
		var name_field = new LiveValidation( "name-field" );
		var email_field = new LiveValidation( "email-field" );
		var msg_field = new LiveValidation( "message-field" );

		name_field.add( Validate.Presence );
		email_field.add( Validate.Presence );
		msg_field.add( Validate.Presence );

		name_field.add( Validate.Length, { minimum: 4 });
		email_field.add( Validate.Email );
		msg_field.add( Validate.Length, { minimum: 10 });

		$(document).on('submit', '#contact', function() {
			if($('#contact .LV_invalid_field').length == 0) {
				var target = $(this);
				submit_form(target);
			}
			return false;
		});
	}
});

function submit_form(target) {


	$.post(target.attr('action'), target.serialize(), function(data) {
		target.find('.row:last p').html(data).fadeIn(300);
	});
}

function init_tabs(container) {
	$(container).each(function() {
		$this = $(this);

		$this.find('.tab-links li:first').addClass('current');
		$this.find('.tab-entry:first').addClass('current');
	});

	$(document).on('click', '.tab-links li', function() {
		var $this = $(this);

		if(!$this.hasClass('current')) {
			var tab_idx = $this.index();

			$this.addClass('current').siblings('.current').removeClass('current');
			$this.parents('.tabs:eq(0)').find('.tab-entry').eq(tab_idx).slideDown(400).siblings('.tab-entry:visible').slideUp(400);
		}
		return false;
	})
}