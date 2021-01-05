<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<html lang="en">
    <head>
        <title>Taakbeheer</title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
        <link href="${contextPath}\resources\css\all.css" type="text/css" rel="stylesheet">
        <script src="${contextPath}\resources\js\validation.js"></script>
        <script src="${contextPath}\resources\js\ajax.js"></script>
    </head>

    <body>
        <jsp:include page="navbar.jsp" />
            <div class="jumbotron jumbotron-fluid">
                <div class="container">
                    <h1 class="voorstellingDisplay4">Taakbeheer standaardtaken</h1>
                </div>
            </div>
        <div class="container">

            <a id="nieuweTaakModalButton" class="btn btn-primary" onclick="taakAanmaken('${contextPath}')" data-toggle="modal" data-target="#nieuweTaakModal">Taak aanmaken</a>

            <table class="table table-hover">
                <thead>
                    <th>Taaknaam</th>
                    <th>Standaard bezetting</th>
                    <th>Wijzigen</th>
                    <th>Verwijderen</th>
                </thead>

                <c:forEach items="${alleTaken}" var="taak">
                <tbody>
                    <td>
                        <c:out value="${taak.taakNaam}"/>
                    </td>
                    <td>
                    <c:out value="${taak.standaardBezetting}"/>
                    </td>
                    <td>
                        <i class="far fa-edit" title="Wijzigen" data-toggle="modal"
                         data-target="#wijzigTaakModal"
                         onclick="wijzigTaak('${contextPath}','${taak.taakId}')">
                         </i>
                    </td>
                    <td>
                        <c:set var="taak" value="${taak}" />
                        <c:if test="${!takenIngeplandBijGepubliceerdeVoorstellingen[taak]}">
                        <i class="fas fa-trash" title="Verwijderen" data-toggle="modal"
                        data-target="#waarschuwingsModal"
                        onclick="vullenModal(
                        'Taak verwijderen',
                        'Deze taak wordt nu ook bij alle nog niet gepubliceerde taken verwijderd. Weet je zeker dat je wilt verwijderen?',
                        'Verwijderen',
                        '/planner/taak/verwijderen/<c:out value= '${taak.taakId}'/>','btn btn-danger')">
                        </i>
                        </c:if>
                    </td>

                </tbody>
                </c:forEach>
            </table>
        </div>

        <div class="modal fade" id="nieuweTaakModal" tabindex="-1" role="dialog" aria-labelledby="nieuweTaakModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 class="modal-title" id="nieuweTaakModalLabel">Nieuwe standaardtaak aanmaken</h2>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="taakAanmaken"></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="modal fade" id="wijzigTaakModal" tabindex="-1" role="dialog" aria-labelledby="wijzigTaakModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 class="modal-title" id="wijzigTaakModalLabel">Standaardtaak wijzigen</h2>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="taakWijzigen"></p>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="waarschuwingsPopups.jsp" />
        <script type="text/javascript" src="${contextPath}\resources\js\modal.functies.js"></script>
    </body>
</html>