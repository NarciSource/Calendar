local OpenApiPrefix = {
	PRIORITY = 800,
	VERSION = "1.0",
}

-- Lua JSON
local cjson = require("cjson.safe")

function OpenApiPrefix:header_filter(conf)
	kong.response.clear_header("Content-Length") -- body length 자동 계산을 위해 삭제
end

function OpenApiPrefix:body_filter(conf)
	-- chunk 처리
	local chunk = ngx.arg[1] -- 현재 body chunk
	local eof = ngx.arg[2] -- 마지막 chunk 여부

	ngx.ctx.buffered = (ngx.ctx.buffered or "") .. (chunk or "") -- chunk를 버퍼링

	if not eof then
		ngx.arg[1] = nil
		return
	end

	local body = ngx.ctx.buffered

	-- json 수정
	local json = cjson.decode(body)
	if json and (json.openapi or json.swagger) then
		if json.servers and #json.servers > 0 then
			json.servers[1].url = conf.prefix
		else
			json.servers = { { url = conf.prefix } }
		end

		body = cjson.encode(json)
	end

	ngx.arg[1] = body
end

return OpenApiPrefix
