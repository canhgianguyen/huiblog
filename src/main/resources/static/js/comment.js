$(document).ready(function () {
    $("#comment-submit-btn").on("click", function () {
        if ($("#comment-content").val().trim() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Đã nhập nội dung đôu -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        let data = {
            postID: $("#main-content").attr("postid"),
            content: $("#comment-content").val().trim()
        }
        console.log(data)

        axios.post("/api/comment", data).then(function (res) {
            if (res.data.success) {
                location.reload();
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
});