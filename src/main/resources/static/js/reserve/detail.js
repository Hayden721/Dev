function getOptionInfo() {
    $(".option-list").on("click", function () {
        const roptionNo = $(this).find('input').val();
        const roomNo = $('input[name="roomNo"]').val();
        const sellerNo = $('input[name="sellerNo"]').val();

        $.ajax({
            type: 'get',           // 타입 (get, post, put 등등)
            url: '/sharespot/reserve/ajax/reserve-option',  // 요청할 서버url
            dataType: 'html',       // 데이터 타입 (html, xml, json, text 등등)
            data: {  // 보낼 데이터 (Object , String, Array)
                roomNo: roomNo,
                sellerNo : sellerNo,
                optionNo : roptionNo,
            },
            success: function (result) { // 결과 성공 콜백함수
                $(".reserveOptionAjax").html(result)
            },
            error: function (request, status, error) {
                console.log(error)
            }
        })
    });
}

function bookmark() {
    const roomNo = $('input[name="roomNo"]').val();
    console.log("roomNo", roomNo);

    $('#bookmark-btn').click(function () {
        const $this = $(this);
        const $bookmark = $this.find('.bookmark-icon-container');
        $.ajax({
            type: 'post',           // 타입 (get, post, put 등등)
            url: '/sharespot/reserve/bookmark',  // 요청할 서버url
            dataType: 'html',       // 데이터 타입 (html, xml, json, text 등등)
            data: {  // 보낼 데이터 (Object , String, Array)
                roomNo: roomNo
            },
            success: function (result) { // 결과 성공 콜백함수
                console.log(result);

                if (result === "true") {
                    $bookmark.html('<i style="font-size: 15px" class="bi bi-bookmark-fill"></i>');
                } else if (result === "false") {
                    $bookmark.html('<i style="font-size: 15px" class="bi bi-bookmark"></i>')
                }
            },
            error: function (request, status, error) {
                console.log(error)
            }
        })
    })
}
function isBookmarked() {
    // detail 페이지 들어왔을 때 북마크
    const isBookmark = $('input[name="bookmarked"]').val();
    const $bookmark = $('#bookmark-btn').find('.bookmark-icon-container');
    if (isBookmark === "true") {
        $bookmark.html('<i style="font-size: 15px" class="bi bi-bookmark-fill"></i>');
    } else if (isBookmark === "false") {
        $bookmark.html('<i style="font-size: 15px" class="bi bi-bookmark"></i>')
    }
}

