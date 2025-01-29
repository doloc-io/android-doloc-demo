# Demo for using doloc.io with Android

This is a simple demo for using [doloc.io](https://doloc.io) with Android.

It is recommended to follow our dedicated guide for [Android](https://doloc.io/getting-started/frameworks/android/) to get started.


## Pre-requisites

To update the translations, you need to set the `API_TOKEN` environment variable to your doloc.io API token.
You can get a free API token by signing up at [doloc.io](https://doloc.io/account).

```bash
export API_TOKEN=YOUR_API_TOKEN
```

## Running the demo

This demo can be run on an Android emulator or a physical device.

To get started you might use [Android Studio](https://developer.android.com/studio).
There you can easily run the demo in an emulator. 

Generally, the Android runtime selects the language based on the device's language settings.
For testing different locales, please refer to [Android Localization Testing](https://developer.android.com/guide/topics/resources/localization#testing)

## Adding translations

You can add new strings manually to `app/src/main/res/values/strings.xml` or use the resource editor included in Android Studio.
If you use the integrated editor, make sure to _not_ add the new string resource to translated files (`app/src/main/res/values-de/strings.xml` and `app/src/main/res/values-fr/strings.xml`).

An example could be:

```xml
<string name="your_id">some new string</string>
```

After adding the new message, you need to run doloc to translate and add it to the translated files.
There are two alternative ways to do that:

- Local workflow
- CI workflow

### Local workflow

Run the following command to extract the new message:

```bash
export API_TOKEN=YOUR_API_TOKEN
gradle update-i18n
```

Observe that the new message is added to `app/src/main/res/values-de/strings.xml` 
and `app/src/main/res/values-fr/strings.xml` already is translated!

### CI workflow

If you are using the CI workflow, you can simply push your changes to the repository.
Check out the workflow definition in `.github/workflows/localization.yml` to see how the translations are updated.

When your change is merged into the main branch, the translations will be updated automatically and a new commit will be created with the updated translations.

**Note:** This workflow expects the `DOLOC_API_TOKEN` to be set as a [secret](https://docs.github.com/en/actions/security-for-github-actions/security-guides/using-secrets-in-github-actions) in your repository.