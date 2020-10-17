$(document).ready(function(){
	$('#buscar').click(function(e){
		e.preventDefault();


		if ($('.p').hasClass('outt') && fecha.value!='' && ingreso.value!='Ingresar'){
			$('.p').removeClass('outt');
			/*alert("HOLA MUNDO");*/
			console.log(ingreso);
		}
		else{
			Swal.fire(
				'Campo vacío',
				'Seleccione una fecha',
				'error'
				)
		}

	});
});

var fecha=document.getElementById('fecha');
var cliente=document.getElementById('cliente');
var total=document.getElementById('costo');
var numero=document.getElementById('numero');
var barbero=document.getElementById('barbero');
var hora=document.getElementById('hora');
var tipo=document.getElementById('pago');
var ingreso=document.getElementById('Ingresar');




function guardar(){

	if (cliente.value=='' || cliente.value==null) {
		Swal.fire(
			'Campo vacío',
			'Escriba el nombre del cliente',
			'error'
			)
	}
	else if (numero.value=='' || numero.value==null) {

		Swal.fire(
			'Campo vacío',
			'Escriba el numero de telefono',
			'error'
			)
	}
	else if (barbero.value=='' || barbero.value==null) {
		Swal.fire(
			'Campo vacío',
			'Seleccione el barbero',
			'error'
			)
	}
	else if (hora.value=='' || hora.value==null) {

		Swal.fire(
			'Campo vacío',
			'Seleccione la hora',
			'error'
			)
	}
	else{
		Swal.fire(
			'Listo',
			'Tu cita ha sido reservada a las 11:00 am con numero de reserva 55987' ,
			'success'
			)
	}

}


function ingr(){

	Swal.fire({
		title: 'Advertencia',
		html: 'Para reservar una cita o comprar un producto inicia sesión',
		icon: 'warning'

	}).then((result) => {
		location.href="index.html";

	})
	
}


var client=document.getElementById('client');
var fechita=document.getElementById('fechita');
var textod=document.getElementById('textod');


function gdpqrs(){

	if (client.value=='' ||  client.value==null){
		Swal.fire(
			'Error',
			'El campo cliente esta vacio' ,
			'error'
			)

	}else if (fechita.value=='' || fechita.value==null){

		Swal.fire(
			'Error',
			'El campo fecha esta vacio' ,
			'error'
			)
	}else if (textod.value=='' || textod.value==null){
		Swal.fire(
			'Error',
			'La descripcion es necesaria' ,
			'error'
			)

	}else{

		Swal.fire({
			title: 'Listo',
			html: 'Tu PQRS ha sido creada numero de caso 4',
			icon: 'success'

		}).then((result) => {

			if ($('.si').hasClass('osi4')){
				$('.osi4').removeClass('pero');
				/*alert("HOLA MUNDO");*/
				console.log(ingreso);
			}
			else{
				return false;
			}


		})

	}

}

$(document).ready(function(){
	$('#eliminar1').click(function(f){
		f.preventDefault();

		Swal.fire({
			title: 'Advertencia',
			html: 'Se eliminara la PQRS #1',
			icon: 'warning'

		}).then((result) => {
			if ($('.si').hasClass('osi')){
				$('.osi').addClass('elim');
				/*alert("HOLA MUNDO");*/
				console.log(ingreso);
			}
			else{
				return false;
			}

		});

	})



});
$(document).ready(function(){
	$('#eliminar2').click(function(g){
		g.preventDefault();


		Swal.fire({
			title: 'Advertencia',
			html: 'Se eliminara la PQRS #2',
			icon: 'warning'

		}).then((result) => {
			if ($('.si').hasClass('osi1')){
				$('.osi1').addClass('elim');
				/*alert("HOLA MUNDO");*/
				console.log(ingreso);
			}
			else{
				return false;
			}

		});

	})



});
$(document).ready(function(){
	$('#eliminar3').click(function(h){
		h.preventDefault();

		Swal.fire({
			title: 'Advertencia',
			html: 'Se eliminara la PQRS # 3',
			icon: 'warning'

		}).then((result) => {
			if ($('.si').hasClass('osi2')){
				$('.osi2').addClass('elim');
				/*alert("HOLA MUNDO");*/
				console.log(ingreso);
			}
			else{
				return false;
			}

		});

	})



});

$(document).ready(function(){
	$('#eliminar4').click(function(i){
		i.preventDefault();

		Swal.fire({
		title: 'Advertencia',
		html: 'Se eliminara la PQRS # 4',
		icon: 'warning'

	}).then((result) => {
		if ($('.si').hasClass('osi4')){
				$('.osi4').addClass('elim');
				/*alert("HOLA MUNDO");*/
				console.log(ingreso);
			}
			else{
				return false;
			}

	});

	})


		
});