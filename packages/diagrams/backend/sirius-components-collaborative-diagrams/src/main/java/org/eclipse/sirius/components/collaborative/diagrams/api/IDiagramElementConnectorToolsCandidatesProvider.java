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
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Provides the connector tool candidates available from a diagram element.
 *
 * @author mcharfadi
 * @since 2026.11.0
 */
public interface IDiagramElementConnectorToolsCandidatesProvider {

    boolean canHandle(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, String diagramElementId);

    List<String> getConnectorToolsCandidates(IEditingContext editingContext, DiagramContext diagramContext, DiagramDescription diagramDescription, Object diagramElement);
}
