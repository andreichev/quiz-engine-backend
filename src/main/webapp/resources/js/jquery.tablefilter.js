/**
 * Created by apple on 23.03.17.
 */
function filterTable(tbody, filter, cellClass, mutedClass) {
    tbody.find("tr").each(function () {
        var tr = $(this);
        var text = tr.find('.' + cellClass).text();
        if(text.indexOf(filter) > -1){
            tr.removeClass(mutedClass);
        } else {
            tr.addClass(mutedClass);
        }
    });
}
