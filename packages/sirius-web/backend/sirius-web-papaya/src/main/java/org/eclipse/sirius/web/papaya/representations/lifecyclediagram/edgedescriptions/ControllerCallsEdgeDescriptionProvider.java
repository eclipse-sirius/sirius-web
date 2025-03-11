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
package org.eclipse.sirius.web.papaya.representations.lifecyclediagram.edgedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ControllerNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ApplicationServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the controller calls edge description.
 *
 * @author sbegaudeau
 */
public class ControllerCallsEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Controller#calls";

    private final IColorProvider colorProvider;

    public ControllerCallsEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.SERVICE_DARK))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .centerLabelExpression("calls")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.calls")
                .isDomainBasedEdge(false)
                .style(extendsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var controllerCallsEdgeDescription = cache.getEdgeDescription(NAME).orElse(null);
        var controllerNodeDescription = cache.getNodeDescription(ControllerNodeDescriptionProvider.NAME).orElse(null);
        var applicationServiceNodeDescription = cache.getNodeDescription(ApplicationServiceNodeDescriptionProvider.NAME).orElse(null);

        controllerCallsEdgeDescription.getSourceDescriptions().add(controllerNodeDescription);
        controllerCallsEdgeDescription.getTargetDescriptions().add(applicationServiceNodeDescription);
        diagramDescription.getEdgeDescriptions().add(controllerCallsEdgeDescription);
    }
}
