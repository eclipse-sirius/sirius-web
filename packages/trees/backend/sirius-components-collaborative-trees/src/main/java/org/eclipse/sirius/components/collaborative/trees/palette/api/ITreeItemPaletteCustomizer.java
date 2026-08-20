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
package org.eclipse.sirius.components.collaborative.trees.palette.api;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.palette.dto.Palette;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 * Used to customize the palette of a tree item.
 *
 * @author sbegaudeau
 * @since v2026.9.0
 */
public interface ITreeItemPaletteCustomizer {
    boolean canHandle(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem);

    Palette customize(IEditingContext editingContext, TreeDescription treeDescription, Tree tree, TreeItem treeItem, Palette palette);
}
