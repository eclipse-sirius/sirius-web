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

/**
 * Use to handle undo/redo by computing Representation Events from the record of IRepresentationEventRecorder.
 *
 * @author mcharfadi
 */
public interface IRepresentationEventHandler {

    boolean canHandle(String mutationId, IEditingContext editingContext);

    void undo(String mutationId, IEditingContext editingContext);

    void redo(String mutationId, IEditingContext editingContext);

}
