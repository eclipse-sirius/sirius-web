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

package org.eclipse.sirius.web.application.views.viewsexplorer.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.IDeleteTreeItemHandler;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IReadOnlyObjectPredicate;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerDeletionService;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

/**
 * Used to delete elements in the views explorer.
 *
 * @author tgiraudet
 */
@Service
public class ViewsExplorerDeletionService implements IViewsExplorerDeletionService {

    private final IReadOnlyObjectPredicate readOnlyObjectPredicate;

    private final List<IDeleteTreeItemHandler> deleteTreeItemHandlers;

    private final IMessageService messageService;

    public ViewsExplorerDeletionService(IReadOnlyObjectPredicate readOnlyObjectPredicate, List<IDeleteTreeItemHandler> deleteTreeItemHandlers, IMessageService messageService) {
        this.readOnlyObjectPredicate = Objects.requireNonNull(readOnlyObjectPredicate);
        this.deleteTreeItemHandlers = Objects.requireNonNull(deleteTreeItemHandlers);
        this.messageService = Objects.requireNonNull(messageService);
    }

    @Override
    public boolean isDeletable(Object self) {
        return !this.readOnlyObjectPredicate.test(self) && self instanceof RepresentationMetadata;
    }

    @Override
    public IStatus delete(IEditingContext editingContext, Tree tree, TreeItem treeItem) {
        var optionalHandler = this.deleteTreeItemHandlers.stream()
            .filter(handler -> handler.canHandle(editingContext, treeItem))
            .findFirst();

        if (optionalHandler.isPresent()) {
            IDeleteTreeItemHandler deleteTreeItemHandler = optionalHandler.get();
            return deleteTreeItemHandler.handle(editingContext, treeItem, tree);
        }

        return new Failure(this.messageService.failedToDelete());
    }
}
