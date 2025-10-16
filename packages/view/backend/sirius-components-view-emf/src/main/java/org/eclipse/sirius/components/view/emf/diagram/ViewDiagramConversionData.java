/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import java.util.Map;

import org.eclipse.sirius.components.diagrams.description.EdgeDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.view.emf.IViewConversionData;

/**
 * Data returned after the diagram has been converted.
 *
 * @author mcharfadi
 */
public record ViewDiagramConversionData(
        Map<org.eclipse.sirius.components.view.diagram.NodeDescription, NodeDescription> convertedNodes,
        Map<org.eclipse.sirius.components.view.diagram.EdgeDescription, EdgeDescription> convertedEdges
) implements IViewConversionData {
}
