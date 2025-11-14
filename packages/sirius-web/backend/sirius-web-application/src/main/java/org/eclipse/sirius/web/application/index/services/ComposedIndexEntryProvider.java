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
package org.eclipse.sirius.web.application.index.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.index.services.api.IDefaultIndexEntryProvider;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntry;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntryProvider;
import org.eclipse.sirius.web.application.index.services.api.IIndexEntryProviderDelegate;
import org.springframework.stereotype.Service;

/**
 * Provides index entries.
 *
 * @author gdaniel
 */
@Service
public class ComposedIndexEntryProvider implements IIndexEntryProvider {

    private final List<IIndexEntryProviderDelegate> indexEntryProviderDelegates;

    private final IDefaultIndexEntryProvider defaultIndexEntryProvider;

    public ComposedIndexEntryProvider(List<IIndexEntryProviderDelegate> indexEntryProviderDelegates, IDefaultIndexEntryProvider defaultIndexEntryProvider) {
        this.indexEntryProviderDelegates = Objects.requireNonNull(indexEntryProviderDelegates);
        this.defaultIndexEntryProvider = Objects.requireNonNull(defaultIndexEntryProvider);
    }

    @Override
    public Optional<IIndexEntry> getIndexEntry(IEditingContext editingContext, Object object) {
        Optional<IIndexEntryProviderDelegate> optionalIndexEntryProviderDelegate = this.indexEntryProviderDelegates.stream()
                .filter(indexEntryProviderDelegate -> indexEntryProviderDelegate.canHandle(editingContext, object))
                .findFirst();
        if (optionalIndexEntryProviderDelegate.isPresent()) {
            return optionalIndexEntryProviderDelegate.get().getIndexEntry(editingContext, object);
        } else {
            return this.defaultIndexEntryProvider.getIndexEntry(editingContext, object);
        }
    }
}
