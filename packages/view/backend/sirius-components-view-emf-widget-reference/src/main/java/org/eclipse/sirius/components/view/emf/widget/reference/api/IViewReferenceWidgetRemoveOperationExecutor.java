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
package org.eclipse.sirius.components.view.emf.widget.reference.api;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.View;

/**
 * Executes the removal body of a View-based reference widget.
 *
 * @author tgiraudet
 * @since v2026.11.0
 */
public interface IViewReferenceWidgetRemoveOperationExecutor {

    IStatus execute(IEditingContext editingContext, View view, Object self, EObject owner, EReference reference, Object valueToRemove, List<Operation> operations);
}
