// Submit when click Previous, Next button.
function submitPagination(formId, page) {

    document.getElementById('paginationPage').value = page;
    document.getElementById(formId).submit();
}

// Submit when press enter in pagination text editor.
function enterKeyPress(e, formId, page){
    if (typeof e == 'undefined' && window.event) {
        e = window.event;
    }
    if (e.keyCode == 13){
        submitPagination('frmPagination', page);
    }
}
