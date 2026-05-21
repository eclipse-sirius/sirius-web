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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * Used to test the size of pull requests.
 *
 * <p>
 *     This test will be used to evaluate the size of a pull request in GitHub. For that it will use the following rules
 * </p>
 * <ul>
 *     <li>It will ignore the generated code of the various metamodels</li>
 *     <li>It will treat deleted files as a single line. One should not be penalized for removing outdated code</li>
 *     <li>It will ignore tests for now. This may change in the future</li>
 *     <li>It will consider lines added and removed equally everywhere</li>
 * </ul>
 *
 * @author sbegaudeau
 */
public class PullRequestSizeChecker {

    public static void main(String[] args) {
        var baseCommit = args[0];
        var headCommit = args[1];

        try {
            var processBuilder = new ProcessBuilder("git", "log", "-p", baseCommit + ".." + headCommit);
            processBuilder.redirectErrorStream(true);
            var process = processBuilder.start();

            var lineCount = PullRequestSizeChecker.getContributionLineCount(process);

            var exitCode = process.waitFor();

            PullRequestSizeChecker.publishResult(lineCount, exitCode);
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
        }
    }

    private static void publishResult(long lineCount, int processExitCode) {
        System.out.println("## PR Size Report\n");
        System.out.println("**Total modified lines (adjusted):** " + lineCount + "\n");
        if (lineCount <= 100) {
            System.out.println("### Perfect\nThis PR is tiny and focused. It will be incredibly easy to review!");
        } else if (lineCount <= 300) {
            System.out.println("### Good\nThis PR is a very reasonable size. Good job keeping it manageable.");
        } else if (lineCount <= 500) {
            System.out.println("### Getting Large\nThis PR is quite large. Make sure you've provided excellent documentation and context for the reviewers.");
        } else {
            System.out.println("### Too Long\nThis PR is too large (over 500 lines). Review fatigue is highly likely. Consider breaking this down into smaller, standalone Pull Requests if possible.");
        }

        System.exit(processExitCode);
    }

    private static long getContributionLineCount(Process process) {
        long lineCount = 0;

        try (var reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            boolean inDiff = false;
            boolean skipCurrentFile = false;
            boolean currentFileDeleted = false;

            String line = reader.readLine();
            while (line != null) {
                if (line.startsWith("commit ")) {
                    // Detect the start of a new commit
                    inDiff = false;
                } else if (line.startsWith("diff --git ")) {
                    // Detect the start of a new file diff
                    inDiff = true;
                    skipCurrentFile = PullRequestSizeChecker.shouldIgnoreFile(line);
                    currentFileDeleted = false;
                } else if (inDiff && !skipCurrentFile) {
                    // Parse the diff contents if we are in a valid file block
                    if (line.startsWith("deleted file mode ")) {
                        // We are in a deleted file
                        currentFileDeleted = true;
                        lineCount = lineCount + 1; // Treat a deleted file as a single line
                    } else if (!currentFileDeleted) {
                        var isAddition = line.startsWith("+") && !line.startsWith("+++");
                        var isRemoval = line.startsWith("-") && !line.startsWith("---");
                        if (isAddition || isRemoval) {
                            lineCount = lineCount + 1; // Add one line for each change
                        }
                    }
                }

                line = reader.readLine();
            }
        } catch (IOException exception) {
            System.out.println(exception);
        }

        return lineCount;
    }

    private static boolean shouldIgnoreFile(String line) {
        if (line.contains(".ecore") || line.contains(".genmodel")) {
            return false;
        }
        return List.of(
                "CHANGELOG.adoc",
                "package-lock.json",
                "doc/",
                "tests/",
                "packages/papaya/backend/sirius-components-papaya",
                "packages/papaya/backend/sirius-components-papaya-edit",
                "packages/view/backend/sirius-components-view-builder",
                "packages/view/backend/sirius-components-view",
                "packages/view/backend/sirius-components-view-edit",
                "packages/view/backend/sirius-components-view-deck",
                "packages/view/backend/sirius-components-view-deck-edit",
                "packages/view/backend/sirius-components-view-form",
                "packages/view/backend/sirius-components-view-form-edit",
                "packages/view/backend/sirius-components-view-diagram-customnodes",
                "packages/view/backend/sirius-components-view-diagram-customnodes-edit",
                "packages/view/backend/sirius-components-view-diagram",
                "packages/view/backend/sirius-components-view-diagram-edit",
                "packages/view/backend/sirius-components-view-gantt",
                "packages/view/backend/sirius-components-view-gantt-edit",
                "packages/view/backend/sirius-components-view-table",
                "packages/view/backend/sirius-components-view-table-edit",
                "packages/view/backend/sirius-components-view-table-customcells",
                "packages/view/backend/sirius-components-view-table-customcells-edit",
                "packages/view/backend/sirius-components-view-tree",
                "packages/view/backend/sirius-components-view-tree-edit",
                "packages/view/backend/sirius-components-widget-reference-view",
                "packages/view/backend/sirius-components-widget-reference-view-edit",
                "packages/view/backend/sirius-components-widget-table-view",
                "packages/view/backend/sirius-components-widget-table-view-edit"
        ).stream().anyMatch(line::contains);
    }
}
