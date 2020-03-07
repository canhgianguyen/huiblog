$(document).ready(function() {

    var dataCategory = {};
    var cateid;
    var check = 0;

    $('#summernote').summernote({height: 200});


    function addNewCate(cate) {
        $('.list-user').append(`
	        <tr>
				<td align="center">${cate.categoryId}</td>
				<td>${cate.name}</td>
				<td>${$.format.date(cate.createdDate, "dd-MM-yyyy HH:mm:ss")}</td>
				<td>${$.format.date(cate.updatedDate, "dd-MM-yyyy HH:mm:ss")}</td>
				<td align="center">
					<button class="mb-xs btn btn-info btn-edit-cate" cateid="${cate.categoryId}"><i class="fa fa-pencil" title="Edit category"
                                                                                                                              style="cursor: pointer"></i></>
                    <button class="mb-xs btn btn-info btn-delete-cate" cateid="${cate.categoryId}"><i class="fa fa-trash-o" title="Delete category"></i></button>
				</td>
			</tr>   
		`)
    }

    function loadCate() {
        $('.list-user').html("");
        axios.get("/api/categories").then(function (res) {
            for (cate of res.data) {
                addNewCate(cate);
            }
        });
    }

    setTimeout(() => {
        // Add new category
        $("#add-cate").on("click", function () {
            $('#input-cate-name').val();
            $('#modal-add-cate').modal();
        });

        $(".btn-save-category").on("click", function () {
            if($('#input-cate-name').val().trim() === '') {
                new PNotify({
                    title: 'Error!',
                    text: 'Đã nhập gì vào đôu -_-',
                    type: 'error',
                    delay: 2000
                });
                return;
            }

            var postPutLink = '/api/categories';
            dataCategory.name = $('#input-cate-name').val().trim();
            axios.post(postPutLink, dataCategory).then(function(res) {
                if(res.data.success) {
                    new PNotify({
                        title: 'Success!',
                        text: res.data.message,
                        type: 'success',
                        delay: 1500
                    });
                    $("#modal-add-cate .model-close").click();
                    loadCate();
                } else  {
                    new PNotify({
                        title: 'Error!',
                        text: res.data.message,
                        type: 'error',
                        delay: 1500
                    });
                }
            });
        });

        // Update Category
        $(".btn-edit-cate").on("click",function (){
            console.log('hhh')
            cateid = $(this).attr("cateid");
            axios.get("/api/categories/"+ cateid).then(function(res){
                if(res.data.success) {
                    $('#input-cate-name-update').val(res.data.data.name);
                    $('#modal-update-cate').modal();
                }
            });
        });

        $(".btn-update-cate").on("click", function () {
            if($('#input-cate-name-update').val().trim() === '') {
                new PNotify({
                    title: 'Error!',
                    text: 'Đã nhập gì vào đôu -_-',
                    type: 'error',
                    delay: 2000
                });
                return;
            }

            dataCategory.name = $('#input-cate-name-update').val();

            var linkPut = "/api/categories/" + cateid;

            axios.put(linkPut , dataCategory).then(function (res) {
                if(res.data.success) {
                    new PNotify({
                        title: 'Success!',
                        text: res.data.message,
                        type: 'success',
                        delay: 1500
                    });
                    loadCate();
                } else  {
                    new PNotify({
                        title: 'Error!',
                        text: res.data.message,
                        type: 'error',
                        delay: 1500
                    });
                }
            });
        })


        // Delete category
        $(".btn-delete-cate").on("click", function () {
            var id = $(this).attr("cateid");
            swal({
                title: 'Delete Category',
                text: "You won't be able to revert this!",
                type: 'warning',
                showCancelButton: true
            }).then(function (result) {
                if (result.value) {
                    var linkdelete = "/api/categories/" + id;
                    axios.delete(linkdelete).then(function (res) {
                        if(res.data.success) {
                            new PNotify({
                                title: 'Success!',
                                text: res.data.message,
                                type: 'success',
                                delay: 1000
                            });
                            loadCate();;
                        } else  {
                            new PNotify({
                                title: 'Error!',
                                text: res.data.message,
                                type: 'error',
                                delay: 1500
                            });
                        }
                    });
                }
            })
        });
    }, 2000);
});
