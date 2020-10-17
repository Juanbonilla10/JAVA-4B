(function(){
    $(document).ready(function(){
        $('.alt-form').click(function(){
            $('.form-content').animate({
                height: "toggle",
                opacity: 'toggle'
            }, 600);
        });
        let formRegistro = document.getElementsByName('form-input');
        for (let i = 0; i < formRegistro.length; i++) {
            formRegistro[i].addEventListener('blur', function(){
                if (this.value.length >= 1) {
                    this.nextElementSibling.classList.add('active');
                    this.nextElementSibling.classList.remove('error');
                } else if (this.value.length = " ") {
                    this.nextElementSibling.classList.add('error');
                    this.nextElementSibling.classList.remove('active');
                } else {
                    this.nextElementSibling.classList.remove('active');
                }
            })
        }

    })
}())



var usuario=document.getElementById('usuer');
var contrasena=document.getElementById('pass');
var usuarios=["juan","brayan","laura","delayla","pablo"];



function ingresarsi(){



    if (usuario.value==='' && contrasena.value==='') {


     swal({
       title: "Algo salio mal",
       text: "El nombre y la contrasena estan vacios",
       icon: "warning",
       button: "Listo",
   });
 }else if(usuario.value=== null || usuario.value==='') {

    swal({
       title: "Algo salio mal",
       text: "El nombre esta vacio",
       icon: "warning",
       button: "Listo",
   });
}else if (contrasena.value=== null || contrasena.value==='') {

    swal({
       title: "Algo salio mal",
       text: "La contrasena esta vacia",
       icon: "warning",
       button: "Listo",
   });

}else if (contrasena.value!='' && usuario.value!='') {

    var usuariofinal= usuario.value;
    var itera=5;
    var nombre;
        var decir=  swal({
           title: "Listo",
           text: "Bienvenido a A Y T information" + nombre,
           icon: "success",
           button: "Listo",
       });

    for (var i = -1; usuariofinal!=usuarios[i] && i<5; i++) {


        


        if (contrasena.value==1007400892){
            decir;
            nombre="Juan";
            location.href="../AdminBSBMaterialDesign/pages/ui/home.html";

        }else if (contrasena.value==12345){
            decir;
            nombre="Brayan";
            location.href="../AdminBSBMaterialDesign/pages/auxiliar/home.html";
        }else if (contrasena.value==1002521673){
            decir;
            nombre="Laura";
            location.href="../AdminBSBMaterialDesign/pages/barbero/home.html";
        }else if (contrasena.value==678910){
            decir;
            nombre="pablo";
            location.href="servicios.html";
        }
        else{

           swal({
               title: "Algo salio mal",
               text: "Usuario inexistente",
               icon: "error",
               button: "Listo",
           });

       }

       /*if (usuariofinal===usuariofinal[i]){

        swal({
           title: "Listo",
           text: "Listo tus crendenciales son correctas",
           icon: "success",
           button: "Listo",
       });

       }
       else {swal({
           title: "Listo",
           text: "Listo tus crendenciales son incorrectas",
           icon: "success",
           button: "Listo",
       });
   }*/
   console.log(i);
}

}

}