/*******************************************************************************
 * Copyright (c) 2019, 2024 Obeo.
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
import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationDescriptionSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.tools.ITool;
import org.eclipse.sirius.components.diagrams.tools.Palette;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Class used to manipulate tools and tool sections.
 *
 * @author hmarchadour
 */
@Service
public class ToolService implements IToolService {

    private static final String OBJECT_ID_ARRAY_SEPARATOR = ",";

    private final IRepresentationDescriptionSearchService representationDescriptionSearchService;

    private final IObjectService objectService;

    public ToolService(IRepresentationDescriptionSearchService representationDescriptionSearchService, IObjectService objectService) {
        this.representationDescriptionSearchService = Objects.requireNonNull(representationDescriptionSearchService);
        this.objectService = Objects.requireNonNull(objectService);
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

    @Override
    public void addToolVariablesInVariableManager(List<ToolVariable> toolvariables, IEditingContext editingContext, VariableManager variableManager) {
        toolvariables.forEach(toolVariable -> this.handleToolVariable(toolVariable, editingContext, variableManager));
    }

    private void handleToolVariable(ToolVariable toolvariable, IEditingContext editingContext, VariableManager variableManager) {
        switch (toolvariable.type()) {
            case STRING -> variableManager.put(toolvariable.name(), toolvariable.value());
            case OBJECT_ID -> {
                var optionalObject = this.objectService.getObject(editingContext, toolvariable.value());
                variableManager.put(toolvariable.name(), optionalObject.orElse(null));
            }
            case OBJECT_ID_ARRAY -> {
                String value = toolvariable.value();
                List<String> objectsIds = List.of(value.split(OBJECT_ID_ARRAY_SEPARATOR));
                List<Object> objects = objectsIds.stream()
                        .map(objectId -> this.objectService.getObject(editingContext, objectId))
                        .map(optionalObject -> optionalObject.orElse(null))
                        .toList();
                variableManager.put(toolvariable.name(), objects);
            }
            default -> {
                //We do nothing, the variable type is not supported
            }
        }
    }

}
