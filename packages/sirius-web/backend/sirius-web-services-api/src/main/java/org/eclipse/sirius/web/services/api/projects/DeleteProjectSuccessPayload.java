/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.web.services.api.projects;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.web.services.api.viewer.IViewer;

/**
 * Represent the result returned when deleting a project through the graphql API.
 *
 * @author fbarbin
 */
public final class DeleteProjectSuccessPayload implements IPayload {

    private final UUID id;

    private final IViewer viewer;

    public DeleteProjectSuccessPayload(UUID id, IViewer viewer) {
        this.id = Objects.requireNonNull(id);
        this.viewer = Objects.requireNonNull(viewer);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public IViewer getViewer() {
        return this.viewer;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, viewer: '{'id: {2}, username: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.viewer.getId(), this.viewer.getUsername());
    }
}
