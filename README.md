# ReviewPreference

`ReviewPreference` is a Kotlin class designed to manage the display of app review prompts based on certain conditions in Android applications.

## Overview

In Android apps, it's often useful to prompt users to leave a review after they've used the app a certain number of times. The `ReviewPreference` class simplifies this process by encapsulating the logic for tracking app openings and displaying the review prompt when appropriate.

## Features

- Tracks the number of times the app has been opened.
- Displays a review prompt after a specified number of openings.
- Uses Android's `SharedPreferences` for persistent storage of opening count and review prompt status.
- Integrates with the Play Core library's `ReviewManager` to handle the review flow.

## Usage

1. Add the `ReviewPreference` class to your Android project.
2. Initialize `ReviewPreference` with an instance of your `Activity`.
3. Call the `openTimes` method to track app openings and display the review prompt when necessary.

```kotlin
// Example usage:
val reviewPreference = ReviewPreference(this)
reviewPreference.openTimes(times = 5)
```

## Dependencies
- AndroidX Preference Library
- Play Core Library

## Contributing

Contributions are welcome! If you have any suggestions, bug reports, or feature requests, please open an issue or submit a pull request.
