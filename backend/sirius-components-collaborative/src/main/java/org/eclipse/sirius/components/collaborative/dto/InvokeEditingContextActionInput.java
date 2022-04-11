/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.dto;

import java.text.MessageFormat;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.handlers.InvokeEditingContextActionEventHandler;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input object of the {@link InvokeEditingContextActionEventHandler}.
 *
 * @author rpage
 */
public final class InvokeEditingContextActionInput implements IInput {
    private UUID id;

    private String editingContextId;

    private String actionId;

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getActionId() {
        return this.actionId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, actionId: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.actionId);
    }
}
