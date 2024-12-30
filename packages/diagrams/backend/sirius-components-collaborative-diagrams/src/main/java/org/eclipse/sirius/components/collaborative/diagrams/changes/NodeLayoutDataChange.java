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

import java.util.Optional;

import org.eclipse.sirius.components.diagrams.layoutdata.NodeLayoutData;

/**
 * The previous and new node layout data.
 *
 * <p>
 * If previousNodeLayoutData.isEmpty and newNodeLayoutData.isPresent, the change represents an add.
 * If previousNodeLayoutData.isPresent and newNodeLayoutData.isEmpty, the change represents a deletion.
 * Otherwise, the change represents a layout change (move, resize, ...)
 * </p>
 *
 * NOTE: A change with previousNodeLayoutData.isEmpty and newNodeLayoutData.isEmpty is not valid.
 *
 * @author gcoutable
 */
public record NodeLayoutDataChange(Optional<NodeLayoutData> previousNodeLayoutData, Optional<NodeLayoutData> newNodeLayoutData) {
}
