// 다음 버튼 활성화
function nextButtonActivate() {
    $('#title, #content, #room-div, input[name="postcode"], input[name="address"], input[name="detailAddress"]').on('input', function () {
        let checkValue =
            $('#title').val().trim() !== "" &&
            $('#content').val().trim() !== "" &&
            $('#room-div').val().trim() !== "" &&
            $("input[name='postcode']").val().trim() !== "" &&
            $("input[name='address']").val().trim() !== "" &&
            $("input[name='detailAddress']").val().trim() !== "";

        $('#submit-btn').prop('disabled', !checkValue);
    })
}

// 방 설명 글자수 카운터
function roomContentTextCount() {
    const $content = $('#content');
    const $remainingLetters = $('#remaining-letters'); // 작성 가능한 글자 수
    const $letterMaxLength = $('#letter-maxlength');
    let findMaxLength = parseInt($content.attr('maxLength')); // 글자 수 제한 값

    console.log("$contentval", $content.val().length);

    $remainingLetters.text(findMaxLength - $content.val().length); // 초기값
    $letterMaxLength.text(findMaxLength); // 지정한 글자 제한 값

    // room-content에 입력 이벤트가 발생했을 때
    $content.on('input', function (){
        let currentLength = $(this).val().length; //현재 작성한 글자 수

        // 현재 글자수가 최대 글자 수를 넘지 않았을 경우
        if(currentLength <= findMaxLength) {
            $remainingLetters.text(findMaxLength - currentLength);
        }else {
            $(this).val($(this).val().substring(0, findMaxLength));
        }

        if(findMaxLength - currentLength === 0 ) {
            $remainingLetters.css('color', 'red');
            $letterMaxLength.css('color', 'red');
            $('.slash').css('color', 'red');
        } else {
            $remainingLetters.css('color', 'black');
            $letterMaxLength.css('color', 'black');
            $('.slash').css('color', 'black');
        }
    })
}
// 방 input값 가지고 오기
function getRoomInfo() {
    return {
        roomTitle: $('input[name="roomTitle"]').val(),
        roomContent: $('#content').val(),
        roomDiv: $('#room-div').val(),
        postcode: $('input[name="postcode"]').val(),
        address: $('input[name="address"]').val(),
        detailAddress: $('input[name="detailAddress"]').val(),
        extraAddress: $('input[name="extraAddress"]').val()
    }
}

// 다음 단계
function createRoomSubmit() {
    $('#submit-btn').click(function () {

        const postRoomInfo = getRoomInfo();

        $.ajax({
            type: "POST",
            url: "/seller/create/room",
            data: JSON.stringify(postRoomInfo),
            contentType: "application/json",
            success: function (result) {
                console.log("Data Post Success");
                window.location.href = result;

            }, function (request, status, error) {
                console.log("2ajax : " + error)
                console.log("Response Text: " + request.responseText);
                alert("시스템 오류가 발생했습니다.");

                $.ajax({
                    type: "POST",
                    url: "/seller/error/delete/room",
                    dataType: "json",
                    success: function (result) {
                        console.log("오류 방삭제 완료");
                    },
                    error: function (error) {
                        console.log(error);
                    }

                })
            }
        });
    })
}

