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
package org.eclipse.sirius.components.collaborative.diagrams.dto;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

import java.util.List;
import java.util.UUID;

/**
 * Input for the edition of an image node appearance.
 *
 * @author mcharfadi
 */
public record EditImageNodeAppearanceInput(UUID id, String editingContextId, String representationId, List<String> nodeIds, ImageNodeAppearanceInput appearance) implements IDiagramInput {

}
