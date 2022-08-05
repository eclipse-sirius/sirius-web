/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.view.emf.diagram;

import org.eclipse.emf.ecore.EObject;

/**
 * Provide high-level methods to navigate inside EMF models.
 *
 * @author pcdavid
 */
public interface IEMFNavigationService {
    <T extends EObject> T getAncestor(Class<T> type, EObject object);
}
