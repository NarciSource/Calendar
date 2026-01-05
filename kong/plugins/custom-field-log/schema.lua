local typedefs = require("kong.db.schema.typedefs")

return {
	name = "custom-field-log",
	fields = {
		{
			config = {
				type = "record",
				fields = {
					{
						path = typedefs.path, -- 로그 파일 경로
					},
				},
			},
		},
	},
}
