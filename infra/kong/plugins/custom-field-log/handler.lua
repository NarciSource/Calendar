local CustomFieldLog = {
	PRIORITY = 10, -- 실행 우선순위
	VERSION = "1.0",
}

-- WebSocket 여부 저장
function CustomFieldLog:access(conf)
	local headers = ngx.req.get_headers()
	ngx.ctx.is_ws = headers["upgrade"] and headers["upgrade"]:lower() == "websocket"
end

-- 로그를 기록하는 함수
function CustomFieldLog:log(conf)
	-- 요청 정보
	local client_ip = kong.client.get_ip() or "unknown"
	local method = kong.request.get_method() or "GET"
	local path = kong.request.get_path() or "/"
	local scheme = ngx.var.scheme or "http"
	local protocol = (ngx.ctx.is_ws and (scheme == "https" and "WSS" or "WS"))
		or (scheme == "https" and "HTTPS" or "HTTP")
	local version = ngx.req.http_version() or 1.1
	local request_bytes_sent = tonumber(ngx.var.request_length) or 0
	local headers = ngx.req.get_headers()

	-- 게이트웨이 정보
	local gateway_ip = ngx.var.server_addr or "unknown"
	local gateway_port = ngx.var.server_port or "unknown"
	local gateway_domain = ngx.req.get_headers()["host"] or "-"

	-- 업스트림 정보
	local upstream = ngx.var.upstream_addr or "unknown"
	local upstream_last_octet = upstream:match("([^:]+)"):match("(%d+)$") or "?"
	local upstream_path = ngx.var.upstream_uri or path

	-- 서비스 및 라우트 정보
	local service = kong.router.get_service()
	local route = kong.router.get_route()
	local route_name = route and route.name or "unknown"
	local upstream_name = service and service.host or "unknown"

	-- 응답 정보
	local status_messages = {
		[101] = "Switching Protocol",
		[200] = "OK",
		[201] = "Created",
		[204] = "No Content",
		[304] = "Not Modified",
		[400] = "Bad Request",
		[401] = "Unauthorized",
		[403] = "Forbidden",
		[404] = "Not Found",
		[500] = "Internal Server Error",
		[502] = "Bad Gateway",
		[504] = "Gateway Timeout",
	}
	local upstream_status = ngx.var.upstream_status
	local gateway_status = kong.response.get_status()
	local status_message = status_messages[gateway_status] or "Unknown"
	local response_bytes_sent = tonumber(ngx.var.body_bytes_sent) or 0
	local latency = tonumber(ngx.var.request_time) or 0

	-- 로그 출력
	local log_path = conf.path or "/usr/local/kong/logs/select-field.log"
	local f = io.open(log_path, "a+")

	if f then
		f:write(
			string.format(
				"[%s %.1f] %s (client) %s %sbytes\n",
				protocol,
				version,
				method,
				client_ip,
				request_bytes_sent
			)
		)
		f:write(string.format("            → %s:%s (gateway) %s\n", gateway_ip, gateway_port, path))
		f:write(string.format("            → %s\n", route_name))
		f:write(
			string.format(
				"            → %s (%s #%s) %s\n",
				upstream,
				upstream_name,
				upstream_last_octet,
				upstream_path
			)
		)
		f:write(
			string.format(
				"            ← %s/%s %s %sbytes %.3fs\n",
				upstream_status,
				gateway_status,
				status_message,
				response_bytes_sent,
				latency
			)
		)
		f:close()
	end
end

return CustomFieldLog
