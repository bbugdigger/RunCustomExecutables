# Run Custom Executables

<!-- Plugin description -->
An IntelliJ Platform plugin that adds a simple Run Configuration to run custom executables.

A simple Run Configuration plugin to run custom executables with the following features:
- Run predefined executables: Rust Compiler (rustc) or Cargo from PATH
- Run any custom executable from your local filesystem
- Pass arguments to the executable
<!-- Plugin description end -->

## Demo Video

https://github.com/user-attachments/assets/ea7475b9-e85f-4a54-96ee-15d509e37e07

### Features:
- Run predefined executables: Rust Compiler (rustc) or Cargo from PATH
- Run any custom executable from your local filesystem
- Pass arguments to the executable

### Versions and tools I have used for development and testing

- **JDK 21**
- **Gradle 9.2**
- **IntelliJ IDEA** / **RustRover**

## Getting Started

### 1. Clone the Repository

```
git clone https://github.com/bbugdigger/RunCustomExecutables.git
cd RunCustomExecutables
```

### 2. Build the Plugin

#### On Windows (PowerShell):
```
.\gradlew.bat build
```

#### On macOS/Linux:
```
./gradlew build
```

### 3. Install the Plugin in RustRover (or any JetBrains IDE)

1. Open **RustRover** (or IntelliJ IDEA, CLion, etc.)

2. Go to **Settings/Preferences**: `File → Settings → Plugins`

3. Click the **gear icon** next to the search bar

4. Select **"Install Plugin from Disk..."**

5. Navigate to the built plugin file:

6. Click **OK**

## Usage

1. Open any project in RustRover

2. Go to **Run → Edit Configurations...**

3. Click the **+ (Add)** button

4. Select **"Custom Executable"** from the list

5. Configure your run configuration:
   - **Executable**: Choose from:
     - `Rust Compiler (rustc)` - uses rustc from PATH
     - `Cargo` - uses cargo from PATH  
     - `Custom Executable` - browse for any executable file
   - **Executable path**: (shown when "Custom Executable" is selected) Browse to select your executable
   - **Program arguments**: Command-line arguments to pass to the executable

6. Click **Run** or **Debug** to execute

## Development

### Run the Plugin in a Sandbox IDE

To test the plugin during development without installing it:

```
# Windows
.\gradlew.bat runIde

# macOS/Linux
./gradlew runIde
```

This launches a sandboxed instance of IntelliJ IDEA with the plugin loaded.

