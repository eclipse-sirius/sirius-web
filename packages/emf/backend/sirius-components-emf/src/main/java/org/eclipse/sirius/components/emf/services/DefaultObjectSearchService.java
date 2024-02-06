/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.collaborative.api.IRepresentationSearchService;
import org.eclipse.sirius.components.core.api.IDefaultObjectSearchService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.springframework.stereotype.Service;

/**
 * Default implementation of the IDefaultObjectService.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
@Service
public class DefaultObjectSearchService implements IDefaultObjectSearchService {
    private static final String ID_SEPARATOR = "#";

    private final IRepresentationSearchService representationSearchService;

    public DefaultObjectSearchService(IRepresentationSearchService representationSearchService) {
        this.representationSearchService = Objects.requireNonNull(representationSearchService);
    }

    @Override
    public Optional<Object> getObject(IEditingContext editingContext, String objectId) {
        var optionalObject = Optional.of(editingContext)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet)
                .flatMap(resourceSet -> this.getEObject(resourceSet, objectId));

        return optionalObject.or(() -> this.getRepresentation(editingContext, objectId));
    }

    private Optional<Object> getEObject(ResourceSet resourceSet, String objectId) {
        Optional<EObject> optionalEObject = Optional.empty();

        int index = objectId.indexOf(ID_SEPARATOR);
        if (index != -1) {
            String resourceLastSegment = objectId.substring(0, index);
            String eObjectURIFragment = objectId.substring(index + ID_SEPARATOR.length());
            optionalEObject = resourceSet.getResources().stream()
                    .filter(resource -> resourceLastSegment.equals(resource.getURI().lastSegment())).findFirst()
                    .map(resource -> resource.getEObject(eObjectURIFragment));
        } else {
            optionalEObject = resourceSet.getResources().stream()
                    .flatMap(resource -> Optional.ofNullable(resource.getEObject(objectId)).stream())
                    .findFirst();
        }

        // If not found in the resources of the ResourceSet, we search in the PackageRegistry resources
        if (optionalEObject.isEmpty()) {
            URI uri = URI.createURI(objectId);
            if (uri.hasFragment()) {
                EObject eObject = resourceSet.getEObject(uri, false);
                optionalEObject = Optional.ofNullable(eObject);
            }
        }
        return optionalEObject.map(Object.class::cast);
    }

    private Optional<Object> getRepresentation(IEditingContext editingContext, String representationId) {
        if (representationId != null && !representationId.isBlank()) {
            return this.representationSearchService.findById(editingContext, representationId, IRepresentation.class)
                    .map(Object.class::cast);
        }
        return Optional.empty();
    }
}
