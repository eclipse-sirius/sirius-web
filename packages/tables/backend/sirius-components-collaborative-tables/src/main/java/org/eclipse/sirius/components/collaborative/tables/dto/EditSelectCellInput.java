/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.tables.dto;

import java.util.UUID;

/**
 * The input object for the Select-based cell edition mutation.
 *
 * @author arichard
 */
public record EditSelectCellInput(
        UUID id,
        String editingContextId,
        String representationId,
        String tableId,
        UUID cellId,
        String newValue) implements IEditCellInput {

}
