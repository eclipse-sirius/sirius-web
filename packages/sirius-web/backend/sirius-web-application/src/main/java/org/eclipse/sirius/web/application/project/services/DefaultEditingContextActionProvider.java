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
package org.eclipse.sirius.web.application.project.services;

import java.util.List;
import java.util.Objects;
import java.util.Set;

import org.eclipse.sirius.components.collaborative.api.IEditingContextActionProvider;
import org.eclipse.sirius.components.collaborative.dto.EditingContextAction;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.domain.boundedcontexts.project.Project;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;

/**
 * Used to provide the default editing context actions.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultEditingContextActionProvider implements IEditingContextActionProvider {

    public static final String EMPTY_ACTION_ID = "empty";

    private final IProjectSearchService projectSearchService;

    public DefaultEditingContextActionProvider(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    public List<EditingContextAction> getEditingContextAction(IEditingContext editingContext) {
        var isWithoutNature = new UUIDParser().parse(editingContext.getId())
                .flatMap(this.projectSearchService::findById)
                .map(Project::getNatures)
                .orElseGet(Set::of)
                .isEmpty();
        if (isWithoutNature) {
            return List.of(new EditingContextAction(EMPTY_ACTION_ID, "Others..."));
        }
        return List.of();
    }
}
