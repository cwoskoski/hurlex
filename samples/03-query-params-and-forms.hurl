# =============================================================================
# Query Parameters and Form Data
# Demonstrates query strings, form params, and multipart uploads
# =============================================================================

# Query string parameters using [QueryStringParams]
GET http://httpbin.org/get
[QueryStringParams]
search: hurl testing
page: 1
limit: 25
sort: name
order: asc
HTTP 200
[Asserts]
jsonpath "$.args.search" == "hurl testing"
jsonpath "$.args.page" == "1"
jsonpath "$.args.limit" == "25"

# URL-encoded form parameters
POST http://httpbin.org/post
[FormParams]
username: johndoe
password: s3cr3t
remember_me: true
HTTP 200
[Asserts]
jsonpath "$.form.username" == "johndoe"
jsonpath "$.form.password" == "s3cr3t"

# Multipart form data (file upload simulation)
POST http://httpbin.org/post
[MultipartFormData]
field1: value1
field2: value2
HTTP 200
[Asserts]
jsonpath "$.form.field1" == "value1"
jsonpath "$.form.field2" == "value2"
