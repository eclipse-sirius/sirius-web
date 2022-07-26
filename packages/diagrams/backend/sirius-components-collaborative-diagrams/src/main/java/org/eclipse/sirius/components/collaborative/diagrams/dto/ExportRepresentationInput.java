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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * The input object for the export representation service.
 *
 * @author rpage
 */
public final class ExportRepresentationInput implements IDiagramInput {
    private final UUID id;

    private final String representationId;

    public ExportRepresentationInput(UUID id, String representationId) {
        this.id = Objects.requireNonNull(id);
        this.representationId = Objects.requireNonNull(representationId);
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    @Override
    public UUID getId() {
        return this.id;
    }
}
