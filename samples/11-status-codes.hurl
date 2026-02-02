# =============================================================================
# Status Codes
# Demonstrates handling various HTTP status codes
# =============================================================================

# 200 OK
GET http://httpbin.org/status/200
HTTP 200

# 201 Created
POST http://httpbin.org/status/201
HTTP 201

# 204 No Content
DELETE http://httpbin.org/status/204
HTTP 204

# 301 Moved Permanently (without following)
GET http://httpbin.org/status/301
HTTP 301

# 301 with redirect following
GET http://httpbin.org/redirect-to?url=http://httpbin.org/get&status_code=301
[Options]
location: true
HTTP 200

# 400 Bad Request
GET http://httpbin.org/status/400
HTTP 400

# 401 Unauthorized
GET http://httpbin.org/status/401
HTTP 401

# 403 Forbidden
GET http://httpbin.org/status/403
HTTP 403

# 404 Not Found
GET http://httpbin.org/status/404
HTTP 404

# 500 Internal Server Error
GET http://httpbin.org/status/500
HTTP 500

# Wildcard status (accept any status)
GET http://httpbin.org/get
HTTP *
