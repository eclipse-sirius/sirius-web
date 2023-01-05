/*******************************************************************************
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.selection.dto;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the selection event subscription.
 *
 * @author arichard
 */
public record SelectionEventInput(UUID id, String editingContextId, String selectionId, String targetObjectId) implements IInput {
}
