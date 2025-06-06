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
package org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.HeaderSeparatorDisplayMode;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.interfacenode.InterfaceNodePaletteProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the interface node description.
 *
 * @author sbegaudeau
 */
public class InterfaceNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Interface";

    public static final String OPERATION_NAME = "Interface Operation";

    private final IColorProvider colorProvider;

    public InterfaceNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .headerSeparatorDisplayMode(HeaderSeparatorDisplayMode.IF_CHILDREN)
                .withHeader(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        var interfaceNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var operationNodeDescription = this.operationNodeDescription();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Interface")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(interfaceNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenDescriptions(operationNodeDescription)
                .build();
    }

    private NodeDescription operationNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .position(InsideLabelPosition.MIDDLE_LEFT)
                .style(insideLabelStyle)
                .build();

        var operationNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(OPERATION_NAME)
                .domainType("papaya:Operation")
                .semanticCandidatesExpression("aql:self.operations")
                .insideLabel(insideLabel)
                .style(operationNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalInterfaceNodeDescription = cache.getNodeDescription(NAME);
        if (optionalInterfaceNodeDescription.isPresent()) {
            var interfaceNodeDescription = optionalInterfaceNodeDescription.get();

            var palette = new InterfaceNodePaletteProvider().getNodePalette(cache);
            interfaceNodeDescription.setPalette(palette);

            diagramDescription.getNodeDescriptions().add(interfaceNodeDescription);
        }
    }

}
