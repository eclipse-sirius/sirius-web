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
package org.eclipse.sirius.web.papaya.views.details;

import static org.eclipse.sirius.web.papaya.views.details.PapayaDetailsViewPageDescriptionProvider.WHITE_COLOR;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.generated.form.FormBuilders;
import org.eclipse.sirius.components.view.builder.generated.reference.ReferenceBuilders;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.form.ButtonDescription;
import org.eclipse.sirius.components.view.form.GroupDescription;
import org.eclipse.sirius.components.view.form.GroupDisplayMode;
import org.eclipse.sirius.components.view.form.ListDescription;
import org.eclipse.sirius.components.view.form.PageDescription;
import org.eclipse.sirius.components.view.form.TreeDescription;
import org.eclipse.sirius.components.view.widget.reference.ReferenceWidgetDescription;
import org.eclipse.sirius.web.papaya.views.details.api.IPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Used to provide the custom page description for papaya components.
 *
 * @author sbegaudeau
 */
@Service
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class ComponentPageDescriptionProvider implements IPageDescriptionProvider {

    private final NamedElementWidgetsProvider namedElementWidgetsProvider;

    public ComponentPageDescriptionProvider(NamedElementWidgetsProvider namedElementWidgetsProvider) {
        this.namedElementWidgetsProvider = Objects.requireNonNull(namedElementWidgetsProvider);
    }

    @Override
    public PageDescription getPageDescription(IColorProvider colorProvider) {
        var corePropertiesGroupDescription = this.getCorePropertiesGroupDescription(colorProvider);
        var additionalInformationGroupDescription = this.getAdditionalInformationGroupDescription(colorProvider);

        return new FormBuilders().newPageDescription()
                .name("Papaya Component")
                .domainType("papaya:Component")
                .labelExpression("aql:self.name")
                .toolbarActions(
                        this.getNewChildComponentToolbarAction(colorProvider),
                        this.getNewPackageToolbarAction(colorProvider)
                )
                .groups(
                        corePropertiesGroupDescription,
                        additionalInformationGroupDescription
                )
                .build();
    }

    private ButtonDescription getNewChildComponentToolbarAction(IColorProvider colorProvider) {
        var newChildComponentStyle = new FormBuilders().newButtonDescriptionStyle()
                .backgroundColor(colorProvider.getColor(WHITE_COLOR))
                .build();

        return new FormBuilders().newButtonDescription()
                .name("New Child Component")
                .labelExpression("")
                .helpExpression("Create a new child component")
                .imageExpression("aql:'/icons/papaya/full/obj16/Component.svg'")
                .style(newChildComponentStyle)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newCreateInstance()
                                                .typeName("papaya:Component")
                                                .referenceName("components")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private ButtonDescription getNewPackageToolbarAction(IColorProvider colorProvider) {
        var newPackageStyle = new FormBuilders().newButtonDescriptionStyle()
                .backgroundColor(colorProvider.getColor(WHITE_COLOR))
                .build();

        return new FormBuilders().newButtonDescription()
                .name("New Package")
                .labelExpression("")
                .helpExpression("Create a new package")
                .imageExpression("aql:'/icons/papaya/full/obj16/Package.svg'")
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
        corePropertiesGroupDescription.getChildren().add(this.getDependenciesWidget());
        corePropertiesGroupDescription.getChildren().add(this.getUsedAsDependencyByWidget());

        return corePropertiesGroupDescription;
    }

    private ReferenceWidgetDescription getDependenciesWidget() {
        var dependenciesStyle = new ReferenceBuilders().newReferenceWidgetDescriptionStyle()
                .build();

        return new ReferenceBuilders().newReferenceWidgetDescription()
                .name("Dependencies")
                .labelExpression("Dependencies")
                .referenceOwnerExpression("aql:self")
                .referenceNameExpression("aql:'dependencies'")
                .style(dependenciesStyle)
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:self")
                                .children(
                                        new ViewBuilders().newSetValue()
                                                .featureName("dependencies")
                                                .valueExpression("aql:newValue")
                                                .build()
                                )
                                .build()
                )
                .build();
    }

    private ListDescription getUsedAsDependencyByWidget() {
        return new FormBuilders().newListDescription()
                .name("Used As Dependency By")
                .labelExpression("Used As Dependency By")
                .valueExpression("aql:self.usedAsDependencyBy")
                .displayExpression("aql:candidate.name")
                .isDeletableExpression("false")
                .build();
    }

    private GroupDescription getAdditionalInformationGroupDescription(IColorProvider colorProvider) {
        var additionalInformationGroupDescription = new FormBuilders().newGroupDescription()
                .name("Additional Information")
                .labelExpression("Additional Information")
                .semanticCandidatesExpression("aql:self")
                .displayMode(GroupDisplayMode.LIST)
                .build();

        additionalInformationGroupDescription.getChildren().add(this.getAllDependenciesWidget());
        additionalInformationGroupDescription.getChildren().add(this.getAllComponentsWidget());

        return additionalInformationGroupDescription;
    }

    private TreeDescription getAllDependenciesWidget() {
        return new FormBuilders().newTreeDescription()
                .name("All Dependencies")
                .labelExpression("All Dependencies")
                .childrenExpression("aql:self.dependencies")
                .treeItemLabelExpression("aql:self.name")
                .treeItemBeginIconExpression("aql:Sequence{'/icons/papaya/full/obj16/Component.svg'}")
                .isCheckableExpression("aql:false")
                .isTreeItemSelectableExpression("aql:false")
                .build();
    }

    private TreeDescription getAllComponentsWidget() {
        return new FormBuilders().newTreeDescription()
                .name("All Components")
                .labelExpression("All Components")
                .childrenExpression("aql:self.eContents(papaya::Component)")
                .treeItemLabelExpression("aql:self.name")
                .treeItemBeginIconExpression("aql:Sequence{'/icons/papaya/full/obj16/Component.svg'}")
                .isCheckableExpression("aql:false")
                .isTreeItemSelectableExpression("aql:false")
                .build();
    }
}
