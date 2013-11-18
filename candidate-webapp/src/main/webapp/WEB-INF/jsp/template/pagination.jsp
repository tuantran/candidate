<form id="frmPagination" method="POST">
     <input type="hidden" name="paginationPage" id="paginationPage" value="${searchEmailFormBean.currentPage}" />


     <a class="btn_gray" href="javascript:void(0);" onclick="submitPagination('frmPagination', '${searchEmailFormBean.previousPage}');">&#171; Previous Page</a>
     &#160;&#160;Show Page&#160;&#160;:&#160;&#160;
     <input type="text" class="paging" id="txtPaginationPage" style="width: 50px;" onkeypress="enterKeyPress(event, 'frmPagination', document.getElementById('txtPaginationPage').value);" value="${searchEmailFormBean.currentPage}" />
     &#160;&#160;/&#160;&#160;${searchEmailFormBean.lastPage}&#160;&#160;
     <a class="btn_gray" href="javascript:void(0);" onclick="submitPagination('frmPagination',  document.getElementById('txtPaginationPage').value);">GO</a>
     &#160;&#160;
     <a class="btn_gray" href="javascript:void(0);" onclick="submitPagination('frmPagination', '${searchEmailFormBean.nextPage}');">Next Page &#187;</a>
</form>