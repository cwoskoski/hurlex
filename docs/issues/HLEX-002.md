# HLEX-002: Expand Test Coverage

## Type
Testing

## Priority
High

## Description
The project has minimal test coverage — only 10 lexer test cases and a skeleton parser test. Core IDE features have no tests at all.

## Tasks
- [ ] Add parser test data (input `.hurl` files and expected PSI tree `.txt` files)
- [ ] Add lexer tests for edge cases (malformed input, incomplete templates, nested JSON)
- [ ] Add completion contributor tests (verify method, section, keyword, header completions)
- [ ] Add syntax highlighter tests (verify token-to-color mappings)
- [ ] Add annotator tests
- [ ] Add folding tests (verify fold regions for entries, sections, multiline strings)
- [ ] Add structure view tests (verify tree structure matches entries)
- [ ] Add reference resolution tests (template variable navigation)
- [ ] Add find usages tests (capture variables, template variables)
- [ ] Add run configuration tests (serialization, command line construction)

## Acceptance Criteria
- Parser tests cover all grammar rules with matching expected PSI trees
- Each IDE feature has at least basic happy-path test coverage
- Tests pass in CI
