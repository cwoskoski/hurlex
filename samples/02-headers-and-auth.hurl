# =============================================================================
# Headers and Authentication
# Demonstrates header usage, basic auth, and bearer tokens
# =============================================================================

# Custom headers
GET http://httpbin.org/headers
Accept: application/json
X-Custom-Header: my-value
X-Request-Id: abc-123
User-Agent: Hurlex/1.0
HTTP 200
[Asserts]
jsonpath "$.headers.X-Custom-Header" == "my-value"
jsonpath "$.headers.X-Request-Id" == "abc-123"

# Basic Authentication using [BasicAuth] section
GET http://httpbin.org/basic-auth/admin/secret
[BasicAuth]
admin: secret
HTTP 200
[Asserts]
jsonpath "$.authenticated" == true
jsonpath "$.user" == "admin"

# Bearer token authentication
GET http://httpbin.org/bearer
Authorization: Bearer my-secret-token-12345
HTTP 200
[Asserts]
jsonpath "$.authenticated" == true

# Content negotiation
GET http://httpbin.org/response-headers?Content-Type=text/plain
Accept: text/plain
Accept-Encoding: gzip, deflate
Accept-Language: en-US,en;q=0.9
HTTP 200
