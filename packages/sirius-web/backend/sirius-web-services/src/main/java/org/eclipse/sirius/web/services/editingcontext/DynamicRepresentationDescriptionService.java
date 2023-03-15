/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
import java.util.stream.Stream;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.emf.ecore.util.ECrossReferenceAdapter;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.persistence.entities.DocumentEntity;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.editingcontext.api.IDynamicRepresentationDescriptionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service to discover diagram descriptions dynamically from the existing user-defined documents.
 *
 * @author pcdavid
 */
@Service
public class DynamicRepresentationDescriptionService implements IDynamicRepresentationDescriptionService {
    private final Logger logger = LoggerFactory.getLogger(DynamicRepresentationDescriptionService.class);

    private final IDocumentRepository documentRepository;

    private final EPackage.Registry ePackageRegistry;

    private final IViewConverter viewConverter;

    private final boolean isStudioDefinitionEnabled;

    public DynamicRepresentationDescriptionService(IDocumentRepository documentRepository, EPackage.Registry ePackageRegistry, IViewConverter viewConverter, @Value("${org.eclipse.sirius.web.features.studioDefinition:false}") boolean isStudioDefinitionEnabled) {
        this.documentRepository = Objects.requireNonNull(documentRepository);
        this.ePackageRegistry = Objects.requireNonNull(ePackageRegistry);
        this.viewConverter = Objects.requireNonNull(viewConverter);
        this.isStudioDefinitionEnabled = isStudioDefinitionEnabled;
    }

    @Override
    public List<IRepresentationDescription> findDynamicRepresentationDescriptions(String editingContextId, EditingDomain editingDomain) {
        List<IRepresentationDescription> dynamicRepresentationDescriptions = new ArrayList<>();
        if (this.isStudioDefinitionEnabled) {
            List<View> views = new ArrayList<>();
            List<EPackage> accessibleEPackages = this.getAccessibleEPackages(editingDomain);
            ResourceSet resourceSet = this.createResourceSet(this.ePackageRegistry);
            this.documentRepository.findAllByType(ViewPackage.eNAME, ViewPackage.eNS_URI).forEach(documentEntity -> {
                Resource resource = this.loadDocumentAsEMF(documentEntity, resourceSet);
                views.addAll(this.getViewDefinitions(resource).toList());
            });
            // @formatter:off
            views.forEach(view -> this.viewConverter.convert(views, accessibleEPackages).stream()
                    .filter(Objects::nonNull)
                    .forEach(dynamicRepresentationDescriptions::add));
            // @formatter:on
        }
        return dynamicRepresentationDescriptions;
    }

    private List<EPackage> getAccessibleEPackages(EditingDomain editingDomain) {
        var packageRegistry = editingDomain.getResourceSet().getPackageRegistry();

        return packageRegistry.values().stream()
                .filter(EPackage.class::isInstance)
                .map(EPackage.class::cast)
                .toList();
    }

    private Stream<View> getViewDefinitions(Resource resource) {
        return resource.getContents().stream().filter(View.class::isInstance).map(View.class::cast);
    }

    private Resource loadDocumentAsEMF(DocumentEntity documentEntity, ResourceSet resourceSet) {
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
        resourceSet.getResources().add(resource);
        try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
            resource.load(inputStream, null);
        } catch (IOException | IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return resource;
    }

    private ResourceSet createResourceSet(EPackage.Registry packageRegistry) {
        ResourceSet resourceSet = new ResourceSetImpl();
        resourceSet.eAdapters().add(new ECrossReferenceAdapter());
        resourceSet.setPackageRegistry(packageRegistry);
        return resourceSet;
    }
}