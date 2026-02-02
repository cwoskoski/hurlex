# =============================================================================
# Template Variables
# Demonstrates variable interpolation throughout requests
# Run with: hurl --variable host=httpbin.org --variable user=testuser samples/10-template-variables.hurl
# =============================================================================

# Variables in URLs
GET http://{{host}}/get?user={{user}}
HTTP 200
[Asserts]
jsonpath "$.args.user" == "{{user}}"

# Variables in headers
GET http://{{host}}/headers
User-Agent: Hurlex/{{user}}
X-Username: {{user}}
HTTP 200
[Asserts]
jsonpath "$.headers.X-Username" == "{{user}}"

# Variables in JSON body
POST http://{{host}}/post
Content-Type: application/json
{
  "username": "{{user}}",
  "host": "{{host}}"
}
HTTP 200
[Asserts]
jsonpath "$.json.username" == "{{user}}"

# Capture and reuse as variable
GET http://{{host}}/uuid
HTTP 200
[Captures]
generated_id: jsonpath "$.uuid"

# Use captured variable in next request
POST http://{{host}}/post
Content-Type: application/json
{
  "id": "{{generated_id}}",
  "user": "{{user}}"
}
HTTP 200
[Asserts]
jsonpath "$.json.id" == "{{generated_id}}"
