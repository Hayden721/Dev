const $dragArea = $('.upload-box');
let imageArray = []; // 일반 이미지
let selectThumbnail; // 썸네일 이미지
let thumbnailImage;
console.log("imageArray", imageArray);


function highlightOn(e) {
    e.preventDefault();
    e.stopPropagation();
    $dragArea.addClass("highlight");
}
function highlightOff(e) {
    e.preventDefault();
    e.stopPropagation();
    $dragArea.removeClass("highlight");
}

// 드래그해서 input에 이미지 넣기
function imageDrop(e) {
    e.preventDefault();
    e.stopPropagation();
    $dragArea.removeClass('highlight');

    const imageFile = e.originalEvent.dataTransfer.files; // 드롭된 이미지 가져오기

    if (imageFile.length > 0) {
        handleImageFile(imageFile);
    }
}
// input-box를 클릭했을 때
function uploadBoxClick(e) {
    console.log($(e.target).hasClass('image-input'));
    if(!$(e.target).hasClass('image-input')) {
        e.stopPropagation();
        $('.image-input').trigger('click');
    }
}

function inputChange() {
    $('.image-input').on("change", function (e) {
        const imageFile = e.target.files;
        if(imageFile.length > 0) {
            handleImageFile(imageFile);
        }
    })
}

function thumbnailClick() {
    $(document).on('click', '.preview-img', function () {
        if(selectThumbnail) {
            selectThumbnail.removeClass('thumbnail');
        }

        $(this).addClass('thumbnail');

        selectThumbnail = $(this);
        console.log("selectThumbnail", selectThumbnail[0]);

        // 선택된 썸네일 이미지
        thumbnailImage = selectThumbnail ? imageArray[$('.preview-img').index(selectThumbnail[0])] : null;
        console.log("썸네일 이미지 " + thumbnailImage);

        // 썸네일이 존재할 시 imageArray에서 썸네일 이미지를 제거
        if(thumbnailImage) {
            imageArray = imageArray.filter(function (file) {
                return file !== thumbnailImage;
            })
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
            const img = $('<img class="preview-img">').attr('src', event.target.result);
            const delBtn = $('<button style="top: -10px; right: -10px;"></button>').attr('class', 'btn-close delete-btn');
            const div = $('<div class="preview-div"></div>').append(img, delBtn);
            $('.grid-images-wrap').append(div);
        };
        imageArray.push(file);
        reader.readAsDataURL(file);
    }
}

function deleteImage() {
    $(document).on("click", ".delete-btn", function () {
        console.log("click")
        const $previewDiv = $(this).closest('.preview-div'); // 삭제버튼의 부모요소 찾기
        const $previewImg = $previewDiv.find('.preview-img'); // 부모 요소에서 .preview-img를 찾기
        const $thumbnailImg = $previewImg.hasClass('thumbnail'); // 썸네일 이미지 찾기
        console.log("$thumbnail", $thumbnailImg);

        // 만약 클릭한 값이 썸네일 이미지라면
        if($thumbnailImg) {
                thumbnailImage = null;
                selectThumbnail = null;
                console.log("썸네일 이미지 삭제 완료");
                $previewDiv.remove();

            } else { // 일반 이미지일 때
                const imgIndex = $('.preview-img').index($previewImg[0]);
                console.log("imgIndex", imgIndex);
                const removeImg = imageArray[imgIndex];
                console.log("removeImg", removeImg);

                imageArray = imageArray.filter(function (file, index) {
                return index !== imgIndex;
            });

            $previewDiv.remove();
            console.log("imageArray", imageArray);
        }
    })
}

// 이미지 서버에 업로드
function uploadImages() {
    $('#submit-btn').on('click',function () {
        const formData = new FormData(); // 클릭마다 초기화
        //썸네일
        formData.append('thumbnail', thumbnailImage);
        // 추가 이미지
        for(let i = 0; i< imageArray.length; i++) {
            formData.append('extraImg', imageArray[i]);
        }

        formData.forEach((value, key) => {
            console.log(`${key}: ${value}`);
        })


        if(!thumbnailImage) {
            alert("썸네일 이미지를 선택하지 않았습니다.");
        } else {
            $.ajax({
                type: 'POST',
                url: '/seller/create/room/image/upload',
                data: formData,
                processData: false,
                contentType: false,
                success:function (response) {
                    window.location.href = response;
                },
                error: function () {
                    alert('업로드 실패: 방 생성을 확인해 주세요.');
                }
            });
        }
    })
}







