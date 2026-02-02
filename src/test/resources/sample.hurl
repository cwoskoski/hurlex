# Test getting users
GET http://localhost:8080/api/users
Content-Type: application/json
[QueryStringParams]
page: 1
limit: 10

HTTP 200
[Captures]
token: header "Authorization"
user_id: jsonpath "$.users[0].id"

[Asserts]
status == 200
header "Content-Type" contains "json"
jsonpath "$.users" count == 10

# Create user with template variable
POST http://localhost:8080/api/users
Content-Type: application/json
{
  "name": "{{username}}",
  "email": "test@example.com"
}

HTTP 201
[Asserts]
jsonpath "$.id" isInteger
jsonpath "$.name" == "{{username}}"

# Multiline body
POST http://localhost:8080/api/graphql
```graphql
{
  users {
    id
    name
  }
}
```

HTTP 200

# Options example
GET http://localhost:8080/api/retry-test
[Options]
retry: 3
retry-interval: 1000
verbose: true

HTTP 200
