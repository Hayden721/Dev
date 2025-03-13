const regexPatterns = {
    // 아이디: 영어 대소문자 + 숫자, 최소 4자 이상
    idRegex: /^[a-zA-Z0-9]{4,}$/,

    // 비밀번호: 영어 대소문자 + 숫자, 최소 8자 이상
    passwordRegex: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z0-9]{8,16}$/,
    // 이름: 한글이름 1~8자
    nameRegex: /^[가-힣]{1,8}$/,

    // 이메일: xxx@xxx.xxx
    emailRegex: /^[^\s@]+@[^\s@]+\.[^\s@]+$/,

    // 전화번호: 숫자로만 구성, 10자리 또는 11자리 (하이픈 없음)
    phoneRegex: /^\d{10,11}$/,
}

export default regexPatterns;