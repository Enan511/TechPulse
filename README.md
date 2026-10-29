# TechPulse: Multi-Platform Electronics Price Aggregator and Search Analytics Engine

COMP 8547 Advanced Computing Concepts, Fall 2026, **Group 7**

TechPulse is a Java console app. It crawls five Canadian PC-hardware stores (GPU, CPU, RAM and SSD listings),
saves the products as CSV, and lets the user search them with our own data structures: an inverted index,
page ranking, spell checking, word completion, word and search frequency, regex validation and extraction,
and a cross-store "cheapest deal" comparator.

| Member ---------- Crawler ----------- Feature 1 ---------- Feature 2 |

| Mohammad Enan Al Harun Sahan ----- Canada Computers -------- Inverted indexing ----- Page ranking |
| Sheikh MD. Nafis Noor Nabil ------------- PccZone ------------- Spell checking ------------- Search frequency |
| Aditya Mahmud ----------------- Vuugo ------------------ Word completion ----------------- Frequency count |
| Nikhil Mahara ----------------- PC-Canada --------------- Data validation (regex) ------------------ Deal comparator + app integration |
| Tengi Sherpa -------------- IT Hardware Hub -------------- HTML parser --------------- Finding patterns (regex) |

**Your exact files, what to build first and who waits on whom: [docs/TEAM_TASKS.md](docs/TEAM_TASKS.md).**

---

## 1. Install these once

| What | Where | Check it worked |
|---|---|---|
| **JDK 21** (any JDK 17+ works) | https://adoptium.net/temurin/releases?version=21&os=any&arch=any → (mac or)Windows .msi. It installs as *Eclipse Temurin JDK with Hotspot 21.x* (that's correct; "Eclipse" is the foundation, not the IDE). On the **Custom Setup** screen the options are rows in a list, not checkboxes: *Add to PATH* is on by default; *Set JAVA_HOME variable* is off (red ✗), so click its icon → **Will be installed on local hard drive**. Missed it? See the JAVA_HOME note below | new terminal: `java -version` shows 21 |
| **Git** | https://git-scm.com/downloads (keep the default options) | `git --version` |
| **VS Code** | https://code.visualstudio.com | |
| **Extension Pack for Java** (by Microsoft) | VS Code → Extensions (Ctrl+Shift+X) → search *Extension Pack for Java* → Install | a "Java" section appears in the Explorer |
| Google Chrome | only if your crawler uses Selenium | |

You do **not** need to install Maven. The Java extension reads `pom.xml` and downloads Jsoup, Selenium and
JUnit by itself the first time you open the project.

**JAVA_HOME not set?** VS Code finds the JDK without it, but Maven and some other tools look for it. Set it once
(check the exact folder name in `C:\Program Files\Eclipse Adoptium\`), then reopen VS Code:

```
setx JAVA_HOME "C:\Program Files\Eclipse Adoptium\jdk-21.0.12.101-hotspot"
```

Tell Git who you are (once per computer, use the email of your GitHub account):

```
git config --global user.name "Your Name"
git config --global user.email "you@example.com"
```

## 2. Get the project into VS Code

1. Accept the GitHub invitation email (or open https://github.com/notifications).
2. In VS Code press **Ctrl+Shift+P** → type **Git: Clone** → paste the repo link → choose a folder
   (e.g. `Documents\COMP8547`) → **Open** when it asks. Sign in to GitHub in the browser window if it asks.
3. Click **Yes, I trust the authors**.
4. Wait about 1–3 minutes the first time. The status bar shows *"Java: Importing…"* and then a 👍 / *"Java: Ready"*.
   This is when the libraries are downloaded.

## 3. Run it

- **Easiest:** open `src/main/java/ca/uwindsor/techpulse/TechPulseApp.java` and click **Run** (the small
  link above `public static void main`).
- Or go to **Run and Debug** (Ctrl+Shift+D) → pick **Run TechPulse** → ▶. You type your menu choices in the **Terminal** panel.
- Breakpoints work the same way: click left of a line number, then use **Debug** instead of Run.

With no crawled data yet, the app loads `data/sample/sample_products.csv` (16 made-up listings) so every
feature can be tried straight away. Until the features are written, the menu answers "No products matched", which is expected.

**Run the tests:** click the flask icon (**Testing**) on the left bar → ▶ Run All Tests.

(Optional, only if you installed Maven: `mvn compile exec:java` runs the app, `mvn test` runs the tests.)

## 4. Project map

```
TechPulse/
├── pom.xml                       libraries (Jsoup, Selenium, JUnit). Ask Nikhil before changing it
├── data/
│   ├── sample/sample_products.csv   fake data for development (don't present it as real)
│   ├── products/<store>.csv         real crawled data, one file per store (committed)
│   └── raw/                         saved HTML pages for debugging (NOT committed)
├── docs/TEAM_TASKS.md            who does what + dependencies + timeline
├── docs/GITHUB_SETUP.md          how the repo was created (for Nikhil)
├── scripts/make-txt-copies.ps1   makes the .java + .txt files for Brightspace
└── src/
    ├── main/java/ca/uwindsor/techpulse/
    │   ├── TechPulseApp.java     console menu that connects everything
    │   ├── model/                Product (shared data shape)
    │   ├── crawler/              StoreCrawler interface + one crawler per store
    │   ├── storage/              ProductCsv (save/load CSV)
    │   ├── parser/               HtmlParser
    │   ├── regex/                PatternExtractor, InputValidator
    │   ├── index/                InvertedIndex, Posting, WordFrequencyCounter
    │   ├── search/               PageRanker, SpellChecker, WordCompletionTrie, SearchTracker
    │   └── deals/                DealComparator
    └── test/java/ca/uwindsor/techpulse/   one test class per feature (same folders)
```

Every file's header says `OWNER:`. **Only edit files you own.** If you need a change in someone else's file or
in a shared file (`Product`, `StoreCrawler`, `ProductCsv`, `TechPulseApp`, `pom.xml`), ask in the group chat.

## 5. Daily Git workflow in VS Code

We never work directly on `main`. Each person works on their own branch and opens a Pull Request (PR).

**Once, at the start of a feature**
1. Click the branch name in the bottom-left status bar (shows `main`) → **+ Create new branch…** →
   name it `feature/<yourname>-<feature>`, e.g. `feature/aditya-trie`.

**While working (as often as you like)**
2. **Source Control** panel (Ctrl+Shift+G) → check the list only has *your* files → type a message
   ("Add DFS to word completion") → **Commit** (say *Yes* if it asks to stage all changes).
3. **Sync Changes** / **Publish Branch** uploads your commits to GitHub.

**When a piece works**
4. On GitHub, open the repo → **Compare & pull request** → base `main` ← your branch → describe what you did → **Create pull request**.
5. Nikhil (or another member) reviews and clicks **Merge**.

**Get everyone else's latest work into your branch** (do this every few days)
6. Bottom-left → switch to `main` → **Sync** (pulls the latest) → switch back to your branch →
   **Ctrl+Shift+P → Git: Merge…** (called *Merge Branch…* in older VS Code) → `main`.
7. If VS Code shows a **merge conflict**, open the file, pick *Accept Current / Incoming / Both* for each
   block, save, commit. If the conflict is in someone else's file, ask them first.

Rules: pull before you start working; don't commit `target/`, `bin/` or Eclipse/IntelliJ files (the
`.gitignore` already skips them); never force-push.

## 6. Using Eclipse instead (if someone needs to)

The instructor's examples use Eclipse, and this repo works there too: **File → Import → Maven → Existing Maven
Projects** → pick the cloned `TechPulse` folder → Finish. Do Git from VS Code or GitHub Desktop so we all
use the same workflow.

## 7. Troubleshooting

| Problem | Fix |
|---|---|
| Red underlines on every `import`, or "cannot be resolved" | Wait for *Java: Ready*. Still red → Ctrl+Shift+P → **Java: Clean Java Language Server Workspace** → Restart |
| "Java runtime could not be located" / wrong Java version | Install JDK 21, then Ctrl+Shift+P → **Java: Configure Java Runtime** and pick it; restart VS Code |
| No **Run** link above `main` | The Extension Pack for Java isn't installed or is still loading |
| App says "No crawled data … using the sample data" | Normal until crawlers save files into `data/products/` |
| Jsoup gets HTTP 403 / "Just a moment…" page | The store blocks bots (Cloudflare). Use Selenium for that store, as in Assignment 1 (Selenium downloads ChromeDriver itself) |
| `SessionNotCreatedException` (Selenium) | Update Chrome, delete `C:\Users\<you>\.cache\selenium`, run again |
| Git asks for a password | Use the browser sign-in pop-up; GitHub doesn't accept account passwords in Git |
| Push rejected ("fetch first") | **Sync** (pull) first, then push again |

## 8. Submission (Brightspace)

The course wants every Java file as **both `.java` and `.txt`, and no zip**. When the code is final, open the VS Code terminal
in the project folder and run:

```
.\scripts\make-txt-copies.ps1
```

All files appear in `submission/`. Upload them together with the report and the slides.
