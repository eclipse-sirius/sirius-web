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
package org.eclipse.sirius.web.application.project.services;

import static org.eclipse.sirius.web.application.project.services.DefaultEditingContextActionProvider.EMPTY_ACTION_ID;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextActionHandler;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.springframework.stereotype.Service;

/**
 * Used to handle the default editing context actions.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultEditingContextActionHandler implements IEditingContextActionHandler {
    @Override
    public boolean canHandle(IEditingContext editingContext, String actionId) {
        return EMPTY_ACTION_ID.equals(actionId);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, String actionId) {
        return switch (actionId) {
            case EMPTY_ACTION_ID -> this.createEmptyDocument(editingContext);
            default -> new Failure("Unknown action");
        };
    }

    private IStatus createEmptyDocument(IEditingContext editingContext) {
        return this.getResourceSet(editingContext).map(resourceSet -> {
            JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
            resource.eAdapters().add(new ResourceMetadataAdapter("Others..."));
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
