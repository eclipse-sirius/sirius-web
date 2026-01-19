/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.undo.services.api;

import java.util.function.Predicate;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * Used to test if an input should be ignored by the undo-redo recorder.
 *
 * @author gdaniel
 */
public interface IUndoRedoIgnoreInputPredicate extends Predicate<IInput> {

}
