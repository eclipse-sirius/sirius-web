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
package org.eclipse.sirius.web.application.studio.services;

import static org.eclipse.sirius.web.application.studio.services.StudioEditingContextActionProvider.EMPTY_DOMAIN_ID;
import static org.eclipse.sirius.web.application.studio.services.StudioEditingContextActionProvider.EMPTY_VIEW_ID;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.DomainFactory;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.studio.services.api.IDomainNameProvider;
import org.springframework.stereotype.Service;

/**
 * Used to handle the studio related editing context actions.
 *
 * @author sbegaudeau
 */
@Service
public class StudioEditingContextActionHandler implements IEditingContextActionHandler {

    private final IDomainNameProvider domainNameProvider;

    public StudioEditingContextActionHandler(IDomainNameProvider domainNameProvider) {
        this.domainNameProvider = Objects.requireNonNull(domainNameProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, String actionId) {
        return List.of(EMPTY_DOMAIN_ID, EMPTY_VIEW_ID).contains(actionId);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, String actionId) {
        return switch (actionId) {
            case EMPTY_DOMAIN_ID -> this.createEmptyDomainDocument(editingContext);
            case EMPTY_VIEW_ID -> this.createEmptyViewDocument(editingContext);
            default -> new Failure("Unknown action");
        };
    }

    private IStatus createEmptyDomainDocument(IEditingContext editingContext) {
        return this.getResourceSet(editingContext).map(resourceSet -> {
            JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
            Domain domain = DomainFactory.eINSTANCE.createDomain();
            domain.setName(this.domainNameProvider.getSampleDomainName());
            resource.getContents().add(domain);
            resource.eAdapters().add(new ResourceMetadataAdapter("Domain"));
            resourceSet.getResources().add(resource);

            return (IStatus) new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
        }).orElse(new Failure("Unsupported editing context"));
    }

    private IStatus createEmptyViewDocument(IEditingContext editingContext) {
        return this.getResourceSet(editingContext).map(resourceSet -> {
            View newView = ViewFactory.eINSTANCE.createView();
            DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
            diagramDescription.setName("New Diagram Description");
            newView.getDescriptions().add(diagramDescription);

            JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
            resource.getContents().add(newView);
            resource.eAdapters().add(new ResourceMetadataAdapter("View"));
            resourceSet.getResources().add(resource);

            return (IStatus) new Success(ChangeKind.SEMANTIC_CHANGE, Map.of());
        }).orElse(new Failure("Unsupported editing context"));
    }

    private Optional<ResourceSet> getResourceSet(IEditingContext editingContext) {
        return Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(AdapterFactoryEditingDomain::getResourceSet);
    }
}
