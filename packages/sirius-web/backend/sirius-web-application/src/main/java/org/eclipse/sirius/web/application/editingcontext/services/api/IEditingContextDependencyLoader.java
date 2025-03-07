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
package org.eclipse.sirius.web.application.editingcontext.services.api;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Loads dependencies into the editing context.
 *
 * @author gdaniel
 */
public interface IEditingContextDependencyLoader {

    void loadDependencies(IEditingContext editingContext);

}
