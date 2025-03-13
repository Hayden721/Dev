import regexPatterns from '/js/regex.js';

// 입력 값 확인 후 회원가입 버튼 활성화/비활성화
export function checkInputValues() {
    const idDuplicate = $('#id-duplicate-value').val().trim(); // 아이디 중복체크 값
    const pwConfirm = $('#pw-check-value').val().trim(); // 비밀번호 확인 체크 값
    const name = $('#name').val().trim(); // 이름
    const email = $('#email-confirm-value').val().trim(); // 이메일
    const phone = $('#phone').val().trim(); // 휴대번호

    console.log(idDuplicate + "," + pwConfirm + "," + name + "," + email + "," + phone);

    // 빈칸이 아니라면 활성화
    if(idDuplicate !== 1 && idDuplicate !== "" && pwConfirm !== 0 && pwConfirm !== "" && name !== "" && email !== "" && email !== 0 && phone !== "") {
        $('.register-btn').prop('disabled', false);
    }else {
        // 빈칸이라면 비활성화
        $('.register-btn').prop('disabled', true);
    }
}

// 비밀번호 확인
export function passwordConfirm() {
    $('#password-confirm, #password').on('input', function () {
        const password = $('#password').val(); // 비밀번호
        const pwConfirmVal = $('#password-confirm').val(); // 비밀번호 확인
        console.log("password" + password);


        if(pwConfirmVal === "") {
            $('#pw-confirm-message').text("");
            $('#pw-check-value').val("0");

        } else if(password !== pwConfirmVal) {
            $('#pw-confirm-message').text("비밀번호가 일치하지 않습니다.");
            $('#pw-check-value').val("0");

        } else {
            $('#pw-confirm-message').text("");
            $('#pw-check-value').val("1");
        }
    })
}


// 가입정보 정규화
export function registerFormat() {
    // 아이디 정규화
    $('#id').on('input', function () {
        const id = $('#id').val();
        console.log("아이디 정규식 : " + id);
        if(id === "") {
            $('#id-message').text("");

        }else if(!regexPatterns.idRegex.test(id) ) {
            $('#id-message').text("올바른 형식의 아이디가 아닙니다.");
        } else {
            $('#id-message').text("");
        }

    });

    // 패스워드 정규화
    $('#password').on('input', function () {
        const password = $('#password').val();

        if(password === "") {
            $('#password-message').text("");
        }else if(!regexPatterns.passwordRegex.test(password) ) {
            $('#password-message').text("올바른 형식의 비밀번호가 아닙니다.");
        } else {
            $('#password-message').text("");
        }

    });
    // 이름 정규화
    $('#name').on('input', function () {
        const name = $('#name').val();

        if(name === "") {
            $('#name-message').text("");
        }else if(!regexPatterns.nameRegex.test(name) ) {
            $('#name-message').text("올바른 형식의 이름이 아닙니다.");
        } else {
            $('#name-message').text("");
        }

    });

    // 이메일 정규화
    $('#email').on('input', function () {
        const email = $('#email').val().trim();

        console.log("email: " + email);
        if (!regexPatterns.emailRegex.test(email)) {
            $('#email-message').text("올바른 형식의 이메일이 아닙니다.");
            $('#email-confirm-value').val("0");

        } else if(email === "") {
            $('#email-message').text("");
            $('#email-confirm-value').val("0");

        } else {
            $('#email-message').text("");
            $('#email-confirm-value').val("1");
        }
    });

    $('#phone').on('input', function () {
        const phone = $('#phone').val();
        console.log("아이디 정규식 : " + phone);
        if(phone === "") {
            $('#phone-message').text("");
        }else if(!regexPatterns.phoneRegex.test(phone) ) {
            $('#phone-message').text("올바른 형식의 전화번호가 아닙니다.");
        } else {
            $('#phone-message').text("");
        }

    });

}

// 중복 아이디 체크
export function idDuplicateCheck(url){
    let idValue;
    $('#duplicate-btn').click(function () {
        idValue = $('#id').val();
        console.log(idValue);
        if(idValue === "") {
            alert("아이디가 입력되지 않았습니다!");
        }else {
            $.ajax({
                type: 'GET',
                url: url,
                dataType: 'json',
                data: {
                    idValue: idValue
                },
                success: function (result) {
                    // 2. 중복 되지 않으며 0, 중복되면 1을 반환 / id-duplicate에 데이터 입력
                    console.log("ajax success");
                    $('#id-duplicate-value').val(result);

                    if (result === 0) {
                        alert("사용 가능한 아이디입니다.");
                        checkInputValues();
                    } else {
                        alert("중복된 아이디입니다. 다른 아이디를 사용해 주세요.");
                        checkInputValues();
                    }

                    console.log(result);
                },
                error: function (error) {
                    console.log(error);
                }
            })
        }
    });
    // 아이디 input에 수정이 발생했을 때 id-duplicate-value 초기화

    $('#id').on('input', function () {
        const currentId = $(this).val();
        console.log("currentId: " + currentId);
        if(idValue !== currentId) {
            $('#id-duplicate-value').val("");
        }
    })


}

// 멤버 회원가입
export function register(url, redirectUrl) {
    $('#register-form').on('submit', function (event) {
        event.preventDefault();

        $.ajax({
            type: 'POST',
            url: url,
            data: $(this).serialize(),
            success: function () {
                alert("회원가입을 완료했습니다. 로그인을 해주세요");
                window.location.href = redirectUrl;
            },
            error: function (error) {
                alert("회원가입에 실패했습니다. 다시 시도해 주세요.");
                console.log(error);
            }
        })
    })
}
