<%@page import="java.util.Enumeration"%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib tagdir="/WEB-INF/tags" prefix="ui"%>
<ui:html>
    <ui:navbar_authorization />
    <div class="container wrap">
        <ui:authorization_form />
    </div>
<%--
<ul>
<%
  Enumeration<String> en =  request.getAttributeNames();

  while (en.hasMoreElements()) {
%>
    <li><%=en.nextElement()%></li>
<%
  }
%>
</ul>

<ul>
<%
  Enumeration<String> en2 =  session.getAttributeNames();

  while (en2.hasMoreElements()) {
%>
    <li><%=en2.nextElement()%></li>
<%
  }
%>
</ul>
--%>
<ui:footer />
</ui:html>