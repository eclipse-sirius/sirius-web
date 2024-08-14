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
package org.eclipse.sirius.components.collaborative.widget.reference.browser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.collaborative.widget.reference.browser.api.IModelBrowserNavigationService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.springframework.stereotype.Service;

/**
 * Services for the navigation through the Widget Reference Tree.
 *
 * @author frouene
 */
@Service
public class ModelBrowserNavigationService implements IModelBrowserNavigationService {

    private final IIdentityService identityService;

    private final IObjectSearchService objectSearchService;


    public ModelBrowserNavigationService(IIdentityService identityService, IObjectSearchService objectSearchService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public List<String> getAncestors(IEditingContext editingContext, String treeItemId) {
        List<String> ancestorsIds = new ArrayList<>();

        var optionalSemanticObject = this.objectSearchService.getObject(editingContext, treeItemId);

        Optional<Object> optionalObject = Optional.empty();
        if (optionalSemanticObject.isPresent()) {
            // The first parent of a semantic object item is the item for its actual container
            optionalObject = optionalSemanticObject.filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .map(eObject -> Optional.<Object>ofNullable(eObject.eContainer()).orElse(eObject.eResource()));
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
