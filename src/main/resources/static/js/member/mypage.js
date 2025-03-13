function passwordChange() {
    $('#submit-btn').on('click', function () {
        const password = $('input[name="memberPw"]').val();
        const newPassword = $('input[name="memberNewPw"]').val();
        const confirmPassword = $('input[name="memberConfirmPw"]').val();


        $.ajax({
            type: 'post', // 타입 (get, post, put 등등)
            url: '/sharespot/mypage/edit/password', // 요청할 서버url
            dataType: 'html', // 데이터 타입 (html, xml, json, text 등등)
            data: {  // 보낼 데이터 (Object , String, Array)
                password: password,
                newPassword: newPassword,
                confirmPassword: confirmPassword
            },
            success: function () {
                location.href= "/sharespot/mypage/edit";
            },
            error: function (error) {
                alert(error.responseText);
            }
        })

    })
}