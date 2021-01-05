<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <form:form action="${contextPath}/planner/taak/wijzigen" modelAttribute="taak" method="post" autocomplete="off">
            <form:hidden path="taakId"></form:hidden>
            <table>
                <div>
                    <tr>
                        <td>Taaknaam:</td>
                        <td>
                            <form:input type="text" id="taakNaam" class="form-control mb-2 mr-sm-2" path="taakNaam" required="required" />
                        </td>
                    </tr>
                    <tr>
                        <td>Standaardbezetting: </td>
                        <td>
                            <form:input id="bezetting" class="form-control mb-2 mr-sm-2" path="standaardBezetting" required="required" />
                        </td>
                    </tr>
                    <tr>
                        <div id="waarschuwing"><div>
                    </tr>
                    <tr>
                        <td>
                            <input id="wijzigingenTaakOpslaan" type="" class="btn btn-danger" value="Wijzigen"/>
                        </td>
                    </tr>
                </div>
            </table>
        </form:form>
