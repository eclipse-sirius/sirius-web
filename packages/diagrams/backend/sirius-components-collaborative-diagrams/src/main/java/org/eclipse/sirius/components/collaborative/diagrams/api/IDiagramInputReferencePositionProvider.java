/*******************************************************************************
 * Copyright (c) 2023, 2026 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.events.ICause;

/**
 * Provides diagram input reference position for a diagram.
 *
 * @author frouene
 */
public interface IDiagramInputReferencePositionProvider {

    boolean canHandle(ICause cause);

    ReferencePosition getReferencePosition(ICause cause, DiagramContext diagramContext);
}
