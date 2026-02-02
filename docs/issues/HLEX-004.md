# HLEX-004: Add Error Highlighting and Inspections

## Type
Feature

## Priority
Medium

## Description
Parse errors are not surfaced in the editor and there are no custom inspections to catch common mistakes.

## Tasks
- [ ] Surface parser errors as red squiggly underlines in the editor
- [ ] Add inspection: unknown section name (e.g., `[BadSection]`)
- [ ] Add inspection: duplicate headers within the same request
- [ ] Add inspection: invalid HTTP status code in response line
- [ ] Add inspection: unclosed template variable (`{{var` without `}}`)
- [ ] Add inspection: empty assert/capture section (section declared but no entries)
- [ ] Register inspections in `plugin.xml` with configurable severity levels
- [ ] Add quick-fix actions where applicable (e.g., fix section name typo)

## Acceptance Criteria
- Syntax errors are visually indicated in the editor
- Each inspection can be toggled on/off in Settings > Inspections
- At least one quick-fix is provided
