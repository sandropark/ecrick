# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## 프로젝트 개요

전자책벌레(ecrick)는 여러 공공도서관의 전자책을 통합 검색하는 서비스입니다. Spring Boot 3.1 + Gradle 멀티모듈 구조로 개발되었습니다.

## 빌드 및 실행 명령어

### 빌드
```bash
./gradlew clean build
```

### 테스트 실행
```bash
# 전체 테스트
./gradlew test

# 특정 모듈 테스트
./gradlew :domain:test
./gradlew :crawler:crawler-domain:test

# 단일 테스트 클래스 실행
./gradlew test --tests com.ecrick.domain.repository.BookRepositoryTest

# 단일 테스트 메서드 실행
./gradlew test --tests com.ecrick.domain.repository.BookRepositoryTest.search
```

### 애플리케이션 실행
```bash
# 메인 웹 애플리케이션 (포트: 8080)
./gradlew :boot:bootRun

# 크롤러 애플리케이션
./gradlew :crawler:crawler-boot:bootRun
```

### Docker 빌드 및 배포
```bash
# Docker 이미지 빌드
docker build -t ecrick .

# Blue-Green 배포 (deploy 디렉토리에서)
cd deploy
sudo ./deploy.sh
```

## 모듈 구조 및 의존성

### 메인 애플리케이션 모듈
```
boot (EcrickApp.java - 포트: 8080)
├── domain (JPA 엔티티, 레포지토리, 서비스)
└── web (컨트롤러, Thymeleaf 뷰)
```

### 크롤러 애플리케이션 모듈
```
crawler
├── crawler-boot (CrawlerApp.java - 크롤러 실행)
├── crawler-domain (크롤러 비즈니스 로직)
├── crawler-web (관리자 페이지 컨트롤러)
└── crawler-infra (파서 구현체: Aladin, Yes24, Kyobo, 서울도서관 등)
```

### 의존성 규칙
- `boot` → `domain`, `web`
- `crawler-boot` → `domain`, `web`, `crawler-domain`, `crawler-web`, `crawler-infra`
- 모든 서브모듈은 `bootJar.enabled = false`, `jar.enabled = true`
- `boot`와 `crawler-boot`만 `bootJar.enabled = true`

## 핵심 아키텍처

### 데이터 모델
- **Book**: 중복 제거된 도서 정보 (약 30만 건)
  - Unique Key: `title + author + publisher`
  - 동일 도서의 중복 데이터를 줄여 검색 성능 최적화

- **Core**: 실제 소장 도서 정보 (약 120만 건)
  - Unique Key: `title + author + publisher + publicDate + library + vendor`
  - 같은 책이라도 도서관/공급사가 다르면 별도 레코드로 저장
  - Book과 1:N 관계 (외래키: `book_id`)

- **Library**: 도서관 정보
  - Core와 1:N 관계

### 크롤링 메커니즘
- **별도 스레드 실행**: 사용성을 위해 크롤링은 비동기로 동작
- **병렬 처리**: `parallelStream()`을 사용하여 여러 도서관 동시 크롤링
- **파서 패턴**: 각 도서관/공급사별 파서 구현체 (`CrawlerParser` 인터페이스)
  - Aladin, Yes24(JSON/XML), Kyobo(JSON/XML), Bookcube, OPMS, SeoulLib, SeoulEdu
- **DB 분리 전략**: 로컬 DB로 크롤링 후 RDS로 복제 (쓰기 부하 분산)

### 배포 전략
- **Blue-Green 무중단 배포**: `deploy.sh` 스크립트로 자동화
- **CI/CD**: GitHub Actions → Docker Hub → OCI 인스턴스
- **헬스체크**: 새 컨테이너 기동 확인 후 Nginx 프록시 전환

## 테스트 작성 규칙

### 테스트 작성 시 준수사항
- **행위 검증보다 상태 검증 우선**: Mockito `verify()`보다 실제 상태 변경 확인
- **필수 필드가 아니면 임의 값 사용**: 테스트 데이터 생성 시 가독성 유지
- **통합 테스트**: `@SpringBootTest` 사용 시 `@ActiveProfiles` 지정
- **테스트 격리**: 각 테스트는 독립적으로 실행 가능해야 함

### 테스트 프로필
```java
@ActiveProfiles("prd")  // 운영 DB 테스트
@ActiveProfiles("test") // 테스트 DB
```

## 주요 기술 스택 및 설정

### JPA & QueryDSL
- QueryDSL 5.0.0 (Jakarta) 사용
- `BookRepositoryCustomImpl`에서 동적 쿼리 구현
- p6spy로 SQL 로깅 (`P6SpyFormatter`)

### Thymeleaf
- 서버 사이드 렌더링으로 검색 결과 페이지 제공
- 템플릿 위치: `web/src/main/resources/templates`

### 타임존 설정
```java
// EcrickApp.java, CrawlerApp.java
@EventListener(ApplicationReadyEvent.class)
public void timeZoneConfig() {
    TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    Locale.setDefault(Locale.KOREA);
}
```

## 개발 시 주의사항

### 코드 수정 후 검증 필수
- 빌드 및 테스트 실행으로 문제 없는지 확인
- 특히 도메인 모델 변경 시 영향도 큰 부분 테스트 필수

### Unique 제약조건 유지
- Book, Core 엔티티의 `@UniqueConstraint`는 중복 데이터 방지의 핵심
- 수정 시 반드시 기존 제약조건 유지

### 크롤러 개발 시
- 새 공급사/도서관 추가 시 `CrawlerParser` 구현체 작성
- 파서 테스트 클래스 필수 작성 (예: `AladinParserTest`)
- 응답 DTO는 `crawler-infra/dto`에 정의

### 성능 고려사항
- 검색 쿼리는 Book 테이블 기준 (30만 건)으로 최적화됨
- 대량 데이터 처리 시 페이징 필수
- 크롤링 시 병렬 스트림으로 성능 개선
