<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Profiel wijzigen</title>
        <script src="${contextPath}\resources\js\validation.js"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
    </head>

    <body>
        <c:if test="${empty aspirant}">
            <jsp:include page="navbar.jsp" />
        </c:if>
    <div class="jumbotron jumbotron-fluid">
        <div class="container">
            <h1 class="voorstellingDisplay4">Profiel wijzigen</h1>
        </div>
    </div>
        <c:if test="${not empty aspirant}">
            <div id="profielGegevensAlert" class="alert alert-primary" role="alert">
                <div class="container" >
                Vul eerst je profielgegevens aan voordat je verder kunt
                </div>
            </div>
        </c:if>

        <div class="container">
            <form:form method="POST" modelAttribute="medewerkerProfielGegevens" novalidate="true" class="was-validated needs-validation" autocomplete="off">
                <h2 class="form-signin-heading">Persoonlijke gegevens</h2>

                <form:hidden path="profielId"></form:hidden>
                <form:hidden path="medewerker.medewerkerId"></form:hidden>

                <div class="form-row">
                <div class="col-md-4 mb-3">
                    <spring:bind path="voornaam">
                        <div>
                            <form:input type="text" path="voornaam" class="form-control"
                                        placeholder="Voornaam" required="true" pattern="^([- \w\d\u00c0-\u024f]+)$"></form:input>
                            <div class="invalid-feedback">
                                Voer hier je voornaam in.
                            </div>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-md-2 mb-3">
                <spring:bind path="tussenvoegsel">
                    <div>
                        <form:input type="text" path="tussenvoegsel" class="form-control"
                                    placeholder="tussenvoegsel"></form:input>
                    </div>
                </spring:bind>
                </div>
                <div class="col-md-4 mb-3">
                <spring:bind path="achternaam">
                    <div>
                        <form:input type="text" path="achternaam" class="form-control"
                                    placeholder="Achternaam" required="true" pattern="[^\s]+"></form:input>
                        <div class="invalid-feedback">
                            Voer hier je achternaam in.
                        </div>
                    </div>
                </spring:bind>
                </div>
                </div>

                <div class="form-row">
                <div class="col-md-4 mb-3">
                    <spring:bind path="emailadres">
                        <div>
                            <form:input type="email" path="emailadres" class="form-control"
                                        placeholder="e-mailadres" required="true"></form:input>
                            <div class="invalid-feedback">
                                Voer hier je e-mailadres in
                            </div>
                        </div>
                    </spring:bind>
                </div>
                 <div class="col-md-3 mb-3">
                    <spring:bind path="localDate">
                        <div>
                            <form:input type="date" path="localDate" class="form-control"
                                        placeholder="yyyy-mm-dd" required="true"></form:input>
                            <div class="invalid-feedback">
                                Voer hier je geboortedatum in
                            </div>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-md-2 mb-3">
                    <spring:bind path="telefoonnummer">
                        <div>
                            <form:input type="text" path="telefoonnummer" class="form-control"
                                        placeholder="0612345678" required="true" pattern="\d{1,16}"></form:input>
                            <div class="invalid-feedback">
                                Voer hier je telefoonummer in.
                            </div>
                        </div>
                    </spring:bind>
                </div>
            </div>

            <h2 class="form-signin-heading">Adresgegevens</h2>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <spring:bind path="straatnaam">
                        <div>
                            <form:input type="text" path="straatnaam" class="form-control"
                                        placeholder="Straatnaam"></form:input>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-md-2 mb-3">
                    <spring:bind path="huisnummer">
                        <div>
                            <form:input type="number" path="huisnummer" class="form-control"
                                        placeholder="huisnummer" min="0"></form:input>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-md-2 mb-3">
                    <spring:bind path="toevoeging">
                        <div>
                            <form:input type="text" path="toevoeging" class="form-control"
                                        placeholder="toevoeging"></form:input>
                        </div>
                    </spring:bind>
                </div>
            </div>

            <div class="form-row">
                <div class="col-md-2 mb-3">
                    <spring:bind path="postcode">
                        <div>
                            <form:input type="text" path="postcode" class="form-control"
                                        placeholder="1234 AB"></form:input>
                        </div>
                    </spring:bind>
                </div>
                <div class="col-md-4 mb-3">
                    <spring:bind path="woonplaats">
                        <div>
                            <form:input type="text" path="woonplaats" class="form-control"
                                        placeholder="Woonplaats"></form:input>
                        </div>
                    </spring:bind>
                </div>
            </div>
            <h2 class="form-signin-heading">Welke taak heeft je voorkeur?</h2>
            <div class="form-row">
                <div class="col-md-4 mb-3">
                    <spring:bind path="voorkeurstaak">
                        <div>
                            <form:select path="voorkeurstaak">
                                <form:option value="0" label="Maak een keuze"/>
                                <form:options items="${takenLijst}" itemValue="taakId" itemLabel="taakNaam" />
                            </form:select>
                        </div>
                    </spring:bind>
                </div>
            </div>

                <button class="btn btn-primary" type="submit">Opslaan</button>
                <c:if test="${empty aspirant}">
                <a class="btn btn-primary" href="${contextPath}/profiel">Annuleren</a>
                </c:if>
            </form:form>
        </div>
    </body>
</html>