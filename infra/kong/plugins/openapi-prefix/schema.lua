local typedefs = require("kong.db.schema.typedefs")

return {
	name = "openapi-prefix",
	fields = {
		{
			config = {
				type = "record",
				fields = {
					{
						prefix = {
							type = "string",
							required = true,
							description = "Gateway prefix to inject into OpenAPI servers",
						},
					},
				},
			},
		},
	},
}
