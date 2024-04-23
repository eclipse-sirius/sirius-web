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

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.ITreeFilterProvider;
import org.eclipse.sirius.components.collaborative.trees.api.TreeFilter;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;

/**
 * Provides the filters of the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class StudioExplorerTreeFilterProvider implements ITreeFilterProvider {

    public static final String HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID = "hide-color-palettes";

    private final IProjectSearchService projectSearchService;

    public StudioExplorerTreeFilterProvider(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public List<TreeFilter> get(String editingContextId, TreeDescription treeDescription, String representationId) {
        var isStudio = new UUIDParser().parse(editingContextId)
                .flatMap(this.projectSearchService::findById)
                .map(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(StudioProjectTemplateProvider.STUDIO_NATURE::equals))
                .orElse(false)
                .booleanValue();

        var filters = new ArrayList<TreeFilter>();
        if (isStudio) {
            filters.add(new TreeFilter(HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID, "Hide In-Memory Studio Color Palettes", true));
        }

        return filters;
    }
}
