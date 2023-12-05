/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.collaborative.diagrams.dto.ReferencePosition;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * Provides diagram input reference position for a diagram.
 *
 * @author frouene
 */
public interface IDiagramInputReferencePositionProvider {

    boolean canHandle(IInput diagramInput);

    ReferencePosition getReferencePosition(IInput diagramInput, IDiagramContext diagramContext);
}
