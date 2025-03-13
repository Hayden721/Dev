// option-create에 사용
function generateOptions() {
    let createCount = 6;
    let select = $("#option-selection");

    for (let i=1; i<= createCount; i++){
        let option = $("<option></option>").val(i).text(i);
        select.append(option);
    }
}

// 옵션 개수에 따라 입력칸 생성
function generateOptionForm() {
    $("#option-selection").change(function (){
        let selectValue = $(this).val();
        let inputForm = $("#generateInputForm");
        let html= "";

        if(selectValue === "0") {
            html += "";
            inputForm.html(html);
        }else {
            for (let i = 1; i <= selectValue; i++) {
                html +=
                    "<div class='input-form mb-1'>" +
                    "<fieldset class='fieldset-style mb-2'>"+
                        "<legend>옵션" + i + "</legend>" +
                        "<label class='option-label mb-2'> 옵션이름" +
                            "<input name='optionTitle' class='value-check form-control'>" +
                        "</label>" +
                        "<label class='option-label'> 옵션 내용" +
                            "<input name='optionContent' class='value-check form-control'>" +
                        "</label>" +
                        "<lable class='option-label w-50'>옵션 가격" +
                            "<div class='input-group option-price'>" +
                                "<input name='optionPrice' class='value-check form-control price' placeholder='숫자만 입력 가능합니다.'>" +
                                "<span class='input-group-text' id='basic-addon2'>₩</span>"+
                            "</div>" +
                        "</lable>" +
                    "</fieldset>" +
                    "</div>";
            }
            inputForm.html(html);
            checkInput();
        }
    });
}

// 옵션 입력칸 빈칸 검사
function checkInput() {
    // .value-check 클래스를 가진 입력란에 대해 input 이벤트를 감지
    $('.value-check').on('input', function() {
        // 빈 칸 여부 확인
        let valueCheck = false;
        $('.value-check').each(function() {
            if ($(this).val().trim() === '') {
                valueCheck = true;
                return false; // 빈 칸 발견 시 반복 중단
            }
        });

        // 빈 칸이 없으면 버튼 활성화, 빈 칸이 있으면 비활성화
        if (valueCheck) {
            $('#submit-btn').prop('disabled', true); // 비활성화
        } else {
            $('#submit-btn').prop('disabled', false); // 활성화
        }

    });
    // 숫자 정규화
    $('.price').on('input', function () {
        this.value = this.value.replace(/[^0-9]/g, '');
    })
}

function optionSubmit() {
    // 등록 버튼 클릭했을 때
    $("#submit-btn").click(function () {
        let options = [];
        console.log(options);

        $('.input-form').each(function () {
            let optionTitle = $(this).find("input[name='optionTitle']").val();
            let optionContent = $(this).find("input[name='optionContent']").val();
            let optionPrice = $(this).find("input[name='optionPrice']").val();

            let option = {
                rOptionTitle: optionTitle,
                rOptionContent: optionContent,
                rOptionPrice: optionPrice
            }
            options.push(option);
        })

        console.log("options: ", options);

        $.ajax({
            type: "POST",
            contentType: "application/json",
            url: "/seller/create/option",
            data: JSON.stringify(options),
            success: function (result) {
                window.location.href= result;
            },
            error: function () {
                console.error("Error inserting options");
            }
        });
    });
}