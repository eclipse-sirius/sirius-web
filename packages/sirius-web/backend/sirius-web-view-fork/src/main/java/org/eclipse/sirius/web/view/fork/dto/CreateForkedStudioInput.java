/*******************************************************************************
 * Copyright (c) 2024, 2025 CEA LIST and others.
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

package org.eclipse.sirius.web.view.fork.dto;

import org.eclipse.sirius.components.core.api.IInput;

import java.util.UUID;

/**
 * CreateForkedStudioInput used when forking a studio.
 *
 * @author mcharfadi
 */
public record CreateForkedStudioInput(UUID id, String editingContextId, String representationId, String tableId) implements IInput {

}
