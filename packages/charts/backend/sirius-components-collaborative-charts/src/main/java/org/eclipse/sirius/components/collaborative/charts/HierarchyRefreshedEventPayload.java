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

import org.eclipse.sirius.components.charts.hierarchy.Hierarchy;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to indicate that the hierarchy representation has been refreshed.
 *
 * @author sbegaudeau
 */
public final class HierarchyRefreshedEventPayload implements IPayload {
    private final UUID id;

    private final Hierarchy hierarchy;

    public HierarchyRefreshedEventPayload(UUID id, Hierarchy hierarchy) {
        this.id = Objects.requireNonNull(id);
        this.hierarchy = Objects.requireNonNull(hierarchy);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Hierarchy getHierarchy() {
        return this.hierarchy;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, hierarchy: '{'id: {2}, label: {3}'}''}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.hierarchy.getId(), this.hierarchy.getLabel());
    }
}
