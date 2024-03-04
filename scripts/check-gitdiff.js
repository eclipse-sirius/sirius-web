/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
const gitDiffCommand = `git status --porcelain`;
let diffMessage = childProcess.execSync(gitDiffCommand, {
  encoding: "utf8",
});

diffMessage = diffMessage.trimEnd();
const diffMessageLines = diffMessage.split(/\r?\n/);

if (diffMessageLines.length == 1) {
  if (diffMessageLines[0] == " M .npmrc") {
    console.log("All files have been added to the commit");
  }
} else if (diffMessageLines.length > 0) {
  console.log("The following files should be added to the commit");
  diffMessageLines.forEach((line) => {
    if (line != " M .npmrc") {
      console.log(line);
    }
  });
  process.exit(1);
} else {
  console.log("All files have been added to the commit");
}
