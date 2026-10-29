# Putting TechPulse on GitHub (for Nikhil, one time)

The starter project is already complete in `finalProject/TechPulse/`. These steps publish it, invite the group,
and protect `main`. About 15 minutes.

## 1. Before you start

- Install **JDK 21**, **Git**, **VS Code** and the **Extension Pack for Java** (README §1).
- Open a new terminal and check:
  ```
  java -version
  git --version
  ```
- Tell Git who you are (use your GitHub email):
  ```
  git config --global user.name "Nikhil Mahara"
  git config --global user.email "<your GitHub email>"
  ```

## 2. Check that the project runs

1. VS Code → **File → Open Folder…** → `D:\uwindsor\2026-Fall\Applied Computing\finalProject\TechPulse`
   (open the **TechPulse** folder itself, not `finalProject`).
2. Trust the folder, wait for *Java: Ready*.
3. Open `TechPulseApp.java` → **Run**. You should see `Products loaded: 16` and the menu. Type `0` to exit.
4. Testing panel (flask icon) → Run All → `ProductCsvTest` has 3 green tests.

## 3. Create the empty repository on GitHub

1. https://github.com/new
2. **Repository name:** `TechPulse` (or `COMP8547-Group7-TechPulse`)
3. **Private** (our code shouldn't be visible to other sections; plagiarism rules).
4. **Do not** tick *Add a README*, *.gitignore* or *license*. We already have them, and ticking them causes a
   conflict on the first push.
5. **Create repository**. Keep the page open; you need the URL (`https://github.com/<you>/TechPulse.git`).

## 4. First commit and push from VS Code

1. In VS Code (with the TechPulse folder open): **Source Control** (Ctrl+Shift+G) → **Initialize Repository**.
2. You'll see ~40 files. Check that there is **no** `target/`, `bin/` or `.settings/` in the list (the `.gitignore` handles it).
3. Message: `Initial project setup` → **Commit** → answer **Yes** to "stage all changes".
4. Click **Publish Branch** → choose **Publish to GitHub private repository**… *or*, since you already created
   the repo in step 3, use the terminal instead:
   ```
   git branch -M main
   git remote add origin https://github.com/<you>/TechPulse.git
   git push -u origin main
   ```
   A browser window asks you to sign in to GitHub the first time.
5. Refresh the GitHub page: the files and the README should be visible.

## 5. Invite the group

Repo → **Settings → Collaborators → Add people** → add each member's GitHub username or email:

- Mohammad Enan Al Harun Sahan
- Sheikh MD. Nafis Noor Nabil
- Aditya Mahmud
- Tengi Sherpa

They must **accept the invitation email** before they can push.

## 6. Protect `main` (recommended)

Repo → **Settings → Branches → Add branch ruleset** (or *Add classic branch protection rule*):

- Branch name pattern / target: `main`
- ✅ **Require a pull request before merging** → required approvals: **1**
- ✅ **Block force pushes**
- Save.

Now nobody can break `main` by accident. All work arrives through Pull Requests (README §5).
(Free private repos may only support rulesets on some plans. If GitHub says it isn't available, skip this step
and just agree in the group to always use PRs.)

## 7. Message for the group chat

> Repo is up: <link>. Accept the GitHub invite, then follow README.md (install + clone + run).
> Your files and the order of work are in docs/TEAM_TASKS.md, so check your section.
> **Tengi:** `HtmlParser.tokenize`, `PatternExtractor.parsePrice` and `extractModelCode` are needed by
> everyone, so please do those first.
> Everyone: work on a branch `feature/<name>-<feature>` and open a Pull Request. Never push straight to main.

## 8. Your job as repo owner afterwards

- Review and merge PRs (check that the app still starts and the tests are green).
- Keep the shared files (`Product`, `StoreCrawler`, `ProductCsv`, `TechPulseApp`, `pom.xml`) consistent; announce any change.
- Before submission: everything merged → run `scripts/make-txt-copies.ps1` → upload the files from `submission/`.
