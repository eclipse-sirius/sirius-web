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
package org.eclipse.sirius.components.collaborative.representations.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationInput;
import org.eclipse.sirius.components.representations.IRepresentation;

/**
 * Use to create event record to be handled by IRepresentationEventHandler when doing an undo/redo.
 *
 * @author mcharfadi
 */
public interface IRepresentationEventRecorder {

    void recordChange(IEditingContext editingContext, IRepresentation previousRepresentation, IRepresentation updatedRepresentation, IRepresentationInput input);

}
