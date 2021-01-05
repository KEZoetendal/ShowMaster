<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<c:set var="beschikbaar" value="Beschikbaar"/>
<c:set var="misschien" value="Misschien"/>
<c:set var="nietBeschikbaar" value="Niet Beschikbaar"/>

<!doctype html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform">
    <head>
        <title></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css"
              integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
        <link href="${contextPath}\resources\css\all.css" type="text/css" rel="stylesheet">
        <link href="${contextPath}\resources\css\radioButton.css" type="text/css" rel="stylesheet">
        <script src="${contextPath}\resources\js\ajax.js"></script>
        <script src="${contextPath}\resources\js\javascript.functies.js"></script>
    </head>

    <body>
        <jsp:include page="navbar.jsp"/>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="voorstellingDisplay4">Inschrijven open voorstellingen</h1>
            </div>
        </div>
        <div class="container">
            <div class="container">
                <h2>Ben je beschikbaar?</h2>
                    <p>Geef per voorstelling aan of je beschikbaar bent. Je kunt kiezen uit de volgende opties:</p>
                   <table>
                        <tr>
                            <td><i id="keuzeJa" class="fa fa-check"></i>
                            </td>
                            <td>Ja</td>
                            <td><i id="keuzeMisschien" class="fa fa-question"></i>
                            </td>
                            <td>Misschien</td>
                            <td><i id="keuzeNee" class="fa fa-times"></i>
                            </td>
                            <td>Nee</td>
                        </tr>
                   </table>
                <br>
                <a id="terugNaarRooster" class="btn btn-primary" href="${contextPath}/medewerker/rooster">Terug naar rooster</a>
                <table class="table table-hover" id="myTable">
                    <thead>
                    <tr>
                        <th class="sticky-top" id="voorstellingsnaam" scope="col" onclick="sortTable(0)">Voorstelling</th>
                        <th class="sticky-top" scope="col" onclick="sortTable(1)">Datum</th>
                        <th class="sticky-top" scope="col">Ja</th>
                        <th class="sticky-top" scope="col">Misschien</th>
                        <th class="sticky-top" scope="col">Nee</th>
                    </tr>
                    </thead>

                    <tbody>

                    <input type="radio" name="fixed${indexValue}" value="Fixed"></td>
                        <c:forEach items="${voorstellingLijst}" var="voorstelling">
                            <c:if test="${voorstelling.status == 'Gepubliceerd'}">
                                <tr>
                                    <td><h1><c:out value="${voorstelling.naam}"/></h1></td>
                                    <td><c:out value="${voorstelling.datum}"/></td>
                                    <td>
                                      <input class="ja" type="radio" name="beschikbaarheid${voorstelling.voorstellingId}" id="ja${voorstelling.voorstellingId}">
                                        <label class="labelJa" for="ja${voorstelling.voorstellingId}"
                                        onclick="beschikbaarheidStatusDoorgeven(${voorstelling.voorstellingId}, '${beschikbaar}','${contextPath}') ">
                                            <i id="icoonJa" class="fas fa-check" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                    <td>
                                      <input class="misschien" type="radio" name="beschikbaarheid${voorstelling.voorstellingId}" id="misschien${voorstelling.voorstellingId}">
                                        <label class="labelMisschien" for="misschien${voorstelling.voorstellingId}"
                                        onclick="beschikbaarheidStatusDoorgeven(${voorstelling.voorstellingId}, '${misschien}','${contextPath}') ">
                                            <i id="icoonMisschien" class="fas fa-question" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                    <td>
                                      <input class="nee" type="radio" name="beschikbaarheid${voorstelling.voorstellingId}" id="nee${voorstelling.voorstellingId}">
                                        <label class="labelNee" for="nee${voorstelling.voorstellingId}"
                                        onclick="beschikbaarheidStatusDoorgeven(${voorstelling.voorstellingId}, '${nietBeschikbaar}','${contextPath}') ">
                                            <i id="icoonNee" class="fas fa-times" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                </tr>
                            </c:if>
                        </c:forEach>
                        <c:forEach items="${inschrijvingen}" var="inschrijving">
                            <tr>
                                <td><h1><c:out value="${inschrijving.voorstelling.naam}"/></h1></td>
                                <td><c:out value="${inschrijving.voorstelling.datum}"/></td>

                                    <td>
                                      <input class="ja" type="radio" name="beschikbaarheid${inschrijving.voorstelling.voorstellingId}" id="ja${inschrijving.voorstelling.voorstellingId}"
                                        value="Beschikbaar" ${inschrijving.inschrijvingStatus=='Beschikbaar'?'checked':''}>

                                        <label class="labelJa" for="ja${inschrijving.voorstelling.voorstellingId}"
                                             onclick="beschikbaarheidStatusDoorgeven(${inschrijving.voorstelling.voorstellingId}, '${beschikbaar}','${contextPath}') ">
                                            <i id="icoonJa" class="fas fa-check" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                    <td>
                                      <input class="misschien" type="radio" name="beschikbaarheid${inschrijving.voorstelling.voorstellingId}" id="misschien${inschrijving.voorstelling.voorstellingId}"
                                        value="Misschien" ${inschrijving.inschrijvingStatus=='Misschien'?'checked':''}>
                                        <label class="labelMisschien" for="misschien${inschrijving.voorstelling.voorstellingId}"
                                        onclick="beschikbaarheidStatusDoorgeven(${inschrijving.voorstelling.voorstellingId}, '${misschien}','${contextPath}') ">
                                            <i id="icoonMisschien" class="fas fa-question" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                    <td>
                                      <input class="nee" type="radio" name="beschikbaarheid${inschrijving.voorstelling.voorstellingId}" id="nee${inschrijving.voorstelling.voorstellingId}"
                                        value="Niet Beschikbaar" ${inschrijving.inschrijvingStatus=='Niet Beschikbaar'?'checked':''}>
                                        <label class="labelNee" for="nee${inschrijving.voorstelling.voorstellingId}"
                                        onclick="beschikbaarheidStatusDoorgeven(${inschrijving.voorstelling.voorstellingId}, '${nietBeschikbaar}','${contextPath}') ">
                                            <i id="icoonNee" class="fas fa-times" aria-hidden="true"></i>
                                        </label>
                                    </td>
                                </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </body>
</html>