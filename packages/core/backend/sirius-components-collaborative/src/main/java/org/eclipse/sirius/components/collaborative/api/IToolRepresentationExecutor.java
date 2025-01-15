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
package org.eclipse.sirius.components.collaborative.api;

import java.util.List;

import org.eclipse.sirius.components.collaborative.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Used to execute a tool on a representation.
 *
 * @author frouene
 */
public interface IToolRepresentationExecutor {

    boolean canExecute(IEditingContext editingContext, IRepresentation representation);

    IStatus execute(IEditingContext editingContext, IRepresentation representation, String toolId, String targetObjectId, List<ToolVariable> variables);
}
