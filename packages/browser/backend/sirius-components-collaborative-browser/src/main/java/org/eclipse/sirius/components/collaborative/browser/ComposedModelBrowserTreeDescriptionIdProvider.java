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
package org.eclipse.sirius.components.collaborative.browser;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.browser.api.IDefaultModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.collaborative.browser.api.IModelBrowserTreeDescriptionIdProviderDelegate;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to find the tree description id to use for the model browser of a specific reference widget.
 *
 * @author pcdavid
 * @see IModelBrowserTreeDescriptionIdProviderDelegate
 */
@Service
public class ComposedModelBrowserTreeDescriptionIdProvider implements IModelBrowserTreeDescriptionIdProvider {

    private final IDefaultModelBrowserTreeDescriptionIdProvider defaultTreeDescriptionIdProvider;

    private final List<IModelBrowserTreeDescriptionIdProviderDelegate> treeDescriptionIdProviderDelegates;

    public ComposedModelBrowserTreeDescriptionIdProvider(IDefaultModelBrowserTreeDescriptionIdProvider defaultTreeDescriptionIdProvider,
            List<IModelBrowserTreeDescriptionIdProviderDelegate> treeDescriptionIdProviderDelegates) {
        this.defaultTreeDescriptionIdProvider = Objects.requireNonNull(defaultTreeDescriptionIdProvider);
        this.treeDescriptionIdProviderDelegates = Objects.requireNonNull(treeDescriptionIdProviderDelegates);
    }

    @Override
    public String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String modelBrowserId) {
        return this.treeDescriptionIdProviderDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext, modelBrowserId))
                .findFirst()
                .map(delegate -> delegate.getModelBrowserTreeDescriptionId(editingContext, modelBrowserId))
                .orElseGet(() -> this.defaultTreeDescriptionIdProvider.getModelBrowserTreeDescriptionId(editingContext, modelBrowserId));
    }
}
