# HLEX-009: Build Pipeline and Marketplace Distribution

## Type
DevOps

## Priority
Medium

## Description
The CI pipeline builds and tests but lacks plugin verification, signing, and publishing steps needed for JetBrains Marketplace distribution.

## Tasks
- [ ] Add IntelliJ Plugin Verifier to CI workflow to check API compatibility
- [ ] Configure plugin signing (certificate and key) for Marketplace submissions
- [ ] Add a publish workflow (manual trigger) to push releases to JetBrains Marketplace
- [ ] Add multi-version compatibility testing (241, 242, 243, 251)
- [ ] Add `.editorconfig` for consistent code style in the project source
- [ ] Evaluate whether generated code in `src/main/gen` should be gitignored (regenerated on build)
- [ ] Add release automation (tag-based versioning, GitHub Releases)

## Acceptance Criteria
- CI runs plugin verifier on every PR
- A tagged release triggers Marketplace publishing
- Plugin passes verification for all supported IntelliJ versions
