
function dragStartBeschikbareMedewerker(event, medewerkerId) {
    event.dataTransfer.setData("medewerkerId", medewerkerId)
}


function dragStartIngeplandeMedewerker(event, voorstellingId, voorstellingsTaakId, medewerkerId) {
    event.dataTransfer.setData("medewerkerId", medewerkerId)
    event.dataTransfer.setData("voorstellingId", voorstellingId);
    event.dataTransfer.setData("voorstellingsTaakId", voorstellingsTaakId);
}


function dragging(event) {
}


function allowDrop(event) {
    event.preventDefault();
}


function enterDrag(event) {
    if (event.target.className === "voorstellingsTaak" || "col-4" ) {
        event.target.style.background = "lightgray";
    }
}


function leaveDrag(event) {
    if (event.target.className === "voorstellingsTaak" || "col-4") {
        event.target.style.background = "";
    }
}


function dropBeschikbareMedewerker(event, voorstellingsTaakId, voorstellingId, contextPath) {
    event.preventDefault();
    var medewerkerId = event.dataTransfer.getData("medewerkerId");
    var xhttp = new XMLHttpRequest();

    xhttp.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            location.reload()
        }
    };
    xhttp.open("GET", contextPath + "/planner/voorstellingen/voorstellingsTaak/medewerkerKoppelen/"
        + voorstellingId + "/" + voorstellingsTaakId + "/" + medewerkerId, true);
    xhttp.send();
}


function vrijgevenIngeplandeMedewerker(event, contextPath) {
    event.preventDefault();
    var voorstellingId = event.dataTransfer.getData("voorstellingId");
    var voorstellingsTaakId = event.dataTransfer.getData("voorstellingsTaakId");
    event.target.style.background = "";

    if (voorstellingsTaakId || voorstellingId) {
        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState === 4 && this.status === 200) {
                location.reload();
            }
        };
        xhttp.open("GET", contextPath + "/planner/voorstellingen/voorstellingsTaak/taakVrijGeven/"
            + voorstellingId + "/" + voorstellingsTaakId, true);
        xhttp.send();
    }
}