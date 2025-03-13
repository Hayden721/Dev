let imageData = [];

function optionImgPreview() {
    $('.image-input').change(function () {
        const file = this.files[0];
        const $img = $(this).siblings('.option-image');

        console.log("File selected:", file); // 파일 선택 확인
        console.log("Image element:", $img); // 이미지 요소 확인

        if (file && file.type.startsWith('image/')) {
            const reader = new FileReader();
            reader.onload = function (e) {
                $img.attr('src', e.target.result); // 이미지 src 업데이트
            };
            reader.readAsDataURL(file);
        } else {
            alert("이미지 파일을 선택해주세요.");
        }
    });
}

function optionImgData() {
    const formData = new FormData();

    $('.option-div').each(function () {
        const roptionNo = $(this).find('input[name="roptionNo"]').val();
        console.log("roptionNo",roptionNo);
        const imgFile = $(this).find('.image-input')[0].files[0];
        console.log("imgFile", imgFile);
        if (imgFile) {
            formData.append(roptionNo, imgFile);
            formData.forEach((value, key) => {
                console.log(`${key}: ${value}`);
            })
        }
    })

    $.ajax({
        type: "POST",
        url: "/seller/create/option/image/upload",
        data: formData,
        processData: false,
        contentType: false,
        success:function (response) {
            alert('방을 생성했습니다.');
            window.location.href = response;
        },
        error: function () {
            alert('업로드 실패');
        }
    })
}