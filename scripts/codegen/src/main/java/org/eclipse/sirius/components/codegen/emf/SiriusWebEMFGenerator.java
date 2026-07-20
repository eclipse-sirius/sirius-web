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

import java.nio.file.Path;
import java.util.List;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.codegen.emf.internal.EditProjectLocationResolver;
import org.eclipse.sirius.components.codegen.emf.internal.EmfResourceSetFactory;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratedEditProjectCleaner;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratedModelProjectCleaner;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratorArguments;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratorArgumentsParser;
import org.eclipse.sirius.components.codegen.emf.internal.GeneratorFormattingConfigurer;
import org.eclipse.sirius.components.codegen.emf.internal.GenmodelFinder;
import org.eclipse.sirius.components.codegen.emf.internal.GenmodelGenerationService;
import org.eclipse.sirius.components.codegen.emf.internal.GenmodelLoader;
import org.eclipse.sirius.components.codegen.emf.internal.GenmodelValidator;
import org.eclipse.sirius.components.codegen.emf.internal.LoadedGenmodel;
import org.eclipse.sirius.components.codegen.emf.internal.PluginPropertiesMerger;
import org.eclipse.sirius.components.codegen.emf.internal.ProjectLocationResolver;
import org.eclipse.sirius.components.codegen.emf.internal.ProjectUriMapper;
import org.eclipse.sirius.components.codegen.emf.internal.SvgIconInitializer;

/** EMF code generation entry point for the Sirius Web repository. */
public final class SiriusWebEMFGenerator {
    private final GeneratorArgumentsParser argumentsParser;
    private final GenmodelFinder genmodelFinder;
    private final EmfResourceSetFactory resourceSetFactory;
    private final GenmodelLoader genmodelLoader;
    private final GenmodelValidator genmodelValidator;
    private final GenmodelGenerationService generationService;

    SiriusWebEMFGenerator() {
        ProjectUriMapper projectUriMapper = new ProjectUriMapper();
        this.argumentsParser = new GeneratorArgumentsParser();
        this.genmodelFinder = new GenmodelFinder();
        this.resourceSetFactory = new EmfResourceSetFactory();
        this.genmodelLoader = new GenmodelLoader(new ProjectLocationResolver(), projectUriMapper);
        this.genmodelValidator = new GenmodelValidator();
        this.generationService = new GenmodelGenerationService(projectUriMapper,
                new EditProjectLocationResolver(), new GeneratorFormattingConfigurer(),
                new GeneratedModelProjectCleaner(),
                new GeneratedEditProjectCleaner(new PluginPropertiesMerger(), new SvgIconInitializer()));
    }

    /** Run the generator from the command line. */
    public static void main(String[] args) {
        new SiriusWebEMFGenerator().run(args);
    }

    void run(String[] args) {
        GeneratorArguments arguments = this.argumentsParser.parse(args);
        List<Path> genmodels = this.genmodelFinder.find(arguments.repositoryRoot(), arguments.genmodelPattern());
        ResourceSet resourceSet = this.resourceSetFactory.create();
        List<LoadedGenmodel> loadedGenmodels = this.genmodelLoader.loadAll(resourceSet, arguments.repositoryRoot(), genmodels);
        this.printSummary(arguments, genmodels);
        System.out.printf("Genmodels loaded: %d%n", loadedGenmodels.size());
        this.generationService.generateAll(this.genmodelValidator.validateAll(resourceSet, loadedGenmodels));
    }

    private void printSummary(GeneratorArguments arguments, List<Path> genmodels) {
        System.out.printf("Repository root:  %s%n", arguments.repositoryRoot());
        System.out.printf("Genmodel filter:  %s%n", arguments.genmodelPattern());
        System.out.printf("Genmodels found:  %d%n", genmodels.size());
        genmodels.forEach(System.out::println);
    }
}
