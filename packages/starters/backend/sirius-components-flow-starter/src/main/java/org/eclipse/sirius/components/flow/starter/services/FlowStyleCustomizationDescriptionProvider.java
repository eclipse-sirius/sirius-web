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
package org.eclipse.sirius.components.flow.starter.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapableEditingContextPredicate;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.StyleCustomizationDescription;
import org.eclipse.sirius.web.projects.stylecustomizations.application.services.api.IStyleCustomizationDescriptionProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Provider for descriptions of style customization for flow element.
 *
 * @author gcoutable
 */
@Service
public class FlowStyleCustomizationDescriptionProvider implements IStyleCustomizationDescriptionProvider {

    public static final String FLOW_STYLE_CUSTOMIZATION_I_M_BLUE = "flow-style-customization-I-m-blue";

    public static final String FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI = "flow-style-customization-da-be-di-da-be-dai";

    private final boolean nodeCustomizationEnabled;

    private final IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate;

    public FlowStyleCustomizationDescriptionProvider(@Value("${sirius.web.style.customization.enabled:false}") boolean nodeCustomizationEnabled, IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate) {
        this.nodeCustomizationEnabled = nodeCustomizationEnabled;
        this.flowCapableEditingContextPredicate = Objects.requireNonNull(flowCapableEditingContextPredicate);
    }

    @Override
    public List<StyleCustomizationDescription> getStyleCustomizationDescriptions(String projectId) {
        if (this.nodeCustomizationEnabled && this.flowCapableEditingContextPredicate.test(projectId)) {
            return List.of(new StyleCustomizationDescription(FLOW_STYLE_CUSTOMIZATION_I_M_BLUE, "I'm blue style customization",
                            "Change the background of flow element graphical node to blue if label contains 'I'm Blue'"),
                    new StyleCustomizationDescription(FLOW_STYLE_CUSTOMIZATION_DA_BE_DI_DA_BE_DAI, "Da be di da be dai style customization",
                            "Change the border style of node if label contains 'Da be di da be dai'"));
        }
        return List.of();
    }
}
