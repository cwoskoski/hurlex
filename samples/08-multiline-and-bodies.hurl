# =============================================================================
# Multiline Strings and Body Types
# Demonstrates various body formats
# =============================================================================

# JSON body
POST http://httpbin.org/post
Content-Type: application/json
{
  "user": {
    "name": "Jane Doe",
    "age": 30,
    "active": true,
    "tags": ["admin", "editor"],
    "address": {
      "city": "Portland",
      "state": "OR",
      "zip": "97201"
    }
  }
}
HTTP 200
[Asserts]
jsonpath "$.json.user.name" == "Jane Doe"
jsonpath "$.json.user.age" == 30
jsonpath "$.json.user.active" == true
jsonpath "$.json.user.tags" count == 2

# GraphQL multiline body
POST http://httpbin.org/post
Content-Type: application/json
```graphql
{
  users(first: 10) {
    edges {
      node {
        id
        name
        email
      }
    }
    pageInfo {
      hasNextPage
    }
  }
}
```
HTTP 200

# XML body
POST http://httpbin.org/post
Content-Type: application/xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<user>
  <name>Jane Doe</name>
  <email>jane@example.com</email>
  <role>admin</role>
</user>
```
HTTP 200

# Plain text multiline body
POST http://httpbin.org/post
Content-Type: text/plain
```
This is a plain text body.
It can span multiple lines.
Useful for testing text endpoints.
```
HTTP 200
