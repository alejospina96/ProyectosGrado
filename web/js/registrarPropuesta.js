/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var cantidad = 2;
$(document).ready(function () {
    $('#btnMasParticipantes').click(function () {
        var div = $('#here');
        div.prop("id", "codigo-" + cantidad);
        div.html('  <label for="txtCodigo' + cantidad + '" class="col-sm-3 control-label">\n\
                        Codigo ' + cantidad + ':&nbsp;\n\
                    </label>\n\
                    <div class="col-sm-8 input-div" >\n\
                        <input class="form-control" type="text" name="txtCodigo' + cantidad + '" id="txtCodigo' + cantidad + '" placeholder="Codigo estudiantil" required="required"/>\n\
                    </div>');
        
        div.after(' <div class="form-group row"id="here">\n\
                    </div>');
        cantidad++;
    });
    $('#btnMenosParticipantes').click(function () {
        if (cantidad > 2) {
            cantidad--;
            var div = $('#codigo-' + cantidad);
            $('#here').remove();
            div.html("");
            div.prop("id", "here");
        }
    });
});

function manejarArchivo(fileList){
    var archivo = fileList[0];
    alert(archivo.name+" subido correctamente");
}

