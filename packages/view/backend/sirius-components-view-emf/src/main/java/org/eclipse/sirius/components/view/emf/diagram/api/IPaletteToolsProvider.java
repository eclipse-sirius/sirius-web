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
package org.eclipse.sirius.components.view.emf.diagram.api;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ITool;
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolSection;

import java.util.List;

/**
 * Common interface for services providing extra tools to the palette.
 *
 * @author fbarbin
 */
public interface IPaletteToolsProvider {

    List<ToolSection> createExtraToolSections(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement);

    List<ITool> createQuickAccessTools(DiagramContext diagramContext, Object diagramElementDescription, Object diagramElement);
}
