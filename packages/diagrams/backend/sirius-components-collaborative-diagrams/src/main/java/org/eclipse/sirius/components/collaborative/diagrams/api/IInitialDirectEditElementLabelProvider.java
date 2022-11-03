/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;

/**
 * Implementations should return the initial value of a label when the direct edit is triggered.
 *
 * @author gcoutable
 */
public interface IInitialDirectEditElementLabelProvider {

    boolean canHandle(DiagramDescription diagramDescription);

    String getInitialDirectEditElementLabel(Object graphicalElement, Diagram diagram, IEditingContext editingContext);
}
