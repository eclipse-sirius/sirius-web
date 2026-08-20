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
package org.eclipse.sirius.components.collaborative.diagrams.palette.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.palette.dto.Palette;

/**
 * Used to customize the palette of the connector in a diagram.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
public interface IDiagramConnectorPaletteCustomizer {
    boolean canHandle(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement, Object targetDiagramElement);

    Palette customize(IEditingContext editingContext, Diagram diagram, Object sourceDiagramElement, Object targetDiagramElement, Palette palette);
}
