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
package org.eclipse.sirius.web.application.views.relatedelements.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.sirius.components.collaborative.forms.api.IRelatedElementsDescriptionProvider;
import org.eclipse.sirius.components.collaborative.forms.api.RelatedElementsConfiguration;
import org.eclipse.sirius.components.collaborative.forms.variables.FormVariableProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.views.relatedelements.services.api.ICurrentTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.relatedelements.services.api.IIncomingTreeDescriptionProvider;
import org.eclipse.sirius.web.application.views.relatedelements.services.api.IOutgoingTreeDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Provides a tree widget description with incoming, current and outgoing trees.
 *
 * @author pcdavid
 */
@Service
public class RelatedElementsDescriptionProvider implements IRelatedElementsDescriptionProvider {

    public static final String FORM_DESCRIPTION_ID = "relatedElements_form_description";

    public static final String FORM_TITLE = "Related Elements";

    private static final String GROUP_DESCRIPTION_ID = "defaultRelatedElementsGroup";

    private static final String PAGE_DESCRIPTION_ID = "defaultRelatedElementsPage";

    private final IObjectService objectService;

    private final IIncomingTreeDescriptionProvider incomingTreeDescriptionProvider;

    private final ICurrentTreeDescriptionProvider currentTreeDescriptionProvider;

    private final IOutgoingTreeDescriptionProvider outgoingTreeDescriptionProvider;

    public RelatedElementsDescriptionProvider(IObjectService objectService, IIncomingTreeDescriptionProvider incomingTreeDescriptionProvider, ICurrentTreeDescriptionProvider currentTreeDescriptionProvider, IOutgoingTreeDescriptionProvider outgoingTreeDescriptionProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.incomingTreeDescriptionProvider = Objects.requireNonNull(incomingTreeDescriptionProvider);
        this.currentTreeDescriptionProvider = Objects.requireNonNull(currentTreeDescriptionProvider);
        this.outgoingTreeDescriptionProvider = Objects.requireNonNull(outgoingTreeDescriptionProvider);
    }

    @Override
    public FormDescription getFormDescription() {
        List<GroupDescription> groupDescriptions = List.of(this.getGroupDescription());

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElse(null);

        return FormDescription.newFormDescription(FORM_DESCRIPTION_ID)
                .label(FORM_TITLE)
                .idProvider(this::getFormId)
                .targetObjectIdProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(this.objectService::getId).orElse(null))
                .labelProvider(variableManager -> FORM_TITLE)
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> false)
                .pageDescriptions(List.of(this.getPageDescription(groupDescriptions)))
                .build();
    }

    private String getFormId(VariableManager variableManager) {
        List<?> selectedObjects = variableManager.get(FormVariableProvider.SELECTION.name(), List.class).orElse(List.of());
        List<String> selectedObjectIds = selectedObjects.stream()
                .map(this.objectService::getId)
                .toList();
        return new RelatedElementsConfiguration(selectedObjectIds).getId();
    }

    private GroupDescription getGroupDescription() {
        List<AbstractControlDescription> controlDescriptions = new ArrayList<>();
        controlDescriptions.add(this.incomingTreeDescriptionProvider.getTreeDescription());
        controlDescriptions.add(this.currentTreeDescriptionProvider.getTreeDescription());
        controlDescriptions.add(this.outgoingTreeDescriptionProvider.getTreeDescription());

        return GroupDescription.newGroupDescription(GROUP_DESCRIPTION_ID)
                .idProvider(variableManager -> FORM_TITLE)
                .labelProvider(variableManager -> FORM_TITLE)
                .displayModeProvider(variableManager -> GroupDisplayMode.TOGGLEABLE_AREAS)
                .semanticElementsProvider(variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList())
                .controlDescriptions(controlDescriptions)
                .build();
    }

    private PageDescription getPageDescription(List<GroupDescription> groupDescriptions) {
        Function<VariableManager, String> idProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getId)
                .orElseGet(() -> UUID.randomUUID().toString());

        Function<VariableManager, String> labelProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.objectService::getLabel)
                .orElse("");

        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();

        return PageDescription.newPageDescription(PAGE_DESCRIPTION_ID)
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(semanticElementsProvider)
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(variableManager -> true)
                .build();
    }
}
