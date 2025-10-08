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
package org.eclipse.sirius.components.view.emf.widget.reference;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IDefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetModelBrowserTreeDescriptionIdProvider;
import org.eclipse.sirius.components.view.emf.widget.reference.api.IReferenceWidgetModelBrowserTreeDescriptionIdProviderDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to find the tree description id to use for the model browser of a specific reference widget.
 *
 * @author pcdavid
 * @see IReferenceWidgetModelBrowserTreeDescriptionIdProviderDelegate
 */
@Service
public class ComposedReferenceWidgetModelBrowserTreeDescriptionIdProvider implements IReferenceWidgetModelBrowserTreeDescriptionIdProvider {

    private final IDefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider defaultTreeDescriptionIdProvider;

    private final List<IReferenceWidgetModelBrowserTreeDescriptionIdProviderDelegate> treeDescriptionIdProviderDelegates;

    public ComposedReferenceWidgetModelBrowserTreeDescriptionIdProvider(IDefaultReferenceWidgetModelBrowserTreeDescriptionIdProvider defaultTreeDescriptionIdProvider,
            List<IReferenceWidgetModelBrowserTreeDescriptionIdProviderDelegate> treeDescriptionIdProviderDelegates) {
        this.defaultTreeDescriptionIdProvider = Objects.requireNonNull(defaultTreeDescriptionIdProvider);
        this.treeDescriptionIdProviderDelegates = Objects.requireNonNull(treeDescriptionIdProviderDelegates);
    }

    @Override
    public String getModelBrowserTreeDescriptionId(IEditingContext editingContext, String referenceWidgetDescriptionId, boolean isContainment) {
        return this.treeDescriptionIdProviderDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext, referenceWidgetDescriptionId, isContainment))
                .findFirst()
                .map(delegate -> delegate.getModelBrowserTreeDescriptionId(editingContext, referenceWidgetDescriptionId, isContainment))
                .orElseGet(() -> this.defaultTreeDescriptionIdProvider.getModelBrowserTreeDescriptionId(editingContext, referenceWidgetDescriptionId, isContainment));
    }
}
