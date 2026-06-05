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
package org.eclipse.sirius.web.application.views.search.services;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;
import org.eclipse.sirius.web.application.views.search.services.api.IDefaultSearchService;
import org.eclipse.sirius.web.application.views.search.services.api.ISearchService;
import org.eclipse.sirius.web.application.views.search.services.api.ISearchServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ISearchService} which delegates to {@link ISearchServiceDelegate} or fallback to {@link IDefaultSearchService}.
 *
 * @author gdaniel
 */
@Service
public class ComposedSearchService implements ISearchService {

    private final List<ISearchServiceDelegate> searchServiceDelegates;

    private final IDefaultSearchService defaultSearchService;

    public ComposedSearchService(List<ISearchServiceDelegate> searchServiceDelegates, IDefaultSearchService defaultSearchService) {
        this.searchServiceDelegates = Objects.requireNonNull(searchServiceDelegates);
        this.defaultSearchService = Objects.requireNonNull(defaultSearchService);
    }

    @Override
    public List<Object> search(IEditingContext editingContext, SearchQuery query) {
        Optional<ISearchServiceDelegate> optionalSearchServiceDelegate = this.searchServiceDelegates.stream()
                .filter(searchServiceDelegate -> searchServiceDelegate.canHandle(editingContext, query))
                .findFirst();
        if (optionalSearchServiceDelegate.isPresent()) {
            return optionalSearchServiceDelegate.get().search(editingContext, query);
        } else {
            return this.defaultSearchService.search(editingContext, query);
        }
    }
}
