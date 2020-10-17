/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.emf.services;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.sirius.web.services.api.objects.IEditingContext;

/**
 * Implementation of the editing context.
 *
 * @author sbegaudeau
 */
public class EditingContext implements IEditingContext {

    private final UUID projectId;

    private final EditingDomain editingDomain;

    public EditingContext(UUID projectId, EditingDomain editingDomain) {
        this.projectId = Objects.requireNonNull(projectId);
        this.editingDomain = Objects.requireNonNull(editingDomain);
    }

    @Override
    public UUID getProjectId() {
        return this.projectId;
    }

    @Override
    public Object getDomain() {
        return this.editingDomain;
    }

}
