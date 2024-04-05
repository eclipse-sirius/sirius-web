/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.services.selection;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.selection.Selection;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide a description for the selection representation.
 *
 * @author sbegaudeau
 */
@Service
public class SelectionDescriptionProvider implements IEditingContextProcessor {

    public static final String LABEL = "Selection";

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=selectionDescription&sourceElementId=Test";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public SelectionDescriptionProvider(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getRepresentationDescriptions().put(REPRESENTATION_DESCRIPTION_ID, this.getSelectionDescription());
        }
    }

    private SelectionDescription getSelectionDescription() {
        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getLabel)
                .orElse("");

        Function<VariableManager, List<String>> iconURLProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.labelService::getImagePath)
                .orElse(null);

        Function<VariableManager, List<?>> objectsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(eObject -> (List<EObject>) eObject.eContents())
                .orElse(List.of());

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        Function<VariableManager, String> selectionObjectsIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        return SelectionDescription.newSelectionDescription(REPRESENTATION_DESCRIPTION_ID)
                .label(LABEL)
                .idProvider(variableManager -> Selection.PREFIX)
                .labelProvider(labelProvider)
                .iconURLProvider(iconURLProvider)
                .messageProvider(variableManager -> "Select the objects to consider")
                .objectsProvider(objectsProvider)
                .targetObjectIdProvider(targetObjectIdProvider)
                .selectionObjectsIdProvider(selectionObjectsIdProvider)
                .canCreatePredicate(variableManager -> false)
                .build();
    }
}
