/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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

import org.eclipse.sirius.components.collaborative.tables.api.ITableInput;

/**
 * The input object for the change of the global filter value.
 *
 * @author frouene
 */
public record ChangeGlobalFilterValueInput(UUID id, String editingContextId, String representationId, String tableId, String globalFilterValue) implements ITableInput {

}
