$(function () {
    $('.js-sweetalert button').on('click', function () {
        var type = $(this).data('type');
        if (type === 'basic') {
            showBasicMessage();
        }
        else if (type === 'with-title') {
            showWithTitleMessage();
        }
        else if (type === 'success') {
            showSuccessMessage();
        }
        else if (type === 'confirm') {
            showConfirmMessage();
        }
        else if (type === 'cancel') {
            showCancelMessage();
        }
        else if (type === 'with-custom-icon') {
            showWithCustomIconMessage();
        }
        else if (type === 'html-message') {
            showHtmlMessage();
        }
        else if (type === 'autoclose-timer') {
            showAutoCloseTimerMessage();
        }
        else if (type === 'prompt') {
            showPromptMessage();
        }
        else if (type === 'ajax-loader') {
            showAjaxLoaderMessage();
        }
    });
});

//These codes takes from http://t4t5.github.io/sweetalert/
function showBasicMessage() {
    swal("La descripción esta vacia");
}

function showWithTitleMessage() {
    swal("Here's a message!", "It's pretty, isn't it?");
}

function showSuccessMessage() {
    swal("Good job!", "You clicked the button!", "success");
}

function showConfirmMessage() {
    swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        closeOnConfirm: false
    }, function () {
        swal("Deleted!", "Your imaginary file has been deleted.", "success");
    });
}

function showCancelMessage() {
    swal({
        title: "Are you sure?",
        text: "You will not be able to recover this imaginary file!",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Yes, delete it!",
        cancelButtonText: "No, cancel plx!",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Deleted!", "Your imaginary file has been deleted.", "success");
        } else {
            swal("Cancelled", "Your imaginary file is safe :)", "error");
        }
    });
}

function showWithCustomIconMessage() {
    swal({
        title: "Sweet!",
        text: "Here's a custom image.",
        imageUrl: "../../images/thumbs-up.png"
    });
}

function showHtmlMessage() {
    swal({
        title: "HTML <small>Title</small>!",
        text: "A custom <span style=\"color: #CC0000\">html<span> message.",
        html: true
    });
}

function showAutoCloseTimerMessage() {
    swal({
        title: "Auto close alert!",
        text: "I will close in 2 seconds.",
        timer: 2000,
        showConfirmButton: false
    });
}

function showPromptMessage() {

     Swal.fire({
  title: 'Eliminar',
  text: 'Desea eliminar la cronograma asignado',
  input: 'select',
  inputOptions: {
    'cronograma': {
      CT0004: 'CR0004',
      CT0003: 'CR0003',
      CT0002: 'CR0002',
      CT0001: 'CR0001'
    }
  },
  inputPlaceholder: 'Seleccione el id del cronograma',
  showCancelButton: true,
  inputValidator: (value) => {
    return new Promise((resolve) => {
      if (value === 'CT0001') {
        resolve()
      } else {
        resolve('Seleccione una cita')
      }
    })
  }
})

if (fruit) {
  Swal.fire(`You selected: ${fruit}`)
}

 

}

function showAjaxLoaderMessage() {
    swal({
        title: "Ajax request example",
        text: "Submit to run ajax request",
        type: "info",
        showCancelButton: true,
        closeOnConfirm: false,
        showLoaderOnConfirm: true,
    }, function () {
        setTimeout(function () {
            swal("Ajax request finished!");
        }, 2000);
    });
}


 var uno=document.getElementById('descripcion');
 var dos=document.getElementById('codigo');
 var tre=document.getElementById('cantidad');
 var cuatro=document.getElementById('PVP');
 var cinco=document.getElementById('costo');
 var seis=document.getElementById('categoria');

function crearproduc(){

    if (uno.value==='' && dos.value==='' && tre.value==='' && cuatro.value==='' && cinco.value==='') {
        swal("Error", "Los campos estan vacios", "warning");
        
    }
    else if (uno.value==='') {
        swal("Error", "La descripcion esta vacia", "warning");
    }
    else if (dos.value==='') {
        swal("Error", "El codigo esta vacia", "warning");
    }
    else if (tre.value==='') {
        swal("Error", "La cantidad es necesaria", "warning");
    }
    else if (cuatro.value==='') {
        swal("Error", "El PVP es necesario", "warning");
    }
    else if (cinco.value==='') {
        swal("Error", "El costo no puede estar vacio", "warning");
    }
    else{
       swal({
        title: "Estas seguro?",
        text: "Se creara el producto correspondiente en el inventario",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, crealo",
        cancelButtonText: "No, cancelar producto",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu producto fue creado con exito", "success");
        } else {
            swal("Cancelado", "Tu producto no fue registrado", "error");
        }
    });
    }

}
var siete=document.getElementById('User name');
 var ocho=document.getElementById('Numero de identificacion');
 var nueve=document.getElementById('numero');
 var diez=document.getElementById('fecha');
 var once=document.getElementById('Direccion');
 var doce=document.getElementById('Genero');
  var trece=document.getElementById('correo');

function crecliente(){

    if (siete.value==='' && ocho.value==='' && nueve.value==='' && diez.value==='' && once.value==='' && doce.value==='' && trece.value==='') {
        swal("Error", "Los campos estan vacios", "warning");
        
    }
    else if (siete.value==='') {
        swal("Error", "El nombre esta vacio", "warning");
    }
    else if (ocho.value==='') {
        swal("Error", "El número de documento esta vacio", "warning");
    }
    else if (nueve.value==='') {
        swal("Error", "El número de telefono esta vacio", "warning");
    }
    else if (diez.value==='') {
        swal("Error", "La fecha de nacimiento esta vacio", "warning");
    }
    else if (once.value==='') {
        swal("Error", "La dirección esta vacia", "warning");
    }
    else if (doce.value==='') {
        swal("Error", "El genero no puede estar vacio", "warning");
    }
    else if (trece.value==='') {
        swal("Error", "El correo no puede estar vacio", "warning");
    }
    else{
       swal({
        title: "Estas seguro?",
        text: "Se creara el cliente correspondiente",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, crealo",
        cancelButtonText: "No, cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu registro fue creado con exito", "success");
        } else {
            swal("Cancelado", "Tu cliente no fue registrado", "error");
        }
    });
    }

}
var catorce=document.getElementById('nombre');
 var quince=document.getElementById('cedula');
 var dieciseis=document.getElementById('telefonoo');
 var diecisiete=document.getElementById('ubicacion');
 var dieciocho=document.getElementById('naci');

function creregistro(){

    if (catorce.value==='' && quince.value==='' && dieciseis.value==='' && diecisiete.value==='' && dieciocho.value==='') {
        swal("Error", "Los campos estan vacios", "warning");
        
    }
    else if (catorce.value==='') {
        swal("Error", "El nombre esta vacio", "warning");
    }
    else if (quince.value==='') {
        swal("Error", "El número de documento esta vacio", "warning");
    }
    else if (dieciseis.value==='') {
        swal("Error", "El número de telefono esta vacio", "warning");
    }
    else if (diecisiete.value==='') {
        swal("Error", "La dirección esta vacia", "warning");
    }
    else if (dieciocho.value==='') {
        swal("Error", "La fecha de nacimiento esta vacio", "warning");
    }
    else{
       swal({
        title: "Estas seguro?",
        text: "Se creara el registro correspondiente",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, crealo",
        cancelButtonText: "No, cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu registro fue creado con exito", "success");
        } else {
            swal("Cancelado", "Tu solicitudss no fue registrado", "error");
        }
    });
    }
}

var diecinueve=document.getElementById('usuario');
var veinte =document.getElementById('Contrasena');


function contrasena(){

    if (diecinueve.value==='') {
        swal("Error", "El usuario esta vacio", "warning");
        
    }
    else if (veinte.value==='') {
        swal("Error", "Debe escribir la nueva contraseña", "warning");
    }
    
    else{
       swal({
        title: "Estas seguro?",
        text: "Se actualizará la contraseña",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, actualizarla",
        cancelButtonText: "No, cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu contraseña fue actualizada con exito", "success");
        } else {
            swal("Cancelado", "Tu contraseña no pudo ser actualizada", "error");
        }
    });
    }
}


var veintiuno=document.getElementById('nombre');
 var veintidos=document.getElementById('cedula');
 var veintitres=document.getElementById('telefonoo');
 var veinticuatro=document.getElementById('ubicacion');
 var veinticinco=document.getElementById('naci');

function modificar(){

    if (veintiuno.value==='' && veintidos.value==='' && veintitres.value==='' && veinticuatro.value==='' && veinticinco.value==='') {
        swal("Error", "Los campos estan vacios", "warning");
        
    }
    else if (veintiuno.value==='') {
        swal("Error", "El nombre esta vacio", "warning");
    }
    else if (veintidos.value==='') {
        swal("Error", "El número de documento esta vacio", "warning");
    }
    else if (veintitres.value==='') {
        swal("Error", "El número de telefono esta vacio", "warning");
    }
    else if (veinticuatro.value==='') {
        swal("Error", "La dirección esta vacia", "warning");
    }
    else if (veinticinco.value==='') {
        swal("Error", "La fecha de nacimiento esta vacio", "warning");
    }
    else{
       swal({
        title: "Estas seguro?",
        text: "Se modificara el registro correspondiente",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, crealo",
        cancelButtonText: "No, cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu registro fue modificado con exito", "success");
        } else {
            swal("Cancelado", "Tu solicitud no fue modificada", "error");
        }
    });
    }

}

var veintiseis=document.getElementById('nombress');
 var veintisiete=document.getElementById('cedulass');
 var veintiocho=document.getElementById('telefonoos');
 var veintinueve=document.getElementById('ubicaciones');
 var treinta=document.getElementById('sexo');
 var treintayuno=document.getElementById('sucorreo');
 var treintaydos=document.getElementById('nacimientoss');

function modificar2(){

    if (veintiseis.value==='' && veintisiete.value==='' && veintiocho.value==='' && veintinueve.value==='' && treinta.value==='' && treintayuno.value==='' && treintaydos.value==='') {
        swal("Error", "Los campos estan vacios", "warning");
        
    }
    else if (veintiseis.value==='') {
        swal("Error", "El nombre esta vacio", "warning");
    }
    else if (veintisiete.value==='') {
        swal("Error", "El número de documento esta vacio", "warning");
    }
    else if (veintiocho.value==='') {
        swal("Error", "El número de telefono esta vacio", "warning");
    }
    else if (veintinueve.value==='') {
        swal("Error", "La dirección esta vacia", "warning");
    }
    else if (treinta.value==='') {
        swal("Error", "El genero no tiene ningún dato", "warning");
    }
    else if (treintayuno.value==='') {
        swal("Error", "Digite un correo electronico", "warning");
    }
    else if (treintaydos.value==='') {
        swal("Error", "La fecha de nacimiento esta vacio", "warning");
    }
    else{
       swal({
        title: "Estas seguro?",
        text: "Se modificara el registro correspondiente",
        type: "warning",
        showCancelButton: true,
        confirmButtonColor: "#DD6B55",
        confirmButtonText: "Si, crealo",
        cancelButtonText: "No, cancelar",
        closeOnConfirm: false,
        closeOnCancel: false
    }, function (isConfirm) {
        if (isConfirm) {
            swal("Creado", "Tu registro fue modificado con exito", "success");
        } else {
            swal("Cancelado", "Tu solicitud no fue modificada", "error");
        }
    });
    }

}


function salir(){

    Swal.fire({
  icon: 'info',
  title: 'Hasta luego',
  text: 'Que vuelvas pronto',
  timer: 2000,
  timerProgressBar: true,
  onBeforeOpen: () => {
    Swal.showLoading()
    timerInterval = setInterval(() => {
      const content = Swal.getContent()
      if (content) {
        const b = content.querySelector('b')
        if (b) {
          b.textContent = Swal.getTimerLeft()
        }
      }
    }, 100)
  },
  onClose: () => {
    clearInterval(timerInterval)
  }
}).then((result) => {
  /* Read more about handling dismissals below */
  if (result.dismiss === Swal.DismissReason.timer) {
    window.location.href="../../../fullpag/Inicio.html";
  }
})


     
}