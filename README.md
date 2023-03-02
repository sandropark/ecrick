[![Java CI with Gradle](https://github.com/sandropark/e-lib/actions/workflows/ci.yml/badge.svg)](https://github.com/sandropark/e-lib/actions/workflows/ci.yml)
[![Deploy to Amazon EB](https://github.com/sandropark/e-lib/actions/workflows/deploy.yml/badge.svg)](https://github.com/sandropark/e-lib/actions/workflows/deploy.yml)
# 📚 전자책벌레

> 전자도서관 통합검색 서비스
>
> https://ecrick.com

<br>

## 기술 스택
<div>
  <img src="https://img.shields.io/badge/java-007396?style=for-the-badge&logo=OpenJDK&logoColor=white"> 
  <img src="https://img.shields.io/badge/Jpa-007396?style=for-the-badge&logo=Jpa&logoColor=white"/>
  <img src="https://img.shields.io/badge/Querydsl-007396?style=for-the-badge&logo=Querydsl&logoColor=white"/>
  <img src="https://img.shields.io/badge/Spring Boot-6DB33F?style=for-the-badge&logo=Spring Boot&logoColor=white"/>
  <img src="https://img.shields.io/badge/Thymeleaf-6DB33F?style=for-the-badge&logo=Thymeleaf&logoColor=white"/>
  <img src="https://img.shields.io/badge/Gradle-02303A?style=for-the-badge&logo=Gradle&logoColor=white"/>
  <img src="https://img.shields.io/badge/mariaDB-003545?style=for-the-badge&logo=mariaDB&logoColor=white"> 
</div>
<div>
  <img src="https://img.shields.io/badge/Github Actions-2088FF?style=for-the-badge&logo=Github Actions&logoColor=white"/>
  <img src="https://img.shields.io/badge/AWS RDS-527FFF?style=for-the-badge&logo=Amazon RDS&logoColor=white"/>
  <img src="https://img.shields.io/badge/AWS Elastic Beanstalk-232F3E?style=for-the-badge&logo=AmazonAWS&logoColor=white"/>
</div>

<br>

## 구조

<br>

### 모듈

<img src="https://user-images.githubusercontent.com/89520805/219685535-04098e91-24ab-4b01-8ad8-987a0baab9bc.png" width="540"/>

- 크롤링을 하는 개발 서버의 경우 배포할 필요가 없기 때문에 Gradle을 이용해서 멀티모듈을 구성했습니다.

<br>

### 배포 과정
<img src="https://user-images.githubusercontent.com/89520805/213906909-075efc31-14d1-4843-bc7a-6dc68b2ff6af.png" width="540"/>

- **Github Actions**와 **AWS**(Elastic Beastalk, RDS)를 사용해서 무중단 배포하고 있습니다.

<br>

### 크롤링
<img src="https://user-images.githubusercontent.com/89520805/214044135-fea4f460-c77b-47d1-bf0e-67fcad04cc59.png" width="850"/>

- 관리자 페이지를 만들어서 도서관을 관리하고 있습니다. 
- 각 도서관 별로 선택해서 크롤링할 수 있습니다. 
- 사용성을 위해 크롤링은 별도의 스레드에서 동작합니다.

<br>

<img src="https://user-images.githubusercontent.com/89520805/214520495-78606c97-0763-4aab-953f-8192ed7eeaaa.png" width="700"/>

- 데이터를 요청하는 시간이 제일 오래 걸리기 때문에 여러 스레드를 사용해서 시간을 단축시킵니다.
- 같은 도서관을 여러 번 크롤링해도 중복 데이터가 생기면 안되기 때문에 제목,저자,출판사 등을 Unique Key로 묶어서 중복 데이터가 저장되지 않게 합니다.
- 크롤링 시 쓰기 작업이 너무 빈번하기 때문에 DB에 부하가 큽니다. DB를 분리해서 로컬 DB에 크롤링을 하고 RDS는 조회용으로 사용합니다. 복제 기능을 사용해서 RDS에도 크롤링 결과가 반영되게 합니다.
  
<br>

### DB

<img src="https://user-images.githubusercontent.com/89520805/214531774-c33b0e2e-a588-432b-8a12-83db5b27f195.png" width="600"/>

- 같은 책이어도 소장 도서관 / 공급사가 다르다면 DB에는 다른 Row로 저장됩니다. 현재 약 120만 건의 데이터가 있습니다. 책을 검색하는 게 목적이기 때문에 책 데이터만 따로 테이블을 만들고 중복된 책 데이터를 줄이면 조회 시간을 단축할 수 있습니다. 현재는 제목,저자,출판사가 중복된 데이터를 줄여 약 30만 건의 데이터를 가지고 조회합니다.

## 마주했던 문제

- [프로젝트를 시작하게 된 계기와 받은 도움](https://velog.io/@ticoba/%ED%94%84%EB%A1%9C%EC%A0%9D%ED%8A%B8%EB%A5%BC-%EC%8B%9C%EC%9E%91%ED%95%98%EA%B2%8C-%EB%90%9C-%EA%B3%84%EA%B8%B0)
- ['.ga'을 '.com'으로 - 무료 도메인의 문제점](https://velog.io/@ticoba/.ga%EC%9D%84-.com%EC%9C%BC%EB%A1%9C-%EB%AC%B4%EB%A3%8C-%EB%8F%84%EB%A9%94%EC%9D%B8%EC%9D%98-%EB%AC%B8%EC%A0%9C%EC%A0%90)

## 운영

이 서비스가 도움이 될 만한 분들이 활동하는 [네이버 카페](https://cafe.naver.com/ebook)에 서비스를 홍보했습니다. 

<img src="https://user-images.githubusercontent.com/89520805/219675964-db79174b-46ca-4bde-8d2c-d1a27652cd5a.png" width="650"/>

MAU는 1000이상으로 사용자가 꾸준히 접속하고 있고, 피드백을 받으며 꾸준히 개선하고 있습니다.  

## 개선할 것들

### DB 중복 제거
  - <img src="https://user-images.githubusercontent.com/89520805/214555171-c21f2b8a-9eba-4538-80ad-f179b5f6844b.png" width="650"/>
  - 이렇게 같은 데이터지만 제목/저자/출판사가 조금씩 다르기 때문에 중복데이터가 생긴다. 형태소 분석기 같은 것을 사용해서 중복된 데이터를 하나로 압축하는 작업을 한다.

### 회원 기능
  - <img src="https://user-images.githubusercontent.com/89520805/214551689-d5ea5d9b-cf94-40f2-99c5-676bb8f1737a.png" width="550"/>
  - 자신이 이용하지 않는 전자도서관이 검색 결과에 나오면 필요 없는 정보가 자리를 차지하는 것이기 때문에 사용성이 떨어진다. 원하는 도서관만 필터링해서 검색하는 기능이 필요하다. 매번 접속할 때마다 원하는 도서관을 선택하는 일은 귀찮을 것이다. 소셜로그인 기능을 사용해서 회원 가입을 하고 관심도서관을 저장해 두는 식으로 구현하는 것이 좋을 것 같다.
  - 책 역시 관심도서로 지정하고 목록을 직접 카테고리화 하는 식으로 **관심도서목록**기능을 추가해도 좋을 것 같다.

### 검색최적화
  - 이 서비스가 도움이 될만한 더 많은 사람들에게 알리고싶다. html 태그나 메타데이터 같은 것들도 더 신경쓴다. 
  - 블로그를 연결해서 공지도 하고 유입이 될 수 있는 창구로 사용한다.
