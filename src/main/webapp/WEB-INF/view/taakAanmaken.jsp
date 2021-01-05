<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

        <form:form action="${contextPath}/planner/taak/aanmaken" modelAttribute="taak" method="post" novalidate="true" class="needs-validation" autocomplete="off">
            <table>
                <tr>
                    <td>Taaknaam:</td>
                    <td>
                        <form:input class="form-control" path="taakNaam" required="true" />
                         <div class="invalid-feedback">Voer een geldige taaknaam in.</div>
                    </td>
                </tr>
                <tr>
                    <td>Standaard bezetting:</td>
                    <td>
                        <form:input class="form-control" path="standaardBezetting" required="true" input="integer" pattern="\d{1,5}" min="0"  />
                        <div class="invalid-feedback">
                            Voer een getal in.
                        </div>
                    </td>
                </tr>
                <tr>
                    <div id="waarschuwingNieuweStandaardTaak"><div>
                </tr>
                <tr>
                    <td>
                        <input id="taakOpslaan" type="" class="btn btn-primary" value="Opslaan"/>
                    </td>
                </tr>
            </div>
        </table>
    </form:form>