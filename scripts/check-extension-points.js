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
const childProcess = require("child_process");
const fs = require("fs");

const workspace = process.env.GITHUB_WORKSPACE || process.env.PWD;
const findCommand = `find . -path "./packages/*/frontend/*/src/*" -name "*.ts" -o -name "*.tsx"`;
const result = childProcess.execSync(findCommand, { encoding: "utf8" });
const filePaths = result.split(/\r?\n/);

const pathToIgnore = [
  "./packages/core/frontend/sirius-components-core/src/extension/useData.ts",
  "./packages/core/frontend/sirius-components-core/src/extension/ExtensionRegistry.ts",
  "./packages/core/frontend/sirius-components-core/src/extension/useComponents.ts",
  "./packages/core/frontend/sirius-components-core/src/extension/useComponent.ts",
]

// Let's find all the extension points
const extensionPointsFiles = {};
for (let index = 0; index < filePaths.length; index++) {
  const filePath = filePaths[index];

  if (
    fs.existsSync(filePath)
    && !pathToIgnore.includes(filePath)
  ) {
    const file = fs.readFileSync(`${workspace}/${filePath}`, {
      encoding: "utf8",
    });
    if (file.includes(": DataExtensionPoint<") || file.includes(": ComponentExtensionPoint<")) {
      extensionPointsFiles[filePath] = file;
    }
  }
}

const errors = [];

// Let's consider each extension point files one by one
for (let filePath in extensionPointsFiles) {
  console.log(filePath);
  const file = extensionPointsFiles[filePath];

  if (!filePath.endsWith('ExtensionPoints.tsx')) {
    errors.push("The file " + filePath + " should match the pattern XxxExtensionPoints.tsx")
  }

  let tempIndex = file.indexOf('ExtensionPoint<');
  while (tempIndex !== -1) {
    const extensionPointStartIndex = file.lastIndexOf("export const", tempIndex);
    const extensionPointEndIndex = file.indexOf(";", extensionPointStartIndex);
    const extensionPointDeclaration = file.substring(extensionPointStartIndex, extensionPointEndIndex);
    console.log(extensionPointDeclaration);
    console.log();

    if (!extensionPointDeclaration.includes("ExtensionPoint: ")) {
      errors.push("The extension point should be named with the pattern XxxExtensionPoint");
    }

    const endDocumentationIndex = file.indexOf("*/", extensionPointStartIndex - 5);
    if (endDocumentationIndex === -1) {
      errors.push("An extension point in " + filePath + " is missing some documentation");
    }

    tempIndex = file.indexOf('ExtensionPoint<', extensionPointEndIndex + 1);
  }
}

if (errors.length > 0) {
  console.log("The following issues have been found in extension points");
  console.log(errors);
  process.exit(1);
}
