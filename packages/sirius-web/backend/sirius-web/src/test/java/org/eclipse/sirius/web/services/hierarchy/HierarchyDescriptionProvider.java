/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.core.api.IIdentityService;
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

    private final IIdentityService identityService;

    public HierarchyDescriptionProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
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
                .map(this.identityService::getId)
                .orElse(null);

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(HierarchyDescription.LABEL, String.class)
                .orElse("label");

        Function<VariableManager, List<Object>> childSemanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, EObject.class)
                .map(eObject -> {
                    List<Object> objects = new ArrayList<>();
                    objects.addAll(eObject.eContents());
                    return objects;
                })
                .orElse(List.of());

        var builder = HierarchyDescription.newHierarchyDescription(HIERARCHY_DESCRIPTION_ID)
                .label("Sample Hierarchy Description")
                .kind(KIND)
                .canCreatePredicate(canCreatePredicate)
                .targetObjectIdProvider(targetObjectIdProvider)
                .labelProvider(labelProvider)
                .childSemanticElementsProvider(childSemanticElementsProvider)
                .iconURLsProvider(variableManager -> List.of());

        return builder.build();
    }
}
