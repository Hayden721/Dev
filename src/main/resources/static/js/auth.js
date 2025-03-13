function findPasswordPost(url) {
    const memberId = $('input[name="memberId"]').val();
    const memberName = $('input[name="memberName"]').val();

    $.ajax({
        type: "POST",
        url: url,
        dataType: 'json',
        data: {
            memberId: memberId,
            memberName: memberName
        },
        success: function (result) {
            console.log(result);
            if(result === true) {
                alert("비밀번호를 재설정합니다.");
                location.href = "/sharespot/member/change/password";
            }
        },
        error: function (error) {
            alert(error.responseText);
        }
    })
}

function changePasswordPost(url) {
    const memberNo = $('input[name="memberNo"]').val();
    const password = $('input[name="password"]').val();
    const confirmPassword = $('input[name="confirmPassword"]').val();
    console.log("memberNo", memberNo);

    $.ajax({
        type: "POST",
        url: url,
        dataType: 'html',
        data: {
            memberNo: memberNo,
            password: password,
            confirmPassword: confirmPassword
        },
        success:  function (result) {
            alert(result);
            location.href = "/sharespot/member/login";
        },
        error(error) {
            alert(error.responseText);
            console.log("오류입니다.");
        }
    })
}