<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>


<!DOCTYPE html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform" xmlns:jsp="http://java.sun.com/JSP/Page">
    <head>
        <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
        <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
        <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
        <meta charset="utf-8">
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
        <link href="${contextPath}\resources\css\all.css" type="text/css" rel="stylesheet">
        <script src="${contextPath}\resources\js\ajax.js"></script>
        <script src="${contextPath}\resources\js\roosterPagina.js"></script>

        <title>Rooster</title>
    </head>

    <body>
    <jsp:include page="navbar.jsp"/>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 id="voorstellingDisplay4" class="display-4">${voorstelling.naam}</h1>
                <h3 id="voorstellingDatumDisplay4" class="display-4"> ${voorstelling.datum}</h3>
            </div>
        </div>
        <div class="container">
            <a id="overzichtVoorstellingenButton" class="btn btn-primary" href="${contextPath}/planner/voorstellingen">Overzicht Voorstellingen</a>
            <div class="row flex-xl-nowrap">
                <div class="col-8" role="main">
                    <table class="table table-hover">
                        <thead>
                        <tr>
                            <th scope="col">Taak</th>
                            <th scope="col">Medewerker</th>
                            <th scope="col">Verwijderen</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${takenBijVoorstelling}" var="takenBijVoorstelling">
                            <tr>
                                <td>
                                    <c:out value="${takenBijVoorstelling.taak.taakNaam}"/>
                                </td>
                                <td>
                                    <c:choose>
                                    <c:when test="${empty takenBijVoorstelling.medewerker.gebruikersnaam}">
                                    <div class="voorstellingsTaak" id="${takenBijVoorstelling.voorstellingsTaakId}"
                                         ondrop="dropBeschikbareMedewerker(event, ${takenBijVoorstelling.voorstellingsTaakId}, ${takenBijVoorstelling.voorstelling.voorstellingId}, '${contextPath}')"
                                         ondragover="allowDrop(event)"
                                         ondragenter="enterDrag(event)"
                                         ondragleave="leaveDrag(event)">
                                    Openstaand
                                    </div>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="beschikbareMedewerker" draggable="true" id="${takenBijVoorstelling.voorstellingsTaakId}"
                                             ondragstart="dragStartIngeplandeMedewerker(event, ${takenBijVoorstelling.voorstelling.voorstellingId}, ${takenBijVoorstelling.voorstellingsTaakId}, ${takenBijVoorstelling.medewerker.medewerkerId})"
                                             ondrag="dragging(event)">
                                            <c:out value="${takenBijVoorstelling.medewerker.medewerkerProfielGegevens.voornaam} " />
                                            <c:if test="${not empty takenBijVoorstelling.medewerker.medewerkerProfielGegevens.tussenvoegsel}">
                                            <c:out value="${takenBijVoorstelling.medewerker.medewerkerProfielGegevens.tussenvoegsel} "/>
                                            </c:if>
                                            <c:out value="${takenBijVoorstelling.medewerker.medewerkerProfielGegevens.achternaam}" />
                                            <sub class="voorkeursTaakInRooster"><c:out value="${takenBijVoorstelling.medewerker.medewerkerProfielGegevens.voorkeurstaak.taakNaam}"/></sub>
                                        </div>
                                    </c:otherwise>
                                    </c:choose>
                                    </td>
                                    <td>
                                        <i class="fas fa-trash" title="Verwijderen" data-toggle="modal"
                                        data-target="#waarschuwingsModal"
                                        onclick="vullenModal(
                                        'Taak verwijderen',
                                        'Weet je zeker dat je deze taak wilt verwijderen?',
                                        'Verwijderen',
                                        '/planner/voorstellingen/voorstellingsTaak/verwijderen/<c:out value= '${takenBijVoorstelling.voorstelling.voorstellingId}'/>/<c:out value= '${takenBijVoorstelling.voorstellingsTaakId}'/>')">
                                        </i>
                                    </td>
                                </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                    <table>
                        <tr>
                            <td>
                                <div class="dropdown">
                                    <button class="btn btn-secondary dropdown-toggle" type="button" id="dienstToevoegenButton" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                        Dienst Toevoegen
                                    </button>
                                    <div class="dropdown-menu" aria-labelledby="dropdownMenuButton">
                                        <c:forEach items="${alleTaken}" var="taak">
                                            <a class="dropdown-item" href="${contextPath}/planner/voorstellingen/voorstellingsTaak/toevoegen/${voorstelling.voorstellingId}/
                                            <c:out value='${taak.taakId}' />">
                                                <c:out value="${taak.taakNaam}"/>
                                            </a>
                                        </c:forEach>
                                    </div>
                                </div>
                            </td>
                            <td>
                                <a class="btn btn-primary" id="genereerRooster" onclick="genereerRooster(${voorstelling.voorstellingId}, '${contextPath}')">Genereer Rooster</a>
                            </td>
                        </tr>
                    </table>
                </div>
                <div class="col-4" id="dropzoneBeschikbareMedewerkers"
                     ondragenter="enterDrag(event)"
                     ondragleave="leaveDrag(event)"
                     ondragover="allowDrop(event)"
                     ondrop="vrijgevenIngeplandeMedewerker(event, '${contextPath}')">
                    <h1 id="beschikbareMedewerkers">Beschikbare medewerkers</h1>
                    <p id="sleepinstructie">sleep naar openstaande dienst</p>
                    <c:forEach items="${beschikbareMedewerkers}" var="medewerkerInschrijvingVoorstelling">
                        <div class="beschikbareMedewerker" draggable="true" id="${medewerkerInschrijvingVoorstelling.medewerker.medewerkerId}"
                             ondragstart="dragStartBeschikbareMedewerker(event, ${medewerkerInschrijvingVoorstelling.medewerker.medewerkerId})"
                             ondrag="dragging(event)">
                            <c:out value="${medewerkerInschrijvingVoorstelling.medewerker.medewerkerProfielGegevens.voornaam} " />
                            <c:if test="${not empty medewerkerInschrijvingVoorstelling.medewerker.medewerkerProfielGegevens.tussenvoegsel}">
                            <c:out value="${medewerkerInschrijvingVoorstelling.medewerker.medewerkerProfielGegevens.tussenvoegsel} "/>
                            </c:if>
                            <c:out value="${medewerkerInschrijvingVoorstelling.medewerker.medewerkerProfielGegevens.achternaam}" />
                            <sub class="voorkeursTaakInRooster"><c:out value="${medewerkerInschrijvingVoorstelling.medewerker.medewerkerProfielGegevens.voorkeurstaak.taakNaam}"/></sub>
                        </div>
                    </c:forEach>
                </div>
            </div>
        </div>
        <jsp:include page="waarschuwingsPopups.jsp" />
        <script type="text/javascript" src="${contextPath}\resources\js\modal.functies.js"></script>
    </body>
</html>
