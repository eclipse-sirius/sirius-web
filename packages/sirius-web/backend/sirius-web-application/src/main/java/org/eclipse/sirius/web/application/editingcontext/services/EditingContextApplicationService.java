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
package org.eclipse.sirius.web.application.editingcontext.services;

import java.util.Objects;

import org.eclipse.sirius.web.application.UUIDParser;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextApplicationService;
import org.eclipse.sirius.web.domain.boundedcontexts.project.services.api.IProjectSearchService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Used to interact with editing contexts.
 *
 * @author sbegaudeau
 */
@Service
public class EditingContextApplicationService implements IEditingContextApplicationService {

    private final IProjectSearchService projectSearchService;

    public EditingContextApplicationService(IProjectSearchService projectSearchService) {
        this.projectSearchService = Objects.requireNonNull(projectSearchService);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsById(String editingContextId) {
        return new UUIDParser().parse(editingContextId)
                .map(this.projectSearchService::existsById)
                .orElse(false);
    }
}
