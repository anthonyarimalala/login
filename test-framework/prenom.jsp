<h1>Prenom</h1>
<%@ page import="models.Emp" %>
<%
Emp emp= (Emp) request.getAttribute("ty");
out.println(emp.getPrenom());

%>
<p> </p>