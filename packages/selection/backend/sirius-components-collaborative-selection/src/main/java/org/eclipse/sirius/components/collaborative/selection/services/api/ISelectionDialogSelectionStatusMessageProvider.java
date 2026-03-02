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
package org.eclipse.sirius.components.collaborative.selection.services.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.selection.description.SelectionDescription;

/**
 * Used to compute the selection status message.
 *
 * @author sbegaudeau
 */
public interface ISelectionDialogSelectionStatusMessageProvider {

    boolean canHandle(IEditingContext editingContext, SelectionDescription selectionDescription);

    String handle(IEditingContext editingContext, SelectionDescription selectionDescription, List<String> selectedObjectIds);
}
