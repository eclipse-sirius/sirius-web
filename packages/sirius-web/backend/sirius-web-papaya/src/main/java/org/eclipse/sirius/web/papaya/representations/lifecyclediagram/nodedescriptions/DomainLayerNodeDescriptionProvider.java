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
 * Used to create the domain layer node description.
 *
 * @author sbegaudeau
 */
public class DomainLayerNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Domain Layer";

    private final IColorProvider colorProvider;

    public DomainLayerNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIconExpression("aql:true")
                .withHeader(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("Domain Layer")
                .style(insideLabelStyle)
                .position(InsideLabelPosition.TOP_LEFT)
                .build();

        var childrenLayoutStrategy = new DiagramBuilders().newFreeFormLayoutStrategyDescription()
                .build();

        var nodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.DOMAIN_DARK))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::ApplicationConcern")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var applicationConcernNodeDescription = cache.getNodeDescription(ApplicationConcernNodeDescriptionProvider.NAME).orElse(null);
        var domainLayerNodeDescription = cache.getNodeDescription(NAME).orElse(null);
        applicationConcernNodeDescription.getChildrenDescriptions().add(domainLayerNodeDescription);
    }
}
