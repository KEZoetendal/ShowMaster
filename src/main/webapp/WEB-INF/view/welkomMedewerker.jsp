<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <title>Welkom</title>
    <link rel="stylesheet" type="text/css" href="/stylesheet.css">
    <!-- <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous"> -->
</head>
    <body>
        <jsp:include page="navbar.jsp" />
            <h2>Welkom <c:out value="${medewerkerProfielGegevens.voornaam}"/></h2>
            <form:hidden path="profielId" />
            <jsp:include page="persoonlijkeTakenPerShow.jsp" />
            <a href="/voorstelling/weergeven/openvoorstelling"> <button type="button" class="btn btn-primary btn-lg">Inschrijven</button></a>
    </body>
</html>

