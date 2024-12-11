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
package org.eclipse.sirius.web.application.studio.services.representations;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.trees.api.IDropTreeItemHandler;
import org.eclipse.sirius.components.collaborative.trees.dto.DropTreeItemInput;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.IViewRepresentationDescriptionSearchService;
import org.eclipse.sirius.web.application.views.explorer.services.ExplorerDescriptionProvider;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDropTreeItemExecutor;
import org.springframework.stereotype.Service;

/**
 * Provides the drop tree item tool for the Domain explorer by DSL explorer.
 *
 * @author gdaniel
 */
@Service
public class DomainDropTreeItemHandler implements IDropTreeItemHandler {

    private final IExplorerDropTreeItemExecutor explorerDropTreeItemExecutor;

    private final IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService;

    public DomainDropTreeItemHandler(IExplorerDropTreeItemExecutor explorerDropTreeItemExecutor, IViewRepresentationDescriptionSearchService viewRepresentationDescriptionSearchService) {
        this.explorerDropTreeItemExecutor = Objects.requireNonNull(explorerDropTreeItemExecutor);
        this.viewRepresentationDescriptionSearchService = Objects.requireNonNull(viewRepresentationDescriptionSearchService);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, Tree tree) {
        boolean result = false;
        if (tree.getId().startsWith(ExplorerDescriptionProvider.PREFIX)
                && tree.getDescriptionId().startsWith(IRepresentationDescriptionIdProvider.PREFIX)) {
            var optionalViewTreeDescription = this.viewRepresentationDescriptionSearchService.findById(editingContext, tree.getDescriptionId());
            if (optionalViewTreeDescription.isPresent()) {
                result = optionalViewTreeDescription.get().getName().equals(DomainViewTreeDescriptionProvider.DOMAIN_EXPLORER_DESCRIPTION_NAME);
            }
        }
        return result;
    }

    @Override
    public IStatus handle(IEditingContext editingContext, Tree tree, DropTreeItemInput input) {
        return this.explorerDropTreeItemExecutor.drop(editingContext, tree, input.droppedElementIds(), input.targetElementId(), input.index());
    }

}
