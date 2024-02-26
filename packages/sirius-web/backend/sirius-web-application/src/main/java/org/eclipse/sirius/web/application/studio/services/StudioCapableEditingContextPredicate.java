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

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Nature;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to test if an editing context is capable of supporting a studio.
 *
 * @author sbegaudeau
 */
@Service
public class StudioCapableEditingContextPredicate implements IStudioCapableEditingContextPredicate {

    private final IProjectSearchService projectSearchService;

    public StudioCapableEditingContextPredicate(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public boolean test(IEditingContext editingContext) {
        return new UUIDParser().parse(editingContext.getId())
                .flatMap(this.projectSearchService::findById)
                .filter(this::isStudio)
                .isPresent();
    }

    private boolean isStudio(Project project) {
        return project.getNatures().stream()
                .map(Nature::name)
                .anyMatch(StudioProjectTemplateProvider.STUDIO_NATURE::equals);
    }
}
