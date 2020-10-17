$(document).ready(function(){
	$('.ter').click(function(e){
		e.preventDefault();

		if ($('.unicos .t').hasClass('bloqueo')) {


			$('.unicos .t').removeClass('bloqueo');

		}
		else{
			return false;
		}
		
	});
});

var valor= document.getElementById('valor');
var hora= document.getElementById('hora');
var nom="Cristian Ortiz";


function ventana(){

	Swal.fire({
  position: 'top-end',
  icon: 'success',
  title: 'Los cambios fueron guardados',
  showConfirmButton: false,
  timer: 1500
})

}
function crearcrono(){

	console.log(valor.value);

if (valor.value!='' && hora.value != '' ) {

	Swal.fire({
  position: 'top-end',
  icon: 'success',
  title: 'Se creo el cronograma con exito',
  showConfirmButton: false,
  timer: 1500
})


}
else{
	Swal.fire({
  position: 'top-end',
  icon: 'warning',
  title: 'Estilista no disponible',
  showConfirmButton: false,
  timer: 1500
})
}

}