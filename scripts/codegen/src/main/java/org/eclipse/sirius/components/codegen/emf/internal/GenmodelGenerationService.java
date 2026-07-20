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

package org.eclipse.sirius.components.codegen.emf.internal;

import java.nio.file.Files;
import java.util.List;

import org.eclipse.emf.codegen.ecore.generator.Generator;
import org.eclipse.emf.codegen.ecore.genmodel.GenModel;
import org.eclipse.emf.codegen.ecore.genmodel.GenModelPackage;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenBaseGeneratorAdapter;
import org.eclipse.emf.codegen.ecore.genmodel.generator.GenModelGeneratorAdapterFactory;
import org.eclipse.emf.common.util.BasicMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;

public final class GenmodelGenerationService {
    private final ProjectUriMapper projectUriMapper;
    private final EditProjectLocationResolver editProjectLocationResolver;
    private final GeneratorFormattingConfigurer formattingConfigurer;
    private final GeneratedModelProjectCleaner modelProjectCleaner;
    private final GeneratedEditProjectCleaner editProjectCleaner;

    public GenmodelGenerationService(ProjectUriMapper projectUriMapper, EditProjectLocationResolver editProjectLocationResolver,
            GeneratorFormattingConfigurer formattingConfigurer, GeneratedModelProjectCleaner modelProjectCleaner,
            GeneratedEditProjectCleaner editProjectCleaner) {
        this.projectUriMapper = projectUriMapper;
        this.editProjectLocationResolver = editProjectLocationResolver;
        this.formattingConfigurer = formattingConfigurer;
        this.modelProjectCleaner = modelProjectCleaner;
        this.editProjectCleaner = editProjectCleaner;
    }

    public void generateAll(List<LoadedGenmodel> genmodels) {
        for (LoadedGenmodel entry : genmodels) {
            this.generate(entry);
        }
    }

    private void generate(LoadedGenmodel entry) {
        Resource resource = entry.resource();
        GenModel genModel = this.findGenModel(resource);
        if (genModel == null) {
            System.err.println("No GenModel root found in " + resource.getURI());
            return;
        }
        Generator generator = new Generator();
        generator.getAdapterFactoryDescriptorRegistry()
                .addDescriptor(GenModelPackage.eNS_URI, GenModelGeneratorAdapterFactory.DESCRIPTOR);
        genModel.reconcile();
        genModel.setCanGenerate(true);
        genModel.setDynamicTemplates(false);
        genModel.setCodeFormatting(true);
        genModel.setCommentFormatting(true);
        genModel.setCleanup(true);
        generator.setInput(genModel);
        this.formattingConfigurer.configure(generator, resource.getResourceSet(), entry.projectLocation());
        EditProjectLocation editProject = this.editProjectLocationResolver.resolve(entry.projectLocation(), genModel);
        if (editProject != null && Files.isDirectory(editProject.projectRoot())) {
            this.projectUriMapper.registerProject(resource.getResourceSet(), editProject.projectName(), editProject.projectRoot());
        }
        System.out.println("Generating MODEL for " + resource.getURI());
        generator.generate(genModel, GenBaseGeneratorAdapter.MODEL_PROJECT_TYPE, new BasicMonitor.Printing(System.out));
        if (entry.projectLocation() != null) {
            this.modelProjectCleaner.cleanup(entry.projectLocation().projectRoot());
        }
        System.out.println("Generating EDIT for " + resource.getURI());
        generator.generate(genModel, GenBaseGeneratorAdapter.EDIT_PROJECT_TYPE, new BasicMonitor.Printing(System.out));
        if (editProject != null) {
            this.editProjectCleaner.cleanup(editProject.projectRoot());
        }
    }

    private GenModel findGenModel(Resource resource) {
        for (EObject root : resource.getContents()) {
            if (root instanceof GenModel genModel) {
                return genModel;
            }
        }
        return null;
    }
}
