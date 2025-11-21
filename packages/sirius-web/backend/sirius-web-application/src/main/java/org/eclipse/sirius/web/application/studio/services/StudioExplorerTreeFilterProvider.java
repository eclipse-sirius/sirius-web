/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.description.TreeDescription;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
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

    private final IProjectEditingContextService projectEditingContextService;

    public StudioExplorerTreeFilterProvider(IProjectSearchService projectSearchService, IProjectEditingContextService projectEditingContextService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
    }

    @Override
    public List<TreeFilter> get(IEditingContext editingContext, TreeDescription treeDescription, Tree tree) {
        var isStudio = this.projectEditingContextService.getProjectId(editingContext.getId())
                .flatMap(this.projectSearchService::findById)
                .map(project -> project.getNatures().stream()
                        .map(Nature::name)
                        .anyMatch(StudioProjectTemplateProvider.STUDIO_NATURE::equals))
                .orElse(false);

        var filters = new ArrayList<TreeFilter>();
        if (isStudio && ExplorerDescriptionProvider.DESCRIPTION_ID.equals(treeDescription.getId())) {
            filters.add(new TreeFilter(HIDE_STUDIO_COLOR_PALETTES_TREE_FILTER_ID, "Hide In-Memory Studio Color Palettes", true));
        }

        return filters;
    }
}
