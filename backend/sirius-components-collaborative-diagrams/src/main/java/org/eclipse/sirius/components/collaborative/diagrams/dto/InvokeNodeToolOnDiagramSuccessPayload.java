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
import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * The payload of the "Invoke node tool on diagram" mutation returned on success.
 *
 * @author pcdavid
 */
public final class InvokeNodeToolOnDiagramSuccessPayload implements IPayload {
    private final UUID id;

    private final Diagram diagram;

    public InvokeNodeToolOnDiagramSuccessPayload(UUID id, Diagram diagram) {
        this.id = Objects.requireNonNull(id);
        this.diagram = Objects.requireNonNull(diagram);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Diagram getDiagram() {
        return this.diagram;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, diagram: '{'id: {2}, label: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.diagram.getId(), this.diagram.getLabel());
    }
}
