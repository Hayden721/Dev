// 이미지 클릭 트리거
function overlayClickTrigger() {
    const $overlay = $('.overlay');
    $overlay.click(function () {
        $(this).siblings('.image-input').trigger('click');
        console.log("click");
    })
}

// 이미지 미리보기
function handleImagePreview(inputElement) {

    const imageFile = inputElement.files[0];
    console.log("handle imageFile", imageFile);

    if(imageFile) {
        // 이미지 미리보기
        const reader = new FileReader();
        reader.onload = function (e) {
            const previewImg = $(inputElement).siblings('.preview-img');

            previewImg.attr('src', e.target.result);
            previewImg.show();
        };
        reader.readAsDataURL(imageFile);
    }

    console.log("extraImage select", imageFile);
}

// 이미지 데이터 가지고 오기 / 옵션이미지 update, upload
function getImageData() {
    const formData = new FormData();

    $('.option-div').each(function () {
        const roomimageNo = $(this).find('input[name="roomImageNo"]').val();
        const imageFile = $(this).find('.image-input')[0].files[0];
        console.log("roomimageNo", roomimageNo);
        console.log("imageFile", imageFile)

        if(imageFile) {
            formData.append(roomimageNo, imageFile) //(이미지 번호, 이미지 파일);
        }
    })

    return formData;
}

// 이미지 업데이트 실행
function updateOptionImageSubmit(optionImgFormData) {
    return $.ajax({
        type: 'POST',
        url: '/seller/update/option/image',
        data: optionImgFormData,
        processData: false,
        contentType: false,
    })
}

let imageArray = [];

function imageAddAjax() {
    const roomNo = $('input[name="roomNo"]').val();
    console.log("roomNo", roomNo)
    const formData = new FormData();
    imageArray.forEach((file)=> {
        formData.append('image',file);
    })

    $.ajax({
        type: "POST",
        url: "/seller/add/image?roomNo=" + roomNo,
        processData: false,
        contentType: false,
        data: formData,
        success: function (result) {
            console.log("이미지 추가 성공")
            alert("수정되었습니다.");
            location.href="/seller/room/detail?roomNo=" + roomNo;
        },
        error: function (error) {
            console.log("이미지 추가 실패")
        }

    })

}

function imageAdd() {
    console.log("inputchange 실행");
    $('.image-add-input').on('change', function (e) {
        const imageFile = e.target.files;
        if(imageFile.length > 0) {
            handleImageFile(imageFile);
        }
    })
}

// 이미지 미리보기 및 배열 저장
function handleImageFile(imageFile) {
    // 드래그하거나 클릭한 파일 개수 만큼 반복
    for(let i=0; i< imageFile.length; i++) {
        const file = imageFile[i];
        console.log(file);

        // 이미지 미리보기
        const reader = new FileReader();
        reader.onload = function (event) {
            const img = $('<img class="extra-image">').attr('src', event.target.result);
            const delBtn = $('<button style="top: -10px; right: -10px;"></button>').attr('class', 'btn-close delete-btn');
            const div = $('<div class="preview-div position-relative"></div>').append(img, delBtn);
            $('.images-repeat4-container').prepend(div);

            imageArray.push(file);

            delBtn.on('click', function () {
                const index = imageArray.indexOf(file);
                if (index > -1 ) {
                    imageArray.splice(index, 1);
                    div.remove();
                    console.log("imageArray after deletion", imageArray);
                }
            })
        };

        console.log("imageArray", imageArray);


        reader.readAsDataURL(file);
    }
}
//
// function imageDelete(file, div) {
//     $(document).on('click', '.delete-btn', function () {
//         const $preview = $(this).closest('.preview-div'); // 삭제버튼의 부모요소 찾기
//
//         $preview.remove();
//
//     })
// }

