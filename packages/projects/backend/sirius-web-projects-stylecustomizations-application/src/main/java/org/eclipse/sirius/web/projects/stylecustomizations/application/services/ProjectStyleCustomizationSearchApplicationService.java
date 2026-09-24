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
package org.eclipse.sirius.web.projects.stylecustomizations.application.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

import org.eclipse.sirius.web.core.domain.pagination.Window;
import org.eclipse.sirius.web.projects.stylecustomizations.application.dto.StyleCustomizationDTO;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IProjectStyleCustomizationSearchApplicationService;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationDescriptionProvider;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.KeysetScrollPosition;
import org.springframework.stereotype.Service;

/**
 * Application service used to search project style customizations.
 *
 * @author gcoutable
 */
@Service
public class ProjectStyleCustomizationSearchApplicationService implements IProjectStyleCustomizationSearchApplicationService {

    private final List<IStyleCustomizationDescriptionProvider> styleCustomizationDescriptionProviders;

    private final IStyleCustomizationMapper styleCustomizationMapper;

    private final Logger logger = LoggerFactory.getLogger(ProjectStyleCustomizationSearchApplicationService.class);

    public ProjectStyleCustomizationSearchApplicationService(List<IStyleCustomizationDescriptionProvider> styleCustomizationDescriptionProviders, IStyleCustomizationMapper styleCustomizationMapper) {
        this.styleCustomizationDescriptionProviders = Objects.requireNonNull(styleCustomizationDescriptionProviders);
        this.styleCustomizationMapper = Objects.requireNonNull(styleCustomizationMapper);
    }

    @Override
    public Window<StyleCustomizationDTO> getStyleCustomizations(String projectId, KeysetScrollPosition position, int limit) {
        Window<StyleCustomizationDescription> window = new Window<>(List.of(), index -> position, false, false);

        List<StyleCustomizationDescription> styleCustomizationDescriptions = this.getStyleCustomizationDescriptions(projectId);

        if (limit > 0) {
            var cursorStyleCustomizationKey = position.getKeys().get("id");
            if (cursorStyleCustomizationKey instanceof String cursorStyleCustomizationId) {
                if (position.scrollsForward()) {
                    window = this.computeAllAfter(styleCustomizationDescriptions, position, cursorStyleCustomizationId, limit);
                } else {
                    window = this.computeAllBackward(styleCustomizationDescriptions, position, cursorStyleCustomizationId, limit);
                }
            } else if (position.scrollsForward()) {
                window = this.computeAllAfter(styleCustomizationDescriptions, position, null, limit);
            } else {
                window = this.computeAllBackward(styleCustomizationDescriptions, position, null, limit);
            }
        }

        return window.map(this.styleCustomizationMapper::toDTO);
    }

    private List<StyleCustomizationDescription> getStyleCustomizationDescriptions(String projectId) {
        var styleCustomizationDescriptions = new ArrayList<StyleCustomizationDescription>();
        var styleCustomizationIds = new HashSet<String>();
        this.styleCustomizationDescriptionProviders.stream()
                .map(styleCustomizationDescriptionProvider -> styleCustomizationDescriptionProvider.getStyleCustomizationDescriptions(projectId))
                .flatMap(List::stream)
                .forEach(styleCustomizationDescription -> {
                    if (styleCustomizationIds.add(styleCustomizationDescription.id())) {
                        styleCustomizationDescriptions.add(styleCustomizationDescription);
                    } else {
                        this.logger.atWarn()
                                .setMessage("Duplicate style customization description ignored")
                                .addKeyValue("styleCustomizationId", styleCustomizationDescription.id())
                                .addKeyValue("projectId", projectId)
                                .log();
                    }
                });

        return styleCustomizationDescriptions;
    }

    private Window<StyleCustomizationDescription> computeAllAfter(List<StyleCustomizationDescription> styleCustomizationDescriptions, KeysetScrollPosition position, String cursorStyleCustomizationId, int limit) {

        int cursorIndex = -1;
        if (cursorStyleCustomizationId != null) {
            cursorIndex = IntStream.range(0, styleCustomizationDescriptions.size())
                    .filter(index -> styleCustomizationDescriptions.get(index).id().equals(cursorStyleCustomizationId))
                    .findFirst()
                    .orElse(-1);
        }

        int fromIndex = cursorIndex + 1;
        int toIndex = (int) Math.min(styleCustomizationDescriptions.size(), (long) fromIndex + limit + 1);

        List<StyleCustomizationDescription> candidate = styleCustomizationDescriptions.subList(fromIndex, toIndex);
        boolean hasNext = candidate.size() > limit;
        boolean hasPrevious = fromIndex > 0;

        var windowContent = candidate.subList(0, Math.min(candidate.size(), limit));
        return new Window<>(windowContent, index -> position, hasNext, hasPrevious);
    }

    private Window<StyleCustomizationDescription> computeAllBackward(List<StyleCustomizationDescription> styleCustomizationDescriptions, KeysetScrollPosition position, String cursorStyleCustomizationId, int limit) {
        int cursorIndex = styleCustomizationDescriptions.size();
        if (cursorStyleCustomizationId != null) {
            cursorIndex = IntStream.range(0, styleCustomizationDescriptions.size())
                    .filter(index -> styleCustomizationDescriptions.get(index).id().equals(cursorStyleCustomizationId))
                    .findFirst()
                    .orElse(styleCustomizationDescriptions.size());
        }

        int fromIndex = Math.max(0, cursorIndex - limit - 1);
        List<StyleCustomizationDescription> subList = styleCustomizationDescriptions.subList(fromIndex, cursorIndex);

        boolean hasPrevious = subList.size() > limit;
        boolean hasNext = cursorIndex < styleCustomizationDescriptions.size();

        var windowContent = subList;
        if (hasPrevious) {
            windowContent = subList.subList(subList.size() - limit, subList.size());
        }

        return new Window<>(windowContent, index -> position, hasNext, hasPrevious);
    }
}
