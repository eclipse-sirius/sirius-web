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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Used to provide customized behavior for object duplication.
 *
 * @author pcdavid
 */
public interface IObjectDuplicatorDelegate {

    boolean canHandle(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings);

    IStatus duplicateObject(IEditingContext editingContext, EObject objectToDuplicate, EObject containerEObject, String containmentFeature, DuplicationSettings settings);
}
