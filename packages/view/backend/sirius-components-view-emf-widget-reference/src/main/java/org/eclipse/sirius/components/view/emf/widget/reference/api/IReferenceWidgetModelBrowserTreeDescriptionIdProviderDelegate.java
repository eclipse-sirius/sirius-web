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
package org.eclipse.sirius.components.view.emf.widget.reference.api;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Used to customize which tree description to use for a specific reference widget.
 *
 * @author pcdavid
 */
public interface IReferenceWidgetModelBrowserTreeDescriptionIdProviderDelegate {
    boolean canHandle(IEditingContext editingContext, String referenceWidgetDescriptionId, boolean isContainment);

    String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String referenceWidgetDescriptionId, boolean isContainment);
}
