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
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ChannelNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.tools.PublicationChannelEdgePaletteProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the publication channel edge description.
 *
 * @author mcharfadi
 */
public class PublicationChannelEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Publication#channel";

    private final IColorProvider colorProvider;

    public PublicationChannelEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsEdgeStyle = new DiagramBuilders().newEdgeStyleDescription()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .sourceArrowStyle(ArrowStyle.INPUT_ARROW)
                .targetArrowStyle(ArrowStyle.NONE)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .id(NAME)
                .centerLabelExpression("channel")
                .sourceExpression("aql:self.channel")
                .targetExpression("aql:self")
                .domainType("papaya::Publication")
                .isDomainBasedEdge(true)
                .semanticCandidatesExpression("aql:self.eContainer().eAllContents()")
                .style(extendsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var publicationsChannelEdgeDescriptionProvider = cache.getEdgeDescription(NAME).orElse(null);

        var publicationsEdgeDescriptionProvider = cache.getEdgeDescription(PublicationEdgeDescriptionProvider.NAME).orElse(null);

        var channelNodeDescription = cache.getNodeDescription(ChannelNodeDescriptionProvider.NAME).orElse(null);

        publicationsChannelEdgeDescriptionProvider.getSourceDescriptions().add(channelNodeDescription);
        publicationsChannelEdgeDescriptionProvider.getTargetDescriptions().add(publicationsEdgeDescriptionProvider);
        diagramDescription.getEdgeDescriptions().add(publicationsChannelEdgeDescriptionProvider);

        var palette = new PublicationChannelEdgePaletteProvider().getEdgePalette(cache);
        publicationsChannelEdgeDescriptionProvider.setPalette(palette);
    }
}
