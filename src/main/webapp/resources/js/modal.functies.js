function vullenModal (titel, bodyText, buttonText, href, overwriteButtonOpmaak) {
    $("#waarschuwingsModalLabel").text(titel)
    $("#waarschuwingsModalBody").text(bodyText)
    $('#doorgaan').text(buttonText)
    $('#doorgaan').attr("href", href)
    $('#doorgaan').attr("class", overwriteButtonOpmaak);
}

function clearModal() {
    $("#emailveld")[0].value = "";
    $("#tekstVeld")[0].value = "";
}