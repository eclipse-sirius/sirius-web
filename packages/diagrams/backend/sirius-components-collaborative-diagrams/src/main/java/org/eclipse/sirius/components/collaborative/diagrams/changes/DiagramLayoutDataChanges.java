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
package org.eclipse.sirius.components.collaborative.diagrams.changes;

import java.util.Map;

/**
 * The previous and new node layout data and edge layout data.
 *
 * @author gcoutable
 */
public record DiagramLayoutDataChanges(Map<String, NodeLayoutDataChange> nodeLayoutDataChanges,
        Map<String, EdgeLayoutDataChange> edgeLayoutDataChanges) {
}
