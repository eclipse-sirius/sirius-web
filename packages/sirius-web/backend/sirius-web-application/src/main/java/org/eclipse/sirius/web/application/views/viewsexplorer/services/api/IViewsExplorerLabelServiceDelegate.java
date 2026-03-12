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

package org.eclipse.sirius.web.application.views.viewsexplorer.services.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;

/**
 * Used to customize the behavior of the label in the views explorer.
 *
 * @author tgiraudet
 */
public interface IViewsExplorerLabelServiceDelegate {

    boolean canHandle(IEditingContext editingContext);

    boolean isEditable(Object self);

    StyledString getLabel(Object self);

    IStatus editLabel(IEditingContext editingContext, Tree tree, TreeItem treeItem, String newValue);

}
