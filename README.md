# 공간 예약 서비스
> **일반 사용자가 날짜와 시간을 선택해 공간을 예약할 수 있는 웹 서비스입니다.**

## 프로젝트 소개

본 프로젝트는 일반 사용자와 공간 판매자(seller)가 사용할 수 있는 공간 예약 플랫폼입니다.  
사용자는 원하는 날짜와 시간에 공간을 예약하고, 판매자는 자신의 공간을 등록 및 관리할 수 있습니다.  
회원가입, 로그인, 예약 관리 등의 기본적인 기능과 판매자 전용 공간 관리 기능을 구현했습니다.

# 📌 기능
### 일반 사용자 페이지
회원 관리
- 로그인
- 회원가입
- 로그아웃
- 회원 수정
- 비밀번호 찾기
  
예약
- 공간 예약
- 예약 취소
- 예약 조회
  
###  공간 판매자(seller) 페이지
회원 관리
- 로그인
- 회원 가입
- 로그아웃
- 비밀 번호 찾기

공간 관리
- 공간 조회
- 공간 추가
- 공간 삭제
- 공간 수정

예약 관리
- 예약 조회
- 예약 취소

# ⚙️ 개발 환경
### Front-end
![js](https://img.shields.io/badge/HTML5-E34F26?style=for-the-badge&logo=HTML5&logoColor=white)
![js](https://img.shields.io/badge/CSS3-1572B6?style=for-the-badge&logo=CSS3&logoColor=white)
![js](https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=JavaScript&logoColor=white)

### Back-end
![js](https://img.shields.io/badge/SpringBoot-6DB33F?style=for-the-badge&logo=SpringBoot&logoColor=white)
![js](https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white")

### Library
![js](https://img.shields.io/badge/Thymeleaf-005F0F?style=for-the-badge&logo=Thymeleaf&logoColor=white)
![js](https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jQuery&logoColor=white)
![js](https://img.shields.io/badge/BootStrap-7952B3?style=for-the-badge&logo=BootStrap&logoColor=white)

![js](https://img.shields.io/badge/Mybatis-000000?style=for-the-badge&logo=Mybatis&logoColor=white)
![js](https://img.shields.io/badge/SpringSecurity-6DB33F?style=for-the-badge&logo=SpringSecurity&logoColor=white)

### DB
![js](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=MySQL&logoColor=white)


# 📋 ERD
<img src="https://github.com/user-attachments/assets/406d791a-c3a0-4ee1-b6ba-afe4bd0f77c9" width="900" height="550" /> 

[https://www.erdcloud.com/d/eKC3AXvYzvuGYFREJ](https://www.erdcloud.com/d/eKC3AXvYzvuGYFREJ)

# 📄 보고서
https://drive.google.com/file/d/12z94wtLmu65h5qi-KGrhH3flcKBrwVzO/view?usp=drive_link
## 💡 기술적 이슈 및 해결 경험

### Spring Security - 회원 유형별 접근 제어

- 일반 사용자와 판매자의 접근 권한을 분리하기 위해 `@Order` 어노테이션을 사용하여 Security 설정을 다중 구성으로 분리했습니다.
- 사용자 유형에 따라 접근 가능한 URL을 달리 설정하여 페이지 접근 제한을 효과적으로 구현했습니다.

---

## 📈 결과 및 성과

- **AJAX**를 활용한 비동기 통신 방식의 이해 및 적용
- **Spring Security**를 직접 프로젝트에 적용함으로써 웹 보안 처리에 대한 경험 확보

## 🧩 개선 사항

- **클린 코드 작성**
  - 변수 및 함수의 명명, 주석 처리 등을 개선하여 가독성과 유지보수성 향상 예정

- **프로젝트 일정 관리**
  - 기능별 설계 및 구현 단계를 명확히 나누고 일정 계획을 세분화하여 효율적으로 진행하고자 함

## 👨‍💻 개발자

- **개발 인원**: 1인 개발
- **담당 역할**: 기획, 설계, 프론트엔드, 백엔드 등 전체 과정
