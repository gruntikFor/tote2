<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ui"%>

<ui:html>
    <ui:navbar_registration />
    <div class="container wrap">
        <ui:registration_form />
    </div>
    <ui:footer />
</ui:html>