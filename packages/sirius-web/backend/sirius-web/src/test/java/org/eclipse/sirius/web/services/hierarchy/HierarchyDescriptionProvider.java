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
package org.eclipse.sirius.web.services.hierarchy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.function.Predicate;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.charts.hierarchy.descriptions.HierarchyDescription;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.representations.IRepresentation;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 *  Used to provide a Hierarchy  description for testing purpose.
 *
 * @author pcdavid
 */
@Service
public class HierarchyDescriptionProvider implements IEditingContextProcessor  {
    public static final String KIND = IRepresentation.KIND_PREFIX + "?type=TreeMap";

    public static final String HIERARCHY_DESCRIPTION_ID = UUID.nameUUIDFromBytes("sample-hierarchy-description".getBytes()).toString();

    private final IObjectService objectService;

    public HierarchyDescriptionProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getRepresentationDescriptions().put(HIERARCHY_DESCRIPTION_ID, this.createHierarchyDescription());
        }
    }

    private IRepresentationDescription createHierarchyDescription() {
        Predicate<VariableManager> canCreatePredicate = variableManager -> true;

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getLabel)
                .orElse(null);

        Function<VariableManager, List<Object>> childSemanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, EObject.class)
                .map(eObject -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(eObject.eContents());
                    return objects;
                })
                .orElse(List.of());

        // @formatter:on
        return new HierarchyDescription(HIERARCHY_DESCRIPTION_ID, "Sample Hierarchy Description", KIND, canCreatePredicate, targetObjectIdProvider, labelProvider, childSemanticElementsProvider);
    }
}
