/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams.api;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.Palette;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;


/**
 * Provide the connector palette for a sourceDiagramElementId & targetDiagramElementId.
 *
 * @author mcharfadi
 */
public interface IConnectorPaletteProvider {

    boolean canHandle(DiagramDescription diagramDescription);

    Palette handle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object sourceDiagramElement, Object targetDiagramElement, Object sourceElementDescription, Object targetElementDescription);
}
