<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib tagdir="/WEB-INF/tags" prefix="ui"%>

<!DOCTYPE html>
<html>
<ui:include />
<body>
    <h2>Beans</h2>
    <br>
    <c:forEach items="${beans}" var="bean">
        <ul>
            <li>Name</li>
            <li>Class</li>
        </ul>
        <ul>
            <li>${bean.key}</li>
            <li>${bean.value}</li>
        </ul>
    </c:forEach>
    <ui:footer />
</body>
</html>