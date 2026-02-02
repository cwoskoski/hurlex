GET http://localhost/api/health
HTTP 200

GET http://localhost/api/users
HTTP 200
[Asserts]
status == 200
jsonpath "$.users" count == 10

POST http://localhost/api/users
Content-Type: application/json
{
  "name": "new_user"
}
HTTP 201
