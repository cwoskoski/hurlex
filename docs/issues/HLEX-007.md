# HLEX-007: Improve Run Configuration and Hurl CLI Integration

## Type
Enhancement

## Priority
High

## Description
The run configuration is functional but missing several features for a complete developer experience when running Hurl files.

## Tasks
- [ ] Add environment variable support to run configuration UI and command builder
- [ ] Add `--test` mode toggle (run as test vs. normal execution)
- [ ] Add `--verbose` / `--very-verbose` toggle in run config UI
- [ ] Parse console output for assertion failures and create clickable file:line links
- [ ] Detect if `hurl` binary is installed and on PATH:
  - Show notification with install instructions if missing
  - Add "Hurl executable" path setting in plugin preferences
- [ ] Add `--glob` support for running multiple `.hurl` files
- [ ] Add `--report-html` option for generating test reports
- [ ] Support `--variable` flags (individual variable key=value pairs)

## Acceptance Criteria
- Run configuration editor shows all new options
- Missing `hurl` binary shows a helpful notification
- Assertion failures in console output are clickable
- Environment variables are passed to the `hurl` process
