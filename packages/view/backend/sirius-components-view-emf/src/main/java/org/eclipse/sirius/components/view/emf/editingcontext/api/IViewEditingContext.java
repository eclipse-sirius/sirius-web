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
package org.eclipse.sirius.components.view.emf.editingcontext.api;

import java.util.Map;

import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.view.emf.IViewConversionData;

/**
 * Used to identify an editing context that can support for view DSLs.
 *
 * @author sbegaudeau
 * @since v2025.12.0
 */
public interface IViewEditingContext extends IEMFEditingContext {
    Map<String, IViewConversionData> getViewConversionData();
}
