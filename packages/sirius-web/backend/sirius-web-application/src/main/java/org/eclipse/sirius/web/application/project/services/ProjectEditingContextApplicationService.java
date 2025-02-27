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
package org.eclipse.sirius.web.application.project.services;

import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextApplicationService;
import org.eclipse.sirius.web.application.project.services.api.IProjectEditingContextService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to interact with editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class ProjectEditingContextApplicationService implements IProjectEditingContextApplicationService {

    private final IProjectEditingContextService projectEditingContextService;

    public ProjectEditingContextApplicationService(IProjectEditingContextService projectEditingContextService) {
        this.projectEditingContextService = Objects.requireNonNull(projectEditingContextService);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<String> getEditingContextId(String projectId, String name) {
        if (name != null && !name.isBlank()) {
            return this.projectEditingContextService.getEditingContextId(projectId, name);
        }

        return this.projectEditingContextService.getEditingContextId(projectId);
    }

}
