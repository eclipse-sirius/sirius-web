/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
 * Input used to receive diagram layout data.
 *
 * @author sbegaudeau
 */
public record DiagramLayoutDataInput(List<NodeLayoutDataInput> nodeLayoutData, List<EdgeLayoutDataInput> edgeLayoutData) {

}
