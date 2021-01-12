/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IEditingContextFactory;
import org.eclipse.sirius.web.core.api.IEditingContextManager;
import org.eclipse.sirius.web.core.api.IEditingContextPersistenceService;
import org.springframework.stereotype.Service;

/**
 * Service used to manage the editing context.
 *
 * @author gcoutable
 */
@Service
public class EditingContextManager implements IEditingContextManager {

    private final IEditingContextFactory editingContextFactory;

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public EditingContextManager(IEditingContextFactory editingContextFactory, IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextFactory = Objects.requireNonNull(editingContextFactory);
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public IEditingContext createEditingContext(UUID projectID) {
        return this.editingContextFactory.createEditingContext(projectID);
    }

    @Override
    public void persist(UUID projectId, IEditingContext editingContext) {
        this.editingContextPersistenceService.persist(projectId, editingContext);
    }

}
