# =============================================================================
# Captures and Request Chaining
# Demonstrates capturing values and using them in subsequent requests
# =============================================================================

# Step 1: Get a UUID from the server and capture it
GET http://httpbin.org/uuid
HTTP 200
[Captures]
request_uuid: jsonpath "$.uuid"

# Step 2: Use the captured UUID in a follow-up request
POST http://httpbin.org/post
Content-Type: application/json
{
  "uuid": "{{request_uuid}}",
  "action": "verify"
}
HTTP 200
[Asserts]
jsonpath "$.json.uuid" == "{{request_uuid}}"
jsonpath "$.json.action" == "verify"

# Step 3: Capture from headers
GET http://httpbin.org/response-headers?X-Token=abc123&X-Session=session-456
HTTP 200
[Captures]
token: header "X-Token"
session: header "X-Session"

# Step 4: Use captured headers in the next request
GET http://httpbin.org/get
X-Token: {{token}}
X-Session: {{session}}
HTTP 200
[Asserts]
jsonpath "$.headers.X-Token" == "{{token}}"
jsonpath "$.headers.X-Session" == "{{session}}"
