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
package org.eclipse.sirius.components.collaborative.charts;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the hierarchy event subscription.
 *
 * @author sbegaudeau
 */
public final class HierarchyEventInput implements IInput {
    private UUID id;

    private String editingContextId;

    private UUID hierarchyId;

    public HierarchyEventInput() {
        // Used by Jackson
    }

    public HierarchyEventInput(UUID id, String projectId, UUID hierarchyId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(projectId);
        this.hierarchyId = Objects.requireNonNull(hierarchyId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public UUID getHierarchyId() {
        return this.hierarchyId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}, hierarchyId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.editingContextId, this.hierarchyId);
    }
}
