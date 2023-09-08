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

import java.util.List;

/**
 * Indicates which kind of elements support dropping a given node type.
 *
 * @author pcdavid
 */
public record DropNodeCompatibilityEntry(String droppedNodeDescriptionId, boolean droppableOnDiagram, List<String> droppableOnNodeTypes) {
}
