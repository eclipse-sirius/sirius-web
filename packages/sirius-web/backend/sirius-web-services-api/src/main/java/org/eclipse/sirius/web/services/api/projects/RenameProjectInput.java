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

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the rename project mutation.
 *
 * @author fbarbin
 */
public final class RenameProjectInput implements IInput {
    private UUID id;

    private UUID projectId;

    private String newName;

    public RenameProjectInput() {
        // Used by Jackson
    }

    public RenameProjectInput(UUID id, UUID projectId, String newName) {
        this.id = Objects.requireNonNull(id);
        this.projectId = Objects.requireNonNull(projectId);
        this.newName = Objects.requireNonNull(newName);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getProjectId() {
        return this.projectId;
    }

    public String getNewName() {
        return this.newName;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}, newName: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.projectId, this.newName);
    }
}
