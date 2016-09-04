<%@ include file="/WEB-INF/jsp/common/include.jsp"%>
<div class="errorClass">
	<hr />
	<h3>Error Message</h3>
	<s:actionerror />
	<p><c:out value="${exception.message}" /></p>
	<hr />
	<h3>Technical Details</h3>
	<p><c:out value="${exceptionStack}" /></p>
</div>

