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

import fr.obeo.dsl.designer.sample.flow.Named;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.LineStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.renderer.api.INodeStyleCustomizer;
import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapableEditingContextPredicate;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * The flow node style customizer updating the style, color, and size the border of flow elements when their name contains "da be di da be dai".
 *
 * @author gcoutable
 */
@Service
public class DabediNodeStyleCustomizer implements INodeStyleCustomizer {

    private final boolean nodeCustomizationEnabled;

    private final IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate;

    public DabediNodeStyleCustomizer(@Value("${sirius.components.diagram.node.customization.enabled:false}") boolean nodeCustomizationEnabled, IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate) {
        this.nodeCustomizationEnabled = nodeCustomizationEnabled;
        this.flowCapableEditingContextPredicate = Objects.requireNonNull(flowCapableEditingContextPredicate);
    }

    @Override
    public INodeStyle customize(VariableManager variableManager, DiagramDescription diagramDescription, NodeDescription nodeDescription, INodeStyle nodeStyle) {
        INodeStyle customizeStyle = nodeStyle;
        if (this.nodeCustomizationEnabled && nodeStyle instanceof RectangularNodeStyle rectangularNodeStyle) {
            var isFlowProject = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class)
                    .map(IEditingContext::getId)
                    .map(this.flowCapableEditingContextPredicate::test)
                    .orElse(Boolean.FALSE);
            if (isFlowProject) {
                var optionalNamed = variableManager.get(RepresentationVariables.SELF.name(), Named.class);
                if (optionalNamed.isPresent() && optionalNamed.get().getName().contains("da be di da be dai")) {
                    customizeStyle = RectangularNodeStyle.newRectangularNodeStyle(rectangularNodeStyle)
                            .borderSize(5)
                            .borderColor("#231664")
                            .borderStyle(LineStyle.Dash)
                            .build();
                }
            }
        }
        return customizeStyle;
    }
}
