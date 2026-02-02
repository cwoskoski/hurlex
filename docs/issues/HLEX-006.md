# HLEX-006: Add Documentation Provider and Spell Checking

## Type
Feature

## Priority
Low

## Description
There is no quick-documentation support (Ctrl+Q) for Hurl keywords, and string/comment content is not integrated with the IDE spell checker.

## Tasks
- [ ] Implement `HurlDocumentationProvider` for:
  - HTTP methods (brief description of semantics)
  - Section names (what each section does)
  - Query keywords (status, jsonpath, xpath, etc.)
  - Predicate keywords (equals, contains, matches, etc.)
  - Filter keywords (count, toInt, replace, etc.)
  - Option keys (retry, verbose, insecure, etc.)
- [ ] Link documentation to official Hurl docs where possible
- [ ] Implement `HurlSpellcheckingStrategy` to enable spell checking in:
  - Comments
  - String literal values
  - Multiline string bodies
- [ ] Register both in `plugin.xml`

## Acceptance Criteria
- Ctrl+Q on a keyword shows a useful description
- Typos in comments and strings are flagged by the spell checker
