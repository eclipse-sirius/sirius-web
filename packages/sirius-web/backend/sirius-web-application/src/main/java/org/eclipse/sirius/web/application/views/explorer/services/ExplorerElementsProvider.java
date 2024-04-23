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
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerElementsProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeAlteredContentProvider;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IExplorerElementsProvider} for the Sirius Web Explorer view.
 *
 * @author arichard
 */
@Service
public class ExplorerElementsProvider implements IExplorerElementsProvider {

    private final List<IExplorerTreeAlteredContentProvider> alteredContentProviders;

    public ExplorerElementsProvider(List<IExplorerTreeAlteredContentProvider> alteredContentProviders) {
        this.alteredContentProviders = Objects.requireNonNull(alteredContentProviders);
    }

    @Override
    public List<Object> getElements(VariableManager variableManager) {
        List<Object> elements = new ArrayList<>();
        var optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        if (optionalEditingContext.isPresent()) {
            elements.addAll(this.getDefaultElements(variableManager));
            List<String> activeFilterIds = this.getActiveFilterIds(variableManager);

            var providers = this.alteredContentProviders.stream()
                    .filter(provider -> provider.canHandle(optionalEditingContext.get(), activeFilterIds))
                    .toList();

            for (IExplorerTreeAlteredContentProvider provider : providers) {
                elements = provider.apply(elements, variableManager);
            }
        }
        return elements;
    }

    private List<Resource> getDefaultElements(VariableManager variableManager) {
        var optionalEditingContext = Optional.of(variableManager.getVariables().get(IEditingContext.EDITING_CONTEXT));
        var optionalResourceSet = optionalEditingContext.filter(IEditingContext.class::isInstance)
                .filter(IEMFEditingContext.class::isInstance)
                .map(IEMFEditingContext.class::cast)
                .map(IEMFEditingContext::getDomain)
                .map(EditingDomain::getResourceSet);

        return optionalResourceSet.map(resourceSet -> resourceSet.getResources().stream()
                        .sorted(Comparator.nullsLast(Comparator.comparing(this::getResourceLabel, String.CASE_INSENSITIVE_ORDER)))
                        .toList()
                ).orElseGet(ArrayList::new);
    }

    private List<String> getActiveFilterIds(VariableManager variableManager) {
        List<String> activeFilterIds;
        Object objects = variableManager.getVariables().get(TreeRenderer.ACTIVE_FILTER_IDS);
        if (objects instanceof List<?> list) {
            activeFilterIds = list.stream()
                    .filter(String.class::isInstance)
                    .map(String.class::cast)
                    .toList();
        } else {
            activeFilterIds = new ArrayList<>();
        }
        return activeFilterIds;
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
