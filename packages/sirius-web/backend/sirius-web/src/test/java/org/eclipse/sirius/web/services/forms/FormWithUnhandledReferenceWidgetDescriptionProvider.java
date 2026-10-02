/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.services.forms;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextRepresentationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.domain.DomainPackage;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.compatibility.IPropertiesWidgetCreationService;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Provides a programmatic form with no reference widget creation handler.
 *
 * @author tgiraudet
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class FormWithUnhandledReferenceWidgetDescriptionProvider implements IEditingContextRepresentationDescriptionProvider {

    private static final String DESCRIPTION_ID = "FormWithUnhandledReferenceWidget";

    private final IIdentityService identityService;

    private final IPropertiesWidgetCreationService propertiesWidgetCreationService;

    public FormWithUnhandledReferenceWidgetDescriptionProvider(IIdentityService identityService, IPropertiesWidgetCreationService propertiesWidgetCreationService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.propertiesWidgetCreationService = Objects.requireNonNull(propertiesWidgetCreationService);
    }

    public String getRepresentationDescriptionId() {
        return DESCRIPTION_ID;
    }

    @Override
    public List<IRepresentationDescription> getRepresentationDescriptions(IEditingContext editingContext) {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(RepresentationVariables.SELF.name(), Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        var referenceWidget = this.propertiesWidgetCreationService.createReferenceWidget("unhandledSuperTypes", "Super types",
                DomainPackage.Literals.ENTITY__SUPER_TYPES, variableManager -> List.of());

        var groupDescription = GroupDescription.newGroupDescription("unhandledGroup")
                .idProvider(variableManager -> "unhandledGroup")
                .labelProvider(variableManager -> "Group")
                .semanticElementsProvider(variableManager -> variableManager.get(RepresentationVariables.SELF.name(), Object.class).stream().toList())
                .controlDescriptions(List.of(referenceWidget))
                .build();

        var pageDescription = PageDescription.newPageDescription("unhandledPage")
                .idProvider(variableManager -> "unhandledPage")
                .labelProvider(variableManager -> "Page")
                .semanticElementsProvider(variableManager -> variableManager.get(RepresentationVariables.SELF.name(), Object.class).stream().toList())
                .groupDescriptions(List.of(groupDescription))
                .canCreatePredicate(variableManager -> true)
                .build();

        var formDescription = FormDescription.newFormDescription(DESCRIPTION_ID)
                .label("FormWithUnhandledReferenceWidget")
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> "FormWithUnhandledReferenceWidget")
                .targetObjectIdProvider(targetObjectIdProvider)
                .canCreatePredicate(variableManager -> true)
                .pageDescriptions(List.of(pageDescription))
                .variableManagerInitializer(variableManager -> variableManager)
                .iconURLsProvider(variableManager -> List.of())
                .build();
        return List.of(formDescription);
    }
}
