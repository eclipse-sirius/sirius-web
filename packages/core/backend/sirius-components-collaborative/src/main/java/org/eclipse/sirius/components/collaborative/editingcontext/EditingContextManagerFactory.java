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

import org.eclipse.sirius.components.collaborative.api.IEditingContextManager;
import org.eclipse.sirius.components.collaborative.api.IEditingContextManagerFactory;
import org.eclipse.sirius.components.core.api.IEditingContextPersistenceService;
import org.springframework.stereotype.Service;

/**
 * Used to create {@link IEditingContextManager}.
 *
 * @author gcoutable
 */
@Service
public class EditingContextManagerFactory implements IEditingContextManagerFactory {

    private final IEditingContextPersistenceService editingContextPersistenceService;

    public EditingContextManagerFactory(IEditingContextPersistenceService editingContextPersistenceService) {
        this.editingContextPersistenceService = Objects.requireNonNull(editingContextPersistenceService);
    }

    @Override
    public IEditingContextManager createEditingContextManager() {
        return new EditingContextManager(this.editingContextPersistenceService);
    }
}
