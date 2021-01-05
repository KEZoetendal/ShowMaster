<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en" xmlns:c="http://www.w3.org/1999/XSL/Transform">
    <head>
        <title></title>
        <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
        <link href="${contextPath}\resources\css\custom.css" type="text/css" rel="stylesheet">
        <script src="${contextPath}\resources\js\ajax.js"></script>
    </head>

    <body>
        <h2>Wanneer moet je werken?</h2>
            <form:hidden path="profielId" />

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope ="col">Naam</th>
                        <th scope ="col"></th>
                        <th scope ="col">Datum en tijd</td>
                        <th scope ="col">Taak</td>
                        <th scope ="col">Collega's</td>
                    </tr>
                </thead>
                <tbody>

                    <c:forEach items="${allePersoonlijkeVoorstellingsTaken}" var="allePersoonlijkeVoorstellingsTaken">
                        <tr>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.voorstelling.naam}"/></td>
                            <td>
                                <c:choose>
                                    <c:when test="${allePersoonlijkeVoorstellingsTaken.voorstelling.status == 'Geannuleerd'}">
                                        <span class="badge badge-danger">Geannuleerd</span>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- nothing yet -->
                                    </c:otherwise>
                                </c:choose>
                            </td>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.voorstelling.datum}"/></td>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.taak.taakNaam}"/></td>
                            <td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" onclick="roosterLaden(${allePersoonlijkeVoorstellingsTaken.voorstelling.voorstellingId}, '${contextPath}')">
                                Rooster bekijken
                            </button></td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>

        <div class="modal fade" id="exampleModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header">
                        <h2 class="modal-title" id="exampleModalLabel">Rooster</h2>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="rooster"></p>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>

    </body>
</html>