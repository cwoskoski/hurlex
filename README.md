# Hurlex - Hurl Language Support for IntelliJ

An IntelliJ IDEA plugin providing full-featured language support for [Hurl](https://hurl.dev) HTTP testing files.

## Features

- **Syntax highlighting** with customizable colors (Default and Darcula themes)
- **Code completion** for HTTP methods, sections, query keywords, predicates, filters, option keys, and common HTTP headers
- **Structure view** showing request/response entries
- **Code folding** for entries, sections, multiline strings, and JSON bodies
- **Comment/uncomment** support (`#` line comments)
- **Brace matching** for `{}`, `[]`, `{{}}`, and multiline markers
- **Template variable** references and find usages
- **Run configurations** for executing `.hurl` files via the Hurl CLI
- **Gutter run icons** on HTTP method lines for quick execution

## Requirements

- IntelliJ IDEA 2024.1+ (Build 241 - 251.*)
- Java 17+
- [Hurl CLI](https://hurl.dev/docs/installation.html) installed and on your PATH (for running `.hurl` files)

## Installation

### From JetBrains Marketplace

> Coming soon

### From Source

1. Clone the repository:
   ```bash
   git clone git@github.com:cwoskoski/hurlex.git
   cd hurlex
   ```

2. Build the plugin:
   ```bash
   ./gradlew buildPlugin
   ```

3. Install the plugin in IntelliJ:
   - Go to **Settings > Plugins > Gear icon > Install Plugin from Disk...**
   - Select the built plugin zip from `build/distributions/Hurlex-*.zip`

## Development

### Prerequisites

- JDK 17
- IntelliJ IDEA (Community or Ultimate)

### Building

```bash
./gradlew build
```

### Running a Development Instance

```bash
./gradlew runIde
```

This launches a sandboxed IntelliJ instance with the plugin loaded.

### Running Tests

```bash
./gradlew test
```

### Project Structure

```
src/
  main/
    kotlin/         # Plugin source code
      .../language/ # Language, file type, and icon definitions
      .../lexer/    # JFlex lexer definition and token types
      .../parser/   # BNF grammar and parser definition
      .../psi/      # PSI element interfaces and utilities
      .../highlight/ # Syntax highlighter, annotator, color settings
      .../completion/ # Code completion contributor
      .../structure/ # Structure view components
      .../folding/  # Code folding builder
      .../commenter/ # Line comment support
      .../brace/    # Brace matching
      .../reference/ # Template variable references and find usages
      .../run/      # Run configuration, command line state, gutter icons
    gen/            # Generated lexer, parser, and PSI classes
    resources/      # Plugin metadata, icons, color schemes, message bundle
  test/
    kotlin/         # Test classes
    resources/      # Test data files
```

## License

This project is licensed under the Apache License 2.0 - see the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome. Please open an issue to discuss proposed changes before submitting a pull request.
