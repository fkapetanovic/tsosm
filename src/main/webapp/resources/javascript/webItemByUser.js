$(document).ready(function() {
        $( ".deleteButton" ).click(deleteItem);
   
       
    });
function deleteItem() {
    if (confirm("Are you sure?")) {
   return true;
    }
    return false;
}