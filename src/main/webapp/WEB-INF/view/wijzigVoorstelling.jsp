<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <form:form action="${contextPath}/planner/voorstellingen/voorstelling/wijzigen" modelAttribute="voorstelling" method="post" autocomplete="off">
            <form:hidden path="voorstellingId"></form:hidden>
            <form:hidden path="status"></form:hidden>
            <table>
                <div>
                    <tr>
                        <td>Naam voorstelling:</td>
                        <td>
                            <form:input class="form-control mb-2 mr-sm-2" path="naam" required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Datum en tijdstip: </td>
                        <td>
                            <form:input id="wijzigVoorstellingKalender" class="form-control mb-2 mr-sm-2" path="localDateTime" value="${dateString}" required="required"/>
                        </td>
                    </tr>
                    <tr>
                        <td>Status:</td>
                        <td>
                            <input class="form-control" type="text" placeholder="${voorstelling.status}" readonly>
                        </td>
                        <td>
                            <button type="button" class="btn btn-danger" data-dismiss="modal" data-toggle="modal" data-target="#waarschuwingsModal"
                            data-target="#waarschuwingsModal"
                            onclick="vullenModal(
                            'Voorstelling annuleren',
                            'Weet je zeker dat je deze voorstelling wilt annuleren?',
                            'Voorstelling annuleren',
                            '/planner/voorstellingen/voorstelling/annuleren/<c:out value= '${voorstelling.voorstellingId}'/>','btn btn-danger')">
                            Annuleren
                            </button>
                        </td>
                    </tr>
                </div>
                <tr>
                    <td colspan="1">
                        <input type="submit" class="btn btn-primary" value="Opslaan"/>
                    </td>
                </tr>
            </table>
            <jsp:include page="waarschuwingsPopups.jsp" />
            <script type="text/javascript" src="${contextPath}\resources\js\modal.functies.js"></script>
        </form:form>
