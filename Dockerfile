# gradle 이미지를 기본으로 사용한다.
FROM gradle:7.5-jdk11-alpine as builder

# build 디렉토리로 이동
WORKDIR /build

RUN pwd # 테스트
RUN ls -al # 테스트

# 그레이들 파일이 변경되었을 때만 의존성 설치
COPY build.gradle settings.gradle /build/

RUN pwd # 테스트
RUN ls -al # 테스트

RUN gralde build -x test --parallel --continue > /dev/null 2>&1 || true

# 빌더 이미지에서 애플리케이션 빌드
COPY . /build

RUN pwd # 테스트
RUN ls -al # 테스트

RUN gradle build -x test --parallel

#APP
FROM openjdk:11.0-slim
WORKDIR /app

# 빌더 이미지에서 jar 파일만 복사
COPY --from=Builder /build/server/service-server/build/libs/*-SNAPSHOT.jar ./app.jar

RUN pwd # 테스트
RUN ls -al # 테스트

EXPOSE 8080

# root 대신 nobody 권한으로 실행
USER nobody
ENTRYPOINT [                                                \
    "java",                                                 \
    "-jar",                                                 \
    "-Djava.security.egd=file:/dev/./urandom",              \
    "-Dsun.net.inetaddr.ttl=0",                             \
    "app.jar"              \
]