$(document).ready(function() {

    var imglinkUpdate;
    var postID;

    $("#add-post").on("click", function () {
        $('#modal-sign-up').modal();
    });

    // Create Post
    $("#post-cteate-submit-btn").on("click", function () {
        if ($("#post-cteate-category-id").val() === null || $("#post-cteate-file-upload")[0].files.length === 0 || $("#post-cteate-title").val().trim() === '' || $("#post-cteate-content").val() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Nhập hết các thứ vào đi bạn êi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        // Processing Upload Post's Image
        var imgLink;
        var formData = new FormData();
        formData.append('file', $("#post-cteate-file-upload")[0].files[0]);

        axios.post("/api/upload", formData).then(function(res){
            if(res.data.success) {
                imgLink = res.data.data;
                var data = {
                    categoryID: parseInt($("#post-cteate-category-id").val()), // $("#post-cteate-category-id").val(),
                    userID: 1,
                    img: imgLink,
                    title: $("#post-cteate-title").val(),
                    content: $("#post-cteate-content").val()
                };
                console.log(data)

                // Create Content
                axios.post("/api/posts", data).then(function(res){
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
            } else {
                new PNotify({
                    title: 'Error!',
                    text: 'File có vấn đề rồi bạn êi -_-',
                    type: 'error',
                    delay: 500
                });
            }
        });
    });

    // Update Post
    $(".btn-edit-post").on("click",function (){
        postID = $(this).attr("postid");
        axios.get("/api/posts/"+ postID).then(function(res){
            if(res.data.success) {
                //$('#input-cate-name-update').val(res.data.data.name)
                $('#post-update-category-id').val(res.data.data.categoryID);
                $('#post-update-title').val(res.data.data.title);
                $('#post-update-content').val(res.data.data.content);
                $('#post-update-img').attr('src', res.data.data.img);
                imglinkUpdate = res.data.data.img;
                $('#modal-update-post').modal();
            }
        });
    });

    $("#post-update-submit-btn").on("click", function () {
        if ($("#post-update-title").val().trim() === '' || $("#post-update-content").val() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Sao lại xoá các thứ của ngta đi thế bạn ôi??? -_-',
                type: 'error',
                delay: 500
            });
            return;
        }
        var data = {
            categoryID: $("#post-update-category-id").val(),
            img: "Chưa có gì đôu",
            title: $("#post-update-title").val(),
            content: $("#post-update-content").val()
        };

        if($("#post-update-file-upload")[0].files.length === 0) {
            data.img = imglinkUpdate;
            updatePost(data, postID);
        } else {
            var formData = new FormData();
            formData.append('file', $("#post-update-file-upload")[0].files[0]);

            axios.post("/api/upload", formData).then(function(res) {
                if (res.data.success) {
                    data.img = res.data.data;
                    updatePost(data, postID);
                } else {
                    new PNotify({
                        title: 'Error!',
                        text: 'File có vấn đề rồi bạn êi -_-',
                        type: 'error',
                        delay: 500
                    });
                }
            })
        }
    });

    // Delete Post
    $(".btn-delete-post").on("click",function (){
        postID = $(this).attr("postid");
        swal({
            title: 'Delete Post',
            text: "Chắc chưa, delete rồi không lấy lại được đâu nhé !!!",
            type: 'warning',
            showCancelButton: true
        }).then(function (result) {
            if (result.value) {
                var linkdelete = "/api/posts/" + postID;
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
    
    function updatePost(data, postID) {
        axios.put("/api/posts/" + postID, data).then(function(res){
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

});