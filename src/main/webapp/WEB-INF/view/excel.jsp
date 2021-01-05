<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

    <c:url value="/planner/voorstellingen/excel/upload" var="uploadFileUrl" />
    <c:url value="/planner/voorstellingen/excel/toevoegen" var="excelVoorstellingToevoegen" />

    <form> <!-- method="post" name="${message}" enctype="multipart/form-data" action="${uploadFileUrl}?${_csrf.parameterName}=${_csrf.token}" id="uploadForm"> -->
        <input id="csrf" type="hidden" value="?${_csrf.parameterName}=${_csrf.token}" />
        <input id="gekozenBestand" type="file" name="file" accept=".xls,.xlsx" />
        <input id="uploaden" type="button" class='btn btn-primary' disabled="disabled" value="Uploaden" />
    </form>
    <br />
    <form>
        <button id="btn-gekozenBestand-reset" class="btn btn-primary" type="button">Herstel</button>
    </form>
    <br /><div id="geslaagdMessage"></div>
    <br />
    <br />

    <form action="${excelVoorstellingToevoegen }">
        <button id="buttonExcelVoorstellingenToevoegen" class="btn btn-primary" type="submit"
        data-loading-text="<i class='fa fa-spinner fa-spin fa-fw' aria-hidden='true'></i>
        Bezig, even geduld...">
        Voorstelling(en) toevoegen
        </button>
    </form>
    <br />
    <br />
