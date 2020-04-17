<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ui"%>
<!DOCTYPE html>
<html>
<ui:include2/>
<body>
    <ui:navbar-main />
    <div class="container wrap">
        <ui:feedback_form />
    </div>
    <ui:footer />
</body>
</html>
