/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.diagrams.events;

import java.util.Objects;
import java.util.Set;

/**
 * Event to pin or unpin a diagram element.
 *
 * @author frouene
 */
public record PinDiagramElementEvent(Set<String> elementIds, boolean pinned) implements IDiagramEvent {

    public PinDiagramElementEvent {
        Objects.requireNonNull(elementIds);
    }
}
