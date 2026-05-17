# 📚 Flashcard Quiz - Modern Android Learning App

[![Android API](https://img.shields.io/badge/API-24%2B-brightgreen.svg)](https://android-arsenal.com/api?level=24)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

Flashcard Quiz is a polished, feature-rich Android application designed to help users learn and retain information efficiently. It combines modern Android architecture with a delightful user interface to provide a top-tier educational experience.

---

## ✨ Key Features

### 🎨 Premium Visual Experience
- **Custom Branded Splash**: A high-fidelity startup experience featuring 3D stacked cards, linear progress tracking, and professional gradients.
- **Material 3 Design**: Fully compliant with modern Material Design principles for a clean and intuitive look.
- **Dynamic Dark Mode**: A deep-themed UI that adapts to system settings, including specialized dark-mode assets like the search bar.
- **Edge-to-Edge UI**: Seamless layout that intelligently handles system insets (Status Bar & Navigation Bar) for a truly immersive feel.

### 🧠 Advanced Learning Tools
- **3D Card Flip**: An interactive 3D rotation animation when toggling between questions and answers, mimicking real physical cards.
- **Smart Shuffling**: Randomized deck generation for every study session to prevent "positional memory" and enhance recall.
- **Seamless Search**: Real-time filtering of your flashcard deck using a highly responsive search interface.

### 🛠️ Expert Implementation
- **High-Performance Lists**: Leverages `ListAdapter` and `DiffUtil` to ensure minimal CPU usage and smooth 60fps animations during list modifications.
- **Intuitive Gestures**: Integrated **Swipe-to-Delete** for rapid deck management.
- **Robust Persistence**: Powered by **Room Database** with a clean DAO (Data Access Object) pattern for reliable local storage.

---

## 🛠 Tech Stack & Architecture

- **Language:** Java (Core Logic) & Kotlin (Build Scripts)
- **Database:** Room (SQLite abstraction)
- **View Binding:** For type-safe view interaction (Replacing `findViewById`)
- **Animations:** Property Animator & View Property Animator (Custom 3D logic)
- **Concurrency:** Threaded background operations for database safety
- **Layouts:** ConstraintLayout (Complex flat hierarchies) & CoordinatorLayout (Advanced UI behaviors)

---

## 📸 Screenshots

| Splash Screen | Main List | Study Mode |
| :---: | :---: | :---: |
| *(Image Placeholder)* | *(Image Placeholder)* | *(Image Placeholder)* |

> **Note:** To see the actual UI, please refer to the `docs/screenshots` folder (coming soon) or run the app!

---

## 📦 Installation & Setup

1. **Clone the project:**
   ```bash
   git clone https://github.com/YOUR_USERNAME/FlashcardQuiz.git
   ```
2. **Open in Android Studio:**
   - Go to `File > Open` and select the cloned folder.
   - Let Gradle sync finish (usually takes 1-2 minutes).
3. **Run the app:**
   - Select your device/emulator and click the **Run** button.

---

## 🗺️ Roadmap
- [ ] Implement AI-powered card generation.
- [ ] Add Category/Tag support for deck organization.
- [ ] Cloud synchronization (Firebase Integration).
- [ ] Shared deck marketplace.

---

## 📜 License
Distributed under the MIT License. See `LICENSE` for more information.

---
*Developed with ❤️ by Usman Ali*
