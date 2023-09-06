/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.EditingContext;
import org.eclipse.sirius.components.widget.reference.IReferenceWidgetRootCandidateSearchProvider;
import org.springframework.stereotype.Service;

/**
 *
 * Default implementation of {@link IReferenceWidgetRootCandidateSearchProvider}.
 * This implementation returns all root resources from the current model.
 *
 * @author Arthur Daussy
 */
@Service
public class ReferenceWidgetDefaultCandidateSearchProvider implements IReferenceWidgetRootCandidateSearchProvider {

    @Override
    public boolean canHandle(String descriptionId) {
        return true;
    }

    @Override
    public List<? extends Object> getRootElementsForReference(Object targetElement, String descriptionId, IEditingContext editingContext) {
        var optionalResourceSet = Optional.of(editingContext)
                .filter(EditingContext.class::isInstance)
                .map(EditingContext.class::cast)
                .map(EditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        if (optionalResourceSet.isPresent()) {
            var resourceSet = optionalResourceSet.get();
            return resourceSet.getResources().stream()
                    .filter(res -> res.getURI() != null && EditingContext.RESOURCE_SCHEME.equals(res.getURI().scheme()))
                    .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                    .toList();
        }

        return List.of();
    }

    private String getResourceLabel(Resource resource) {
        return resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName)
                .orElse(resource.getURI().lastSegment());
    }
}
