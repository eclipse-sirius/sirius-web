/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.diagrams.events.undoredo;

import org.eclipse.sirius.components.diagrams.events.IDiagramEvent;
import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;

/**
 * Diagram node layout position event.
 *
 * @author mcharfadi
 */
public record DiagramNodeLayoutEvent(String nodeId, NodeLayoutData nodeLayoutData) implements IDiagramEvent {
}
