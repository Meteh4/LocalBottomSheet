# LocalBottomSheet Component

`LocalBottomSheet` is a Jetpack Compose component designed for use with Koin dependency injection and scope management to simplify the creation of bottom sheet screens. It provides a standardized approach for managing `ViewModel` instances within the lifecycle of a bottom sheet, including automatic Koin scope creation and closure.

---

## ✨ Features

- **🔌 Koin Integration**  
  Seamless management of `ViewModel` and other dependencies using [Koin](https://insert-koin.io/).

- **📦 Scope Management**  
  Automatically creates and closes a Koin scope tied to the bottom sheet lifecycle.

- **🧩 Jetpack Compose Support**  
  Declaratively define and manage bottom sheet UI using Jetpack Compose.

---

## 🚀 Usage

Extend from `BottomSheet` and implement your own bottom sheet UI content:

```kotlin
class MyBottomSheetScreen : BottomSheet<MyViewModel>(
    MyViewModel::class.java,
    "my_scope_name"
) {
    @Composable
    override fun SheetContent() {
        // Define the content of the bottom sheet here
    }
}
```
💡 Example Use Case
Use LocalBottomSheet to:

Display dynamic content in a bottom sheet.

Automatically manage ViewModel lifecycle and scope for each sheet.

Create reusable, scoped components using Koin DI.

This is especially useful in multi-screen Compose apps that require modular bottom sheet interactions.

🧰 Installation
1. Add Dependencies
Add Koin and Compose dependencies in your build.gradle (or build.gradle.kts):

Koin
implementation("io.insert-koin:koin-android:3.x.x")
implementation("io.insert-koin:koin-androidx-compose:3.x.x")

Jetpack Compose
implementation("androidx.compose.material:material:<version>")
🔁 Replace 3.x.x and <version> with the latest versions.

2. Configure Koin
Make sure to configure Koin scopes in your Application class or DI module.

🤝 Contributions
Contributions are welcome!
Feel free to:

Open issues

Submit pull requests (PRs)

Suggest improvements

Let's build better bottom sheets together 🚀
