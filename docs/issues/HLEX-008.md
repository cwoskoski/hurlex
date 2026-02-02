# HLEX-008: Add Go-to-Symbol and Enhanced Navigation

## Type
Feature

## Priority
Low

## Description
Capture variables defined in `[Captures]` sections cannot be navigated to via Go-to-Symbol (Ctrl+Alt+Shift+N). Template variable navigation could also be improved.

## Tasks
- [ ] Implement `HurlChooseByNameContributor` for Go-to-Symbol support
- [ ] Index capture variable definitions for symbol lookup
- [ ] Add Go-to-Declaration for template variables (`{{var}}`) to navigate to the `[Captures]` definition
- [ ] Add breadcrumb navigation support showing: File > Entry (method + URL) > Section
- [ ] Add navbar structure provider for file-level navigation

## Acceptance Criteria
- Ctrl+Alt+Shift+N lists capture variable names
- Ctrl+Click on a template variable navigates to its capture definition (when in same file)
- Breadcrumbs show meaningful context
