/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IContentService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerElementsProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeAlteredContentProvider;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Implementation of {@link IExplorerElementsProvider} for the Sirius Web Explorer view.
 *
 * @author arichard
 */
@Service
public class ExplorerElementsProvider implements IExplorerElementsProvider {

    private final List<IExplorerTreeAlteredContentProvider> alteredContentProviders;

    private final IContentService contentService;

    private final ILabelService labelService;

    public ExplorerElementsProvider(List<IExplorerTreeAlteredContentProvider> alteredContentProviders, IContentService contentService, ILabelService labelService) {
        this.alteredContentProviders = Objects.requireNonNull(alteredContentProviders);
        this.contentService = Objects.requireNonNull(contentService);
        this.labelService = Objects.requireNonNull(labelService);
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

    private List<Object> getDefaultElements(VariableManager variableManager) {
        Optional<IEditingContext> optionalEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);
        var resources = new ArrayList<Resource>();
        var elements = new ArrayList<>();
        this.contentService.getContents(optionalEditingContext.orElse(null)).forEach(content -> {
            if (content instanceof Resource resource) {
                resources.add(resource);
            }
            else {
                elements.add(content);
            }
        });
        //We sort the elements as it was done by the former IExplorerServices#getDefaultElements service.
        resources.sort(Comparator.nullsLast(Comparator.comparing(resource -> this.labelService.getStyledLabel(resource).toString(), String.CASE_INSENSITIVE_ORDER)));
        elements.addAll(resources);
        return elements;
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

}
