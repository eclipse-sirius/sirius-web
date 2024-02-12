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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeFilterProvider;
import org.eclipse.sirius.components.collaborative.trees.api.TreeFilter;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.services.api.projects.Nature;
import org.eclipse.sirius.web.services.projects.api.IEditingContextMetadataProvider;
import org.springframework.stereotype.Service;

/**
 * A sample {@link ITreeFilterProvider} for Sirius Web.
 *
 * @author arichard
 */
@Service
public class SampleTreeFilterProvider implements ITreeFilterProvider {

    public static final String HIDE_REPRESENTATIONS_TREE_FILTER_ID = UUID.randomUUID().toString();

    public static final String HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID = UUID.randomUUID().toString();

    private final IEditingContextMetadataProvider editingContextMetadataProvider;

    public SampleTreeFilterProvider(IEditingContextMetadataProvider editingContextMetadataProvider) {
        this.editingContextMetadataProvider = Objects.requireNonNull(editingContextMetadataProvider);
    }

    @Override
    public List<TreeFilter> get(String editingContextId, TreeDescription treeDescription, String representationId) {
        List<TreeFilter> filters = new ArrayList<>();
        filters.add(new TreeFilter(HIDE_REPRESENTATIONS_TREE_FILTER_ID, "Hide Representations", false));

        var isStudioProjectNature = this.editingContextMetadataProvider.getMetadata(editingContextId)
                .natures()
                .stream()
                .map(Nature::natureId)
                .anyMatch("siriusComponents://nature?kind=studio"::equals);
        if (isStudioProjectNature) {
            filters.add(new TreeFilter(HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID, "Hide In-Memory Studio Color Palettes", true));
        }
        return filters;
    }
}
