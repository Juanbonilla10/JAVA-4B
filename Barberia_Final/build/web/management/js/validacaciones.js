//variables pagina de comunicate//
var nombre= document.getElementById('nombre');
var email= document.getElementById('email');
var text= document.getElementById('form7');

correo=/^\w+([\.-]?\w+)*@(?:|hotmail|outlook|yahoo|live|gmail)\.(?:|com|es)+$/;


//variables pagina de validacion de registro//

var nombresito=document.getElementById('nombress');
var correoregistro=document.getElementById('reg-correo');
var controlpass=document.getElementById('reg-rep-pass');
var controlpass1=document.getElementById('reg-pass');
var cc=document.getElementById('cc');
var telefono=document.getElementById('telefono');
var fecha=document.getElementById('fecha');




function registro(){


	if (nombresito.value=='') {
		swal({
			title: "Error",
			text: "El nombre esta vacio",
			icon: "warning",
		});
		return false;
	}
	else if (cc.value=='') {

		swal({
			title: "Error",
			text: "El numero de cedula esta vacio",
			icon: "warning",
		});
		return false;

	}
	else if (telefono.value=='') {

		swal({
			title: "Error",
			text: "El numero de telefono esta vacio",
			icon: "warning",
		});
		return false;

	}

	else if (correoregistro.value==='') {
		swal({
			title: "Error",
			text: "El email esta vacio",
			icon: "warning",
		});
		return false;
	}
	else if (correoregistro.value===null) {
		swal({
			title: "Error",
			text: "El email es nulo",
			icon: "warning",
		});
		return false;
	}else if(!correo.test(correoregistro.value)){

		swal({
			title: "Error",
			text: "El email " + correoregistro.value + " es invalido",
			icon: "warning",
		});
		return false;
	}else if (controlpass.value=='' || controlpass.value==null){

			swal({
			title: "Error",
			text: "La contraseña no puede estar vacio",
			icon: "warning",
		});


	}

	else if (controlpass.value.length<5) {
		swal({
			title: "Error",
			text: "La contraseña es muy corta",
			icon: "warning",
		});

	}else if (controlpass.value.length>10){


		swal({
			title: "Error",
			text: "La contraseña es muy larga",
			icon: "warning",
		});
			
	}else if (controlpass1.value!=controlpass.value){

		swal({
			title: "Error",
			text: "Las contraseñas no coinciden",
			icon: "warning",
		});
	}
	else{

		swal({
			title: "Listo",
			text: "El usuario ha sido registrado",
			icon: "success",
		});
	}


}



//Funciones de comunicate//
function contactenos(){

	/*console.log("enaviando formulario");

	return false;*/
	if (nombre.value=== null || nombre.value=== '') {
		swal({
			title: "Error",
			text: "El nombre es requerido",
			icon: "warning",
		});
		return false;
	}else if (email.value==='') {
		swal({
			title: "Error",
			text: "El email esta vacio",
			icon: "warning",
		});
		return false;
	}else if (email.value===null) {
		swal({
			title: "Error",
			text: "El email es nulo",
			icon: "warning",
		});
		return false;
	}else if(!correo.test(email.value)){

		swal({
			title: "Error",
			text: "El email " + email.value + " es invalido",
			icon: "warning",
		});
		return false;
	}
	else {
		swal({
			title: "Listo",
			text: "Tu mensaje ha sido enviado",
			icon: "success",
		});
		return false;
	}


};