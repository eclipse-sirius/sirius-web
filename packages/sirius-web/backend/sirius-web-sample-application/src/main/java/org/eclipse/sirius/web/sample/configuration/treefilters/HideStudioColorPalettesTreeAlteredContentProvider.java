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
package org.eclipse.sirius.web.sample.configuration.treefilters;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.util.services.ColorPaletteService;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.explorer.api.IExplorerTreeAlteredContentProvider;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.springframework.stereotype.Service;

/**
 * Sample {@link IExplorerTreeAlteredContentProvider} for the Hide In-memory Studio Color Palettes tree filter.
 *
 * @author arichard
 */
@Service
public class HideStudioColorPalettesTreeAlteredContentProvider implements IExplorerTreeAlteredContentProvider {

    private final IEditingContextMetadataProvider editingContextMetadataProvider;

    public HideStudioColorPalettesTreeAlteredContentProvider(IEditingContextMetadataProvider editingContextMetadataProvider) {
        this.editingContextMetadataProvider = Objects.requireNonNull(editingContextMetadataProvider);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, List<String> activeFilterIds) {
        var isStudioProjectNature = this.editingContextMetadataProvider.getMetadata(editingContext.getId())
                .natures()
                .stream()
                .map(Nature::natureId)
                .anyMatch("siriusComponents://nature?kind=studio"::equals);
        return isStudioProjectNature && activeFilterIds.contains(SampleTreeFilterProvider.HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID);
    }

    @Override
    public List<Object> apply(List<Object> computedElements, VariableManager variableManager) {
        return computedElements.stream()
                .filter(element -> !(element instanceof Resource res && res.getURI() != null && ColorPaletteService.SIRIUS_STUDIO_COLOR_PALETTES_URI.equals(res.getURI().toString())))
                .toList();
    }
}
