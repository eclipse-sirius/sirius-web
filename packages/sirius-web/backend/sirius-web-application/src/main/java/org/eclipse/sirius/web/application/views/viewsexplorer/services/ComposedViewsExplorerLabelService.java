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

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.labels.StyledString;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.trees.Tree;
import org.eclipse.sirius.components.trees.TreeItem;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IDefaultViewsExplorerLabelService;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerLabelService;
import org.eclipse.sirius.web.application.views.viewsexplorer.services.api.IViewsExplorerLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to manipulate labels in the definition of the views explorer.
 *
 * @author tgiraudet
 */
@Service
public class ComposedViewsExplorerLabelService implements IViewsExplorerLabelService {

    private final IDefaultViewsExplorerLabelService defaultExplorerLabelService;

    private final List<IViewsExplorerLabelServiceDelegate> viewsExplorerLabelServiceDelegates;

    public ComposedViewsExplorerLabelService(IDefaultViewsExplorerLabelService defaultExplorerLabelService, List<IViewsExplorerLabelServiceDelegate> viewsExplorerLabelServiceDelegates) {
        this.viewsExplorerLabelServiceDelegates = Objects.requireNonNull(viewsExplorerLabelServiceDelegates);
        this.defaultExplorerLabelService = Objects.requireNonNull(defaultExplorerLabelService);
    }

    @Override
    public boolean isEditable(IEditingContext editingContext, Object self) {
        return this.viewsExplorerLabelServiceDelegates
            .stream()
            .filter(delegate -> delegate.canHandle(editingContext))
            .findFirst()
            .map(delegate -> delegate.isEditable(self))
            .orElseGet(() -> this.defaultExplorerLabelService.isEditable(self));
    }

    @Override
    public StyledString getLabel(IEditingContext editingContext, Object self) {
        return this.viewsExplorerLabelServiceDelegates
            .stream()
            .filter(delegate -> delegate.canHandle(editingContext))
            .findFirst()
            .map(delegate -> delegate.getLabel(self))
            .orElseGet(() -> this.defaultExplorerLabelService.getLabel(self));
    }

    @Override
    public IStatus editLabel(IEditingContext editingContext, Tree tree, TreeItem treeItem, String newValue) {
        return this.viewsExplorerLabelServiceDelegates
            .stream()
            .filter(delegate -> delegate.canHandle(editingContext))
            .findFirst()
            .map(delegate -> delegate.editLabel(editingContext, tree, treeItem, newValue))
            .orElseGet(() -> this.defaultExplorerLabelService.editLabel(editingContext, tree, treeItem, newValue));
    }

}
