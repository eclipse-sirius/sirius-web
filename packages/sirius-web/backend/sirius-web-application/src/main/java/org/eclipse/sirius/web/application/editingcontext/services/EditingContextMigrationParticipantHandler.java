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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.editingcontext.services.api.IEditingContextMigrationParticipantPredicate;
import org.eclipse.sirius.web.application.studio.services.api.IStudioCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Used to indicate if the editing context is concern by the migration participants.
 *
 * @author frouene
 */
@Service
public class EditingContextMigrationParticipantHandler implements IEditingContextMigrationParticipantPredicate {

    private final IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate;

    public EditingContextMigrationParticipantHandler(IStudioCapableEditingContextPredicate studioCapableEditingContextPredicate) {
        this.studioCapableEditingContextPredicate = Objects.requireNonNull(studioCapableEditingContextPredicate);
    }

    @Override
    public boolean test(IEditingContext editingContextId) {
        return this.studioCapableEditingContextPredicate.test(editingContextId);
    }
}
