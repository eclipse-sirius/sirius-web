/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.diagrams;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.ToolSection;
import org.springframework.stereotype.Service;

/**
 * Class used to manipulate tools and tool sections.
 *
 * @author hmarchadour
 */
@Service
public class ToolService implements IToolService {

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    public ToolService(IRepresentationDescriptionSearchService representationDescriptionSearchService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
    }

    private List<ToolSection> getToolSections(IEditingContext editingContext) {
        // @formatter:off
        return this.representationDescriptionSearchService.findAll(editingContext).values().stream()
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .flatMap(diagramDescription -> diagramDescription.getToolSections().stream())
                .toList();
        // @formatter:on
    }

    @Override
    public Optional<ITool> findToolById(IEditingContext editingContext, String toolId) {
        // @formatter:off
        return this.getToolSections(editingContext)
                .stream()
                .map(ToolSection::getTools)
                .flatMap(Collection::stream)
                .filter(tool -> Objects.equals(tool.getId(), toolId))
                .findFirst();
        // @formatter:on
    }

}
