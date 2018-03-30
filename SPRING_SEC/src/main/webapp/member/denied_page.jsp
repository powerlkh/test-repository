<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<body>
    <h1>Unauthorized Access !!</h1>
    <hr />

    <c:if test="${not empty error}">
        <div style="color:red">
            login denied...<br />
            Caused : ${sessionScope["SPRING_SECURITY_LAST_EXCEPTION"].message}
        </div>
    </c:if>

    <p class="message">login denied!</p>
</body>
</html>