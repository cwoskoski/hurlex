# Publishing to JetBrains Marketplace

This document describes how to set up automated publishing for the Hurlex plugin.

## Prerequisites

### 1. JetBrains Marketplace Token

1. Go to [JetBrains Marketplace](https://plugins.jetbrains.com/)
2. Log in and navigate to your profile
3. Go to **My Tokens** (under the hub icon)
4. Click **Generate Token**
5. Save the token — you'll need it for the `PUBLISH_TOKEN` secret

### 2. Plugin Signing Keys

Plugin signing is required for JetBrains Marketplace. Generate a key pair using the marketplace ZIP signer tool:

```bash
# Download the marketplace-zip-signer CLI
curl -L -o marketplace-zip-signer.jar \
  https://github.com/JetBrains/marketplace-zip-signer/releases/latest/download/marketplace-zip-signer-cli.jar

# Generate a key pair
java -jar marketplace-zip-signer.jar generate-keys \
  -priv-key-file private.pem \
  -cert-file chain.crt \
  -password "your-password-here"
```

This produces:
- `chain.crt` — the certificate chain (for `CERTIFICATE_CHAIN`)
- `private.pem` — the private key (for `PRIVATE_KEY`)

### 3. GitHub Secrets

Add the following secrets to your repository (**Settings > Secrets and variables > Actions**):

| Secret | Description |
|---|---|
| `PUBLISH_TOKEN` | JetBrains Marketplace API token |
| `CERTIFICATE_CHAIN` | Contents of `chain.crt` |
| `PRIVATE_KEY` | Contents of `private.pem` |
| `PRIVATE_KEY_PASSWORD` | Password used when generating the key pair |

## Triggering a Release

1. Update `CHANGELOG.md` with the new version's changes
2. Create and push a version tag:

```bash
git tag v0.2.0
git push origin v0.2.0
```

The `release.yml` workflow will:
- Extract the version from the tag (e.g., `v0.2.0` becomes `0.2.0`)
- Build the plugin with that version
- Run tests and verify the plugin
- Sign the plugin
- Create a GitHub Release with the changelog
- Publish to JetBrains Marketplace

## Local Development

For local development, the version comes from `gradle.properties` (`pluginVersion`). The `VERSION` environment variable is only set in CI.

To test the version override locally:

```bash
VERSION=0.2.0 ./gradlew patchPluginXml
```

Then inspect `build/patchedPluginXmlFiles/plugin.xml` to verify the version and changelog.
