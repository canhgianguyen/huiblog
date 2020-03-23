$(document).ready(function() {

    $("#btn-sign-up").on("click", function () {
        if ($('#sign-up-name').val().trim() === '' || $('#sign-up-email').val().trim() === '' || $('#sign-up-password').val().trim() === '' || $('#sign-up-password-confirmation').val().trim() === '') {
            new PNotify({
                title: 'Error!',
                text: 'Nhập đầy đủ các thứ vào đi bạn êi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        if (!isEmail($('#sign-up-email').val().trim())) {
            new PNotify({
                title: 'Error!',
                text: 'Chưa đúng định dạng email rồi bạn ơi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        if ($('#sign-up-password').val().trim() !== $('#sign-up-password-confirmation').val().trim()) {
            new PNotify({
                title: 'Error!',
                text: 'Password không trung nhau rồi bạn ơi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        if (($('#sign-up-password').val().trim().length < 4) || ($('#sign-up-password').val().trim().length > 30)) {
            new PNotify({
                title: 'Error!',
                text: 'Password phải nằm trong khoảng 4 đến 30 ký tự bạn ơi -_-',
                type: 'error',
                delay: 500
            });
            return;
        }

        var data = {
            name: $('#sign-up-name').val().trim(),
            email: $('#sign-up-email').val().trim(),
            password: $('#sign-up-password').val().trim()
        }

        axios.post("/api/users/signup", data).then(function(res){
            if(res.data.success) {
                new PNotify({
                    title: 'Success!',
                    text: res.data.message,
                    type: 'success',
                    delay: 500
                });

                var dataSignIn = {
                    email: $('#sign-up-email').val().trim(),
                    password: $('#sign-up-password').val().trim()
                }

                axios.post("/api/users/signin", dataSignIn).then(function(res){
                    setTimeout(() => location.replace("/"), 500);
                });
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

    function isEmail(email) {
        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
        return regex.test(email);
    }
});