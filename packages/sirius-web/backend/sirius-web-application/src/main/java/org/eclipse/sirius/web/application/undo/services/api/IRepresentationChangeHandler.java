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
package org.eclipse.sirius.web.application.undo.services.api;

import java.util.UUID;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Use to redo some representation changes.
 *
 * @author mcharfadi
 */
public interface IRepresentationChangeHandler {

    boolean canHandle(UUID inputId, IEditingContext editingContext);

    void redo(UUID inputId, IEditingContext editingContext);

    void undo(UUID inputId, IEditingContext editingContext);

}
