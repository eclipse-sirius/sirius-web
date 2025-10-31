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
package org.eclipse.sirius.web.application.impactanalysis.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.change.ChangeDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.datatree.DataTree;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IChangeDescriptionDataTreeProvider;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IChangeDescriptionDataTreeProviderDelegate;
import org.eclipse.sirius.web.application.impactanalysis.services.api.IDefaultChangeDescriptionDataTreeProvider;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IChangeDescriptionDataTreeProvider} which delegates to
 * {@link IChangeDescriptionDataTreeProviderDelegate} or fallback to {@link IDefaultChangeDescriptionDataTreeProvider}.
 *
 * @author gdaniel
 */
@Service
public class ComposedChangeDescriptionDataTreeProvider implements IChangeDescriptionDataTreeProvider {

    private final List<IChangeDescriptionDataTreeProviderDelegate> changeDescriptionDataTreeProviderDelegates;

    private final IDefaultChangeDescriptionDataTreeProvider defaultChangeDescriptionDataTreeProvider;

    public ComposedChangeDescriptionDataTreeProvider(List<IChangeDescriptionDataTreeProviderDelegate> changeDescriptionDataTreeProviderDelegates,
            IDefaultChangeDescriptionDataTreeProvider defaultChangeDescriptionDataTreeProvider) {
        this.changeDescriptionDataTreeProviderDelegates = Objects.requireNonNull(changeDescriptionDataTreeProviderDelegates);
        this.defaultChangeDescriptionDataTreeProvider = Objects.requireNonNull(defaultChangeDescriptionDataTreeProvider);
    }

    @Override
    public Optional<DataTree> getDataTree(IEditingContext editingContext, ChangeDescription changeDescription) {
        var optionalDelegate = this.changeDescriptionDataTreeProviderDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContext, changeDescription))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getDataTree(editingContext, changeDescription);
        }
        return this.defaultChangeDescriptionDataTreeProvider.getDataTree(editingContext, changeDescription);
    }

}
