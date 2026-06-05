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
package org.eclipse.sirius.web.application.architecture;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;
import org.eclipse.sirius.web.application.views.search.services.ComposedSearchService;
import org.eclipse.sirius.web.application.views.search.services.api.IDefaultSearchService;
import org.eclipse.sirius.web.application.views.search.services.api.ISearchServiceDelegate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests for the composed search service.
 *
 * @author gdaniel
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ComposedSearchServiceTests {

    @Test
    @DisplayName("Given a search service delegate, when a search is performed, then the delegate is used")
    public void givenSearchServiceDelegateWhenSearchIsPerformedThenTheDelegateIsUsed() {
        String resultString = "result";
        ISearchServiceDelegate searchServiceDelegate = new ISearchServiceDelegate.NoOp() {
            @Override
            public boolean canHandle(IEditingContext editingContext, SearchQuery query) {
                return true;
            }

            @Override
            public List<Object> search(IEditingContext editingContext, SearchQuery query) {
                return List.of(resultString);
            }
        };

        IDefaultSearchService defaultSearchService = new IDefaultSearchService.NoOp();

        ComposedSearchService composedSearchService = new ComposedSearchService(List.of(searchServiceDelegate), defaultSearchService);

        var result = composedSearchService.search(new IEditingContext.NoOp(), new SearchQuery("test", false, false, false, false, false));
        assertThat(result).containsExactly(resultString);
    }

    @Test
    @DisplayName("Given a search service delegate that is not relevant, when a search is performed, then the default one is used")
    public void givenSearchServiceDelegateThatIsNotRelevantWhenSearchIsPerformedThenTheDefaultOneIsUsed() {
        String resultString = "result";
        ISearchServiceDelegate searchServiceDelegate = new ISearchServiceDelegate.NoOp() {

            @Override
            public boolean canHandle(IEditingContext editingContext, SearchQuery query) {
                return false;
            }
        };

        IDefaultSearchService defaultSearchService = new IDefaultSearchService.NoOp() {

            @Override
            public List<Object> search(IEditingContext editingContext, SearchQuery query) {
                return List.of(resultString);
            }
        };

        ComposedSearchService composedSearchService = new ComposedSearchService(List.of(searchServiceDelegate), defaultSearchService);

        var result = composedSearchService.search(new IEditingContext.NoOp(), new SearchQuery("test", false, false, false, false, false));
        assertThat(result).containsExactly(resultString);
    }
}
