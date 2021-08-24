/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo and others.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.diagrams.Diagram;

/**
 * The payload of the "drop on diagram" mutation returned on success.
 *
 * @author hmarchadour
 */
public final class DropOnDiagramSuccessPayload implements IPayload {
    private final UUID id;

    private final Diagram diagram;

    public DropOnDiagramSuccessPayload(UUID id, Diagram diagram) {
        this.id = Objects.requireNonNull(id);
        this.diagram = Objects.requireNonNull(diagram);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public Diagram getdiagram() {
        return this.diagram;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, diagram: '{'id: {2}, label: {3}'}''}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.diagram.getId(), this.diagram.getLabel());
    }
}
