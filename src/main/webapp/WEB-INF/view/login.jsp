<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Inloggen</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <script src="${contextPath}\resources\js\validation.js"></script>
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
	</head>

   <header>
        <div class="jumbotron" id="mainJumbotron">
            <div class="container" id="loginscreenJumbotron"  >
                <h1 class="display-2">ShowMaster</h1>
                <h1 class="display-4">We Plan, You Party</h1>
            </div>
        </div>
   </header>

    <body id="bolwerkAchtergrondFoto">
        <div class="container" id="containerLoginWWReset" >
            <form method="POST" action="${contextPath}/login"  class="needs-validation" novalidate="true" autocomplete="off">

                <div class="form-group ${error != null ? 'has-error' : ''}">
                    <span class = "alert-success">${message}</span>

                    <input name="username" type="text" class="form-control my-2" placeholder="E-mailadres"
                           autofocus="true" required="true">
                    <div class="invalid-feedback">
                        Voer een E-mailadres in
                    </div>

                    <input name="password" type="password" class="form-control my-2" placeholder="Wachtwoord" required="true"/>
                    <div class="invalid-feedback">
                        Voer een wachtwoord in
                    </div>

                    <div class="text-danger">
                        <small>${error}</small> </div>
                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>

                    <button class="btn btn-primary my-2" type="submit">Log in</button>
                    <a href="${contextPath}/wachtwoord/reset"><button class="btn btn-primary my-2" type="button" >Wachtwoord Vergeten</button></a>
                </div>
            </form>
        </div>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="${contextPath}/resources/js/bootstrap.min.js"></script>
  </body>
</html>