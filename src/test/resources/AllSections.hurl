GET http://localhost/api/search
[QueryStringParams]
q: test
page: 1

POST http://localhost/api/form
[FormParams]
field1: value1
field2: value2

POST http://localhost/api/upload
[MultipartFormData]
file1: file,test.txt;

GET http://localhost/api/cookies
[Cookies]
session: abc123

GET http://localhost/api/auth
[BasicAuth]
user: password

GET http://localhost/api/data
[Options]
retry: 3
verbose: true
insecure: true
HTTP 200
[Captures]
data_id: jsonpath "$.id"
[Asserts]
status == 200
