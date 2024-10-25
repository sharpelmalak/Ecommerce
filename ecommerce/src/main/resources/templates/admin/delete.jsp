<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Delete User</title>
</head>
<body>
<h2>Delete User</h2>

<p>Are you sure you want to delete this user?</p>

<!-- User deletion form -->
<form action="${pageContext.request.contextPath}/user" method="post">
    <input type="hidden" name="action" value="delete">
    <input type="hidden" name="userId" value="${param.userId}">
    <button type="submit">Delete</button>
    <a href="${pageContext.request.contextPath}/user/list">Cancel</a>
</form>

<c:if test="${not empty error}">
    <div class="alert alert-danger">
            ${error}
    </div>
</c:if>
</body>
</html>