# Questify — Gamified Productivity Task Planner

An Android application that turns everyday tasks into **quests**. Completing a quest earns XP, which levels up the user, builds a daily streak, and unlocks achievement badges. Built to make productivity tracking feel rewarding rather than tedious.

---

## Table of Contents

- [Features](#features)
- [Screenshots](#screenshots)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)
- [Gamification Logic](#gamification-logic)
- [Data Storage](#data-storage)
- [Getting Started](#getting-started)
- [Design System](#design-system)
- [Known Limitations](#known-limitations)
- [Future Enhancements](#future-enhancements)

---

## Features

**Onboarding**
- Branded splash screen with gradient background
- Three-page swipeable onboarding carousel explaining the app
- One-time user profile setup (name and email), skipped automatically on subsequent launches

**Quest Management**
- Create quests with a title, description, category, priority, and XP reward
- Categories: Study, Work, Health, Personal, Other
- Priorities: Low, Medium, High
- XP rewards: 10, 20, 30, or 50 XP per quest
- Filter quests by All / Pending / Completed
- Mark quests complete with a single tap

**Gamification**
- XP accumulation with a progressive leveling curve
- Daily streak tracking with automatic reset on missed days
- Six unlockable achievement badges
- Full-screen celebration on quest completion, including level-up and badge-unlock callouts

**Analytics**
- Total quests, completed count, and completion rate
- Quest breakdown by category with visual progress indicators

**Profile**
- Displays saved user name and email
- Current level, total XP, and active streak
- Settings entry points (Edit Profile, Notifications, Appearance, About)

---

## Screenshots

> Add screenshots to a `/screenshots` folder and reference them here.

| <img width="100" height="240" alt="image" src="https://github.com/user-attachments/assets/62491bb0-8df9-4eba-a651-cc7c7f7abc62" />
 | <img width="100" height="240" alt="image" src="https://github.com/user-attachments/assets/3788f330-dd4e-4ddc-8114-b1494d06c6cc" />
 | <img width="100" height="240" alt="image" src="https://github.com/user-attachments/assets/593c62bf-85d2-4a06-bd5e-72a58c395e1b" />
 |

| <img width="100" height="240" alt="image" src="https://github.com/user-attachments/assets/196418f3-7dd9-419b-aa83-865e4940d42a" />
 |<img width="100" height="240" alt="image" src="https://github.com/user-attachments/assets/af0df87e-f644-40ec-b959-82203fc1fc6b" />
 |![Uploading image.png…]()


---

## Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Android Views with XML layouts |
| Primary Layout | ConstraintLayout |
| Lists & Grids | RecyclerView with custom adapters |
| Navigation | Activities + Fragments with BottomNavigationView |
| Paging | ViewPager2 (onboarding carousel) |
| Design Components | Material Components (MaterialCardView, BottomNavigationView) |
| Persistence | SharedPreferences with JSON serialization (`org.json`) |
| Minimum SDK | API 26 (Android 8.0) |
| IDE | Android Studio |

No external backend, database engine, or third-party networking libraries are used — the app is fully offline and self-contained.

---

## Architecture

The app follows a straightforward layered approach:

```
┌─────────────────────────────────────────────┐
│  UI Layer                                   │
│  Activities, Fragments, RecyclerView         │
│  Adapters                                    │
└────────────────┬────────────────────────────┘
                 │
┌────────────────▼────────────────────────────┐
│  Storage / Logic Layer                      │
│  QuestStorage, BadgeStorage                  │
│  (XP math, streak rules, badge conditions)   │
└────────────────┬────────────────────────────┘
                 │
┌────────────────▼────────────────────────────┐
│  Persistence                                │
│  SharedPreferences (JSON-encoded)            │
└─────────────────────────────────────────────┘
```

**Navigation flow:**

```
SplashActivity
      │  (checks "onboarding_complete" flag)
      ├── first launch ──► OnboardingActivity ──► UserSetupActivity ──┐
      │                                                               │
      └── returning user ────────────────────────────────────────────►│
                                                                      ▼
                                                              HomeActivity
                                                        (hosts 5 bottom-nav tabs)
                                                                      │
        ┌──────────────┬──────────────┬──────────────┬────────────────┤
        ▼              ▼              ▼              ▼                ▼
   HomeFragment  QuestsFragment  AnalyticsFragment  RewardsFragment  ProfileFragment

   AddQuestActivity          — launched from Home / Quests
   QuestCompletedActivity    — launched on quest completion
```

---

## Project Structure

```
app/src/main/
├── java/com/dhruti/project_1/
│   │
│   ├── SplashActivity.kt            # Branded splash, routes to onboarding or home
│   ├── OnboardingActivity.kt        # 3-page ViewPager2 carousel
│   ├── OnboardingItem.kt            # Onboarding page data model + page list
│   ├── OnboardingAdapter.kt         # RecyclerView adapter for carousel pages
│   ├── UserSetupActivity.kt         # Name/email capture, saves profile
│   │
│   ├── HomeActivity.kt              # Bottom-nav host, swaps fragments
│   ├── HomeFragment.kt              # Dashboard: greeting, XP, streak, quest list
│   ├── QuestsFragment.kt            # Full quest list with filters
│   ├── AnalyticsFragment.kt         # Stats and category breakdown
│   ├── RewardsFragment.kt           # Badge grid
│   ├── ProfileFragment.kt           # User info and settings rows
│   ├── PlaceholderFragment.kt       # Generic stub fragment
│   │
│   ├── AddQuestActivity.kt          # Quest creation form
│   ├── QuestCompletedActivity.kt    # Celebration screen
│   │
│   ├── Quest.kt                     # Quest data model
│   ├── QuestStorage.kt              # Quest CRUD, XP math, streak logic
│   ├── Badge.kt                     # Badge model + badge definitions
│   ├── BadgeStorage.kt              # Badge unlock rules engine
│   │
│   ├── QuestAdapter.kt              # RecyclerView adapter for quests
│   ├── CategoryAdapter.kt           # RecyclerView adapter for category stats
│   └── BadgeAdapter.kt              # RecyclerView adapter for badge grid
│
└── res/
    ├── layout/
    │   ├── activity_splash.xml
    │   ├── activity_onboarding.xml
    │   ├── item_onboarding_page.xml
    │   ├── activity_user_setup.xml
    │   ├── activity_home.xml
    │   ├── fragment_home.xml
    │   ├── fragment_quests.xml
    │   ├── fragment_analytics.xml
    │   ├── fragment_rewards.xml
    │   ├── fragment_profile.xml
    │   ├── fragment_placeholder.xml
    │   ├── activity_add_quest.xml
    │   ├── activity_quest_completed.xml
    │   ├── item_quest.xml
    │   ├── item_category_row.xml
    │   └── item_badge.xml
    │
    ├── drawable/
    │   ├── bg_splash_gradient.xml   # Purple vertical gradient
    │   ├── bg_card_primary.xml      # Purple rounded card
    │   ├── bg_card_white.xml        # White rounded card with border
    │   ├── bg_input_field.xml       # Rounded input field
    │   ├── bg_success_circle.xml    # Green circle for completion icon
    │   ├── circle_icon_bg.xml       # Light purple circular icon background
    │   ├── dot_active.xml           # Active onboarding dot
    │   └── dot_inactive.xml         # Inactive onboarding dot
    │
    └── menu/
        └── bottom_nav_menu.xml      # 5 bottom navigation items
```

---

## Gamification Logic

### XP and Leveling

Each quest carries an XP reward chosen at creation time (10, 20, 30, or 50 XP). XP required to advance from one level to the next scales linearly:

```kotlin
fun xpForLevel(level: Int): Int = 200 + (level - 1) * 150
```

| Level | XP to next level |
|---|---|
| 1 | 200 |
| 2 | 350 |
| 3 | 500 |
| 4 | 650 |
| 5 | 800 |

On completion, total XP is accumulated and the current level is recalculated by repeatedly subtracting each level's threshold until the remainder no longer covers the next one. The remainder becomes the in-level progress shown on the XP bar.

### Streak Tracking

Streaks are computed by comparing the current day against the last recorded active day, using a day stamp derived from year and day-of-year:

| Condition | Result |
|---|---|
| First ever completion | Streak set to 1 |
| Completed again same day | Streak unchanged |
| Completed the next day | Streak incremented |
| One or more days skipped | Streak reset to 1 |

### Badges

Six badges unlock automatically when their condition is met. Conditions are re-evaluated after every quest completion.

| Badge | Unlock Condition |
|---|---|
| First Quest | Complete 1 quest |
| 7 Day Warrior | Reach a 7-day streak |
| Quest Master | Complete 50 quests |
| XP Hunter | Earn 1000 total XP |
| Level 5 | Reach level 5 |
| Perfect Week | Reach a 7-day streak with at least 7 completions |

Already-unlocked badges are tracked so a badge never re-fires a notification.

---

## Data Storage

All persistence uses a single `SharedPreferences` file named `questify_prefs`.

| Key | Type | Purpose |
|---|---|---|
| `user_name` | String | Display name from setup |
| `user_email` | String | Email from setup |
| `onboarding_complete` | Boolean | Controls splash routing |
| `total_xp` | Int | Lifetime XP earned |
| `level` | Int | Current level |
| `streak` | Int | Current consecutive-day streak |
| `longest_streak` | Int | Best streak achieved |
| `last_active_day` | Int | Day stamp of last completion |
| `quests_json` | String | All quests, JSON-encoded array |
| `unlocked_badges` | StringSet | IDs of unlocked badges |

Quests are serialized to JSON manually via `org.json.JSONArray` / `JSONObject`, keeping the app dependency-free while still supporting structured data.

---

## Getting Started

### Prerequisites

- Android Studio (Ladybug or newer)
- Android SDK with API 34 installed
- An emulator or physical device running Android 8.0 (API 26) or higher

### Setup

1. Clone or download the project.
2. Open Android Studio and select **File → Open**, then choose the project root folder (the one containing `build.gradle.kts`).
3. Allow Gradle to sync — this downloads the required AndroidX and Material dependencies and requires an internet connection on first run.
4. Select a device or emulator from the toolbar dropdown.
5. Click **Run** (or press `Shift + F10`).

### Dependencies

```kotlin
implementation("androidx.core:core-ktx:...")
implementation("androidx.appcompat:appcompat:...")
implementation("com.google.android.material:material:...")
implementation("androidx.constraintlayout:constraintlayout:...")
implementation("androidx.recyclerview:recyclerview:...")
implementation("androidx.viewpager2:viewpager2:1.1.0")
implementation("androidx.fragment:fragment-ktx:...")
```

### Resetting App Data

Because onboarding is skipped after the first successful setup, use one of the following to see the onboarding flow again:

- **Clear app data:** long-press the app icon → App Info → Storage & cache → Clear Storage
- **Reinstall:** uninstall the app, then run it again from Android Studio

---

## Design System

### Color Palette

| Name | Hex | Usage |
|---|---|---|
| Primary | `#7C3AED` | Buttons, XP cards, active states |
| Primary Light | `#A788FA` | Secondary accents |
| Card Stroke | `#EDE9FE` | Card borders, inactive chips |
| Background | `#F8FAFC` | Screen background |
| Text Primary | `#1E1B2E` | Headings and body text |
| Text Secondary | `#6B7280` | Captions and supporting text |
| Success | `#22C55E` | Completion states, XP gains |
| Warning | `#F59E0B` | Streak indicators |
| Error | `#EF4444` | Error states |
| Info | `#3B82F6` | Informational accents |

### Typography Scale

| Style | Size | Weight |
|---|---|---|
| Heading 1 | 24sp | Bold |
| Heading 2 | 20sp | SemiBold |
| Heading 3 | 18sp | Medium |
| Body 1 | 16sp | Regular |
| Body 2 | 14sp | Regular |
| Caption | 12sp | Regular |

---

## Known Limitations

- **Single user only.** The setup screen captures a name and email for display purposes but performs no authentication. All data lives in one shared preferences store, so multiple people using the same install would share the same quests, XP, and badges.
- **No cloud sync or backup.** Uninstalling the app permanently deletes all progress.
- **No due-date reminders.** Quests can be assigned a due date conceptually, but no notification or alarm system is wired up.
- **No quest editing or deletion.** Quests can be created and completed but not modified or removed from the UI.
- **Placeholder iconography.** Several icons use built-in Android system drawables rather than custom artwork.
- **SharedPreferences at scale.** Storing all quests as a single JSON string is simple and dependency-free, but would not scale well to thousands of records; a Room database would be the appropriate upgrade.

---

## Future Enhancements

- Migrate persistence from SharedPreferences to **Room** for proper relational storage and query support
- Add **Firebase Authentication** and **Firestore** to support real multi-user accounts with cloud sync
- Implement **WorkManager**-backed notifications for due-date reminders
- Add edit and delete actions for existing quests
- Extend analytics with weekly and monthly trend charts
- Add dark mode support
- Replace placeholder icons with a custom illustration set

---

## License

This project was built as an educational exercise. Adapt and reuse freely.
