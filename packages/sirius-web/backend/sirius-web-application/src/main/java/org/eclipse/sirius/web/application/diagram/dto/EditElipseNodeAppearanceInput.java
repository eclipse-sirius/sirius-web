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
package org.eclipse.sirius.web.application.diagram.dto;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;
import org.eclipse.sirius.components.collaborative.diagrams.dto.RectangularNodeAppearanceInput;

import java.util.UUID;

/**
 * Input for the edition of an elipse node's appearance.
 *
 * @author nvannier
 */
public record EditElipseNodeAppearanceInput(UUID id, String editingContextId, String representationId, String nodeId, RectangularNodeAppearanceInput appearance) implements IDiagramInput {

}
