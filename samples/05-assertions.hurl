# =============================================================================
# Assertions
# Demonstrates the full range of assertion predicates and queries
# =============================================================================

# Status code assertions
GET http://httpbin.org/status/200
HTTP 200
[Asserts]
status == 200
status >= 200
status < 300

# Header assertions
GET http://httpbin.org/get
HTTP 200
[Asserts]
header "Content-Type" contains "application/json"
header "Content-Type" startsWith "application/"
header "Content-Type" endsWith "json"
header "Content-Type" matches "application/.*json"

# JSON body assertions with various predicates
GET http://httpbin.org/get?name=test&count=42&active=true
HTTP 200
[Asserts]
jsonpath "$.args.name" == "test"
jsonpath "$.args.name" != "wrong"
jsonpath "$.args.name" isString
jsonpath "$.args.name" exists
jsonpath "$.args.name" not isEmpty
jsonpath "$.args.missing" not exists
jsonpath "$.url" startsWith "http"
jsonpath "$.url" contains "httpbin"

# Collection assertions
GET http://httpbin.org/get?a=1&a=2&a=3
HTTP 200
[Asserts]
jsonpath "$.args.a" isCollection
jsonpath "$.args.a" count == 3
jsonpath "$.args.a" includes "1"

# Duration assertion
GET http://httpbin.org/delay/0
HTTP 200
[Asserts]
duration < 5000

# Body assertions
GET http://httpbin.org/html
HTTP 200
[Asserts]
body contains "Herman Melville"

# Type checking predicates
GET http://httpbin.org/get?num=42&flag=true
HTTP 200
[Asserts]
jsonpath "$.url" isString
jsonpath "$.headers" not isEmpty
