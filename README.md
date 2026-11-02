# Mobile Application Development using Android (24CS2021)
**Register Number:** URK23CS1221  

This repository contains all 11 Android laboratory experiments structured as separate Gradle subproject modules within a single project repository. Each folder can be directly accessed or linked on GitHub.

---

## 📁 Repository Structure & Experiment Links

| Exp No. | Experiment Title | Module Folder Link | Key Technologies Used |
|---|---|---|---|
| **Ex 1** | Basic Views & UI Layouts | [`/ex01`](./ex01) | Jetpack Compose, Column, OutlinedTextField, Button, State |
| **Ex 2** | Activity Interaction using Intents & Animation | [`/ex02`](./ex02) | Explicit Intents, Intent Extras, Compose Animations (`fadeIn`, `scaleIn`, `slideInVertically`) |
| **Ex 3** | Employee Announcement (Fragment-Compose Interoperability) | [`/ex03`](./ex03) | Fragment, `ComposeView`, Material 3 Cards, Dynamic UI Types |
| **Ex 4** | Navigate with Data Between Screens | [`/ex04`](./ex04) | Navigation Compose (`NavHost`, `composable`), Nav Arguments |
| **Ex 5** | RecyclerView-like List in Jetpack Compose | [`/ex05`](./ex05) | `LazyColumn`, Material 3 Cards, Reusable List-Item Components |
| **Ex 6** | Data Management using DataStore & Room Database | [`/ex06`](./ex06) | Room Entity/DAO/Database, Preferences DataStore (Dark Mode toggle), Coroutines Flow |
| **Ex 7** | Background Tasks using WorkManager | [`/ex07`](./ex07) | `WorkManager`, `Worker`, `OneTimeWorkRequestBuilder`, Logcat Logging |
| **Ex 8** | Fetching Data from REST API with JSON | [`/ex08`](./ex08) | `HttpURLConnection`, `Dispatchers.IO`, `JSONObject`, `CircularProgressIndicator` |
| **Ex 9** | Firebase Cloud Firestore Integration | [`/ex09`](./ex09) | Firebase BoM, Cloud Firestore Real-time Snapshot Listener, CRUD Operations |
| **Ex 10** | Authentication with Firebase Authentication | [`/ex10`](./ex10) | Firebase Auth, Email/Password Registration, Sign In & Protected Screen |
| **Ex 11** | Testing & Debugging (JUnit & Compose UI Testing) | [`/ex11`](./ex11) | JUnit Unit Testing, Compose UI Testing (`createComposeRule`, `onNodeWithText`) |

---

## 🚀 How to Run in Android Studio

1. **Clone the Repository**:
   ```bash
   git clone https://github.com/your-username/ANDROID_URK23CS1221.git
   ```
2. **Open in Android Studio**: Open the root folder `ANDROID_URK23CS1221` in Android Studio.
3. **Sync Gradle**: Allow Gradle to synchronize dependencies.
4. **Select Module to Run**: In the run configuration dropdown in Android Studio, select any of the experiment modules (`ex01`, `ex02`, ..., `ex11`, or `app`) and click **Run ▶**.
