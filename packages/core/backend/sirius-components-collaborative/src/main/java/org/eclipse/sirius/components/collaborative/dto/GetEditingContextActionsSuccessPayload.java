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
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * The payload object for this query.
 *
 * @author rpage
 */
public final class GetEditingContextActionsSuccessPayload implements IPayload {
    private final UUID id;

    private final List<EditingContextAction> editingContextActions;

    public GetEditingContextActionsSuccessPayload(UUID id, List<EditingContextAction> editingContextActions) {
        this.id = Objects.requireNonNull(id);
        this.editingContextActions = Objects.requireNonNull(editingContextActions);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public List<EditingContextAction> getEditingContextActions() {
        return this.editingContextActions;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextActions: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextActions);
    }
}
