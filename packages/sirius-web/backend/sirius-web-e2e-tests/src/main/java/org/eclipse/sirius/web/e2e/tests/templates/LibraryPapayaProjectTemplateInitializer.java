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
package org.eclipse.sirius.web.e2e.tests.templates;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.components.papaya.Component;
import org.eclipse.sirius.components.papaya.Interface;
import org.eclipse.sirius.components.papaya.Package;
import org.eclipse.sirius.components.papaya.PapayaFactory;
import org.eclipse.sirius.components.papaya.Project;
import org.eclipse.sirius.web.application.library.services.LibraryMetadataAdapter;
import org.eclipse.sirius.web.application.project.services.api.IProjectTemplateInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

/**
 * Used to create a papaya test project with a dependency to a library.
 * <p>
 * The project contains a single package with an interface that extends an interface from the Java@0.0.3 library.
 * </p>
 *
 * @author gdaniel
 */
@Profile("test")
@Service
public class LibraryPapayaProjectTemplateInitializer implements IProjectTemplateInitializer {

    private final Logger logger = LoggerFactory.getLogger(LibraryPapayaProjectTemplateInitializer.class);

    @Override
    public boolean canHandle(String projectTemplateId) {
        return LibraryPapayaTemplatesProvider.LIBRARY_PAPAYA_TEMPLATE_ID.equals(projectTemplateId);
    }

    @Override
    public Optional<RepresentationMetadata> handle(ICause cause, String projectTemplateId, IEditingContext editingContext) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            var documentId = UUID.randomUUID();
            var resource = new JSONResourceFactory().createResourceFromPath(documentId.toString());
            var resourceMetadataAdapter = new ResourceMetadataAdapter("Papaya");
            resource.eAdapters().add(resourceMetadataAdapter);
            emfEditingContext.getDomain().getResourceSet().getResources().add(resource);

            var project = PapayaFactory.eINSTANCE.createProject();
            project.setName("Project");

            var papayaPackage = PapayaFactory.eINSTANCE.createPackage();
            papayaPackage.setName("Package");
            project.getElements().add(papayaPackage);

            var papayaInterface = PapayaFactory.eINSTANCE.createInterface();
            papayaInterface.setName("Interface");
            Optional<Interface> optionalMapInterface = this.getJavaUtilMapInterface(emfEditingContext.getDomain().getResourceSet());
            if (optionalMapInterface.isPresent()) {
                papayaInterface.getExtends().add(optionalMapInterface.get());
            } else {
                this.logger.error("Cannot find the Map interface from the Java library");
            }
            papayaPackage.getTypes().add(papayaInterface);

            resource.getContents().add(project);
        }
        return Optional.empty();
    }

    private Optional<Interface> getJavaUtilMapInterface(ResourceSet resourceSet) {
        Optional<Resource> optionalJavaResource = resourceSet.getResources().stream()
            .filter(resource -> resource.eAdapters().stream()
                    .filter(LibraryMetadataAdapter.class::isInstance)
                    .map(LibraryMetadataAdapter.class::cast)
                    .anyMatch(libraryMetadataAdapter -> Objects.equals(libraryMetadataAdapter.getNamespace(), "papaya")
                            && Objects.equals(libraryMetadataAdapter.getName(), "java")
                            && Objects.equals(libraryMetadataAdapter.getVersion(), "0.0.3")))
            .findFirst();
        if (optionalJavaResource.isPresent()) {
            Resource javaResource = optionalJavaResource.get();
            return javaResource.getContents().stream()
                .filter(Project.class::isInstance)
                .map(Project.class::cast)
                .flatMap(project -> project.getElements().stream())
                .filter(Component.class::isInstance)
                .map(Component.class::cast)
                .flatMap(component -> component.getPackages().stream())
                .filter(Package.class::isInstance)
                .map(Package.class::cast)
                .filter(papayaPackage -> Objects.equals(papayaPackage.getName(), "java.util"))
                .flatMap(papayaPackage -> papayaPackage.getTypes().stream())
                .filter(Interface.class::isInstance)
                .map(Interface.class::cast)
                .filter(papayaInterface -> Objects.equals(papayaInterface.getName(), "Map"))
                .findFirst();
        }
        return Optional.empty();
    }

}
