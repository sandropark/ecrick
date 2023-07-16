#!/bin/sh

DEFAULT_CONF="/etc/nginx/nginx.conf" # 새로 띄운 컨테이너로 리버스 프록시 하기 위해서 사용

BEFORE=blue
AFTER=green
BEFORE_PORT=8081
AFTER_PORT=8080

# 현재 실행 중인 컨테이너가 green 이라면
if docker ps | grep -q green; then
    BEFORE=green
    AFTER=blue
    BEFORE_PORT=8080
    AFTER_PORT=8081
fi

docker compose up -d nginx

echo "### $BEFORE => $AFTER ###"
echo "1. $AFTER container up"
docker compose up -d $AFTER # 컨테이너 실행 port는 green이냐 blue냐에 따라 compose 파일에 정의되어 있다.

# 헬스체크
while true;do
  echo "2. health check..."
  sleep 3
  echo "request : http://localhost:$AFTER_PORT"
  REQUEST=$(curl http://localhost:$AFTER_PORT)
  if [ -n "$REQUEST" ]; then # 서비스 가능하면 break
    echo "health check success"
    break ;
  fi
done;

echo "3. change proxy port and reload nginx"
# nginx가 새로운 포트를 리버스 프록싱하게 수정 후 nginx 재시작
docker exec nginx sed -i "s/$BEFORE/$AFTER/g" $DEFAULT_CONF; docker exec nginx service nginx reload
echo "4. $BEFORE container down"
docker compose stop $BEFORE  # 이전 버전 컨테이너 멈추기
echo "5. delete unused images"
docker image prune -af    # 사용하지 않는 이미지 삭제 (새로운 버전이 배포될 때마다 새로운 이미지를 pull하기 때문)