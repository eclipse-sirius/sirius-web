/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
const payload = process.env.PAYLOAD;
console.log(payload);

const body = JSON.parse(payload);

const {
  repository: { pullRequest },
} = body;
const { milestone, closingIssuesReferences } = pullRequest;

let hasError = false;

// Check milestone consistency
if (!milestone || !milestone.title) {
  hasError = true;
  console.log("The pull request has not been assigned to a milestone");
}

for (const issue of closingIssuesReferences.nodes) {
  if (!issue.milestone || !issue.milestone.title) {
    hasError = true;
    console.log(`The linked issue #${issue.number} is missing a milestone`);
  }

  if (milestone.title !== issue.milestone?.title) {
    hasError = true;
    console.log(
      `The milestone of the pull request does not match the milestone of the linked issue #${issue.number}`
    );
  }

  const { labels } = issue;
  const hasAtLeastOnePackageLabel =
    labels.nodes.filter((label) => label.name.startsWith("package:")).length >=
    1;
  const hasOneTypeLabel =
    labels.nodes.filter((label) => label.name.startsWith("type:")).length === 1;
  const hasOneDifficultyLabel =
    labels.nodes.filter((label) => label.name.startsWith("difficulty:"))
      .length === 1;

  if (
    !hasAtLeastOnePackageLabel ||
    !hasOneTypeLabel ||
    !hasOneDifficultyLabel
  ) {
    hasError = true;
    console.log(
      `The issue #${issue.number} is either lacking the "package:", "type:", or "difficulty:" label`
    );
  }
}

if (hasError) {
  process.exit(1);
}
