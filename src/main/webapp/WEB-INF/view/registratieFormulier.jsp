<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Inschrijven</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="${contextPath}\resources\js\validation.js"></script>
        <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
    </head>

    <body>
        <div class="jumbotron jumbotron-fluid">
            <div class="container">
                <h1 class="voorstellingDisplay4">Account aanmaken</h1>
            </div>
        </div>
        <div class="container">
            <form:form method="POST" modelAttribute="medewerker" class="needs-validation" novalidate="true">

                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <a class="form-control">${gebruikersnaam}</a>
                    </div>
                <spring:bind path="wachtwoord">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="password" path="wachtwoord" class="form-control ${status.error ? 'is-invalid' : ''}" placeholder="Wachtwoord" autofocus="true" required="true" pattern="[^\s]+"></form:input>
                            <div class="invalid-feedback">
                            Voer een geldig wachtwoord in
                            </div>
                        <small><form:errors path="wachtwoord" cssClass="text-danger" element="div"></form:errors></small>
                    </div>
                </spring:bind>

                <spring:bind path="wachtwoordBevestigen">
                    <div class="form-group ${status.error ? 'has-error' : ''}">
                        <form:input type="password" path="wachtwoordBevestigen" class="form-control ${status.error ? 'is-invalid' : ' '}"
                            placeholder="Wachtwoord bevestigen" required="true" pattern="[^\s]+"></form:input>
                        <small><form:errors path="wachtwoordBevestigen" cssClass="text-danger" element="div"></form:errors></small>
                    </div>
                </spring:bind>

                <spring:bind path="planner">
                    <!-- CHECKBOX VERBORGEN IVM DEMO-->
                    <div> <!-- hidden class="form-group ${status.error ? 'has-error' : ''}" -->
                        <form:checkbox path="planner" value="Planner" /> Planner
                    </div>
                </spring:bind>
            <button class="btn btn-primary" type="submit">Inloggen</button>
            </form:form>
        </div>
    </body>
</html>
