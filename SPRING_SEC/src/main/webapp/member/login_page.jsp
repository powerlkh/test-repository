<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<html>
<body>
<h1 id="banner">Member Login</h1>
<form name="f" action="/auth" method="POST">
<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
<table>
    <tr>
        <td>Username:</td>
        <td><input type='text' name='mid' value="lkh"/>  j_spring_security_check j_username</td>
    </tr>
    <tr>
        <td>Password:</td>
        <td><input type='password' name='mpw' value="1111"> j_spring_security_check  j_password</td>
    </tr>
    <tr>
        <td colspan='2' align="center">
        	<input type="submit" value="LOGIN" >
        </td>
    </tr>
</table>
</form>

<c:if test="${not empty error}">
	<div class="error">${error}</div>
</c:if>
<c:if test="${not empty msg}">
	<div class="msg">${msg}</div>
</c:if>


</body>
</html>