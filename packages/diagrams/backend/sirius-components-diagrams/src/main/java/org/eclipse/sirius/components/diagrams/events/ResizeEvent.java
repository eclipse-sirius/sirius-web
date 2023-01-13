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
import org.eclipse.sirius.components.diagrams.Size;

/**
 * Represent an immutable resize event.
 *
 * <p>
 * It can be created thanks to an user action, or it can be created internally when a node being resized lays out its
 * children as a list. Each child will have to handle its own resize event. When the event is created thanks to a user
 * action, it will be marked as such. When the node is created internally for a layout purpose, it will not be marked as
 * created by user.
 * </p>
 *
 * @author fbarbin
 */

public record ResizeEvent(String nodeId, Position positionDelta, Size newSize) implements IDiagramEvent {
}
