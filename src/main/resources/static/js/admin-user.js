$(document).ready(function () {

    var userID;
    var imglinkUpdate;

    $("#add-user").on("click", function () {
        $('#modal-add-user').modal();
    });

    // Create User
    $("#user-cteate-submit-btn").on("click", function () {
        if ($("#user-cteate-name").val().trim() === '' || $("#user-cteate-email").val().trim() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Nhập hết các thứ vào đi bạn êi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        if (!isEmail($('#user-cteate-email').val().trim())) {
            new PNotify({
                title: 'Error!',
                text: 'Chưa đúng định dạng email rồi bạn ơi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        var data ={
            name: $('#user-cteate-name').val().trim(),
            email: $('#user-cteate-email').val().trim()
        }

        axios.post("/api/users", data).then(function (res) {
            if (res.data.success) {
                new PNotify({
                    title: 'Success!',
                    text: res.data.message,
                    type: 'success',
                    delay: 500
                });
                setTimeout(() => location.reload(), 500);
            } else {
                new PNotify({
                    title: 'Error!',
                    text: res.data.message,
                    type: 'error',
                    delay: 500
                });
            }
        });
    });

    // Update User
    $(".btn-edit-user").on("click", function () {
        userID = $(this).attr("userid");
        axios.get("/api/users/" + userID).then(function (res) {
            if (res.data.success) {
                $('#user-update-avatar').attr('src', res.data.data.avatar);
                $('#user-update-name').val(res.data.data.name);
                $('#user-update-email').val(res.data.data.email);
                $('#user-update-email').prop('disabled', true);
                $('#user-update-role').val(res.data.data.role);

                imglinkUpdate = res.data.data.avatar;
                $('#modal-update-user').modal();
            }
        });
    });

    $("#user-update-submit-btn").on("click", function () {
        if ($("#user-update-name").val().trim() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Sao lại xoá các thứ của ngta đi thế bạn ôi??? -_-',
                type: 'error',
                delay: 500
            });
            return;
        }
        var data = {
            name: $("#user-update-name").val().trim(),
            avatar: "Chưa có gì đôu",
            role: $("#user-update-role").val()
        };

        if ($("#user-update-file-upload")[0].files.length === 0) {
            data.avatar = imglinkUpdate;
            updateUser(data, userID);
        } else {
            var formData = new FormData();
            formData.append('file', $("#user-update-file-upload")[0].files[0]);

            axios.post("/api/upload", formData).then(function (res) {
                if (res.data.success) {
                    data.avatar = res.data.data;
                    updateUser(data, userID);
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
    $(".btn-delete-user").on("click", function () {
        userID = $(this).attr("userid");
        swal({
            title: 'Delete User',
            text: "Chắc chưa, delete rồi không lấy lại được đâu nhé !!!",
            type: 'warning',
            showCancelButton: true
        }).then(function (result) {
            if (result.value) {
                var linkdelete = "/api/users/" + userID;
                axios.delete(linkdelete).then(function (res) {
                    if (res.data.success) {
                        new PNotify({
                            title: 'Success!',
                            text: res.data.message,
                            type: 'success',
                            delay: 500
                        });
                        setTimeout(() => location.reload(), 500);
                    } else {
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

    function updateUser(data, userID) {
        axios.put("/api/users/" + userID, data).then(function (res) {
            if (res.data.success) {
                new PNotify({
                    title: 'Success!',
                    text: res.data.message,
                    type: 'success',
                    delay: 500
                });
                setTimeout(() => location.reload(), 500);
            } else {
                new PNotify({
                    title: 'Error!',
                    text: res.data.message,
                    type: 'error',
                    delay: 500
                });
            }
        });
    }

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }

});