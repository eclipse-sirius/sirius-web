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
package org.eclipse.sirius.components.codegen.emf;

import java.io.IOException;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.formatter.DefaultCodeFormatterConstants;

/**
 * EMF code generation entry point for the Sirius Web repository.
 * <p>
 * The generator discovers {@code .genmodel} files, loads and validates them, runs EMF code
 * generation for MODEL and EDIT targets, then applies repository-specific cleanup
 * (remove generated metadata, merge i18n properties, and initialize missing SVG icons).
 */
public final class SiriusWebEMFGenerator {

    private static final String GENMODEL_EXTENSION = ".genmodel";
    private static final String DEFAULT_SVG_CLASSPATH = "/Default.svg";

    /**
     * Run the generator from the command line.
     *
     * @param args expected: {@code <repository-root>}.
     */
    public static void main(String[] args) {
        GeneratorArguments arguments = GeneratorArguments.parse(args);
        List<Path> genmodels = GenmodelFinder.findGenmodels(arguments.repositoryRoot(), arguments.genmodelPattern());
        ResourceSet resourceSet = ResourceSetFactory.create();
        List<LoadedGenmodel> loadedGenmodels = GenmodelLoader.loadAll(resourceSet, arguments.repositoryRoot(), genmodels);
        printSummary(arguments, genmodels);
        System.out.printf("Genmodels loaded: %d%n", loadedGenmodels.size());
        List<LoadedGenmodel> validGenmodels = GenmodelValidator.validateAll(resourceSet, loadedGenmodels);
        GenmodelCodeGenerator.generateAll(validGenmodels);
    }

    /**
     * Print a summary of the repository root and the discovered genmodels.
     *
     * @param arguments parsed generator arguments.
     * @param genmodels discovered genmodel paths.
     */
    private static void printSummary(GeneratorArguments arguments, List<Path> genmodels) {
        System.out.printf("Repository root:  %s%n", arguments.repositoryRoot());
        System.out.printf("Genmodel filter:  %s%n", arguments.genmodelPattern());
        System.out.printf("Genmodels found:  %d%n", genmodels.size());
        genmodels.forEach(path -> System.out.println(path));
    }

    private record GeneratorArguments(Path repositoryRoot, String genmodelPattern) {
        private static final String DEFAULT_GENMODEL_PATTERN = "**/src/**.genmodel";

        /**
         * Parse CLI arguments and validate the repository root.
         *
         * @param args CLI arguments.
         * @return parsed arguments.
         * @throws IllegalArgumentException when the repository root is missing or invalid.
         */
        static GeneratorArguments parse(String[] args) {
            if (args == null || args.length < 1) {
                throw new IllegalArgumentException(
                        "Expected 1 argument: <repository-root>");
            }
            Path repositoryRoot = toPath(Objects.requireNonNull(args[0], "repository root path"));
            String genmodelPattern = DEFAULT_GENMODEL_PATTERN;
            for (int i = 1; i < args.length; i++) {
                String arg = args[i];
                if (arg != null && arg.startsWith("--genmodel-pattern=")) {
                    genmodelPattern = arg.substring("--genmodel-pattern=".length());
                }
            }

            if (!Files.isDirectory(repositoryRoot)) {
                throw new IllegalArgumentException("Repository root not found: " + repositoryRoot);
            }
            if (genmodelPattern == null || genmodelPattern.isBlank()) {
                throw new IllegalArgumentException("Genmodel pattern must not be blank.");
            }
            return new GeneratorArguments(repositoryRoot, genmodelPattern);
        }

        /**
         * Convert a string path or {@code file:} URI to a {@link Path}.
         *
         * @param value raw string path or URI.
         * @return resolved path.
         */
        private static Path toPath(String value) {
            if (value.startsWith("file:")) {
                return Path.of(java.net.URI.create(value));
            }
            return Path.of(value);
        }
    }

    private static final class GenmodelFinder {
        private GenmodelFinder() {
        }

        /**
         * Discover {@code .genmodel} files under the repository root.
         *
         * @param repositoryRoot repository root to scan.
         * @param genmodelPattern glob-style pattern for relative paths.
         * @return sorted list of genmodel paths.
         */
        static List<Path> findGenmodels(Path repositoryRoot, String genmodelPattern) {
            Path normalizedRoot = repositoryRoot.toAbsolutePath().normalize();
            java.nio.file.PathMatcher matcher = repositoryRoot.getFileSystem()
                    .getPathMatcher("glob:" + genmodelPattern);
            try (Stream<Path> paths = Files.walk(repositoryRoot)) {
                return paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(GENMODEL_EXTENSION))
                        .filter(path -> matchesPattern(normalizedRoot, matcher, path))
                        .sorted(Comparator.naturalOrder())
                        .collect(Collectors.toList());
            } catch (IOException exception) {
                throw new IllegalStateException("Failed to scan repository: " + repositoryRoot, exception);
            }
        }

        /**
         * Match a path against a glob pattern, using the repository root as base.
         *
         * @param repositoryRoot normalized repository root.
         * @param matcher compiled glob matcher.
         * @param path absolute or relative path to check.
         * @return {@code true} when the path matches the pattern.
         */
        private static boolean matchesPattern(Path repositoryRoot, java.nio.file.PathMatcher matcher, Path path) {
            Path normalizedPath = path.toAbsolutePath().normalize();
            if (!normalizedPath.startsWith(repositoryRoot)) {
                return false;
            }
            return matcher.matches(repositoryRoot.relativize(normalizedPath));
        }
    }

    private static final class ResourceSetFactory {
        private ResourceSetFactory() {
        }

        /**
         * Build an EMF resource set with required factories and URI mappings.
         *
         * @return initialized {@link ResourceSet}.
         */
        static ResourceSet create() {
            ResourceSet resourceSet = new ResourceSetImpl();
            resourceSet.getResourceFactoryRegistry()
                    .getExtensionToFactoryMap()
                    .put(Resource.Factory.Registry.DEFAULT_EXTENSION, new EcoreResourceFactoryImpl());
            resourceSet.getPackageRegistry().put(GenModelPackage.eNS_URI, GenModelPackage.eINSTANCE);
            resourceSet.getResources().add(EcorePackage.eINSTANCE.eResource());
            resourceSet.getURIConverter().getURIMap().put(
                    URI.createURI("platform:/plugin/org.eclipse.emf.ecore/model/Ecore.ecore"),
                    URI.createURI("http://www.eclipse.org/emf/2002/Ecore"));
            return resourceSet;
        }
    }

    private static final class GenmodelLoader {
        private GenmodelLoader() {
        }

        /**
         * Load all genmodels and attach project metadata.
         *
         * @param resourceSet shared EMF resource set.
         * @param repositoryRoot repository root.
         * @param genmodels list of genmodel paths to load.
         * @return loaded genmodels with project location metadata.
         */
        static List<LoadedGenmodel> loadAll(ResourceSet resourceSet, Path repositoryRoot, List<Path> genmodels) {
            return genmodels.stream()
                    .map(path -> loadGenmodel(resourceSet, repositoryRoot, path))
                    .collect(Collectors.toList());
        }

        /**
         * Load a single genmodel resource and map it into the platform URI space.
         *
         * @param resourceSet shared EMF resource set.
         * @param repositoryRoot repository root.
         * @param path path to the genmodel.
         * @return loaded genmodel and its project metadata.
         */
        private static LoadedGenmodel loadGenmodel(ResourceSet resourceSet, Path repositoryRoot, Path path) {
            URI fileUri = toFileURI(path);
            ProjectLocation projectLocation = ProjectLocation.from(repositoryRoot, path);
            mapToPlatformResource(resourceSet, projectLocation, fileUri);
            mapProjectRoot(resourceSet, projectLocation);
            Resource resource = resourceSet.getResource(fileUri, true);
            return new LoadedGenmodel(resource, projectLocation);
        }

        /**
         * Register a platform:/resource mapping so EMF can resolve project-relative URIs.
         *
         * @param resourceSet shared EMF resource set.
         * @param projectLocation inferred project location.
         * @param fileUri file URI for the genmodel.
         */
        private static void mapToPlatformResource(
                ResourceSet resourceSet,
                ProjectLocation projectLocation,
                URI fileUri) {
            if (projectLocation == null) {
                return;
            }
            // Make platform:/resource URI resolution deterministic for EMF.
            URI platformUri = URI.createURI("platform:/resource/"
                    + projectLocation.projectName()
                    + "/"
                    + projectLocation.projectRelativePath());
            resourceSet.getURIConverter().getURIMap().put(platformUri, fileUri);
            info("Registered URI mapping: " + platformUri + " -> " + fileUri);
        }

        /**
         * Register project root mappings used by EMF when resolving workspace URIs.
         *
         * @param resourceSet shared EMF resource set.
         * @param projectLocation project metadata.
         */
        private static void mapProjectRoot(ResourceSet resourceSet, ProjectLocation projectLocation) {
            if (projectLocation == null) {
                return;
            }
            registerProjectMapping(resourceSet, projectLocation.projectName(), projectLocation.projectRoot());
        }

        /**
         * Convert a file path into a file URI.
         *
         * @param path filesystem path.
         * @return file URI for the path.
         */
        private static URI toFileURI(Path path) {
            return URI.createFileURI(path.toAbsolutePath().toString());
        }
    }

    private record ProjectLocation(String projectName, String projectRelativePath, Path projectRoot) {
        /**
         * Infer project metadata from a genmodel path using the repository layout.
         *
         * @param repositoryRoot repository root.
         * @param genmodelPath genmodel path to resolve.
         * @return project metadata or {@code null} if the path cannot be mapped.
         */
        static ProjectLocation from(Path repositoryRoot, Path genmodelPath) {
            Path absoluteGenmodel = genmodelPath.toAbsolutePath().normalize();
            Path absoluteRoot = repositoryRoot.toAbsolutePath().normalize();
            if (!absoluteGenmodel.startsWith(absoluteRoot)) {
                return null;
            }
            Path relativeToRoot = absoluteRoot.relativize(absoluteGenmodel);
            int backendIndex = indexOfSegment(relativeToRoot, "backend");
            if (backendIndex < 0 || backendIndex + 1 >= relativeToRoot.getNameCount()) {
                return null;
            }
            String projectName = relativeToRoot.getName(backendIndex + 1).toString();
            Path projectRoot = absoluteRoot.resolve(relativeToRoot.subpath(0, backendIndex + 2));
            Path projectRelativePath = projectRoot.relativize(absoluteGenmodel);
            return new ProjectLocation(projectName, toUnixPath(projectRelativePath), projectRoot);
        }

        /**
         * Find the index of the first segment matching the provided name.
         *
         * @param path path to inspect.
         * @param segment segment name to find.
         * @return index or {@code -1} when not found.
         */
        private static int indexOfSegment(Path path, String segment) {
            for (int i = 0; i < path.getNameCount(); i++) {
                if (segment.equals(path.getName(i).toString())) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Render a path with forward slashes regardless of platform.
         *
         * @param path path to render.
         * @return Unix-style path string.
         */
        private static String toUnixPath(Path path) {
            StringBuilder builder = new StringBuilder();
            for (Path segment : path) {
                if (!builder.isEmpty()) {
                    builder.append('/');
                }
                builder.append(segment.toString());
            }
            return builder.toString();
        }
    }

    private record LoadedGenmodel(Resource resource, ProjectLocation projectLocation) {
    }

    private record EditProjectLocation(String projectName, Path projectRoot) {
    }

    private static final class GeneratedFileCleaner {
        private GeneratedFileCleaner() {
        }

        /**
         * Remove generated metadata files from the MODEL project root.
         *
         * @param projectRoot project root directory.
         */
        static void cleanupProjectRoot(Path projectRoot) {
            deleteIfExists(projectRoot.resolve("build.properties"));
            deleteIfExists(projectRoot.resolve("plugin.xml"));
            deleteIfExists(projectRoot.resolve("plugin.properties"));
        }

        /**
         * Postprocess EDIT project output (i18n merge, icons, cleanup).
         *
         * @param projectRoot edit project root directory.
         */
        static void cleanupEditProjectRoot(Path projectRoot) {
            Path rootPluginProperties = projectRoot.resolve("plugin.properties");
            Path resourcesPluginProperties = projectRoot.resolve("src/main/resources/plugin.properties");
            mergePluginProperties(rootPluginProperties, resourcesPluginProperties);
            ensureSvgIcons(projectRoot);
            deleteIfExists(projectRoot.resolve("build.properties"));
            deleteIfExists(projectRoot.resolve("plugin.xml"));
            deleteIfExists(rootPluginProperties);
        }

        /**
         * Delete a path if it exists.
         *
         * @param path file to delete.
         */
        private static void deleteIfExists(Path path) {
            try {
                if (Files.deleteIfExists(path)) {
                    System.out.println("Cleaned generated file: " + path);
                }
            } catch (IOException exception) {
                warn("Failed to remove generated file: " + path + " (" + exception.getMessage() + ")");
            }
        }

        /**
         * Merge missing keys from the generated root plugin.properties into resources.
         *
         * @param generatedRoot generated properties file at project root.
         * @param resourcesFile customized properties file under resources.
         */
        private static void mergePluginProperties(Path generatedRoot, Path resourcesFile) {
            // Preserve customized resources and append only newly generated keys.
            if (!Files.isRegularFile(generatedRoot)) {
                return;
            }
            Properties generated = loadProperties(generatedRoot);
            if (generated.isEmpty()) {
                return;
            }
            Set<String> existingKeys = new HashSet<>();
            if (Files.isRegularFile(resourcesFile)) {
                Properties existing = loadProperties(resourcesFile);
                existingKeys.addAll(existing.stringPropertyNames());
            }
            List<String> missingEntries = new ArrayList<>();
            for (String key : generated.stringPropertyNames()) {
                if (!existingKeys.contains(key)) {
                    missingEntries.add(formatPropertyLine(key, generated.getProperty(key)));
                }
            }
            if (missingEntries.isEmpty()) {
                return;
            }
            try {
                Path parent = resourcesFile.getParent();
                if (parent != null) {
                    Files.createDirectories(parent);
                }
                boolean needsNewline = fileNeedsTrailingNewline(resourcesFile);
                try (java.io.BufferedWriter writer = Files.newBufferedWriter(
                        resourcesFile,
                        StandardOpenOption.CREATE,
                        StandardOpenOption.APPEND)) {
                    if (needsNewline) {
                        writer.newLine();
                    }
                    for (String line : missingEntries) {
                        writer.write(line);
                        writer.newLine();
                    }
                }
                System.out.println("Merged " + missingEntries.size() + " plugin.properties entries into " + resourcesFile);
            } catch (IOException exception) {
                warn("Failed to merge plugin.properties entries into " + resourcesFile
                        + " (" + exception.getMessage() + ")");
            }
        }

        /**
         * Load Java properties from disk.
         *
         * @param path path to the properties file.
         * @return loaded properties (empty on failure).
         */
        private static Properties loadProperties(Path path) {
            Properties properties = new Properties();
            try (java.io.InputStream stream = Files.newInputStream(path)) {
                properties.load(stream);
            } catch (IOException exception) {
                warn("Failed to load properties from " + path + " (" + exception.getMessage() + ")");
                return new Properties();
            }
            return properties;
        }

        /**
         * Format a single properties entry using Java escaping rules.
         *
         * @param key property key.
         * @param value property value.
         * @return a line suitable for a .properties file.
         */
        private static String formatPropertyLine(String key, String value) {
            Properties properties = new Properties();
            properties.setProperty(key, value == null ? "" : value);
            StringWriter writer = new StringWriter();
            try {
                properties.store(writer, null);
            } catch (IOException exception) {
                return key + "=" + (value == null ? "" : value);
            }
            for (String line : writer.toString().split("\\R")) {
                if (!line.startsWith("#") && !line.isBlank()) {
                    return line;
                }
            }
            return key + "=" + (value == null ? "" : value);
        }

        /**
         * Check whether a file ends with a newline.
         *
         * @param path file to inspect.
         * @return {@code true} when the file exists and does not end with a newline.
         * @throws IOException if the file cannot be read.
         */
        private static boolean fileNeedsTrailingNewline(Path path) throws IOException {
            if (!Files.isRegularFile(path)) {
                return false;
            }
            byte[] bytes = Files.readAllBytes(path);
            if (bytes.length == 0) {
                return false;
            }
            return bytes[bytes.length - 1] != '\n';
        }

        /**
         * Ensure SVG placeholders exist for each generated GIF icon, then remove the GIF folder.
         *
         * @param projectRoot edit project root directory.
         */
        private static void ensureSvgIcons(Path projectRoot) {
            Path generatedIconsRoot = projectRoot.resolve("icons");
            if (!Files.isDirectory(generatedIconsRoot)) {
                return;
            }
            String defaultSvgContent = loadDefaultSvgContent();
            if (defaultSvgContent == null) {
                return;
            }
            Path resourcesIconsRoot = projectRoot.resolve("src/main/resources/icons");
            try (Stream<Path> paths = Files.walk(generatedIconsRoot)) {
                // Mirror all generated GIF icon paths to SVG in resources.
                List<Path> gifPaths = paths.filter(Files::isRegularFile)
                        .filter(path -> path.getFileName().toString().endsWith(".gif"))
                        .collect(Collectors.toList());
                for (Path gifPath : gifPaths) {
                    Path relative = generatedIconsRoot.relativize(gifPath);
                    Path svgPath = resourcesIconsRoot.resolve(swapExtension(relative, ".gif", ".svg"));
                    if (Files.exists(svgPath)) {
                        continue;
                    }
                    Path parent = svgPath.getParent();
                    if (parent != null) {
                        Files.createDirectories(parent);
                    }
                    Files.writeString(svgPath, defaultSvgContent, StandardOpenOption.CREATE_NEW);
                    System.out.println("Initialized missing SVG icon: " + svgPath);
                }
            } catch (IOException exception) {
                warn("Failed to ensure SVG icons for " + projectRoot + " (" + exception.getMessage() + ")");
            }
            deleteDirectoryRecursively(generatedIconsRoot);
        }

        /**
         * Load the default SVG placeholder from the classpath.
         *
         * @return SVG content or {@code null} when unavailable.
         */
        private static String loadDefaultSvgContent() {
            try (java.io.InputStream stream = SiriusWebEMFGenerator.class.getResourceAsStream(DEFAULT_SVG_CLASSPATH)) {
                if (stream == null) {
                    warn("Default.svg not found on classpath: " + DEFAULT_SVG_CLASSPATH);
                    return null;
                }
                return new String(stream.readAllBytes(), StandardCharsets.UTF_8);
            } catch (IOException exception) {
                warn("Failed to read Default.svg (" + exception.getMessage() + ")");
                return null;
            }
        }

        /**
         * Replace the file extension of the last path segment.
         *
         * @param path path to adjust.
         * @param fromExtension extension to replace (including dot).
         * @param toExtension new extension (including dot).
         * @return updated path.
         */
        private static Path swapExtension(Path path, String fromExtension, String toExtension) {
            String fileName = path.getFileName().toString();
            if (fileName.endsWith(fromExtension)) {
                String newFileName = fileName.substring(0, fileName.length() - fromExtension.length()) + toExtension;
                Path parent = path.getParent();
                return parent == null ? Path.of(newFileName) : parent.resolve(newFileName);
            }
            return path;
        }

        /**
         * Recursively delete a directory and all its contents.
         *
         * @param directory directory to remove.
         */
        private static void deleteDirectoryRecursively(Path directory) {
            if (!Files.exists(directory)) {
                return;
            }
            try (Stream<Path> paths = Files.walk(directory)) {
                List<Path> ordered = paths.sorted(Comparator.reverseOrder()).collect(Collectors.toList());
                for (Path path : ordered) {
                    Files.deleteIfExists(path);
                }
                System.out.println("Removed generated icons directory: " + directory);
            } catch (IOException exception) {
                warn("Failed to remove generated icons directory: " + directory
                        + " (" + exception.getMessage() + ")");
            }
        }
    }

    private static final class GenmodelValidator {
        private GenmodelValidator() {
        }

        /**
         * Validate all resources loaded in the resource set (genmodel + referenced ecore).
         *
         * @param resourceSet shared EMF resource set.
         * @param genmodels loaded genmodels.
         * @return genmodels list when all resources are valid.
         * @throws IllegalStateException when any validation error is detected.
         */
        static List<LoadedGenmodel> validateAll(ResourceSet resourceSet, List<LoadedGenmodel> genmodels) {
            // Resolve referenced resources (e.g., .ecore) so they are included in validation.
            EcoreUtil.resolveAll(resourceSet);
            boolean hasErrors = false;
            for (Resource resource : List.copyOf(resourceSet.getResources())) {
                hasErrors |= validateResource(resource);
            }
            if (hasErrors) {
                throw new IllegalStateException("Validation failed. Fix reported errors before generation.");
            }
            return genmodels;
        }

        /**
         * Validate the content of a genmodel resource.
         *
         * @param resource genmodel resource.
         * @return {@code true} when errors were found.
         */
        private static boolean validateResource(Resource resource) {
            boolean hasErrors = false;
            for (EObject root : resource.getContents()) {
                Diagnostic diagnostic = Diagnostician.INSTANCE.validate(root);
                hasErrors |= reportErrors(resource.getURI(), diagnostic);
            }
            if (!hasErrors) {
                System.out.println("✓ Validated " + resource.getURI());
            }
            return hasErrors;
        }

        /**
         * Report validation errors recursively.
         *
         * @param resourceUri URI of the validated resource.
         * @param diagnostic root diagnostic to inspect.
         * @return {@code true} when errors were reported.
         */
        private static boolean reportErrors(URI resourceUri, Diagnostic diagnostic) {
            boolean hasErrors = false;
            if (diagnostic.getSeverity() == Diagnostic.ERROR) {
                System.err.println("Validation error in " + resourceUri + ": " + diagnostic.getMessage());
                hasErrors = true;
            }
            for (Diagnostic child : diagnostic.getChildren()) {
                hasErrors |= reportErrors(resourceUri, child);
            }
            return hasErrors;
        }
    }

    private static final class GenmodelCodeGenerator {
        private GenmodelCodeGenerator() {
        }

        /**
         * Generate MODEL and EDIT code for all valid genmodels.
         *
         * @param genmodels validated genmodels to generate.
         */
        static void generateAll(List<LoadedGenmodel> genmodels) {
            for (LoadedGenmodel genmodelEntry : genmodels) {
                Resource resource = genmodelEntry.resource();
                GenModel genModel = findGenModel(resource);
                if (genModel == null) {
                    warn("No GenModel root found in " + resource.getURI());
                    continue;
                }
                Generator generator = new Generator();
                generator.getAdapterFactoryDescriptorRegistry()
                        .addDescriptor(GenModelPackage.eNS_URI, GenModelGeneratorAdapterFactory.DESCRIPTOR);
                genModel.reconcile();
                genModel.setCanGenerate(true);
                genModel.setCodeFormatting(true);
                genModel.setCommentFormatting(true);
                genModel.setCleanup(true);
                generator.setInput(genModel);
                configureFormatting(generator, resource.getResourceSet(), genmodelEntry.projectLocation());
                EditProjectLocation editProjectLocation = resolveEditProjectLocation(
                        genmodelEntry.projectLocation(),
                        genModel);
                if (editProjectLocation != null && Files.isDirectory(editProjectLocation.projectRoot())) {
                    registerProjectMapping(resource.getResourceSet(),
                            editProjectLocation.projectName(),
                            editProjectLocation.projectRoot());
                }
                System.out.println("Generating MODEL for " + resource.getURI());
                generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE,
                        new BasicMonitor.Printing(System.out));
                if (genmodelEntry.projectLocation() != null) {
                    GeneratedFileCleaner.cleanupProjectRoot(genmodelEntry.projectLocation().projectRoot());
                }
                System.out.println("Generating EDIT for " + resource.getURI());
                generator.generate(genModel, GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE,
                        new BasicMonitor.Printing(System.out));
                if (editProjectLocation != null) {
                    GeneratedFileCleaner.cleanupEditProjectRoot(editProjectLocation.projectRoot());
                }
            }
        }

        /**
         * Extract the GenModel root from an EMF resource.
         *
         * @param resource EMF resource to scan.
         * @return genmodel root or {@code null}.
         */
        private static GenModel findGenModel(Resource resource) {
            for (EObject root : resource.getContents()) {
                if (root instanceof GenModel genModel) {
                    return genModel;
                }
            }
            return null;
        }

        /**
         * Configure EMF code formatting options for generation.
         *
         * @param generator EMF generator instance.
         * @param resourceSet shared resource set.
         * @param projectLocation model project metadata (optional).
         */
        private static void configureFormatting(
                Generator generator,
                ResourceSet resourceSet,
                ProjectLocation projectLocation) {
            Generator.Options options = generator.getOptions();
            options.codeFormatting = true;
            options.commentFormatting = true;
            options.importOrganizing = true;
            options.cleanup = true;
            options.resourceSet = resourceSet;
            java.util.Map<String, String> formatterOptions = DefaultCodeFormatterConstants.getEclipseDefaultSettings();
            formatterOptions.putAll(JavaCore.getOptions());
            formatterOptions.putAll(loadFormatterPreferencesFromClasspath());
            formatterOptions.putAll(loadFormatterPreferencesFromProject(projectLocation));
            options.codeFormatterOptions = formatterOptions;
        }

        /**
         * Resolve the edit project location from genmodel metadata.
         *
         * @param projectLocation model project metadata.
         * @param genModel genmodel being generated.
         * @return edit project metadata or {@code null}.
         */
        private static EditProjectLocation resolveEditProjectLocation(
                ProjectLocation projectLocation,
                GenModel genModel) {
            if (projectLocation == null) {
                return null;
            }
            Path parentDir = projectLocation.projectRoot().getParent();
            if (parentDir == null) {
                return null;
            }
            // GenModel can express edit output via directory or plugin id; support both.
            String editProjectDirectory = genModel.getEditProjectDirectory();
            EditProjectLocation editProjectLocation = resolveFromEditProjectDirectory(
                    editProjectDirectory,
                    parentDir);
            if (editProjectLocation != null) {
                return editProjectLocation;
            }
            String editPluginId = genModel.getEditPluginID();
            if (editPluginId == null || editPluginId.isBlank()) {
                return null;
            }
            return new EditProjectLocation(editPluginId, parentDir.resolve(editPluginId));
        }

        /**
         * Resolve an edit project location from {@code editProjectDirectory}.
         *
         * @param editProjectDirectory raw directory from the genmodel.
         * @param parentDir parent directory for relative resolution.
         * @return edit project metadata or {@code null}.
         */
        private static EditProjectLocation resolveFromEditProjectDirectory(
                String editProjectDirectory,
                Path parentDir) {
            if (editProjectDirectory == null || editProjectDirectory.isBlank()) {
                return null;
            }
            Path editDirectoryPath = Path.of(editProjectDirectory);
            if (editDirectoryPath.isAbsolute() && Files.exists(editDirectoryPath)) {
                Path projectRoot = projectRootFromSourcePath(editDirectoryPath);
                if (projectRoot == null) {
                    return null;
                }
                return new EditProjectLocation(projectRoot.getFileName().toString(), projectRoot);
            }
            String normalized = editProjectDirectory.replace('\\', '/').trim();
            if (normalized.isEmpty()) {
                return null;
            }
            while (normalized.startsWith("/")) {
                normalized = normalized.substring(1);
            }
            if (normalized.isEmpty()) {
                return null;
            }
            Path relative = Path.of(normalized);
            if (relative.getNameCount() == 0) {
                return null;
            }
            String projectName = relative.getName(0).toString();
            return new EditProjectLocation(projectName, parentDir.resolve(projectName));
        }

        /**
         * Convert a source folder path to its project root.
         *
         * @param editDirectoryPath path pointing into an edit project.
         * @return project root path.
         */
        private static Path projectRootFromSourcePath(Path editDirectoryPath) {
            // Normalize to project root when the directory points into src/*.
            int srcIndex = indexOfSegment(editDirectoryPath, "src");
            if (srcIndex > 0) {
                return resolvePathPrefix(editDirectoryPath, srcIndex);
            }
            return editDirectoryPath;
        }

        /**
         * Find the index of a path segment.
         *
         * @param path path to inspect.
         * @param segment segment to find.
         * @return index or {@code -1}.
         */
        private static int indexOfSegment(Path path, String segment) {
            for (int i = 0; i < path.getNameCount(); i++) {
                if (segment.equals(path.getName(i).toString())) {
                    return i;
                }
            }
            return -1;
        }

        /**
         * Resolve the prefix of a path up to a segment index.
         *
         * @param path original path.
         * @param endExclusive index of the first segment to exclude.
         * @return resolved prefix path.
         */
        private static Path resolvePathPrefix(Path path, int endExclusive) {
            Path root = path.getRoot();
            Path subpath = path.subpath(0, endExclusive);
            if (root == null) {
                return subpath;
            }
            return root.resolve(subpath);
        }

        /**
         * Load Eclipse formatter preferences from the generator classpath.
         *
         * @return formatter options map (empty when unavailable).
         */
        private static java.util.Map<String, String> loadFormatterPreferencesFromClasspath() {
            java.util.Properties properties = new java.util.Properties();
            try (java.io.InputStream stream = SiriusWebEMFGenerator.class
                    .getResourceAsStream("/org.eclipse.jdt.core.prefs")) {
                if (stream == null) {
                    warn("Formatter preferences not found on classpath: /org.eclipse.jdt.core.prefs");
                    return java.util.Map.of();
                }
                properties.load(stream);
            } catch (java.io.IOException exception) {
                warn("Failed to load formatter preferences: " + exception.getMessage());
                return java.util.Map.of();
            }
            java.util.Map<String, String> formatterOptions = new java.util.HashMap<>();
            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("org.eclipse.jdt.core.formatter.")) {
                    formatterOptions.put(key, properties.getProperty(key));
                }
            }
            return formatterOptions;
        }

        /**
         * Load project-specific formatter preferences.
         *
         * @param projectLocation model project metadata (optional).
         * @return formatter options map (empty when unavailable).
         */
        private static java.util.Map<String, String> loadFormatterPreferencesFromProject(
                ProjectLocation projectLocation) {
            if (projectLocation == null) {
                return java.util.Map.of();
            }
            Path prefsPath = projectLocation.projectRoot().resolve(".settings/org.eclipse.jdt.core.prefs");
            if (!Files.isRegularFile(prefsPath)) {
                return java.util.Map.of();
            }
            java.util.Properties properties = new java.util.Properties();
            try (java.io.InputStream stream = Files.newInputStream(prefsPath)) {
                properties.load(stream);
            } catch (IOException exception) {
                warn("Failed to load formatter preferences: " + prefsPath + " (" + exception.getMessage() + ")");
                return java.util.Map.of();
            }
            java.util.Map<String, String> formatterOptions = new java.util.HashMap<>();
            for (String key : properties.stringPropertyNames()) {
                if (key.startsWith("org.eclipse.jdt.core.formatter.")) {
                    formatterOptions.put(key, properties.getProperty(key));
                }
            }
            info("Loaded formatter preferences from " + prefsPath);
            return formatterOptions;
        }
    }

    /**
     * Register platform:/resource mappings for an EMF project.
     *
     * @param resourceSet shared resource set.
     * @param projectName project name.
     * @param projectRoot project root path.
     */
    private static void registerProjectMapping(ResourceSet resourceSet, String projectName, Path projectRoot) {
        URI platformProjectUri = URI.createURI("platform:/resource/" + projectName + "/");
        URI projectFileUri = URI.createFileURI(projectRoot.toString() + "/");
        resourceSet.getURIConverter().getURIMap().put(platformProjectUri, projectFileUri);
        info("Registered URI mapping: " + platformProjectUri + " -> " + projectFileUri);
    }

    /**
     * Write an informational message to standard output.
     *
     * @param message message to print.
     */
    private static void info(String message) {
        System.out.println(message);
    }

    /**
     * Write a warning message to standard error.
     *
     * @param message message to print.
     */
    private static void warn(String message) {
        System.err.println(message);
    }
}
