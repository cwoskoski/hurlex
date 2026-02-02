# HLEX-005: Add Code Formatting Support

## Type
Feature

## Priority
Medium

## Description
There is no `FormattingModelBuilder` for Hurl files. Users cannot auto-format their `.hurl` files with the standard IDE reformat action.

## Tasks
- [ ] Implement `HurlFormattingModelBuilder`
- [ ] Define spacing rules:
  - Blank line between entries (request/response pairs)
  - No extra blank lines within a section
  - Consistent indentation for section contents (headers, key-value pairs, asserts)
  - Space after `:` in headers
  - Proper alignment of JSON body content
- [ ] Implement `HurlCodeStyleSettings` and settings UI panel
- [ ] Register in `plugin.xml`
- [ ] Add tests for formatting behavior

## Acceptance Criteria
- Code > Reformat Code (Ctrl+Alt+L) correctly formats `.hurl` files
- Formatting is configurable via Settings > Code Style > Hurl
