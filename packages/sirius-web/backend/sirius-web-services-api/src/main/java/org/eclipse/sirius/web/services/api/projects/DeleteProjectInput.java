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
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the delete project mutation.
 *
 * @author sbegaudeau
 */
public final class DeleteProjectInput implements IInput {
    private UUID id;

    private UUID projectId;

    @Override
    public UUID getId() {
        return this.id;
    }

    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, projectId: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.projectId);
    }
}
