# Android Calculator Demo

A modern Android calculator app built with MVVM architecture, featuring a sleek dark theme and material design elements.

## Features

- Basic arithmetic operations (+, -, ×, ÷)
- Special functions (Clear, +/-, %)
- Dark theme with orange operation buttons
- Material design with ripple effects
- Error handling for invalid operations
- Real-time display updates

## Technical Details

- **Architecture**: MVVM (Model-View-ViewModel)
- **Minimum SDK**: 28 (Android 9.0)
- **Target SDK**: 35
- **Language**: Java
- **Dependencies**:
  - AndroidX Lifecycle (ViewModel, LiveData)
  - Material Design Components

## Project Structure

- `app/src/main/java/com/example/calculatordemo/`
  - `MainActivity.java`: UI controller
  - `CalculatorViewModel.java`: Business logic and state management
  - `Calculator.java`: Core calculation engine

## Testing

The project includes comprehensive unit tests for both the Calculator and CalculatorViewModel classes, covering:
- Basic arithmetic operations
- Input validation
- Error handling
- Edge cases

## Building and Running

1. Clone the repository
2. Open in Android Studio
3. Build and run on an emulator or physical device

## License

This project is open source and available under the MIT License.
