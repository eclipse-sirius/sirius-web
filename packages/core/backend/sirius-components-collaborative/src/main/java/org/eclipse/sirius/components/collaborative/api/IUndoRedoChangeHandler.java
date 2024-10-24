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
package org.eclipse.sirius.components.collaborative.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.UndoInput;
import org.eclipse.sirius.components.core.api.representations.IRepresentationChangeEvent;

/**
 * Interface used to declare services to handle change for undo/redo.
 *
 * @author mcharfadi
 */
public interface IUndoRedoChangeHandler {
    ChangeDescription undo(IRepresentationChangeEvent change, IEditingContext editingContext, UndoInput undoInput);
    ChangeDescription redo(IRepresentationChangeEvent change, IEditingContext editingContext, UndoInput undoInput);
}

