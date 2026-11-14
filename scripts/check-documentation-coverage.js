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
const childProcess = require("child_process");

const event = process.env.GITHUB_EVENT;

const body = JSON.parse(event);
const baseSHA = body.pull_request.base.sha;
const headSHA = body.pull_request.head.sha;

const sourceFileExtensions = [".java", ".js", ".ts", ".tsx"];
const diff = childProcess.execFileSync(
  "git",
  ["diff", "--unified=0", `${baseSHA}...${headSHA}`],
  { encoding: "utf8" },
);

let changedFilePath = "";
let addedCodeLines = 0;
let addedDocumentationLines = 0;

for (const line of diff.split(/\r?\n/)) {
  if (line.startsWith("+++ b/")) {
    changedFilePath = line.substring("+++ b/".length);
  } else if (line.startsWith("+++ ")) {
    changedFilePath = "";
  } else if (line.startsWith("+") && line.trim().length > 1) {
    if (changedFilePath.startsWith("doc/content/")) {
      addedDocumentationLines++;
    } else if (
      sourceFileExtensions.some((extension) =>
        changedFilePath.endsWith(extension),
      )
    ) {
      addedCodeLines++;
    }
  }
}

const requiredDocumentationLines = Math.max(10, Math.ceil(addedCodeLines / 20));

console.log(`Added code lines: ${addedCodeLines}`);
console.log(`Added documentation lines: ${addedDocumentationLines}`);
console.log(`Required documentation lines: ${requiredDocumentationLines}`);

if (addedDocumentationLines < requiredDocumentationLines) {
  console.log(
    `This pull request must add at least ${requiredDocumentationLines} non-blank documentation lines under doc/content. It currently adds ${addedDocumentationLines}.`,
  );
  process.exit(1);
}
