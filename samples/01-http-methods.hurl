# =============================================================================
# HTTP Methods
# Demonstrates all common HTTP methods
# =============================================================================

# Simple GET request
GET http://httpbin.org/get
HTTP 200
[Asserts]
status == 200
header "Content-Type" contains "json"

# POST with JSON body
POST http://httpbin.org/post
Content-Type: application/json
{
  "name": "Alice",
  "email": "alice@example.com"
}
HTTP 200
[Asserts]
jsonpath "$.json.name" == "Alice"

# PUT to update a resource
PUT http://httpbin.org/put
Content-Type: application/json
{
  "name": "Alice Updated",
  "email": "alice.updated@example.com"
}
HTTP 200

# PATCH for partial update
PATCH http://httpbin.org/patch
Content-Type: application/json
{
  "email": "newemail@example.com"
}
HTTP 200

# DELETE a resource
DELETE http://httpbin.org/delete
HTTP 200

# HEAD request (returns headers only, no body)
HEAD http://httpbin.org/get
HTTP 200

# OPTIONS request (CORS preflight)
OPTIONS http://httpbin.org/get
HTTP 200
