#!/bin/sh
set -e

# 패키지 설치
apk add --no-cache curl jq gettext

until curl -sf http://keycloak:9000/auth/health/ready; do
  echo "[wait] keycloak not ready yet"
  sleep 10
done

# Admin token 발급
TOKEN=$(curl -s -X POST "http://keycloak:8080/auth/realms/master/protocol/openid-connect/token" \
  -H "Content-Type: application/x-www-form-urlencoded" \
  -d "username=${KEYCLOAK_ADMIN}" \
  -d "password=${KEYCLOAK_ADMIN_PASSWORD}" \
  -d "grant_type=password" \
  -d "client_id=admin-cli" | jq -r '.access_token')

# client UUID 조회
CLIENT_UUID=$(curl -s -X GET "http://keycloak:8080/auth/admin/realms/dev/clients?clientId=${OAUTH2_PROXY_CLIENT_ID}" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.[0].id')
echo "CLIENT_UUID: $CLIENT_UUID"

# client-secret 조회
CLIENT_SECRET=$(curl -s -X GET "http://keycloak:8080/auth/admin/realms/dev/clients/$CLIENT_UUID/client-secret" \
  -H "Authorization: Bearer $TOKEN" | jq -r '.value')
echo "CLIENT_SECRET: $CLIENT_SECRET"

# 시크릿 환경변수 설정
export OAUTH2_PROXY_CLIENT_SECRET=$CLIENT_SECRET

# oauth2-proxy 시작
envsubst < /etc/oauth2-proxy/config.cfg > /etc/oauth2-proxy/config.runtime.cfg
exec /bin/oauth2-proxy --config=/etc/oauth2-proxy/config.runtime.cfg
