/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.services.selection.api.ISelectModelElementVariableConverter;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.viewpoint.description.tool.SelectModelElementVariable;
import org.springframework.stereotype.Service;

/**
 * This class is used to convert a Sirius SelectModelElementVariable to a Sirius Web SelectionDescription. We only
 * handle SelectModelElementVariable for ContainerCreationDescription for now, and only one SelectModelElementVariable
 * per ContainerCreationDescription.
 *
 * @author arichard
 */
@Service
public class SelectModelElementVariableConverter implements ISelectModelElementVariableConverter {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;


    public SelectModelElementVariableConverter(IObjectService objectService, IIdentifierProvider identifierProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
    }

    @Override
    public SelectionDescription convert(SelectModelElementVariable selectModelElementVariable, org.eclipse.sirius.diagram.description.DiagramDescription diagramDescription) {
        return SelectionDescription.newSelectionDescription(this.identifierProvider.getIdentifier(selectModelElementVariable))
                .messageProvider(variableManager -> {
                    String message = selectModelElementVariable.getMessage();
                    if (message == null) {
                        message = "";
                    }
                    return message;
                })
                .idProvider(variableManager -> "selection://")
                .labelProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getLabel).orElse(null))
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .label("Selection Description")
                .canCreatePredicate(variableManager -> false)
                .iconURLsProvider(variableManager -> List.of())
                .build();
    }

}
