/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 *******************************************************************************/

const core = require("@actions/core");
const github = require("@actions/github");

async function run() {
  try {
    // 1. Get the inputs and context
    const token = process.env.GITHUB_TOKEN;
    const octokit = github.getOctokit(token);
    const context = github.context;

    if (!context.payload.pull_request) {
      core.setFailed("No pull request found.");
      return;
    }

    const owner = context.repo.owner;
    const repo = context.repo.repo;
    const pull_number = context.payload.pull_request.number;

    // 2. Fetch all commits in the Pull Request
    // Note: We use pagination to ensure we get all commits if there are many
    const commitsList = await octokit.paginate(octokit.rest.pulls.listCommits, {
      owner,
      repo,
      pull_number,
      per_page: 100,
    });

    let commentBody = "## 🛡️ Commit Size Audit\n\n";
    commentBody += "| Commit | Lines Modified* | Status | Message |\n";
    commentBody += "| :--- | :---: | :---: | :--- |\n";

    let hasErrors = false;

    // 3. Analyze each commit
    for (const commitStub of commitsList) {
      // We need to fetch the individual commit to get the file breakdown
      const detailedCommit = await octokit.rest.repos.getCommit({
        owner,
        repo,
        ref: commitStub.sha,
      });

      const files = detailedCommit.data.files;
      let linesModified = 0;

      // 4. Calculate size excluding specific files/folders
      files.forEach((file) => {
        const filename = file.filename;

        // Exclusion Logic
        if (filename === "CHANGELOG.adoc") return;
        if (filename.startsWith("doc/")) return;

        linesModified += file.additions + file.deletions;
      });

      // 5. Determine the status message
      const shaShort = commitStub.sha.substring(0, 7);
      let status = "";
      let message = "";
      let icon = "";

      if (linesModified < 300) {
        icon = "✅";
        status = "Perfect";
        message = "The size of this commit is perfect.";
      } else if (linesModified >= 300 && linesModified < 500) {
        icon = "⚠️";
        status = "Good";
        message = "The size is good, though approaching the limit.";
      } else if (linesModified >= 500 && linesModified < 1000) {
        icon = "❌";
        status = "Too Large";
        message =
          "This commit is too large. Please split it into smaller contributions (e.g., separate frontend changes or refactoring).";
        hasErrors = true;
      } else {
        icon = "⛔";
        status = "Rejected";
        message = "Way too big. This will not be reviewed as is.";
        hasErrors = true;
      }

      commentBody += `| ${shaShort} | ${linesModified} | ${icon} ${status} | ${message} |\n`;
    }

    commentBody +=
      "\n_* Lines modified excludes `CHANGELOG.adoc` and the `doc/` folder._";

    // 6. Post the comment to the PR
    // Optional: Delete previous bot comments to keep the history clean
    // (You can remove this block if you want to keep history)
    const comments = await octokit.rest.issues.listComments({
      owner,
      repo,
      issue_number: pull_number,
    });

    const botComment = comments.data.find((comment) =>
      comment.body.includes("🛡️ Commit Size Audit"),
    );

    if (botComment) {
      await octokit.rest.issues.updateComment({
        owner,
        repo,
        comment_id: botComment.id,
        body: commentBody,
      });
    } else {
      await octokit.rest.issues.createComment({
        owner,
        repo,
        issue_number: pull_number,
        body: commentBody,
      });
    }

    // Optional: Fail the build if there are "Too Large" commits
    if (hasErrors) {
      core.setFailed(
        "Some commits are too large. Please check the PR comment for details.",
      );
    }
  } catch (error) {
    core.setFailed(error.message);
  }
}

run();
