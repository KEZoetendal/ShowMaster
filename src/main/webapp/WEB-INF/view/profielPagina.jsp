<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Profielpagina</title>

        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
        <link href="${contextPath}\resources\css\all.css" type="text/css" rel="stylesheet">
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="voorstellingDisplay4">Profielpagina</h1>
            </div>
        </div>
        <div class="container">
            <form:form modelAttribute="medewerkerProfielGegevens">
                <form:hidden path="profielId"></form:hidden>
                <div class="jumbotron">
                    <div class="col-md-5 col-sm-5 col-xs-12">
                        <ul style = "list-style: none;"">
                            <li><i class="fas fa-id-card" style="width:50px"></i><strong>
                                ${medewerkerProfielGegevens.voornaam}
                            <c:if test="${empty medewerkerProfielGegevens.tussenvoegsel}"></c:if>
                            <c:if test="${not empty medewerkerProfielGegevens.tussenvoegsel}">
                                ${medewerkerProfielGegevens.tussenvoegsel}</c:if>
                                ${medewerkerProfielGegevens.achternaam}</strong></li>
                            <li><i class="fas fa-home" style="width:50px;"></i>
                                <c:if test="${empty medewerkerProfielGegevens.straatnaam}">nog in/aan te vullen</c:if>
                                    <c:if test="${not empty medewerkerProfielGegevens.straatnaam}">
                                        ${medewerkerProfielGegevens.straatnaam} ${medewerkerProfielGegevens.huisnummer}
                                    <c:if test="${empty medewerkerProfielGegevens.toevoeging}">, </c:if>
                                        <c:if test="${not empty medewerkerProfielGegevens.toevoeging}">
                                        ${medewerkerProfielGegevens.toevoeging}, </c:if>
                                        ${medewerkerProfielGegevens.postcode}
                                         ${medewerkerProfielGegevens.woonplaats}
                                    </c:if>
                            </li>
                            <li placeholder="nog in te vullen"><i class="fas fa-mobile-alt" style="width:50px;"></i>
                            ${medewerkerProfielGegevens.telefoonnummer}</li>
                            <li><i class="fas fa-envelope" style="width:50px;"></i>
                            ${medewerkerProfielGegevens.emailadres}</li>
                            <li><i class="fas fa-birthday-cake" style="width:50px;"></i>
                            ${medewerkerProfielGegevens.geboortedatum}</li>
                            <li><i class="fas fa-user-tag" style="width:50px;"></i>
                            ${medewerkerProfielGegevens.getMedewerker().getGebruikersnaam()}</li>
                            <br>
                            <br>
                            <li><strong>Voorkeurstaak: </strong>
                                <c:if test="${empty medewerkerProfielGegevens.voorkeurstaak}">
                                    Geen voorkeur
                                </c:if>
                                <c:if test="${not empty medewerkerProfielGegevens.voorkeurstaak}">
                                ${medewerkerProfielGegevens.voorkeurstaak.taakNaam}</c:if>
                            </li>
                        </ul>
                    <button class="btn btn-primary" type="submit">Gegevens wijzigen</button>
                </form:form>
            </div>
        </div>
    </body>
</html>