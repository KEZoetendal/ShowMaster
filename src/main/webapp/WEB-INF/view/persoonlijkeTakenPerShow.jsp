<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!doctype html>
<html lang="en">
<head>
    <title></title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
    <body>
        <h2>Persoonlijk rooster</h2>
            <form:hidden path="profielId" />

            <table class="table table-hover">
                <thead>
                    <tr>
                        <th scope ="col">Naam</th>
                        <th scope ="col">Datum en tijd</td>
                        <th scope ="col">Taak</td>
                        <th scope ="col">Collega's</td>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${allePersoonlijkeVoorstellingsTaken}" var="allePersoonlijkeVoorstellingsTaken">
                        <tr>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.getVoorstelling().getNaam()}"/></td>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.getVoorstelling().getDatum()}"/></td>
                            <td><c:out value="${allePersoonlijkeVoorstellingsTaken.getTaak().getTaakNaam()}"/></td>
                            <td><button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModal" onclick="loadDoc(${allePersoonlijkeVoorstellingsTaken.getVoorstelling().getVoorstellingId()})">
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
                        <h5 class="modal-title" id="exampleModalLabel">Rooster</h5>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <p id="rooster"></p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">sluiten</button>
                    </div>
                </div>
            </div>
        </div>


        <script>
function loadDoc(voorstellingId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState == 4 && this.status == 200) {
      document.getElementById("rooster").innerHTML = this.responseText;
    }
  };
  xhttp.open("GET", "voorstelling/rooster/" + voorstellingId, true);
  xhttp.send();
}
</script>



    </body>
</html>