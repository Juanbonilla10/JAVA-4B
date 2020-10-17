function demoFromHTML(){

	var h=document.getElementById('but');


	var doc= new jsPDF()
    

    doc.fromHTML(h,15,15)


    doc.save("output.pdf")


};
function imprimecita(){

	var h=document.getElementById('basic-table');


	var doc= new jsPDF()
    

    doc.fromHTML(h,15,15)


    doc.save("output.pdf")


};

function descargar(){

	var h=document.getElementById('raro');


	var doc= new jsPDF()
    

    doc.fromHTML(h,15,15)


    doc.save("output.pdf")


};