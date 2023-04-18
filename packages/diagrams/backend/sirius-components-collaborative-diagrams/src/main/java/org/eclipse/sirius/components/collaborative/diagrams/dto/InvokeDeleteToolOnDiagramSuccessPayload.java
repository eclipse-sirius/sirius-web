/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
 * The payload of the "Invoke delete tool on diagram" mutation returned on success.
 *
 * @author arichard
 */
public record InvokeDeleteToolOnDiagramSuccessPayload(UUID id, Diagram diagram) implements IPayload {
    public InvokeDeleteToolOnDiagramSuccessPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(diagram);
    }
}
