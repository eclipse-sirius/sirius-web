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

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ApplicationServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.CommandNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.ControllerNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.DomainServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.EventNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.tools.PublicationEdgePaletteProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the publication edge description.
 *
 * @author sbegaudeau
 */
public class PublicationEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Publication";

    private final IColorProvider colorProvider;

    public PublicationEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsEdgeStyle = new DiagramBuilders().newEdgeStyleDescription()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .id(NAME)
                .centerLabelExpression("emits")
                .sourceExpression("aql:self.eContainer()")
                .targetExpression("aql:self.message")
                .isDomainBasedEdge(true)
                .domainType("papaya::Publication")
                .semanticCandidatesExpression("aql:self.eContainer().eAllContents()")
                .style(extendsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var publicationEdgeDescription = cache.getEdgeDescription(NAME).orElse(null);

        var controllerNodeDescription = cache.getNodeDescription(ControllerNodeDescriptionProvider.NAME).orElse(null);
        var applicationServiceNodeDescription = cache.getNodeDescription(ApplicationServiceNodeDescriptionProvider.NAME).orElse(null);
        var domainServiceNodeDescription = cache.getNodeDescription(DomainServiceNodeDescriptionProvider.NAME).orElse(null);

        var commandNodeDescription = cache.getNodeDescription(CommandNodeDescriptionProvider.NAME).orElse(null);
        var eventNodeDescription = cache.getNodeDescription(EventNodeDescriptionProvider.NAME).orElse(null);

        publicationEdgeDescription.getSourceDescriptions().addAll(List.of(controllerNodeDescription, applicationServiceNodeDescription, domainServiceNodeDescription));
        publicationEdgeDescription.getTargetDescriptions().addAll(List.of(commandNodeDescription, eventNodeDescription));
        diagramDescription.getEdgeDescriptions().add(publicationEdgeDescription);

        var palette = new PublicationEdgePaletteProvider().getEdgePalette(cache);
        publicationEdgeDescription.setPalette(palette);
    }
}
