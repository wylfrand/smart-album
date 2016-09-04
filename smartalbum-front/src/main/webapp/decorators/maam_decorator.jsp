<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ include file="/WEB-INF/jsp/common/include.jsp" %>

<!--[if IE]> <html class="ie" xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->
<!--[if !IE]> <html xmlns="http://www.w3.org/1999/xhtml"> <![endif]-->

    <head>
    
    	<!--[if IE]>
			<style type="text/css">
				.box-off-creation {margin-right:-20px!important;}
				.mobile-off {margin-right:-10px!important;}
			</style>
		<![endif]-->
        <title><decorator:title default="MAAM" /></title>
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta lang="fr" content="T&eacute;l&eacute;phone ble, forfait mobile, sfr adsl, t&eacute;l&eacute;phone mobile, abonnement adsl, sfr mobile+adsl" name="keywords" />
        <meta content="Sp&eacute;cialiste de la t&eacute;l&eacute;phonie mobile, SFR vous propose de nombreux abonnement mobile pour t&eacute;l&eacute;phone portable. D&eacute;couvrez les offres de forfait mobile et de t&eacute;l&eacute;phone mobile. SFR Mobile + ADSL, la nouvel" name="description" />
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
        <meta content="index, follow, all" name="robots" />
        <link rel="shortcut icon" href="//img.s-sfr.fr/elements/favicon.ico"></link>
           
        <!-- IST V2-->
        <script src="//static.s-sfr.fr/resources/ist/loader.sfr.min.js" type="text/javascript"></script>
        <script type="text/javascript">
        	sfrIstConfig.myLinesSelectedType = "line";
        	sfrIstConfig.myLinesSelectedItem = 1;
	        sfrIstConfig.menuSelectedItem = 22000;
	        sfrIstConfig.navStartPoint = 200;
	        sfrIstConfig.navSelectedItem = 1350;
	        sfrIstConfig.navDeep = 2;
        </script>
        
        <!--<link rel="stylesheet" type="text/css" href="//s2.s-sfr.fr/css/boutique_ligne/common_light.css" />-->
        <link rel="stylesheet" type="text/css" href="//s2.s-sfr.fr/css/default_struct.css" />
        <!--css de la page-->
        
        <!-- khadi-->
        <link rel="stylesheet" type="text/css" href="<c:url value='/css/css.css'/>" media="all"/>
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap.min.css'/>" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/bootstrap-responsive.min.css'/>" rel="stylesheet" type="text/css"/>
		<link rel="stylesheet" type="text/css" href="<c:url value='/css/custom.css'/>" rel="stylesheet" type="text/css"/>
		<script src="<c:url value='http://bootstrap.twit.free.fr/js/jquery.js'/>"></script>
		<script src="<c:url value='/js/bootstrap.js'/>"></script>
		<script src="<c:url value='/js/bootstrap.min.js'/>"></script>
		<script src="<c:url value='http://bootstrap.twit.free.fr/bootstrap/js/bootstrap.min.js'/>"></script>
        <!-- KHadi -->
        
        <!-- MAAM -->
        <script language="javascript" type="text/javascript" src="<c:url value='/js/maam.js'/>"></script> 
        
        <!-- Fancy box -->
		<script type="text/javascript" src="<c:url value='/js/fancybox/jquery.fancybox-1.3.4.js' />"></script>
		<link rel="stylesheet" type="text/css" href="<c:url value='/js/fancybox/jquery.fancybox-1.3.4.css' />" media="screen" />
        
        
        <!--[if IE 6]>
        <script type="text/javascript" src="js/DD_belatedPNG_0.0.8a-min.js"></script>
        <![endif]-->
        <script type="text/javascript">
            function init_toggle(){
                // toggle
                $(".toggle_div").hide();
                
                $(".btn-add-line").click(function(){
                    $(this).parent().next().slideToggle("medium");
                    return false;
                });
                $(".btn-close-line").click(function(){
                    $(this).parents('.toggle_div').slideToggle("medium");
                    return false;
                });
                
                $(".add-line-first").show();
            }
        
            $(document).ready(function(){   
                init_toggle();
                
                // Go to top button
                $(".go-to-top").click(function(){
                     $('html, body').animate({ scrollTop: 0 }, 'medium');
                     return false;
                });
                
                //POPIN
                $('a.openLayer').bind('click',function(){
                    popId = this.hash;
                    $(popId).show();
                    $('html, body').animate({ scrollTop: 0 }, 'medium');
                });
                $('a.popinClose').bind('click',function(){
                    $(this).parents('.popinLayer').fadeOut("fast");
                    return false;
                });
            });
        </script>
        
        
        <script type="text/javascript">
		$(document).ready(function() {
			// gere le toggle des mentions
		        $("#div-mentions").hide();
			$("#mentions").click(function(){
			    $("#div-mentions").slideToggle(350);
			    return false;
			});
			
			// gestion menu
			$('.tog-div').hide();
			$('#div-comment').show();
			$('.menu-item').click(function(){
				var id = $(this).attr('id');
				$('.tog-div').hide();
				$('#div-'+id).fadeToggle(250);
				$('.menu-item').removeClass('current');
				$(this).addClass('current');
				
				return false;
			});
		});
	</script>

        <decorator:head />
        
        <script language="javascript">
            function initMultiLigneBox(){
                $("#selectLineBox").fancybox({
                    'height'            : 175,
                    'autoScale'         : true,
                    'centerOnScroll'    : true,
                    'hideOnOverlayClick': false,
                    'autoDimensions'    : true,
                    'showCloseButton'   : false,
                    'padding'           : 0
                });  
            }
            
            var autoStartBox = false;
            <c:if test="${startFancyBoxSelectLine}">
                autoStartBox = true;
            </c:if>
            
            if(autoStartBox){
               $(document).ready(function(){
                  initMultiLigneBox();
                  $("#selectLineBox").trigger('click');
               });
            }
            
            function showSelectLigne(){
                initMultiLigneBox();
                $("#selectLineBox").trigger('click');
            }
        </script>
        
        <!-- STATS -->
        <script type="text/javascript" src="//static.s-sfr.fr/stats/header.js"></script>
        <!-- / STATS -->
    </head>
        
    <!-- ##### Body de la page ##### -->
    <body>
    	<!--[if IE 7]><div id="IE"><![endif]-->
    	
          <div id="ctn_gbl">
                <c:if test="${not (MODE_EXTRANET eq true)}">
	                <!-- IST HEADER HOME -->
	                <script type="text/javascript">$sfr.istHeaderHome();</script>
	                <!-- / IST HEADER HOME -->
	                <!-- IST NAVIGATION HORIZONTALE -->
	                <script type="text/javascript">$sfr.istNav();</script>
	                <!-- / IST NAVIGATION HORIZONTALE -->
	                <!-- IST CHEMIN DE FER -->
				    <script type="text/javascript">$sfr.istBreadcrumb();</script>
					<!-- /IST CHEMIN DE FER -->
                </c:if>
                <!-- <div id="headerProto">&nbsp;</div> -->
				
                <!-- #entete -->
	                 <table id="mainTable">
		                <tr class="mainTableTr">
		                	<td class="mainTableLeftMenu">
		                		<script type="text/javascript">$sfr.istMyLines();</script>
		                	</td>
		                	<td>
		                    	<decorator:body />
		                    </td>
		                 </tr>
	                 </table>
	                  </div>
	                 <div>
                <c:if test="${not (MODE_EXTRANET eq true)}">
	                <!-- IST FOOTER -->
	                <script type="text/javascript">$sfr.istFooter();</script>
	                <!-- / IST FOOTER -->
	            </c:if>
                <!-- <div id="footerProto">&nbsp;</div> -->
            </div>
         
        <img height="1" width="1" style="border-style:none;" alt="" src="http://www.googleadservices.com/pagead/conversion/987427255/?label=rar-COHYqgMQt-Pr1gM&amp;guid=ON&amp;script=0"/>
            
        <!--[if IE 6]>
        <script type="text/javascript">DD_belatedPNG.fix('img, .png_bg');</script>
        <![endif]-->
        <!-- Box Multi ligne -->
        <a id="selectLineBox" href="<c:url value='/select-ligne.html?ie_time_debug=' /><%=(new java.util.Date()).getTime()%>" ></a>
        
        <!--[if IE]></div><![endif]-->
        
        <!-- STATS -->
        <script type="text/javascript" src="//static.s-sfr.fr/stats/footer.js"></script>
        <!-- /STATS -->
    </body>
</html>