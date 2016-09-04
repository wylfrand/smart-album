<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" xmlns:th="http://www.thymeleaf.org">
<head>
	<title>Chat</title>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
</head>
<body>
	<h1>Chat</h1>
	
	<form id="joinChatForm" th:action="@{/mvc/chat}" data-bind="visible: activePollingXhr() == null">
		<p>
			<label for="user">User: </label>
			<input id="user" name="user" type="text" data-bind="value: userName"/>
			<input name="messageIndex" type="hidden" data-bind="value: messageIndex"/>
			<button id="start" type="submit" data-bind="click: joinChat">Join Chat</button>
		</p>
	</form>

	<form id="leaveChatForm" th:action="@{/mvc/chat}" data-bind="visible: activePollingXhr() != null">
		<p>
			You're chatting as <strong data-bind="text: userName"></strong>
			<button id="leave" type="submit" data-bind="click: leaveChat">Leave Chat</button>
		</p>
	</form>

	<div data-bind="visible: activePollingXhr() != null">
		<textarea rows="15" cols="60" readonly="readonly" data-bind="text: chatContent"></textarea>
	</div>
	
	<form id="postMessageForm" th:action="@{/mvc/chat}" data-bind="visible: activePollingXhr() != null">
		<p>
			<input id="message" name="message" type="text" data-bind="value: message" />
			<button id="post" type="submit" data-bind="click: postMessage">Post</button>
		</p>
	</form>
</body>

<script src="<c:url value='/templates/netbased/js/functions.js'/>" type="text/javascript" charset="utf-8"></script>
	

<!--  script type="text/javascript" src="<c:url value='/js/chat/knockout-2.0.0.js'/>" th:src="@{/resources/js/chat/knockout-2.0.0.js}"></script-->
<!--  script type="text/javascript" src="<c:url value='/js/chat/chat.js'/>" th:src="@{/resources/js/chat/chat.js}"></script-->

</html>
