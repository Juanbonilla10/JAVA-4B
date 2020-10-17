
$(document).ready(function(){
	$('.eli').click(function(e){
		e.preventDefault();

		Swal.fire({
			title: 'Eliminar',
			text: "¿Estas seguro que deseas eliminar el registro?",
			icon: 'warning',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Si, borrar registro'
		}).then((result) => {
			if (result.value) {

				$('.t').addClass('chao');

			}
		})
		
	});
	$('#amago').click(function(f){
		f.preventDefault();


		Swal.fire({
			title: 'Editar',
			text: "¿Estas seguro que deseas actualizar el registro?",
			icon: 'info',
			showCancelButton: true,
			confirmButtonColor: '#3085d6',
			cancelButtonColor: '#d33',
			confirmButtonText: 'Si, actualizar registro'
		}).then((result) => {
			if (result.value) {

				$('.j').addClass('chao');
				$('.p').removeClass('chao');

			}
		})


	});

});


