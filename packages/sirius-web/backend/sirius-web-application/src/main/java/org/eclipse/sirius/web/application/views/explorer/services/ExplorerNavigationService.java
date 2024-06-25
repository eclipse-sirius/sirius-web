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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerNavigationService;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataMetadataOnly;
import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api.IRepresentationDataSearchService;
import org.springframework.stereotype.Service;

/**
 * Services for the navigation through the Sirius Web Explorer.
 *
 * @author arichard
 */
@Service
public class ExplorerNavigationService implements IExplorerNavigationService {

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;

    private final IRepresentationDataSearchService representationDataSearchService;

    public ExplorerNavigationService(IIdentityService identityService, IObjectSearchService objectSearchService, IRepresentationDataSearchService representationDataSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.representationDataSearchService = Objects.requireNonNull(representationDataSearchService);
    }

    @Override
    public List<String> getAncestors(IEditingContext editingContext, String treeItemId) {
        List<String> ancestorsIds = new ArrayList<>();

        var optionalRepresentationMetadata = new UUIDParser().parse(treeItemId).flatMap(this.representationDataSearchService::findMetadataById);
        var optionalSemanticObject = this.objectSearchService.getObject(editingContext, treeItemId);

        Optional<Object> optionalObject = Optional.empty();
        if (optionalRepresentationMetadata.isPresent()) {
            // The first parent of a representation item is the item for its targetObject.
            optionalObject = optionalRepresentationMetadata.map(RepresentationDataMetadataOnly::targetObjectId)
                    .flatMap(objectId -> this.objectSearchService.getObject(editingContext, objectId));
        } else if (optionalSemanticObject.isPresent()) {
            // The first parent of a semantic object item is the item for its actual container
            optionalObject = optionalSemanticObject.filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .map(eObject -> Optional.<Object> ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
        }

        while (optionalObject.isPresent()) {
            ancestorsIds.add(this.getItemId(optionalObject.get()));
            optionalObject = optionalObject
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
        }
        return ancestorsIds;
    }

    private String getItemId(Object object) {
        String result = null;
        if (object instanceof Resource resource) {
            result = resource.getURI().path().substring(1);
        } else if (object instanceof EObject) {
            result = this.identityService.getId(object);
        }
        return result;
    }
}
