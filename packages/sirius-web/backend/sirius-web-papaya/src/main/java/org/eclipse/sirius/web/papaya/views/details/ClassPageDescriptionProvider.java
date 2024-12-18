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
package org.eclipse.sirius.web.papaya.views.details;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.form.CheckboxDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.SelectDescription;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide a custom page description for papaya classes.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ClassPageDescriptionProvider implements IPageDescriptionProvider {

    private final NamedElementWidgetsProvider namedElementWidgetsProvider;

    public ClassPageDescriptionProvider(NamedElementWidgetsProvider namedElementWidgetsProvider) {
        this.namedElementWidgetsProvider = Objects.requireNonNull(namedElementWidgetsProvider);
    }

    @Override
    public PageDescription getPageDescription(IColorProvider colorProvider) {
        var corePropertiesGroupDescription = this.getCorePropertiesGroupDescription(colorProvider);
        var additionalInformationGroupDescription = this.getAdditionalInformationGroupDescription();

        return new FormBuilders().newPageDescription()
                .name("Papaya Class")
                .domainType("papaya:Class")
                .labelExpression("aql:self.name")
                .toolbarActions()
                .groups(
                        corePropertiesGroupDescription,
                        additionalInformationGroupDescription
                )
                .build();
    }

    private GroupDescription getCorePropertiesGroupDescription(IColorProvider colorProvider) {
        var corePropertiesGroupDescription = new FormBuilders().newGroupDescription()
                .name("Core Properties")
                .labelExpression("Core Properties")
                .semanticCandidatesExpression("aql:self")
                .displayMode(GroupDisplayMode.LIST)
                .build();

        corePropertiesGroupDescription.getChildren().addAll(this.namedElementWidgetsProvider.getWidgets(colorProvider));
        corePropertiesGroupDescription.getChildren().add(this.getVisibilityWidget());
        corePropertiesGroupDescription.getChildren().add(this.getAbstractWidget());
        corePropertiesGroupDescription.getChildren().add(this.getFinalWidget());
        corePropertiesGroupDescription.getChildren().add(this.getStaticWidget());

        return corePropertiesGroupDescription;
    }

    private SelectDescription getVisibilityWidget() {
        return new FormBuilders().newSelectDescription()
                .name("Visibility")
                .labelExpression("Visibility")
                .valueExpression("aql:papaya::Visibility.getEEnumLiteralByLiteral(self.visibility.toString())")
                .candidatesExpression("aql:papaya::Visibility.eLiterals")
                .candidateLabelExpression("aql:candidate.name")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("visibility")
                                                .valueExpression("aql:newValue.instance")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private CheckboxDescription getAbstractWidget() {
        return new FormBuilders().newCheckboxDescription()
                .name("Abstract")
                .labelExpression("Abstract")
                .valueExpression("aql:self.abstract")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("abstract")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private CheckboxDescription getFinalWidget() {
        return new FormBuilders().newCheckboxDescription()
                .name("Final")
                .labelExpression("Final")
                .valueExpression("aql:self.final")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("final")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private CheckboxDescription getStaticWidget() {
        return new FormBuilders().newCheckboxDescription()
                .name("Static")
                .labelExpression("Static")
                .valueExpression("aql:self.static")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("static")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private GroupDescription getAdditionalInformationGroupDescription() {
        return new FormBuilders().newGroupDescription()
                .name("Additional Information")
                .labelExpression("Additional Information")
                .semanticCandidatesExpression("aql:self")
                .displayMode(GroupDisplayMode.LIST)
                .build();
    }
}
