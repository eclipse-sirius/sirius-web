/*******************************************************************************
 * Copyright (c) 2021, 2023 THALES GLOBAL SERVICES.
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

import org.eclipse.sirius.components.diagrams.Position;

/**
 * Represent an immutable move event.
 *
 * @author wpiers
 */
public record MoveEvent(String nodeId, Position newPosition) implements IDiagramEvent {
}
