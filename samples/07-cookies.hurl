# =============================================================================
# Cookies
# Demonstrates cookie sending and capturing
# =============================================================================

# Send cookies with request
GET http://httpbin.org/cookies
[Cookies]
session_id: abc123
theme: dark
lang: en-US
HTTP 200
[Asserts]
jsonpath "$.cookies.session_id" == "abc123"
jsonpath "$.cookies.theme" == "dark"
jsonpath "$.cookies.lang" == "en-US"

# Set cookies via server and verify
GET http://httpbin.org/cookies/set/test_cookie/test_value
[Options]
location: true
HTTP 200
[Asserts]
jsonpath "$.cookies.test_cookie" == "test_value"

# Capture cookies from response
GET http://httpbin.org/cookies/set/auth/token123
[Options]
location: true
HTTP 200
[Captures]
auth_cookie: jsonpath "$.cookies.auth"
[Asserts]
variable "auth_cookie" == "token123"
