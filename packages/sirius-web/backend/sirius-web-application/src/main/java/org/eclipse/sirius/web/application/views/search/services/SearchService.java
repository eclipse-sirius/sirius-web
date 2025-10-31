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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.regex.Pattern;
import java.util.stream.StreamSupport;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.web.application.views.search.dto.SearchMatch;
import org.eclipse.sirius.web.application.views.search.dto.SearchQuery;
import org.eclipse.sirius.web.application.views.search.dto.SearchResult;
import org.eclipse.sirius.web.application.views.search.dto.SearchResultGroup;
import org.eclipse.sirius.web.application.views.search.dto.SearchResultSection;
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
    private static final String TYPE_GROUP_ID = "type";

    private static final String MODELS_GROUP_ID = "model";

    private static final int MAX_RESULT_SIZE = 1000_000;

    private final Logger logger = LoggerFactory.getLogger(SearchService.class);

    private final ILabelService labelService;

    public SearchService(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public SearchResult search(IEditingContext editingContext, SearchQuery query) {
        if (editingContext instanceof IEMFEditingContext emfEditingContext) {
            long start = System.nanoTime();
            var textPredicate = this.toTextPredicate(query);

            // The grouping criteria, initially with no sections.
            // Those will be filled by the actual matches.
            var groups = List.of(
                new SearchResultGroup(MODELS_GROUP_ID, "Model", "/search/Model.svg", new ArrayList<>()),
                new SearchResultGroup(TYPE_GROUP_ID, "Type", "", new ArrayList<>())
            );

            var iterator = emfEditingContext.getDomain().getResourceSet().getAllContents();
            var stream = StreamSupport.stream(Spliterators.spliteratorUnknownSize(iterator, Spliterator.ORDERED), false);
            var matches = stream.flatMap(obj -> this.match(obj, query.searchInAttributes(), textPredicate, groups).stream())
                    .limit(MAX_RESULT_SIZE)
                    .toList();
            var duration = Duration.ofNanos(System.nanoTime() - start);
            this.logger.debug("Search found {} matches in {}s", matches.size(), duration.toMillis());
            return new SearchResult(groups, matches);
        }
        return new SearchResult(List.of(), List.of());
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

    private Optional<SearchMatch> match(Object object, boolean searchInAttributes, Predicate<String> predicate, List<SearchResultGroup> groups) {
        boolean result = false;
        String labelText = this.labelService.getStyledLabel(object).toString();
        boolean isLabelMatch = labelText != null && predicate.test(labelText);
        if (isLabelMatch) {
            result = true;
        } else if (searchInAttributes && object instanceof EObject eObject) {
            result = eObject.eClass().getEAllAttributes().stream()
                    .anyMatch(attribute -> predicate.test(String.valueOf(eObject.eGet(attribute))));
        }
        if (result) {
            var memberships = this.categorize(object, groups);
            return Optional.of(new SearchMatch(object, memberships));
        } else {
            return Optional.empty();
        }
    }

    private ArrayList<String> categorize(Object object, List<SearchResultGroup> groups) {
        var memberships = new ArrayList<String>();
        if (object instanceof EObject eObject) {
            if (eObject.eResource() instanceof Resource) {
                var resource = eObject.eResource();
                var documentName = this.getDocumentName(resource).orElse("");
                var documentId = resource.getURI().toString();
                var modelSection = this.getOrCreateSection(groups, MODELS_GROUP_ID, documentId, documentName, List.of("/search/Model.svg"));
                if (modelSection.isPresent()) {
                    memberships.add(MODELS_GROUP_ID + ":" + modelSection.get().id());
                }
            }
            var type = eObject.eClass().getEPackage().getName() + "::" + eObject.eClass().getName();
            var typeSection = this.getOrCreateSection(groups, TYPE_GROUP_ID, type, type, this.labelService.getImagePaths(eObject));
            if (typeSection.isPresent()) {
                memberships.add(TYPE_GROUP_ID + ":" + typeSection.get().id());
            }
        }
        return memberships;
    }

    private Optional<SearchResultSection> getOrCreateSection(List<SearchResultGroup> groups, String groupId, String sectionId, String label, List<String> iconURLs) {
        return groups.stream()
                .filter(group -> group.id().equals(groupId))
                .findFirst()
                .flatMap(group -> {
                    var existingSection = group.sections().stream().filter(section -> section.id().equals(sectionId)).findFirst();
                    if (existingSection.isPresent()) {
                        return existingSection;
                    } else {
                        String iconURL = "";
                        if (!iconURLs.isEmpty()) {
                            iconURL = iconURLs.get(0);
                        }
                        var newSection = new SearchResultSection(sectionId, label, iconURL);
                        group.sections().add(newSection);
                        return Optional.of(newSection);
                    }
                });
    }

    private Optional<String> getDocumentName(Resource resource) {
        var optionalName = resource.eAdapters().stream()
                .filter(ResourceMetadataAdapter.class::isInstance)
                .map(ResourceMetadataAdapter.class::cast)
                .findFirst()
                .map(ResourceMetadataAdapter::getName);
        return optionalName;
    }
}
