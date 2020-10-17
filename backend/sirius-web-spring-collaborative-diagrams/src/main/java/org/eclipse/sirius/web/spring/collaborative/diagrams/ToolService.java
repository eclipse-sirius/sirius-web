/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.diagrams;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.web.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription;
import org.eclipse.sirius.web.diagrams.tools.ITool;
import org.eclipse.sirius.web.diagrams.tools.ToolSection;
import org.eclipse.sirius.web.services.api.representations.IRepresentationDescriptionService;
import org.springframework.stereotype.Service;

/**
 * Class used to manipulate tools and tool sections.
 *
 * @author hmarchadour
 */
@Service
public class ToolService implements IToolService {

    private final IRepresentationDescriptionService representationDescriptionService;

    public ToolService(IRepresentationDescriptionService representationDescriptionService) {
        this.representationDescriptionService = Objects.requireNonNull(representationDescriptionService);
    }

    @Override
    public List<ToolSection> getToolSections(Diagram diagram) {
        // @formatter:off
        return this.representationDescriptionService.findRepresentationDescriptionById(diagram.getDescriptionId())
            .filter(DiagramDescription.class::isInstance)
            .map(DiagramDescription.class::cast)
            .map(DiagramDescription::getToolSections)
            .orElse(List.of());
        // @formatter:on
    }

    @Override
    public Optional<ITool> findToolById(Diagram diagram, String toolId) {
        // @formatter:off
        return this.getToolSections(diagram)
                .stream()
                .map(ToolSection::getTools)
                .flatMap(Collection::stream)
                .filter(tool -> Objects.equals(tool.getId(), toolId))
                .findFirst();
        // @formatter:on
    }

}
