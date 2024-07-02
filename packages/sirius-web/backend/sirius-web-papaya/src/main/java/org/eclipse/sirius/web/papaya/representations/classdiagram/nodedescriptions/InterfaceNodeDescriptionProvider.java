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
package org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodePalette;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.ImportAllInterfaceSubtypesNodeToolProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.ImportInterfaceImplementationsNodeToolProvider;
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
                .showIcon(true)
                .displayHeaderSeparator(true)
                .withHeader(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var interfaceNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var operationNodeDescription = this.operationNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Interface")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(interfaceNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(operationNodeDescription)
                .build();
    }

    private NodeDescription operationNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var operationNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
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
        var palette = this.palette(cache);

        var optionalInterfaceNodeDescription = cache.getNodeDescription(NAME);
        if (optionalInterfaceNodeDescription.isPresent()) {
            var interfaceNodeDescription = optionalInterfaceNodeDescription.get();
            interfaceNodeDescription.setPalette(palette);

            diagramDescription.getNodeDescriptions().add(interfaceNodeDescription);
        }
    }

    private NodePalette palette(IViewDiagramElementFinder cache) {
        return new DiagramBuilders().newNodePalette()
                .nodeTools(
                        new ImportInterfaceImplementationsNodeToolProvider().create(cache),
                        new ImportAllInterfaceSubtypesNodeToolProvider().create(cache)
                )
                .build();
    }
}
