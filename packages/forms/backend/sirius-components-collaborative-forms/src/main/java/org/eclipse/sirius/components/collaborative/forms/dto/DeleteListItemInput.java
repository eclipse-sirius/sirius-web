/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * The input object for the list item deletion mutation.
 *
 * @author gcoutable
 */
public record DeleteListItemInput(UUID id, String editingContextId, String representationId, String listId, String listItemId) implements IFormInput {
}
