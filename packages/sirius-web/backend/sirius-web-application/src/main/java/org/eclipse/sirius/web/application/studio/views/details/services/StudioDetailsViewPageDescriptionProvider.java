/*******************************************************************************
 * Copyright (c) 2025, 2026 Obeo.
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
package org.eclipse.sirius.web.application.studio.views.details.services;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.emf.FixedColorPropertiesConfigurer;
import org.eclipse.sirius.components.view.emf.diagram.NodeStylePropertiesConfigurer;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewGroupDescriptionProvider;
import org.eclipse.sirius.web.application.studio.views.details.services.api.IStudioDetailsViewPageDescriptionProvider;
import org.springframework.stereotype.Service;

/**
 * Defines the properties view for the View DSL elements, which specializes/customizes the default rules.
 *
 *  The implementation is mostly a fork of the default rules in PropertiesDefaultDescriptionProvider, with two differences:
 *  <ol>
 *    <li>It only applies to elements from the View DSL which do not have their own even more specific properties definition.</li>
 *    <li>It uses a customizable variant of the generic EStringIfDescriptionProvider so that specific text fields can get special treatment (a specific style and completion proposal provider).</li>
 *  </ol>
 *
 * @author tgiraudet
 */
@Service
public class StudioDetailsViewPageDescriptionProvider implements IStudioDetailsViewPageDescriptionProvider {

    /**
     * These types have even more specific properties definition, see {@link NodeStylePropertiesConfigurer}
     * or {@link FixedColorPropertiesConfigurer}.
     */
    private static final List<EClass> TYPES_WITH_CUSTOM_PROPERTIES = List.of(
            DiagramPackage.Literals.IMAGE_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.ICON_LABEL_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.RECTANGULAR_NODE_STYLE_DESCRIPTION,
            DiagramPackage.Literals.LIST_LAYOUT_STRATEGY_DESCRIPTION,
            DiagramPackage.Literals.EDGE_STYLE_DESCRIPTION,
            ViewPackage.Literals.FIXED_COLOR
    );

    private static final String PAGE_DESCRIPTION_ID = "view_properties_description";

    private final IIdentityService identityService;

    private final ILabelService labelService;

    private final IStudioDetailsViewGroupDescriptionProvider studioDetailsViewGroupDescriptionProvider;

    public StudioDetailsViewPageDescriptionProvider(IIdentityService identityService, ILabelService labelService, IStudioDetailsViewGroupDescriptionProvider studioDetailsViewGroupDescriptionProvider) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
        this.studioDetailsViewGroupDescriptionProvider = Objects.requireNonNull(studioDetailsViewGroupDescriptionProvider);
    }

    @Override
    public PageDescription createPageDescription() {
        List<GroupDescription> groupDescriptions = List.of(
            this.studioDetailsViewGroupDescriptionProvider.createCorePropertiesGroup()
        );

        Function<VariableManager, String> idProvider = variableManager ->
            variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElseGet(() -> UUID.randomUUID().toString());

        Function<VariableManager, String> labelProvider = variableManager ->
            variableManager.get(VariableManager.SELF, Object.class)
                .map(self -> this.labelService.getStyledLabel(self).toString())
                .orElseGet(() -> UUID.randomUUID().toString());

        return PageDescription.newPageDescription(
                UUID.nameUUIDFromBytes(PAGE_DESCRIPTION_ID.getBytes()).toString())
                .idProvider(idProvider)
                .labelProvider(labelProvider)
                .semanticElementsProvider(variableManager -> Collections.singletonList(variableManager.getVariables().get(VariableManager.SELF)))
                .groupDescriptions(groupDescriptions)
                .canCreatePredicate(this::canCreate)
                .build();
    }

    private boolean canCreate(VariableManager variableManager) {
        Optional<EObject> selectedElement = variableManager.get(VariableManager.SELF, Object.class)
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast);
        boolean isViewElement = selectedElement
                .map(this::isViewElement)
                .orElse(false);
        boolean hasCustomProperties = selectedElement.map(EObject::eClass).filter(TYPES_WITH_CUSTOM_PROPERTIES::contains).isPresent();
        return isViewElement && !hasCustomProperties;
    }

    private boolean isViewElement(EObject element) {
        return element instanceof View || (element != null && this.isViewElement(element.eContainer()));
    }
}

