POST http://localhost/api/login
Content-Type: application/json
{
  "username": "admin",
  "password": "secret"
}
HTTP 200
[Captures]
auth_token: jsonpath "$.token"

GET http://localhost/api/profile
Authorization: Bearer {{auth_token}}
HTTP 200
[Asserts]
jsonpath "$.username" == "admin"
