$(document).ready(function() {
    $("#btn-sign-in").on("click",function (){
        if($('#sign-in-email').val().trim() === '' || $('#sign-in-password').val().trim() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Nhập đầy đủ các thứ vào đi bạn êi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        var data = {
            email: $('#sign-in-email').val().trim(),
            password: $('#sign-in-password').val().trim()
        }

        axios.post("/api/users/signin", data).then(function(res){
            if(res.data.success) {
                new PNotify({
                    title: 'Success!',
                    text: res.data.message,
                    type: 'success',
                    delay: 500
                });
                setTimeout(() => location.replace("/"), 500);
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
});