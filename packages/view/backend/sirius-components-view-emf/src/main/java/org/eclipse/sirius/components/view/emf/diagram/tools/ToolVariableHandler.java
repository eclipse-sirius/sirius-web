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
package org.eclipse.sirius.components.view.emf.diagram.tools;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.diagrams.dto.ToolVariable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.diagram.tools.api.IToolVariableHandler;
import org.springframework.stereotype.Service;

/**
 * Used to contribute tool variables in a variable manager.
 *
 * @author sbegaudeau
 */
@Service
public class ToolVariableHandler implements IToolVariableHandler {

    private final IObjectSearchService objectSearchService;

    public ToolVariableHandler(IObjectSearchService objectSearchService) {
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
    }

    @Override
    public void addToolVariablesInVariableManager(IEditingContext editingContext, VariableManager variableManager, List<ToolVariable> toolvariables) {
        for (var toolVariable: toolvariables) {
            switch (toolVariable.type()) {
                case STRING -> variableManager.put(toolVariable.name(), toolVariable.value());
                case OBJECT_ID -> {
                    var optionalObject = this.objectSearchService.getObject(editingContext, toolVariable.value());
                    variableManager.put(toolVariable.name(), optionalObject.orElse(null));
                }
                case OBJECT_ID_ARRAY -> {
                    String value = toolVariable.value();
                    List<String> objectsIds = List.of(value.split(","));
                    List<Object> objects = objectsIds.stream()
                            .map(objectId -> this.objectSearchService.getObject(editingContext, objectId))
                            .map(optionalObject -> optionalObject.orElse(null))
                            .toList();
                    variableManager.put(toolVariable.name(), objects);
                }
                default -> {
                    //We do nothing, the variable type is not supported
                }
            }
        }
    }
}
