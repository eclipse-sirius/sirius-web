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
package org.eclipse.sirius.web.application.undo.dto;

import org.eclipse.sirius.components.core.api.IInput;

import java.util.UUID;

/**
 * The input for undo mutation.
 *
 * @author mcharfadi
 */
public record UndoInput(UUID id, String  editingContextId, String mutationId) implements IInput {
}
