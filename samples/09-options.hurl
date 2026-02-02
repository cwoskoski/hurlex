# =============================================================================
# Request Options
# Demonstrates per-request configuration via [Options]
# =============================================================================

# Retry on failure
GET http://httpbin.org/get
[Options]
retry: 3
retry-interval: 1000
HTTP 200

# Follow redirects
GET http://httpbin.org/redirect/2
[Options]
location: true
max-redirs: 5
HTTP 200

# Verbose output for debugging
GET http://httpbin.org/get
[Options]
verbose: true
HTTP 200

# Very verbose (includes request/response body)
POST http://httpbin.org/post
Content-Type: application/json
[Options]
very-verbose: true
{
  "debug": true
}
HTTP 200

# Skip a request (useful during development)
# GET http://httpbin.org/status/500
# [Options]
# skip: true
# HTTP 500

# Allow insecure SSL (self-signed certs)
# GET https://self-signed.example.com/api
# [Options]
# insecure: true
# HTTP 200

# Add delay before request
GET http://httpbin.org/get
[Options]
delay: 500
HTTP 200

# Compressed response
GET http://httpbin.org/gzip
[Options]
compressed: true
HTTP 200
[Asserts]
jsonpath "$.gzipped" == true
