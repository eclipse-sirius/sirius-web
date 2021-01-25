/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.tests;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.text.MatchesPattern.matchesPattern;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

/**
 * General purpose tests of the code.
 *
 * @author sbegaudeau
 */
public class GeneralPurposeTestCases {
    private static final String GIT_FOLDER_NAME = ".git"; //$NON-NLS-1$

    private static final String BACKEND_FOLDER_PATH = "backend"; //$NON-NLS-1$

    private static final String FRONTEND_SRC_FOLDER_PATH = "frontend/src"; //$NON-NLS-1$

    private static final String INTEGRATION_TESTS_CYPRESS_FOLDER_PATH = "integration-tests/cypress"; //$NON-NLS-1$

    private static final String JAVASCRIPT_FILE_EXTENSION = "js"; //$NON-NLS-1$

    private static final String CSS_FILE_EXTENSION = "css"; //$NON-NLS-1$

    private static final String JAVA_FILE_EXTENSION = "java"; //$NON-NLS-1$

    private static final String SUPPRESS_WARNINGS = "@SuppressWarnings"; //$NON-NLS-1$

    private static final String CHECKSTYLE_HIDDEN_FIELD = "@SuppressWarnings(\"checkstyle:HiddenField\")"; //$NON-NLS-1$

    private static final String BUILDER = "Builder"; //$NON-NLS-1$

    private static final String CHECKSTYLE_OFF = "CHECKSTYLE:OFF"; //$NON-NLS-1$

    private static final String ESLINT_DISABLE = "eslint-disable"; //$NON-NLS-1$

    private static final String THROW_NEW = "throw new"; //$NON-NLS-1$

    private static final String DEBUGGER = "debugger"; //$NON-NLS-1$

    private static final String CONSOLE_LOG = "console.log"; //$NON-NLS-1$

    private static final String ALERT = "alert("; //$NON-NLS-1$

    private static final String CONFIRM = "confirm("; //$NON-NLS-1$

    private static final String PROMPT = "prompt("; //$NON-NLS-1$

    private static final String WIDTH_100 = "width: 100%;"; //$NON-NLS-1$

    private static final String HEIGHT_100 = "height: 100%;"; //$NON-NLS-1$

    private static final String INVALID_MATERIALUI_IMPORT = "from '@material-ui/core';"; //$NON-NLS-1$

    //// @formatter:off
    private static final List<Pattern> COPYRIGHT_HEADER = List.of(
            Pattern.compile(Pattern.quote("/*******************************************************************************")), //$NON-NLS-1$
            Pattern.compile(" \\* Copyright \\(c\\) [0-9]{4}(, [0-9]{4})* (.*)\\.$"), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * This program and the accompanying materials")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * are made available under the terms of the Eclipse Public License v2.0")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * which accompanies this distribution, and is available at")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * https://www.eclipse.org/legal/epl-2.0/")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" *")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * SPDX-License-Identifier: EPL-2.0")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" *")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" * Contributors:")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" *     Obeo - initial API and implementation")), //$NON-NLS-1$
            Pattern.compile(Pattern.quote(" *******************************************************************************/"))); //$NON-NLS-1$
    // @formatter:on

    /**
     * Finds the folder containing the Git repository.
     *
     * @return The root folder
     */
    private File getRootFolder() {
        String path = System.getProperty("user.dir"); //$NON-NLS-1$
        File classpathRoot = new File(path);

        File repositoryRootFolder = classpathRoot;
        while (!new File(repositoryRootFolder, GIT_FOLDER_NAME).exists()) {
            repositoryRootFolder = repositoryRootFolder.getParentFile();
        }

        return repositoryRootFolder;
    }

    /**
     * Finds all the files located under the given source folder path with the given extension.
     *
     * @param sourceFolderPath
     *            The path of the source folder
     * @return The path of the files
     */
    private List<Path> findFilePaths(Path sourceFolderPath, String extension) {
        List<Path> filesPaths = new ArrayList<>();

        try (Stream<Path> paths = Files.walk(sourceFolderPath);) {
            // @formatter:off
            List<Path> filePaths = paths.filter(Files::isRegularFile)
                    .filter(filePath -> filePath.toFile().getName().endsWith(extension))
                    .filter(filePath -> !filePath.toString().replace("\\", "/").contains("/.mvn/wrapper/")) //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
                    .collect(Collectors.toList());
            // @formatter:on

            filesPaths.addAll(filePaths);
        } catch (IOException exception) {
            fail(exception.getMessage());
        }

        return filesPaths;
    }

    @Test
    public void checkJavaCode() {
        File rootFolder = this.getRootFolder();
        Path backendFolderPath = Paths.get(rootFolder.getAbsolutePath(), BACKEND_FOLDER_PATH);
        List<Path> javaFilePaths = this.findFilePaths(backendFolderPath, JAVA_FILE_EXTENSION);
        for (Path javaFilePath : javaFilePaths) {
            if (!javaFilePath.endsWith(GeneralPurposeTestCases.class.getSimpleName() + "." + JAVA_FILE_EXTENSION)) { //$NON-NLS-1$
                try {
                    List<String> lines = Files.readAllLines(javaFilePath);
                    for (int index = 0; index < lines.size(); index++) {
                        String line = lines.get(index);
                        this.testNoSuppressWarnings(index, line, javaFilePath, lines);
                        this.testNoCheckstyleOff(index, line, javaFilePath);
                        this.testNoThrowNewException(index, line, javaFilePath);
                    }
                    this.testCopyrightHeader(javaFilePath, lines);
                } catch (IOException exception) {
                    fail(exception.getMessage());
                }
            }
        }
    }

    private void testCopyrightHeader(Path filePath, List<String> lines) {
        assertTrue(lines.size() >= COPYRIGHT_HEADER.size());
        for (int i = 0; i < COPYRIGHT_HEADER.size(); i++) {
            assertThat("Invalid copyright header in " + filePath, lines.get(i), matchesPattern(COPYRIGHT_HEADER.get(i))); //$NON-NLS-1$
        }
    }

    private void testNoSuppressWarnings(int index, String line, Path javaFilePath, List<String> lines) {
        if (line.contains(SUPPRESS_WARNINGS)) {
            boolean isValidUsage = true;
            isValidUsage = isValidUsage && line.contains(CHECKSTYLE_HIDDEN_FIELD);
            isValidUsage = isValidUsage && lines.size() > index;
            isValidUsage = isValidUsage && lines.get(index + 1).contains(BUILDER);

            if (!isValidUsage) {
                fail(this.createErrorMessage("@SuppressWarnings", javaFilePath, index)); //$NON-NLS-1$
            }
        }
    }

    private void testNoCheckstyleOff(int index, String line, Path javaFilePath) {
        if (line.contains(CHECKSTYLE_OFF)) {
            fail(this.createErrorMessage("CHECKSTYLE:OFF", javaFilePath, index)); //$NON-NLS-1$
        }
    }

    private void testNoThrowNewException(int index, String line, Path javaFilePath) {
        if (line.contains(THROW_NEW)) {
            fail(this.createErrorMessage("throw new XXXException", javaFilePath, index)); //$NON-NLS-1$
        }
    }

    @Test
    public void checkJavaScriptCode() {
        File rootFolder = this.getRootFolder();
        Path frontendFolderPath = Paths.get(rootFolder.getAbsolutePath(), FRONTEND_SRC_FOLDER_PATH);
        List<Path> javascriptFilePaths = this.findFilePaths(frontendFolderPath, JAVASCRIPT_FILE_EXTENSION);

        // Path integrationTestsFolderPath = Paths.get(rootFolder.getAbsolutePath(),
        // INTEGRATION_TESTS_CYPRESS_FOLDER_PATH);
        // List<Path> javascriptIntegrationTestFilePaths = this.findFilePaths(integrationTestsFolderPath,
        // JAVASCRIPT_FILE_EXTENSION);

        List<Path> filePaths = new ArrayList<>();
        filePaths.addAll(javascriptFilePaths);
        // filePaths.addAll(javascriptIntegrationTestFilePaths);

        for (Path javascriptFilePath : filePaths) {
            try {
                List<String> lines = Files.readAllLines(javascriptFilePath);
                for (int index = 0; index < lines.size(); index++) {
                    String line = lines.get(index);
                    this.testNoESLintDisable(index, line, javascriptFilePath);
                    this.testNoDebugger(index, line, javascriptFilePath);
                    this.testNoConsoleLog(index, line, javascriptFilePath);
                    this.testNoAlert(index, line, javascriptFilePath);
                    this.testNoConfirm(index, line, javascriptFilePath);
                    this.testNoPrompt(index, line, javascriptFilePath);
                    this.testNoInvalidMaterialUIImport(index, line, javascriptFilePath);
                }
                this.testCopyrightHeader(javascriptFilePath, lines);
            } catch (IOException exception) {
                fail(exception.getMessage());
            }
        }
    }

    private void testNoESLintDisable(int index, String line, Path javascriptFilePath) {
        if (line.contains(ESLINT_DISABLE)) {
            fail(this.createErrorMessage(ESLINT_DISABLE, javascriptFilePath, index));
        }
    }

    private void testNoDebugger(int index, String line, Path javascriptFilePath) {
        if (line.contains(DEBUGGER)) {
            fail(this.createErrorMessage(DEBUGGER, javascriptFilePath, index));
        }
    }

    private void testNoConsoleLog(int index, String line, Path javascriptFilePath) {
        if (line.contains(CONSOLE_LOG)) {
            fail(this.createErrorMessage(CONSOLE_LOG, javascriptFilePath, index));
        }
    }

    private void testNoAlert(int index, String line, Path javascriptFilePath) {
        if (line.contains(ALERT)) {
            fail(this.createErrorMessage(ALERT, javascriptFilePath, index));
        }
    }

    private void testNoConfirm(int index, String line, Path javascriptFilePath) {
        if (line.contains(CONFIRM)) {
            fail(this.createErrorMessage(CONFIRM, javascriptFilePath, index));
        }
    }

    private void testNoPrompt(int index, String line, Path javascriptFilePath) {
        if (line.contains(PROMPT)) {
            fail(this.createErrorMessage(PROMPT, javascriptFilePath, index));
        }
    }

    private void testNoInvalidMaterialUIImport(int index, String line, Path javascriptFilePath) {
        if (line.contains(INVALID_MATERIALUI_IMPORT)) {
            fail(this.createErrorMessage(INVALID_MATERIALUI_IMPORT, javascriptFilePath, index));
        }
    }

    @Test
    public void checkCSSCode() {
        File rootFolder = this.getRootFolder();
        Path frontendFolderPath = Paths.get(rootFolder.getAbsolutePath(), FRONTEND_SRC_FOLDER_PATH);
        List<Path> cssFilePaths = this.findFilePaths(frontendFolderPath, CSS_FILE_EXTENSION);

        for (Path cssFilePath : cssFilePaths) {
            try {
                List<String> lines = Files.readAllLines(cssFilePath);
                this.testCopyrightHeader(cssFilePath, lines);
                for (int index = 0; index < lines.size(); index++) {
                    String line = lines.get(index);
                    this.testHeight100Percent(index, line, cssFilePath);
                    this.testWidth100Percent(index, line, cssFilePath);
                }
            } catch (IOException exception) {
                fail(exception.getMessage());
            }
        }
    }

    /**
     * Test that we are not relying on width: 100%; in our CSS files.
     * <p>
     * In a component based approach, we should rely on layouting a container instead of specifying in the children how
     * they should be layouted in their parents regardless of the layout algorithm used. Use Grid or Flexbox to indicate
     * the layout of the parent but do not start from the children.
     * </p>
     * <p>
     * There are currently a couple of accepted usage such as in our modals but others may be removed. New usages are
     * highly unlikely. We are in the process of removing such usage, not adding new ones.
     * </p>
     *
     * @param index
     *            The number of the line
     * @param line
     *            The line to check
     * @param cssFilePath
     *            The path of the CSS file
     */
    private void testHeight100Percent(int index, String line, Path cssFilePath) {
        // @formatter:off
        var whitelist = Stream.of(
                Path.of("frontend/src/diagram/Sprotty.css"), //$NON-NLS-1$
                Path.of("frontend/src/modals/Modal.module.css"), //$NON-NLS-1$
                Path.of("frontend/src/diagram/palette/tool-section/ToolSection.module.css") //$NON-NLS-1$
        );
        // @formatter:on
        if (whitelist.filter(cssFilePath::endsWith).findFirst().isEmpty()) {
            if (line.contains(HEIGHT_100)) {
                fail(this.createErrorMessage(HEIGHT_100, cssFilePath, index));
            }
        }
    }

    /**
     * Test that we are not relying on width: 100%; in our CSS files.
     * <p>
     * In a component based approach, we should rely on layouting a container instead of specifying in the children how
     * they should be layouted in their parents regardless of the layout algorithm used. Use Grid or Flexbox to indicate
     * the layout of the parent but do not start from the children.
     * </p>
     * <p>
     * There are currently a couple of accepted usage such as in our modals but others may be removed. New usages are
     * highly unlikely. We are in the process of removing such usage, not adding new ones.
     * </p>
     *
     * @param index
     *            The number of the line
     * @param line
     *            The line to check
     * @param cssFilePath
     *            The path of the CSS file
     */
    private void testWidth100Percent(int index, String line, Path cssFilePath) {
        // @formatter:off
        var whitelist = Stream.of(
                Path.of("frontend/src/diagram/Sprotty.css"), //$NON-NLS-1$
                Path.of("frontend/src/modals/Modal.module.css"), //$NON-NLS-1$
                Path.of("frontend/src/diagram/palette/tool-section/ToolSection.module.css") //$NON-NLS-1$
        );
        // @formatter:on
        if (whitelist.filter(cssFilePath::endsWith).findFirst().isEmpty()) {
            if (line.contains(WIDTH_100)) {
                fail(this.createErrorMessage(WIDTH_100, cssFilePath, index));
            }
        }
    }

    private String createErrorMessage(String pattern, Path path, int lineNumber) {
        return "Invalid use of " + pattern + " in " + path.toString() + " line " + lineNumber; //$NON-NLS-1$ //$NON-NLS-2$ //$NON-NLS-3$
    }
}
