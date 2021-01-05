<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Welkom</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="voorstellingDisplay4">Welkom <c:out value="${medewerkerProfielGegevens.voornaam}"/></h1>
            </div>
        </div>

        <div class="container">
            <form:hidden path="profielId"></form:hidden>
            <jsp:include page="persoonlijkeTakenPerShow.jsp" />

            <c:choose>
                <c:when test="${inTevullenVoorstellingen == 0}">
                    <a href="${contextPath}/medewerker/rooster/openvoorstelling"> <button type="button" class="btn btn-primary">Inschrijven</button></a>
                </c:when>
                <c:otherwise>
                    <a href="${contextPath}/medewerker/rooster/openvoorstelling"> <button type="button" class="btn btn-primary">Inschrijven <span class="badge badge-danger">${inTevullenVoorstellingen}</span></button></a>
                </c:otherwise>
            </c:choose>
        </div>
    </body>
</html>

