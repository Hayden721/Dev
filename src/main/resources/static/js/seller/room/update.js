// 방 구분 db값에 맞게 고치기 / room-update에서 사용
function divChange() {
    const roomDiv = $('input[name=room-div-info]').val();
    console.log("roomDiv :", roomDiv);

    $('.room-div option[value="' + roomDiv + '"]').prop('selected', true);
}

function updateRoomSubmit() {
    // 방정보, 썸네일 이미지, 추가 이미지 수정
    const formData = new FormData();
    const roomTitle = $('input[name="roomTitle"]').val();
    const roomContent = $('textarea[name="roomContent"]').val();
    const roomDiv = $('select[name="roomDiv"] option:selected').val();
    console.log("roomDiv", roomDiv);
    const postcode = $('input[name="postcode"]').val();
    const address = $('input[name="address"]').val();
    const detailAddress= $('input[name="detailAddress"]').val();
    const extraAddress = $('input[name="extraAddress"]').val();


    $('.image-input').change(function () {
        const roomImageNo = $(this).closest('.container-hover').find('.room-image-no').val();
        console.log("roomImageNo: ", roomImageNo )
        const image = $(this)[0].files[0];
        console.log("updateImageTest : ", image);
        formData.append(roomImageNo, image);
    })

    $('#submit-btn').click(function(event) {
        event.preventDefault();
        const roomData = {
            roomNo : $('input[name="roomNo"]').val(),
            roomTitle: $('input[name="roomTitle"]').val(),
            roomContent : $('textarea[name="roomContent"]').val(),
            roomDiv : $('select[name="roomDiv"]').val(),
            postcode : $('input[name="postcode"]').val(),
            address : $('input[name="address"]').val(),
            detailAddress : $('input[name="detailAddress"]').val(),
            extraAddress : $('input[name="extraAddress"]').val()
        }
        console.log("roomData", roomData);
        $.ajax({
            type: "POST",
            url: "/seller/update/room/image",
            processData: false,
            contentType: false,
            data: formData,
            success: function(data) {
                console.log('성공:', data);
            },
            error: function(error) {
                console.error('실패:', error);
            }
        });

        $.ajax({
            type: "POST",
            url: "/seller/room/update",
            data: JSON.stringify(roomData),
            contentType: "application/json",
            success: function (result) {
                console.log("result", result);
            }
        })
    });

}

// <select>의 <option> 생성
function updateOptions() {
    const createAble = 6; // 최대 생성 가능 개수
    const optionCount = $('input[name=optionTotal]').val();
    console.log("optionCount", optionCount);
    const total = createAble - optionCount;
    let select = $("#option-selection");

    //옵션 생성하기
    for (let i=1; i<= total; i++){
        let option = $("<option></option>").val(i).text(i);
        select.append(option);
    }
}

// 옵션 개수에 따라 입력칸 생성
function createOptionForm() {
    $("#option-selection").change(function (){
        let selectValue = $(this).val(); // 생성할 옵션 개수
        console.log(selectValue);

        let inputForm = $("#generateInputForm");

        let html= "";

        if(selectValue === "0") {
            html += "";
            inputForm.html(html);
        }else {
            for (let i = 1; i <= selectValue; i++) {
                html +=
                    "    <div class=\"add-option-container add-option-div\">\n" +
                    "\n" +
                    "        <!-- 옵션 이미지 -->\n" +
                    "        <div class=\"option-image-container container-hover\">\n" +
                    "            <img class='preview-img option-image'/>" +
                    "            <input class='image-input' type='file' name='optionImage' hidden>\n" +
                    "           <div class='overlay'>" +
                    "               <i class=\"bi bi-image\" style=\"font-size: 30px; line-height: 30px;\"></i>"+
                    "           </div>" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"option-info-container\">\n" +
                    "            <div class=\"option-name-price-container\">\n" +
                    "                <label>옵션 이름\n" +
                    "                    <input class=\"form-control option-name-add input-check\" name=\"addOptionTitle\" style=\"width: 270px\">\n" +
                    "                </label>\n" +
                    "                <div style=\"margin: 10px\"></div>\n" +
                    "                <label>옵션 가격\n" +
                    "                    <div class='input-group option-price' style=\"width: 150px;\">\n" +
                    "                        <input name='addOptionPrice' class='form-control input-check'>\n" +
                    "                        <span class='input-group-text' id='basic-addon2'>₩</span>\n" +
                    "                    </div>\n" +
                    "                </label>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"option-content-container ms-2\">\n" +
                    "                <label>옵션 설명\n" +
                    "                    <textarea class=\"form-control input-check\" name='addOptionContent' maxlength=\"100\" style=\"resize: none; width: 370px; height: 135px\"></textarea>\n" +
                    "                </label>\n" +
                    "            </div>\n" +
                    "        </div>\n" +
                    "\n" +
                    "    </div>"
            }
            inputForm.html(html);

             // 옵션 추가 input의 빈 칸 체크
            imageClickTrigger(); // 이미지 클릭시 click 트리거 실행

            // 이미지 데이터를 담고 미리보기  previewImage()
            const imageForm = new FormData();
            $('.image-input').on('change', function () {
                previewImage(this, imageForm);
            })
        }
    });
}
// 옵션 추가 모달 닫을 때 초기화하기
function optionModalClear() {
    $("#option-modal-close-btn, #option-close-btn, #add-option-btn").click(function () {
        $("#option-selection").val("0");

        let inputForm = $("#generateInputForm");
        inputForm.html("");
    })
}

function imageClickTrigger() {
    const $imgTag = $('.overlay');
    $imgTag.click(function () {
        $(this).siblings('.image-input').trigger('click');
        console.log("click");
    })
}

function previewImage(inputElement, imageFormData) {

    const imageFile = inputElement.files[0]; // 이미지 파일 가지고 오기
    const previewImg = $(inputElement).siblings('.preview-img');// img class
    const reader = new FileReader(); // 파일 입출력

    if(imageFile) {
        reader.onload = function (e) {
            previewImg.attr('src', e.target.result);
            previewImg.show();
        }
        reader.readAsDataURL(imageFile);
    }
}

function getRoomOptionData() {
    const optionData = $('.option-div').map(function () {
        const optionNo = $(this).find('input[name="roptionNo"]').val();
        const optionTitle = $(this).find('input[name="optionTitle"]').val();
        const optionContent = $(this).find('.option-content-textarea').val();
        const optionPrice = $(this).find('input[name="optionPrice"]').val();

        return {
            rOptionNo: optionNo,
            rOptionTitle: optionTitle,
            rOptionContent: optionContent,
            rOptionPrice: optionPrice
        };
    }).get(); // `get()`을 사용해 jQuery 객체를 배열로 변환

    console.log("Option Data:", optionData);
    return optionData;
}

function getAddRoomOptionData() {
    const addOptionData = $('.add-option-div').map(function () {
        const addOptionTitle = $(this).find('input[name="addOptionTitle"]').val();
        const addOptionContent = $(this).find('textarea[name="addOptionContent"]').val();
        const addOptionPrice = $(this).find('input[name="addOptionPrice"]').val();

        console.log("addOptionTItle", addOptionTitle);
        console.log("addOptionContent", addOptionContent);
        console.log("addOptionPrice", addOptionPrice);

        return {
            rOptionTitle: addOptionTitle,
            rOptionContent: addOptionContent,
            rOptionPrice: addOptionPrice
        };
    }).get(); // `get()`을 사용해 jQuery 객체를 배열로 변환
    console.log(addOptionData)
    return addOptionData;
}

function updateOptionInfoSubmit() {
    const optionInfo = getRoomOptionData();
    console.log("optionInfo : ", optionInfo)
    const roomNo = window.location.search.split('roomNo=')[1];
    console.log("roomNo", roomNo);
    $.ajax({
        type: "POST", // 타입 (get, post, put 등등)
        url: "/seller/room/option/update?roomNo=" + roomNo,  // 요청할 서버url
        contentType: "application/json",
        data: JSON.stringify(optionInfo),
        success: function (result) {
            // window.location.href = result
        },
        error: function (error) {
            alert(error);
        }
    })
}

function getRoomOptionImageData() {
    const imgArray = [];
    
    $('.add-option-div').each(function () {
        const imageFile = $(this).find('input[name="optionImage"]')[0].files[0];

        if(imageFile) {
            imgArray.push(imageFile);
        }
    })

    return imgArray;

}

// 옵션 추가 할 때 사용, addoptionInfo는 추가할 방 정보
function addOptionSubmit(addOptionInfo) {
    const formData = new FormData();

    const imageFiles = getRoomOptionImageData();

    formData.append("optionData", new Blob([JSON.stringify(addOptionInfo)], {type:"application/json"}));
    imageFiles.forEach((file) => {
        formData.append("optionImages", file);
    });

    const roomNo = window.location.search.split('roomNo=')[1];
    console.log("roomNo", roomNo);
    $.ajax({
        type: "POST",
        url: "/seller/room/option/add?roomNo=" + roomNo,
        processData: false,
        contentType: false,
        data: formData,
        success: function (result) {
            //window.location.href = result;

        }
    })
}

// 옵션 삭제
function optionDeleteSubmit() {
    $('.delete-btn').on('click', function () {

        const roomNo = window.location.search.split('roomNo=')[1];

        const roptionNo = $(this).closest('.option-detail-container').find('input[name="roptionNo"]').val();
        const optionImageNo = $(this).closest('.option-detail-container').find('input[name="roomImageNo"]').val();

        $('#spinner-overlay').show();

        console.log("roomNo", roomNo);
        console.log("roptionNo", roptionNo);
        console.log("optionImageNo", optionImageNo);

        // 필요한 데이터 : 해당 roptionNo, optionImageNo, roomNo
        $.ajax({
            type: "POST",
            url: "/seller/room/option/delete",
            data: {
                roomNo: roomNo,
                roptionNo: roptionNo,
                optionImageNo: optionImageNo

            },
            success: function (result) {
                $('#spinner-overlay').hide();

                window.location.href = result;
            }
        })

        // 2. 옵션 삭제

    })


}

function imageDel() {
    $('.image-del-btn').on('click', function () {
        const button = $(this);
        // 이미지 번호 찾기
        const imageNo = button.siblings(".room-image-no").val();
        console.log("imageNo", imageNo);

        $.ajax({
            type: "POST",
            url: "/seller/image/delete",
            contentType: "application/x-www-form-urlencoded",
            dataType: "json",
            data: {
                imageNo: imageNo
            },
            success: function (result) {
                console.log("result", result);
                // 부모 요소 삭제
                button.closest('.container-hover').remove();
            },
            error: function (error) {
                console.error("삭제 오류:", error);
                console.error("삭제 오류 메시지 :", error.responseText);
                console.error("삭제 오류 상태 :", error.status);

            }
        });
    });
}
//---------------------------------------------------------------------------

// 추가옵션 input의 빈 칸 확인
function inputCheckEvent() {
    checkOptionInput();
    $(".option-div, .input-check").on("input", checkOptionInput);
}


function checkOptionInput() {
    const inputFilled = $('.option-div .input-check').toArray().every(input => {
        const value = $(input).val();
        console.log("value", value)
        return value && value.trim() !== "";
    });

    console.log("inputFilled", inputFilled);

    if(inputFilled) {
        $("#submit-btn").prop("disabled", false);
    } else {
        $("#submit-btn").prop("disabled", true);
    }

}






