$(document).ready(function() {

    var dataCategory = {};
    var cateid;

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
                delay: 500
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
                    delay: 500
                });
                setTimeout(() => location.reload(), 500);
            } else  {
                new PNotify({
                    title: 'Error!',
                    text: res.data.message,
                    type: 'error',
                    delay: 500
                });
            }
        });
    });

    // Update Category
    $(".btn-edit-cate").on("click",function (){
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
                delay: 500
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
                    delay: 500
                });
                setTimeout(() => location.reload(), 500);
            } else  {
                new PNotify({
                    title: 'Error!',
                    text: res.data.message,
                    type: 'error',
                    delay: 500
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
                            delay: 500
                        });
                        setTimeout(() => location.reload(), 500);
                    } else  {
                        new PNotify({
                            title: 'Error!',
                            text: res.data.message,
                            type: 'error',
                            delay: 500
                        });
                    }
                });
            }
        })
    });
});