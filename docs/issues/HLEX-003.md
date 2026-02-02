# HLEX-003: Add Live Templates and File Templates

## Type
Feature

## Priority
Medium

## Description
There are no live templates (snippets) or file templates for common Hurl patterns. Users should be able to quickly scaffold requests and create new `.hurl` files from the IDE.

## Tasks
- [ ] Add a "New Hurl File" action to the project tree context menu (File > New)
- [ ] Create live templates for common patterns:
  - `get` — GET request with status assert
  - `post` — POST request with JSON body
  - `postform` — POST with form params
  - `assert` — Common assert block
  - `capture` — Capture block with jsonpath
  - `auth` — BasicAuth section
  - `options` — Options section with retry
- [ ] Register live template context for Hurl files
- [ ] Add template variables with sensible defaults

## Acceptance Criteria
- Right-click > New shows "Hurl File" option
- Typing abbreviation + Tab expands live templates in `.hurl` files
- Templates include cursor placement and editable regions
