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

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.palette.dto.Palette;

/**
 * Used to customize the palette of the diagram or diagram element(s).
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
public interface IDiagramPaletteCustomizer {
    boolean canHandle(IEditingContext editingContext, Diagram diagram, List<Object> diagramElements);

    Palette customize(IEditingContext editingContext, Diagram diagram, List<Object> diagramElements, Palette palette);
}
