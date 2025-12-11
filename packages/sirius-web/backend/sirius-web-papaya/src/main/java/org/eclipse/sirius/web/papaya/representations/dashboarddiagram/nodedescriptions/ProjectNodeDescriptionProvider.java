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
package org.eclipse.sirius.web.papaya.representations.dashboarddiagram.nodedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.INodeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.representations.classdiagram.tools.interfacenode.InterfaceNodePaletteProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the project node description.
 *
 * @author fbarbin
 */
public class ProjectNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "ProjectNode";

    private final IColorProvider colorProvider;

    public ProjectNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
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

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Project")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(interfaceNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
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
