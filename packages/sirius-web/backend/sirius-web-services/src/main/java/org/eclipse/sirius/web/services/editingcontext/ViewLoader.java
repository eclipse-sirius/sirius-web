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
package org.eclipse.sirius.web.services.editingcontext;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.editingcontext.api.IViewLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

/**
 * Used to load view models properly.
 *
 * @author sbegaudeau
 */
@Service
public class ViewLoader implements IViewLoader {

    private final IDocumentRepository documentRepository;

    private final EPackage.Registry ePackageRegistry;

    private final boolean isStudioDefinitionEnabled;

    private final Logger logger = LoggerFactory.getLogger(ViewLoader.class);

    public ViewLoader(IDocumentRepository documentRepository, EPackage.Registry ePackageRegistry, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean isStudioDefinitionEnabled) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        this.isStudioDefinitionEnabled = isStudioDefinitionEnabled;
    }


    @Override
    public List<View> load() {
        List<View> views = new ArrayList<>();

        if (this.isStudioDefinitionEnabled) {
            var resourceSet = this.createResourceSet();
            this.loadStudioColorPalettes(resourceSet);

            this.documentRepository.findAllByType(ViewPackage.eNAME, ViewPackage.eNS_URI).forEach(documentEntity -> {
                Resource resource = this.loadDocument(documentEntity, resourceSet);
                views.addAll(this.getViewDefinitions(resource).toList());
            });
        }

        return views;
    }

    private ResourceSet createResourceSet() {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());
        resourceSet.setPackageRegistry(this.ePackageRegistry);
        return resourceSet;
    }

    private void loadStudioColorPalettes(ResourceSet resourceSet) {
        ClassPathResource classPathResource = new ClassPathResource("studioColorPalettes.json");
        URI uri = URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///" + UUID.nameUUIDFromBytes(classPathResource.getPath().getBytes()));
        Resource resource = new JSONResourceFactory().createResource(uri);
        try (var inputStream = new ByteArrayInputStream(classPathResource.getContentAsByteArray())) {
            resourceSet.getResources().add(resource);
            resource.load(inputStream, null);
            resource.eAdapters().add(new ResourceMetadataAdapter("studioColorPalettes"));
        } catch (IOException exception) {
            this.logger.warn("An error occured while loading document studioColorPalettes.json: {}.", exception.getMessage());
            resourceSet.getResources().remove(resource);
        }
    }

    private Resource loadDocument(DocumentEntity documentEntity, ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
        resourceSet.getResources().add(resource);
        try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
            resource.load(inputStream, null);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return resource;
    }

    private Stream<View> getViewDefinitions(Resource resource) {
        return resource.getContents().stream()
                .filter(View.class::isInstance)
                .map(View.class::cast);
    }
}
