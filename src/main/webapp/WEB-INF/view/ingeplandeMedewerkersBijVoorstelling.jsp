<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<table class="table table-hover">
    <h3>Voorstelling: ${voorstelling.naam}</h3>
    <thead>
    <tr>
        <th scope="col">Taak</th>
        <th scope="col">Medewerker</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${voorstellingOverzicht}" var="voorstellingOverzicht">
        <tr>
            <td><c:out value="${voorstellingOverzicht.getTaak().getTaakNaam()}"/></td>
            <td>
                <c:out value="${voorstellingOverzicht.medewerker.medewerkerProfielGegevens.voornaam} " />
                <c:if test="${not empty voorstellingOverzicht.medewerker.medewerkerProfielGegevens.tussenvoegsel}">
                <c:out value="${voorstellingOverzicht.medewerker.medewerkerProfielGegevens.tussenvoegsel} "/>
                </c:if>
                <c:out value="${voorstellingOverzicht.medewerker.medewerkerProfielGegevens.achternaam}" />
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
