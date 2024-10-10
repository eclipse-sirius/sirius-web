/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.collaborative.trees.api;

import org.eclipse.sirius.components.collaborative.trees.dto.FetchTreeItemContextMenuEntryData;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 *  Interface allowing to provide the data of fetch context menu entries necessary to perform the fetch action itself.
 *
 * @author Jerome Gout
 */
public interface IFetchTreeItemContextMenuEntryDataProvider {

    boolean canHandle(TreeDescription treeDescription);

    FetchTreeItemContextMenuEntryData handle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, String treeItemMenuContextEntryId);
}
