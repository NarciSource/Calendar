local OpenApiPrefix = {
	PRIORITY = 800,
	VERSION = "1.0",
}

-- Lua JSON
local cjson = require("cjson.safe")

function OpenApiPrefix:body_filter(conf)
	local body = kong.response.get_raw_body()

	-- api 문서 수정
	local json = cjson.decode(body)
	if json.servers and #json.servers > 0 then
		json.servers[1].url = conf.prefix
	else
		json.servers = { { url = conf.prefix } }
	end

	body = cjson.encode(json)

	ngx.arg[1] = body
end

return OpenApiPrefix
