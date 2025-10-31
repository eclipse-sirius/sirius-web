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
package org.eclipse.sirius.web.application.views.search.services;

import java.time.Duration;
import java.util.List;
import java.util.Objects;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.views.search.dto.SearchMatch;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;
import org.eclipse.sirius.web.application.views.search.services.api.ISearchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service use to search for elements inside an editing context based on a user-supplied query.
 *
 * @author pcdavid
 */
@Service
public class SearchService implements ISearchService {
    private static final int MAX_RESULT_SIZE = 1000_000;

    private final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private ILabelService labelService;

    public SearchService(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public List<SearchMatch> search(IEditingContext editingContext, SearchQuery query) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            long start = System.nanoTime();
            var textPredicate = this.toTextPredicate(query);

            var iterator = emfEditingContext.getDomain().getResourceSet().getAllContents();
            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
            var result = stream.filter(obj -> this.matches(obj, query.searchInAttributes(), textPredicate))
                    .limit(MAX_RESULT_SIZE)
                    .map(object -> new SearchMatch(object, List.of())).toList();
            var duration = Duration.ofNanos(System.nanoTime() - start);
            this.logger.debug("Search found {} matches in {}s", result.size(), duration.toMillis());
            return result;
        }
        return List.of();
    }

    private Predicate<String> toTextPredicate(SearchQuery query) {
        StringBuilder patternText = new StringBuilder();
        if (query.matchWholeWord()) {
            patternText.append("\\b");
        }
        if (query.useRegularExpression()) {
            patternText.append(query.text());
        } else {
            patternText.append(Pattern.quote(query.text()));
        }
        if (query.matchWholeWord()) {
            patternText.append("\\b");
        }

        int patternFlags = 0;
        if (!query.matchCase()) {
            patternFlags = Pattern.CASE_INSENSITIVE;
        }

        return Pattern.compile(patternText.toString(), patternFlags).asPredicate();
    }

    private boolean matches(Object object, boolean searchInAttributes, Predicate<String> predicate) {
        boolean result = false;
        String labelText = this.labelService.getStyledLabel(object).toString();
        boolean isLabelMatch = labelText != null && predicate.test(labelText);
        if (isLabelMatch) {
            result = true;
        } else if (searchInAttributes && object instanceof EObject eObject) {
            result = eObject.eClass().getEAllAttributes().stream()
                    .anyMatch(attribute -> predicate.test(String.valueOf(eObject.eGet(attribute))));
        }
        return result;
    }

}
