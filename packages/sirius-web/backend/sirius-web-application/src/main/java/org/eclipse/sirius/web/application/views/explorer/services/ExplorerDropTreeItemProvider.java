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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.IDropTreeItemProvider;
import org.eclipse.sirius.components.collaborative.trees.dto.DropTreeItemInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerModificationService;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the drop tree item of the explorer.
 *
 * @author frouene
 */
@Service
public class ExplorerDropTreeItemProvider implements IDropTreeItemProvider {

    private final IExplorerModificationService explorerModificationService;

    public ExplorerDropTreeItemProvider(IExplorerModificationService explorerModificationService) {
        this.explorerModificationService = Objects.requireNonNull(explorerModificationService);
    }

    @Override
    public boolean canHandle(Tree tree) {
        return tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX);
    }

    @Override
    public IStatus handle(IEditingContext editingContext, Tree tree, DropTreeItemInput input) {
        return this.explorerModificationService.moveObject(editingContext, tree, input.droppedElementIds(), input.targetElementId(), input.index());
    }
}
