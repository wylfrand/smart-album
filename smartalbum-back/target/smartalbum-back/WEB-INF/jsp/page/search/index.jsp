<%@page contentType="text/html;charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page session="false"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<script type="text/javascript">
function searchImages(){
	$.fancybox.showLoading();
     $("#searchOptions").closest("form").submit();
  }
</script>
<%-- <%@ include file="/WEB-INF/jsp/page/search/searchOptions.jsp" %> --%>
<div>
	<%@ include file="/WEB-INF/jsp/page/search/searchWidget.jsp" %>
</div>
<!-- <div id="footer-push" class="notext">&nbsp;</div> -->