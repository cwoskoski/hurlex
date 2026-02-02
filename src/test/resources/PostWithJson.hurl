POST http://localhost:8080/api/users
Content-Type: application/json
{
  "name": "test",
  "age": 30,
  "active": true
}

HTTP 201
[Asserts]
jsonpath "$.id" isInteger
