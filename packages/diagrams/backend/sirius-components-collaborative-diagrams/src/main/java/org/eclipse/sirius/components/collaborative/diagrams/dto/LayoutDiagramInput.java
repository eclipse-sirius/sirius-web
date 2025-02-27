/*******************************************************************************
 * Copyright (c) 2023, 2025 Obeo.
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

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.diagrams.api.IDiagramInput;

/**
 * Input used to layout diagrams.
 *
 * @author sbegaudeau
 */
public record LayoutDiagramInput(UUID id, String editingContextId, String representationId, String cause, DiagramLayoutDataInput diagramLayoutData) implements IDiagramInput {

    public static final String CAUSE_LAYOUT = "layout";

}
