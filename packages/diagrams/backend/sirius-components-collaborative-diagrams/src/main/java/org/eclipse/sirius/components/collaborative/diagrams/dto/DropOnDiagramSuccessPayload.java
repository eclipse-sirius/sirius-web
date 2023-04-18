/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.diagrams.Diagram;

/**
 * The payload of the "drop on diagram" mutation returned on success.
 *
 * @author hmarchadour
 */
public record DropOnDiagramSuccessPayload(UUID id, Diagram diagram) implements IPayload {
    public DropOnDiagramSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(diagram);
    }
}
