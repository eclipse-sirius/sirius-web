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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.TreeItemDecorator;

/**
 * Contributes decorators to items in the default Explorer.
 *
 * @author pcdavid
 */
public interface IExplorerDecoratorServiceDelegate {

    /**
     * Indicates whether this contribution applies to the editing context and source object.
     * This check runs for each rendered item and should be inexpensive.
     */
    boolean canHandle(IEditingContext editingContext, Object object);

    /**
     * Returns decorators in display order, or an empty list if there are none for the item.
     */
    List<TreeItemDecorator> getDecorators(IEditingContext editingContext, Object object);
}
