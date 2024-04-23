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

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerTreeAlteredContentProvider;
import org.springframework.stereotype.Service;

/**
 * Used to hide in-memory studio color palettes from the explorer.
 *
 * @author arichard
 */
@Service
public class HideStudioColorPalettesTreeAlteredContentProvider implements IExplorerTreeAlteredContentProvider {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public HideStudioColorPalettesTreeAlteredContentProvider(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, List<String> activeFilterIds) {
        var isStudio = this.studioCapableEditingContextPredicate.test(editingContext);
        return isStudio && activeFilterIds.contains(StudioExplorerTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID);
    }

    @Override
    public List<Object> apply(List<Object> computedElements, VariableManager variableManager) {
        return computedElements.stream()
                .filter(element -> !(element instanceof Resource resource && resource.getURI() != null && ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(resource.getURI().toString())))
                .toList();
    }
}
