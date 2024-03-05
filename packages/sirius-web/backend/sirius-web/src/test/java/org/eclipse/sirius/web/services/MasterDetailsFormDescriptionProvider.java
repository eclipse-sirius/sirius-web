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
package org.eclipse.sirius.web.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.RichTextDescription;
import org.eclipse.sirius.components.forms.description.SelectDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to provide a form description of the master / details pattern.
 *
 * @author sbegaudeau
 */
@Service
public class MasterDetailsFormDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    public static final String DESCRIPTION_ID = "MasterDetailsFormDescription";

    private static final String CUSTOM_VARIABLE = "filter";

    private final IIdentityService identityService;

    public MasterDetailsFormDescriptionProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        SelectDescription selectDescription = SelectDescription.newSelectDescription("select")
                .idProvider(targetObjectIdProvider)
                .labelProvider(variableManager -> "Select")
                .targetObjectIdProvider(targetObjectIdProvider)
                .valueProvider(variableManager -> variableManager.get(CUSTOM_VARIABLE, String.class).orElse(null))
                .optionsProvider(variableManager -> List.of("first", "second", "third"))
                .optionIdProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, String.class).orElse(""))
                .optionLabelProvider(variableManager -> variableManager.get(SelectComponent.CANDIDATE_VARIABLE, String.class).orElse(""))
                .newValueHandler((variableManager, newValue) -> {
                    var currentVariableManager = variableManager;
                    while (!currentVariableManager.hasVariable(CUSTOM_VARIABLE)) {
                        currentVariableManager = currentVariableManager.getParent();
                    }

                    currentVariableManager.put(CUSTOM_VARIABLE, newValue);

                    return new Success();
                })
                .isReadOnlyProvider(variableManager -> false)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .build();

        RichTextDescription richTextDescription = RichTextDescription.newRichTextDescription("richtext")
                .idProvider(targetObjectIdProvider)
                .labelProvider(variableManager -> "RichText")
                .targetObjectIdProvider(targetObjectIdProvider)
                .valueProvider(variableManager -> variableManager.get(CUSTOM_VARIABLE, String.class).orElse(null))
                .newValueHandler((variableManager, newValue) -> new Success())
                .isReadOnlyProvider(variableManager -> false)
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "")
                .messageProvider(diagnostic -> "")
                .build();

        GroupDescription groupDescription = GroupDescription.newGroupDescription("group")
                .idProvider(targetObjectIdProvider)
                .labelProvider(variableManager -> "Group")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .controlDescriptions(List.of(selectDescription, richTextDescription))
                .build();

        PageDescription pageDescription = PageDescription.newPageDescription("page")
                .idProvider(targetObjectIdProvider)
                .labelProvider(variableManager -> "Page")
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(List.of(groupDescription))
                .canCreatePredicate(variableManager -> true)
                .build();

        FormDescription formDescription = FormDescription.newFormDescription(DESCRIPTION_ID)
                .label("MasterDetails")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> "Properties")
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(List.of(pageDescription))
                .variableManagerInitializer(variableManager -> {
                    variableManager.put(CUSTOM_VARIABLE, "first");
                    return variableManager;
                })
                .build();
        return List.of(formDescription);
    }
}
