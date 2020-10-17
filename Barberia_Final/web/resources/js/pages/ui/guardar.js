
function descargar(){

var doc = new jsPDF('p', 'pt');
var elem = document.getElementById('basic-table');
var res = doc.autoTableHtmlToJson(elem);
doc.autoTable(res.columns, res.data);
doc.save("table.pdf");

}