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
package org.eclipse.sirius.components.graphql.tests;

import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IInput;

/**
 * Used to execute some function on the editing context.
 *
 * @author sbegaudeau
 */
public record ExecuteEditingContextFunctionInput(UUID id, String editingContextId, Function<IEditingContext, Object> function) implements IInput {
}
