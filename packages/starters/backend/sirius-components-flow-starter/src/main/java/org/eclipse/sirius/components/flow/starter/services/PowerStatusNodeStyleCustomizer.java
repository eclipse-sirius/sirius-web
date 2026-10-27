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

import fr.obeo.dsl.designer.sample.flow.CompositeProcessor;
import fr.obeo.dsl.designer.sample.flow.PoweredStatus;

import java.util.Objects;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.variables.CoreVariables;
import org.eclipse.sirius.components.diagrams.INodeStyle;
import org.eclipse.sirius.components.diagrams.RectangularNodeStyle;
import org.eclipse.sirius.components.diagrams.description.DiagramDescription;
import org.eclipse.sirius.components.diagrams.description.NodeDescription;
import org.eclipse.sirius.components.diagrams.renderer.api.INodeStyleCustomizer;
import org.eclipse.sirius.components.flow.starter.services.api.IFlowCapableEditingContextPredicate;
import org.eclipse.sirius.components.representations.RepresentationVariables;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * The flow node style customizer updating the background color of flow element when their name contains "I'm Blue".
 *
 * @author gcoutable
 */
@Service
public class PowerStatusNodeStyleCustomizer implements INodeStyleCustomizer {

    private final IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate;

    public PowerStatusNodeStyleCustomizer(IFlowCapableEditingContextPredicate flowCapableEditingContextPredicate) {
        this.flowCapableEditingContextPredicate = Objects.requireNonNull(flowCapableEditingContextPredicate);
    }

    @Override
    @Transactional(readOnly = true)
    public INodeStyle customize(VariableManager variableManager, DiagramDescription diagramDescription, NodeDescription nodeDescription, INodeStyle nodeStyle) {
        INodeStyle customizeStyle = nodeStyle;
        if (nodeStyle instanceof RectangularNodeStyle rectangularNodeStyle) {
            var isFlowProject = variableManager.get(CoreVariables.EDITING_CONTEXT.name(), IEditingContext.class)
                    .map(IEditingContext::getId)
                    .map(this.flowCapableEditingContextPredicate::test)
                    .orElse(Boolean.FALSE);
            var isEnabled = variableManager.get(FlowRendererVariableCustomizer.IS_FLOW_STYLE_CUSTOMIZATION_POWER_STATUS_ENABLED.name(), Boolean.class)
                    .orElse(Boolean.FALSE);
            if (isFlowProject && isEnabled) {
                var optionalCompositeProcessor = variableManager.get(RepresentationVariables.SELF.name(), CompositeProcessor.class);
                if (optionalCompositeProcessor.isPresent()) {
                    customizeStyle = this.customizeNodeStyle(rectangularNodeStyle, optionalCompositeProcessor.get());
                }
            }
        }
        return customizeStyle;
    }

    private INodeStyle customizeNodeStyle(RectangularNodeStyle rectangularNodeStyle, CompositeProcessor compositeProcessor) {
        RectangularNodeStyle customizedNodeStyle = rectangularNodeStyle;
        if (compositeProcessor.getPowerStatus() == PoweredStatus.NONE) {
            customizedNodeStyle = RectangularNodeStyle.newRectangularNodeStyle(rectangularNodeStyle)
                    .borderColor("#EF5350")
                    .build();
        } else if (compositeProcessor.getPowerStatus() == PoweredStatus.LOW) {
            customizedNodeStyle = RectangularNodeStyle.newRectangularNodeStyle(rectangularNodeStyle)
                    .borderColor("#FF9800")
                    .build();
        } else if (compositeProcessor.getPowerStatus() == PoweredStatus.OK) {
            customizedNodeStyle = RectangularNodeStyle.newRectangularNodeStyle(rectangularNodeStyle)
                    .borderColor("#4CAF50")
                    .build();
        }
        return customizedNodeStyle;
    }
}
