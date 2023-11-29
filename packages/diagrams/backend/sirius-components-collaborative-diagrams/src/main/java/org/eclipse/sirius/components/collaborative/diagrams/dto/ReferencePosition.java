/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.diagrams.layoutdata.Position;

/**
 * Payload used to share the reference position.
 *
 * @author sbegaudeau
 */
public record ReferencePosition(String parentId, Position position) {
    public ReferencePosition {
        Objects.requireNonNull(position);
    }
}
