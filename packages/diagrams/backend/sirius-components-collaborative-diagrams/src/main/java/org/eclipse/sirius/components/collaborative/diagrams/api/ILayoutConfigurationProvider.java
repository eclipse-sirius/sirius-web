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

package org.eclipse.sirius.components.collaborative.diagrams.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.diagrams.DiagramContext;
import org.eclipse.sirius.components.collaborative.diagrams.dto.LayoutConfiguration;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Provide the layout configurations for a given diagram.
 *
 * @author ocailleau
 */
public interface ILayoutConfigurationProvider {

    boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription);

    List<LayoutConfiguration> getLayoutConfiguration(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription);
}