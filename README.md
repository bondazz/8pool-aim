# 8 Ball Pool Aim Assist

This project is an Android application that provides an overlay with trajectory prediction for 8 ball pool games.

## Implementation Details

- **Overlay**: Uses `WindowManager` with `TYPE_APPLICATION_OVERLAY`.
- **Screen Capture**: Uses `MediaProjection` API for real-time frame analysis without memory injection.
- **Physics**: Implements $incident\_angle = reflection\_angle$ for wall bounces.
- **Image Processing**: Designed to use OpenCV for detecting the cue ball and aim direction.

## How to Build

1. **Install Android Studio**.
2. **Open this project** in Android Studio.
3. **OpenCV Integration**:
   - Download the OpenCV Android SDK.
   - Import the OpenCV module into your project.
   - Add `implementation(project(":opencv"))` to `app/build.gradle.kts`.
   - Uncomment the OpenCV code in `CVProcessor.kt`.
4. **Permissions**: The app will request Overlay and Screen Capture permissions on first run.

## Anti-Ban Security

- **Safe Capture**: Uses standard MediaProjection API.
- **No Memory Access**: Does not read or write to game RAM or `.so` files.
- **Stealth**: Minimal logcat output.

## Disclaimer

This project is for educational purposes only. Use of aim assistants may violate the terms of service of various games.
