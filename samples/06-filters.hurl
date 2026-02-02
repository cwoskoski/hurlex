# =============================================================================
# Filters
# Demonstrates filter functions applied to query results
# =============================================================================

# count filter - get collection size
GET http://httpbin.org/get?item=a&item=b&item=c
HTTP 200
[Asserts]
jsonpath "$.args.item" count == 3

# nth filter - get element at index
GET http://httpbin.org/get?item=first&item=second&item=third
HTTP 200
[Asserts]
jsonpath "$.args.item" nth 0 == "first"
jsonpath "$.args.item" nth 2 == "third"

# regex filter in assertions
GET http://httpbin.org/get
HTTP 200
[Asserts]
header "Content-Type" matches "application/json"

# URL encode/decode in captures
GET http://httpbin.org/get?message=hello%20world
HTTP 200
[Captures]
raw_message: jsonpath "$.args.message"
[Asserts]
jsonpath "$.args.message" == "hello world"

# Using split filter
GET http://httpbin.org/response-headers?X-Tags=one,two,three
HTTP 200
[Captures]
tags_header: header "X-Tags"
[Asserts]
header "X-Tags" split "," count == 3
