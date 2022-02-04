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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.core.api.WorkbenchSelection;

/**
 * The payload of the "Invoke edge tool on diagram" mutation returned on success.
 *
 * @author pcdavid
 * @author hmarchadour
 */
public final class InvokeEdgeToolOnDiagramSuccessPayload implements IPayload {
    private final UUID id;

    private final WorkbenchSelection newSelection;

    public InvokeEdgeToolOnDiagramSuccessPayload(UUID id, WorkbenchSelection newSelection) {
        this.id = Objects.requireNonNull(id);
        this.newSelection = newSelection;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public WorkbenchSelection getNewSelection() {
        return this.newSelection;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, newSelection: {2}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.newSelection);
    }
}
