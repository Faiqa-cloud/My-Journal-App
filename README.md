<div align="center">

# 📓 My Journal App

### *Your Personal Diary — Powered by Firebase*

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Firebase](https://img.shields.io/badge/Backend-Firebase-FFCA28?style=for-the-badge&logo=firebase&logoColor=black)
![Firestore](https://img.shields.io/badge/Database-Firestore-00BFA6?style=for-the-badge&logo=google-cloud&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-0D1F1F?style=for-the-badge)

<br/>

> A full-stack Android journaling application with secure user authentication, real-time cloud storage, and an elegant dark teal UI — built entirely in Java.

<br/>

</div>

---

## ✨ Features

- 🔐 **Secure Authentication** — Email & password signup/login via Firebase Auth
- 📝 **Create Journals** — Write personal entries with title, description & auto-generated date
- 📋 **Journal Dashboard** — View all your journals in a clean RecyclerView list
- 🗑️ **Delete Journals** — Remove entries instantly from Firestore & UI
- 👤 **User Profile** — View your name (Firestore) and email (Firebase Auth)
- 📖 **Journal Detail View** — Tap any journal to read the full content
- ☁️ **Cloud Sync** — All data stored securely in Firebase Firestore
- 🎨 **Elegant Dark UI** — Custom dark teal theme with rounded cards and clean typography

---

## 📱 App Screens

| Signup | Login | Dashboard | Add Journal | Profile |
|:---:|:---:|:---:|:---:|:---:|
| Create account | Secure login | View journals | Write entry | User info |

> **Theme:** Dark background `#0D1F1F` · Teal accent `#00BFA6` · Custom drawables

---

## 🏗️ Project Architecture

```
MyJournalApp/
│
├── java/com/example/myjournalapp/
│   │
│   ├── models/
│   │   ├── User.java           # User data model (name, email, profilePic)
│   │   └── Journal.java        # Journal data model (id, title, desc, date, email)
│   │
│   ├── Signup.java             # Account creation + Firestore user save
│   ├── Signin.java             # Firebase Auth login
│   ├── MainActivity.java       # Dashboard — loads & displays all journals
│   ├── Addjournal.java         # Create & save new journal entries
│   ├── Detail.java             # Full journal content view
│   ├── Profile.java            # User profile — name + email display
│   └── Adapter.java            # RecyclerView adapter for journal list
│
└── res/
    ├── layout/
    │   ├── activity_signup.xml
    │   ├── activity_signin.xml
    │   ├── activity_main.xml
    │   ├── activity_addjournal.xml
    │   ├── activity_detail.xml
    │   ├── activity_profile.xml
    │   └── journal_item.xml     # Single journal card layout
    │
    └── drawable/
        ├── btn_teal.xml         # Solid teal button style
        ├── btn_outline.xml      # Outlined button style
        ├── input_bg.xml         # Dark input field background
        └── card_bg.xml          # Dark card background
```

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| **Java** | Primary programming language |
| **Android SDK** | Mobile app framework |
| **Firebase Authentication** | Email & password user auth |
| **Firebase Firestore** | NoSQL cloud database |
| **RecyclerView** | Efficient scrollable journal list |
| **Intent** | Screen navigation + data transfer |
| **Material Design** | UI components & FloatingActionButton |

---

## 🔥 Firebase Structure

```
Firestore Database
│
├── users/                          # User collection
│   └── {uid}/                      # Document ID = Firebase Auth UID
│       ├── name: "Faiqa"
│       ├── email: "faiqa@email.com"
│       └── profilePic: ""
│
└── journals/                       # Journals collection
    └── {journalId}/                # Auto-generated unique ID
        ├── id: "abc123"
        ├── title: "My First Day"
        ├── description: "Today was amazing..."
        ├── date: "16 May 2025"
        ├── email: "faiqa@email.com"
        └── username: "Faiqa"
```

---

## ⚙️ Getting Started

### Prerequisites

- Android Studio **Hedgehog** or later
- Android SDK **API 24+**
- A Firebase project ([console.firebase.google.com](https://console.firebase.google.com))

### Installation

**1. Clone the repository**
```bash
git clone https://github.com/your-username/MyJournalApp.git
cd MyJournalApp
```

**2. Firebase Setup**
- Go to [Firebase Console](https://console.firebase.google.com) → Create project
- Add Android app → enter package name `com.example.myjournalapp`
- Download `google-services.json` → place in `app/` folder
- Enable **Authentication** → Sign-in method → **Email/Password**
- Enable **Firestore Database** → Start in test mode

**3. Add Dependencies**

In `build.gradle (Module: app)`:
```gradle
dependencies {
    implementation 'com.google.firebase:firebase-auth:22.3.1'
    implementation 'com.google.firebase:firebase-firestore:24.10.3'
}
```

**4. Sync & Run**
- Click **Sync Now** in Android Studio
- Connect device or start emulator
- Run the app ▶️

---

## 💡 Key Implementation Highlights

### Firebase Authentication
```java
// Signup — creates account and saves user to Firestore
auth.createUserWithEmailAndPassword(email, password)
    .addOnSuccessListener(result -> {
        String uid = auth.getCurrentUser().getUid();
        User user = new User(name, email, "");
        db.collection("users").document(uid).set(user);
    });

// Login — verifies credentials
auth.signInWithEmailAndPassword(email, password)
    .addOnSuccessListener(result -> startActivity(new Intent(this, MainActivity.class)));
```

### Firestore — Load User's Journals Only
```java
db.collection("journals")
  .whereEqualTo("email", currentUser.getEmail())
  .get()
  .addOnSuccessListener(snap -> {
      journalList.clear();
      for (QueryDocumentSnapshot doc : snap)
          journalList.add(doc.toObject(Journal.class));
      adapter.notifyDataSetChanged();
  });
```

### Delete from Firestore + UI
```java
db.collection("journals").document(journal.getId()).delete()
  .addOnSuccessListener(u -> {
      journalList.remove(position);
      notifyItemRemoved(position);
  });
```

---

## 📐 Design System

| Token | Value | Usage |
|---|---|---|
| Background | `#0D1F1F` | All screen backgrounds |
| Card | `#122929` | Journal cards, input fields |
| Accent Teal | `#00BFA6` | Buttons, titles, highlights |
| Mid Teal | `#00696A` | Borders, dividers |
| Text Primary | `#FFFFFF` | Headings |
| Text Secondary | `#B0BEC5` | Subtitles, hints |
| Danger | `#FF5252` | Delete button |

---

## 📋 Firestore Security Rules

```javascript
rules_version = '2';
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

---

## 🚀 Future Enhancements

- [ ] Edit journal entries
- [ ] Search & filter journals
- [ ] Image attachments in journals
- [ ] Biometric / fingerprint login
- [ ] Dark / Light mode toggle
- [ ] Export journals as PDF

---

## 👩‍💻 Developer

<div align="center">

**Faiqa**
 — Mobile Application Development

[![GitHub](https://img.shields.io/badge/GitHub-Follow-0D1F1F?style=for-the-badge&logo=github)](https://github.com/your-username)

*Built with ❤️ and lots of Firebase debugging*

</div>

---

## 📄 License

```
MIT License — feel free to use, modify and learn from this project.
```

---

<div align="center">

**⭐ If this project helped you, please give it a star!**

*Made with Java · Firebase · Android Studio*

</div>
