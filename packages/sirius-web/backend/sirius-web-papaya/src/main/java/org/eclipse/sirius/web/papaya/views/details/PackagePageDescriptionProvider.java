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

import static org.eclipse.sirius.web.papaya.views.details.PapayaDetailsViewPageDescriptionProvider.WHITE_COLOR;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.generated.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.ReferenceBuilders;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the custom page description for papaya packages.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class PackagePageDescriptionProvider implements IPageDescriptionProvider {

    private final NamedElementWidgetsProvider namedElementWidgetsProvider;

    private final QualifiedNameWidgetProvider qualifiedNameWidgetProvider;

    public PackagePageDescriptionProvider(NamedElementWidgetsProvider namedElementWidgetsProvider, QualifiedNameWidgetProvider qualifiedNameWidgetProvider) {
        this.namedElementWidgetsProvider = Objects.requireNonNull(namedElementWidgetsProvider);
        this.qualifiedNameWidgetProvider = Objects.requireNonNull(qualifiedNameWidgetProvider);
    }

    @Override
    public PageDescription getPageDescription(IColorProvider colorProvider) {
        var corePropertiesGroupDescription = this.getCorePropertiesGroupDescription(colorProvider);

        return new FormBuilders().newPageDescription()
                .name("Papaya Package")
                .domainType("papaya:Package")
                .labelExpression("aql:self.name")
                .toolbarActions(
                        this.getNewChildPackageToolbarAction(colorProvider)
                )
                .groups(
                        corePropertiesGroupDescription
                )
                .build();
    }

    private ButtonDescription getNewChildPackageToolbarAction(IColorProvider colorProvider) {
        var newPackageStyle = new FormBuilders().newButtonDescriptionStyle()
                .backgroundColor(colorProvider.getColor(WHITE_COLOR))
                .build();

        return new FormBuilders().newButtonDescription()
                .name("New Child Package")
                .labelExpression("")
                .helpExpression("Create a new child package")
                .imageExpression("aql:'/icons/full/obj16/Package.svg'")
                .style(newPackageStyle)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newCreateInstance()
                                                .typeName("papaya:Package")
                                                .referenceName("packages")
                                                .build()
                                )
                                .build()
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
        corePropertiesGroupDescription.getChildren().add(this.qualifiedNameWidgetProvider.getQualifiedNameWidget(colorProvider));
        corePropertiesGroupDescription.getChildren().add(this.getAnnotationsWidget());

        return corePropertiesGroupDescription;
    }

    private ReferenceWidgetDescription getAnnotationsWidget() {
        var dependenciesStyle = new ReferenceBuilders().newReferenceWidgetDescriptionStyle()
                .build();

        return new ReferenceBuilders().newReferenceWidgetDescription()
                .name("Annotations")
                .labelExpression("Annotations")
                .referenceOwnerExpression("aql:self")
                .referenceNameExpression("aql:'annotations'")
                .style(dependenciesStyle)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("annotations")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();
    }
}
