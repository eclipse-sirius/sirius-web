/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.diagrams.description.IDiagramElementDescription;

import java.util.List;

/**
 * Common interface for services providing extra tools to the group palette.
 *
 * @author mcharfadi
 */
public interface IGroupPaletteToolsProvider {

    List<ToolSection> createExtraToolSections(DiagramContext diagramContext, List<IDiagramElementDescription> targetDescriptions, List<Object> diagramElements);

    List<ITool> createQuickAccessTools(DiagramContext diagramContext, List<IDiagramElementDescription> targetDescriptions, List<Object> diagramElements);
}
