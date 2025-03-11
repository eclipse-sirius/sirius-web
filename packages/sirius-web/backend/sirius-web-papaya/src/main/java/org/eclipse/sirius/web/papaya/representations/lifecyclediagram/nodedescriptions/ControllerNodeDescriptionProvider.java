/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the controller node description.
 *
 * @author sbegaudeau
 */
public class ControllerNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Controller";

    private final IColorProvider colorProvider;

    public ControllerNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.CONTROLLER_DARK))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .position(InsideLabelPosition.MIDDLE_CENTER)
                .build();

        var nodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.CONTROLLER_LIGHT))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.CONTROLLER_DARK))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Controller")
                .semanticCandidatesExpression("aql:self.controllers")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var applicationLayerNodeDescription = cache.getNodeDescription(ApplicationLayerNodeDescriptionProvider.NAME).orElse(null);
        var controllerNodeDescription = cache.getNodeDescription(NAME).orElse(null);
        applicationLayerNodeDescription.getChildrenDescriptions().add(controllerNodeDescription);
    }
}
