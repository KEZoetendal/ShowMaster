<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html lang="nl"><!--<![endif]-->

    <head>

	<!-- Wordpress header scripts and styles -->
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
    <script src="https://code.jquery.com/jquery-3.4.1.slim.min.js" integrity="sha384-J6qa4849blE2+poT4WnyKhv5vZF5SrPo0iEjwBvKU7imGFAV0wwj1yYfoRSJoZ+n" crossorigin="anonymous"></script>
    <script src="https://cdn.jsdelivr.net/npm/popper.js@1.16.0/dist/umd/popper.min.js" integrity="sha384-Q6E9RHvbIyZFJoft+2mJbHaEWldlvI9IOYy5n3zV9zzTtmI3UksdQRVvoxMfooAo" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.4.1/js/bootstrap.min.js" integrity="sha384-wfSDF2E50Y2D1uUdj0O3uMBJnjuUD4Ih7YwaYd1iqfktj0Uod8GCExl3Og8ifwB6" crossorigin="anonymous"></script>
	</head>

	<body class="home page-template-default page page-id-70">
        <form:hidden path="medewerkerId"></form:hidden>
        <form:hidden path="planner"></form:hidden>

        <div id="headerNavbar">
            <nav class="navbar navbar-expand-lg navbar-light bg-light">
            <div class="container">

                    <a class="navbar-brand" href="${contextPath}/medewerker/rooster">
                        <img src="https://hetbolwerk.nl/wp-content/themes/hetbolwerk/images/hetbolwerklogo.svg" height="30" class="d-inline-block align-top" alt="Poppodium Het Bolwerk">
                    </a>
                    <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon"></span>
                    </button>

                        <div class="collapse navbar-collapse" id="navbarNav">
                            <ul class="navbar-nav mr-auto mt-2 mt-lg-0">
                            <li class="nav-item">
                                <a class="nav-link" href="${contextPath}/medewerker/rooster">ROOSTER</a>
                            </li>
                                <sec:authorize access="hasRole('PLANNER')">
                                    <li class="nav-item">
                                        <a class="nav-link" href="${contextPath}/planner/voorstellingen">VOORSTELLINGEN</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${contextPath}/planner/taak/beheer">TAAKBEHEER</a>
                                    </li>
                                    <li class="nav-item">
                                        <a class="nav-link" href="${contextPath}/planner/gebruiker/overzicht">GEBRUIKERS</a>
                                    </li>
                                </sec:authorize>
                                <li class="nav-item">
                                    <a class="nav-link" href="${contextPath}/profiel">MIJN PROFIEL</a>
                                </li>
                            </ul>
                            <div class="my-2 my-lg-0">
                                <button class="btn btn-outline-primary my-2 my-lg-0" type="button" data-toggle="modal"
                                        data-target="#logoutModal">Uitloggen</button></div>
                        </div>
        </nav>
        </div>
        </div>
        <jsp:include page="waarschuwingsPopups.jsp" />
        <script type="text/javascript" src="${contextPath}\resources\js\modal.functies.js"></script>
    </body>
</html>