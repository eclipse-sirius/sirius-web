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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.trees.renderer.TreeRenderer;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerContentService;
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

    private final IExplorerContentService explorerContentService;

    public ExplorerElementsProvider(List<IExplorerTreeAlteredContentProvider> alteredContentProviders, IExplorerContentService explorerContentService) {
        this.alteredContentProviders = Objects.requireNonNull(alteredContentProviders);
        this.explorerContentService = Objects.requireNonNull(explorerContentService);
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
        if (optionalEditingContext.isPresent()) {
            var editingContext = optionalEditingContext.get();
            return this.explorerContentService.getContents(editingContext);
        }
        return List.of();
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
