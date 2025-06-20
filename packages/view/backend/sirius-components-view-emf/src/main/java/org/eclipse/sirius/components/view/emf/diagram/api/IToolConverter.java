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

package org.eclipse.sirius.components.view.emf.diagram.api;

import java.util.List;

import org.eclipse.sirius.components.diagrams.tools.Palette;
import org.eclipse.sirius.components.view.emf.diagram.ViewDiagramDescriptionConverterContext;

/**
 * Convert View-based tool definitions into ITools.
 *
 * @author pcdavid
 */
public interface IToolConverter {

    List<Palette> createPaletteBasedToolSections(org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, ViewDiagramDescriptionConverterContext converterContext);
}
