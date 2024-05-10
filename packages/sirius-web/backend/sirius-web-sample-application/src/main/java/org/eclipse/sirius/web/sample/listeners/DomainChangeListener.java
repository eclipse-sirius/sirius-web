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
package org.eclipse.sirius.web.sample.listeners;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.collaborative.dto.RepresentationRefreshedEvent;
import org.eclipse.sirius.components.core.api.IEditingContextSearchService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.emf.DomainConverter;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.web.persistence.repositories.IDocumentRepository;
import org.eclipse.sirius.web.services.editingcontext.EditingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * The domain change listener that updates a corresponding EPackages in an editing context.
 * Models based on these EPackages are reloaded as well.
 *
 * @author aresekb
 */
@Component
@ConditionalOnProperty(prefix = "org.eclipse.sirius.web.features", name = "studioDefinition")
public class DomainChangeListener {

    private final Logger logger = LoggerFactory.getLogger(DomainChangeListener.class);

    private final IEditingContextSearchService editingContextSearchService;

    private final IObjectService objectService;

    private final IDocumentRepository documentRepository;

    public DomainChangeListener(IEditingContextSearchService editingContextSearchService, IObjectService objectService, IDocumentRepository documentRepository) {
        this.editingContextSearchService = editingContextSearchService;
        this.objectService = objectService;
        this.documentRepository = documentRepository;
    }

    @EventListener
    public synchronized void handleRepresentationRefreshedEvent(RepresentationRefreshedEvent event) {
        var editingContextId = event.getProjectId();
        var optionalEditingContext = this.editingContextSearchService.findById(editingContextId);
        if (optionalEditingContext.isEmpty()) {
            this.logger.warn("Editing context {} not found", editingContextId);
            return;
        }
        var editingContext = (EditingContext) optionalEditingContext.get();

        var targetObjectId = event.getRepresentation().getTargetObjectId();
        var optionalTargetObject = this.objectService.getObject(editingContext, targetObjectId);
        if (optionalTargetObject.isEmpty()) {
            return;
        }
        var targetObject = optionalTargetObject.get();

        if (targetObject instanceof EObject eObject) {
            var optionalDomain = EMFHelpers.findContainer(eObject, Domain.class);
            if (optionalDomain.isPresent()) {
                this.reloadDocuments(editingContext, optionalDomain.get());
            }
        }
    }

    private void reloadDocuments(EditingContext editingContext, Domain domain) {
        var resourceSet = editingContext.getDomain().getResourceSet();
        var projectId = UUID.fromString(editingContext.getId());
        var ePackages = new DomainConverter().convert(List.of(domain)).toList();
        for (var ePackage : ePackages) {
            resourceSet.getPackageRegistry().put(ePackage.getNsURI(), ePackage);
            var documentEntities = this.documentRepository.findAllByType(ePackage.getName(), ePackage.getNsURI()).stream()
                    .filter(documentEntity -> projectId.equals(documentEntity.getProject().getId()))
                    .toList();
            for (var documentEntity : documentEntities) {
                var resource = new JSONResourceFactory().createResourceFromPath(documentEntity.getId().toString());
                try (var inputStream = new ByteArrayInputStream(documentEntity.getContent().getBytes())) {
                    resourceSet.getResources().removeIf(res -> resource.getURI().equals(res.getURI()));
                    resourceSet.getResources().add(resource);
                    resource.load(inputStream, null);
                    resource.eAdapters().add(new ResourceMetadataAdapter(documentEntity.getName()));
                } catch (IOException | IllegalArgumentException exception) {
                    this.logger.warn("An error occured while loading document {}: {}.", documentEntity.getId(), exception.getMessage());
                    resourceSet.getResources().remove(resource);
                }
            }
        }
    }
}
