# SHA-256 Hash Calculator

Simply Java version of https://github.com/B-Con/crypto-algorithms

A simple Java implementation of the SHA-256 cryptographic hash function.

## Features

- Pure Java implementation of SHA-256 algorithm
- Interactive command-line interface for hashing text
- Comprehensive test suite with known test vectors
- No external dependencies

## Files

- `Main.java` - Interactive command-line application
- `SHA256.java` - SHA-256 implementation
- `Test.java` - Test suite with known test vectors
- `README.md` - This documentation

## How to Run

### Compile the project:
```bash
javac *.java
```

### Run the interactive application:
```bash
java Main
```

### Run the test suite:
```bash
java Test
```

## Usage

### Interactive Mode
When you run `java Main`, you'll get an interactive prompt where you can:
- Enter any text to get its SHA-256 hash
- Type 'quit' to exit the program

Example:
```
=== SHA-256 Hash Calculator ===
Enter text to hash (or 'quit' to exit):
> Hello, World!
Input: Hello, World!
SHA-256: dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f

> quit
Goodbye!
```

### Test Suite
The test suite validates the implementation against known SHA-256 test vectors:
- "abc" → ba7816bf8f01cfea414140de5dae2223b00361a396177a9cb410ff61f20015ad
- "" (empty string) → e3b0c44298fc1c149afbf4c8996fb92427ae41e4649b934ca495991b7852b855
- "Hello, World!" → dffd6021bb2bd5b0af676290809ec3a53191dd81c7f70a4b28688a362182986f

## Implementation Details

The SHA-256 implementation follows the official specification:
- Uses the standard SHA-256 constants and initial hash values
- Implements all required functions (CH, MAJ, EP0, EP1, SIG0, SIG1)
- Properly handles message padding and length encoding
- Produces 256-bit (32-byte) hash values

## Requirements

- Java 8 or higher
- No external libraries required

## License

This is a simple educational implementation. Feel free to use and modify as needed. 