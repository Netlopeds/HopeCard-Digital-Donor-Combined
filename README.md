## Open & run (Android Studio)
1. Open Android Studio.
2. **File → Open…**
3. Select this folder: `Donor - HopeCard/Donor - Unified/`
4. Let Gradle sync finish.
5. Run the **app** configuration.

Android Studio will automatically set your Android SDK path and write it into `local.properties`.

## If you get “SDK location not found”
You don’t have an Android SDK configured on this machine.

Fix options:
- Install Android Studio and, during setup, install the **Android SDK + Platform Tools**.
- In Android Studio: **Settings → Appearance & Behavior → System Settings → Android SDK** and install at least one SDK platform.

Once installed, Android Studio will update `local.properties` with:

`sdk.dir=...`

## Gradient buttons
All primary buttons share the same gradient drawable:
- `app/src/main/res/drawable/bg_primary_button_gradient.xml`

## Navigation
Entry point:
- `com.example.hopecard.ui.login.LoginActivity`

Screen flows:
- Login → Sign-Up
- Login → Forgot Password → OTP Verify → Reset Password
- Login → Edit Profile (demo target after successful validation)

## Backend integration TODOs
Search for `TODO(BACKEND)` in Kotlin files. Key insertion points:
- `ui/login/LoginActivity.kt` (authenticate + store session)
- `ui/signup/SignUpActivity.kt` (register + upload IDs)
- `ui/forgot/*` (request OTP / verify OTP / reset password)
- `ui/profile/EditProfileActivity.kt` (load + update profile, upload photo)

