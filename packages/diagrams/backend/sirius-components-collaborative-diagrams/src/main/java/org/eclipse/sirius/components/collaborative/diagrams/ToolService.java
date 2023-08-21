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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.eclipse.sirius.components.collaborative.diagrams.api.IToolService;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.Palette;
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

    private List<Palette> getPalettes(IEditingContext editingContext, Diagram diagram) {
        return this.representationDescriptionSearchService.findById(editingContext, diagram.getDescriptionId())
                .filter(DiagramDescription.class::isInstance)
                .map(DiagramDescription.class::cast)
                .map(DiagramDescription::getPalettes)
                .orElse(List.of());
    }

    @Override
    public Optional<ITool> findToolById(IEditingContext editingContext, Diagram diagram, String toolId) {
        return this.getPalettes(editingContext, diagram)
                .stream()
                .flatMap(palette -> Stream.concat(
                        palette.getTools().stream(),
                        palette.getToolSections().stream()
                                .flatMap(toolSection -> toolSection.getTools().stream())
                ))
                .filter(tool -> Objects.equals(tool.getId(), toolId))
                .findFirst();
    }

}
