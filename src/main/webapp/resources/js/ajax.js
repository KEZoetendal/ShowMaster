function beschikbaarheidStatusDoorgeven(voorstellingId, beschikbaarheidStatus, contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function () {
    if (this.readyState === 4 && this.status === 200) {
    }
  };
  xhttp.open("GET", contextPath + "/medewerker/rooster/openvoorstelling/inschrijven/" + voorstellingId + "/" + beschikbaarheidStatus, true);
  xhttp.send();
}


function roosterLaden(voorstellingId, contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      document.getElementById("rooster").innerHTML = this.responseText;
    }
  };
  xhttp.open("GET",  contextPath + "/rooster/voorstelling/" + voorstellingId, true);
  xhttp.send();
}


function importerenExcel(contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {

      document.getElementById("importExcel").innerHTML = this.responseText;
        $(function() {
                  $('#btn-gekozenBestand-reset').on('click', function(e) {
                      var $el = $('#gekozenBestand');
                      $el.wrap('<form>').closest('form').get(0).reset();
                      $el.unwrap();
                  });
              });

        $(function() {
          $('#gekozenBestand').on('input change', function () {
              if ($(this).val() != '') {
                  $('#uploaden').prop('disabled', false);
              } else {
                  $('#uploaden').prop('disabled', true);
              }
          });
        });

        $(function() {
            $('#uploaden').on('click', function(e) {
                var xhttp = new XMLHttpRequest();
                xhttp.onreadystatechange = function() {
                    if (this.readyState === 4 && this.status === 200) {
                        var $el = $('#gekozenBestand');
                        $el.wrap('<form>').closest('form').get(0).reset();
                        $el.unwrap();
                        $('#uploaden').prop('disabled', true);
                        document.getElementById('geslaagdMessage').innerHTML = this.responseText;
                    }
                };
                    var formData = new FormData();
                    formData.append("file", document.getElementById("gekozenBestand").files[0])
                    var csrf = document.getElementById("csrf").value;
                    xhttp.open("POST", contextPath + "/planner/voorstellingen/excel/upload" + csrf, true);
                    xhttp.send(formData);
              });
        });


        $(function() {
          $('#buttonExcelVoorstellingenToevoegen').on('click', function () {
             var toevoegen = $('#buttonExcelVoorstellingenToevoegen');
               toevoegen.html(toevoegen.data('loading-text'));
          });
        });

    }
  };
  xhttp.open("GET", contextPath + "/planner/voorstellingen/excel", true);
  xhttp.send();
}


function nieuweVoorstelling(contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      document.getElementById("nieuweVoorstellingToevoegen").innerHTML = this.responseText;
        $("#kalender").datetimepicker({
          format: "d-m-Y H:i",
        });
    }
  };
  xhttp.open("GET", contextPath + "/planner/voorstellingen/voorstelling/toevoegen/", true);
  xhttp.send();
}


function wijzigVoorstelling(contextPath, voorstellingId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      document.getElementById("voorstellingWijzigen").innerHTML = this.responseText;
        $("#wijzigVoorstellingKalender").datetimepicker({
          format: "d-m-Y H:i",
        });
    }
  };
  xhttp.open("GET", contextPath + "/planner/voorstellingen/voorstelling/wijzigen/" + voorstellingId, true);
  xhttp.send();
}


function genereerRooster(voorstellingId, contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      console.log(this.responseText);
      location.reload();
    }
  };
  xhttp.open("GET",  contextPath + "/planner/voorstellingen/voorstelling/rooster/genereer/" + voorstellingId, true);
  xhttp.send();
}


function wijzigTaak(contextPath, taakId) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      document.getElementById("taakWijzigen").innerHTML = this.responseText;
      $('#wijzigingenTaakOpslaan').bind('click', function() {
            $('#waarschuwing').text('Let op! Deze wijziging wordt doorgevoerd bij alle nog niet gepubliceerde voorstellingen! Weet je zeker dat je dit wilt?');
            $('#wijzigingenTaakOpslaan').clone().attr('type','submit').insertAfter('#wijzigingenTaakOpslaan').prev().remove();
      });
    }
  };
  xhttp.open("GET", contextPath + "/planner/taak/wijzigen/" + taakId, true);
  xhttp.send();
}


function taakAanmaken(contextPath) {
  var xhttp = new XMLHttpRequest();
  xhttp.onreadystatechange = function() {
    if (this.readyState === 4 && this.status === 200) {
      document.getElementById("taakAanmaken").innerHTML = this.responseText;
      $('#taakOpslaan').bind('click', function() {
            $('#waarschuwingNieuweStandaardTaak').text('Let op! Deze taak wordt toegevoegd bij alle nog niet gepubliceerde voorstellingen! Weet je zeker dat je dit wilt?');
            $('#taakOpslaan').clone().attr('type','submit').insertAfter('#taakOpslaan').prev().remove();
      });
    }
  };
  xhttp.open("GET", contextPath + "/planner/taak/aanmaken", true);
  xhttp.send();
}
