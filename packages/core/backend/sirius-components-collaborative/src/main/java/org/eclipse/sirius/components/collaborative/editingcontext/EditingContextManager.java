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
package org.eclipse.sirius.components.collaborative.editingcontext;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextManager;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;

/**
 * Reacts to semantic changes made in the editing context.
 *
 * @author gcoutable
 */
public class EditingContextManager implements IEditingContextManager {

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public EditingContextManager(IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public void onChange(ChangeDescription changeDescription, IEditingContext editingContext) {
        if (this.shouldPersistTheEditingContext(changeDescription)) {
            this.editingContextPersistenceService.persist(changeDescription.getInput(), editingContext);
        }
    }

    private boolean shouldPersistTheEditingContext(ChangeDescription changeDescription) {
        return ChangeKind.SEMANTIC_CHANGE.equals(changeDescription.getKind());
    }
}
