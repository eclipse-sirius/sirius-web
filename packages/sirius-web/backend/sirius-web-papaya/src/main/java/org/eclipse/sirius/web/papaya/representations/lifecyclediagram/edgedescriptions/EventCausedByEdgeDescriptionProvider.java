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
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.CommandNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.lifecyclediagram.nodedescriptions.EventNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the event caused by edge description.
 *
 * @author sbegaudeau
 */
public class EventCausedByEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Event#causedBy";

    private final IColorProvider colorProvider;

    public EventCausedByEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsEdgeStyle = new DiagramBuilders().newEdgeStyleDescription()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.EVENT_DARK))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .id(NAME)
                .centerLabelExpression("caused by")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.causedBy")
                .isDomainBasedEdge(false)
                .style(extendsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var eventCausedByEdgeDescription = cache.getEdgeDescription(NAME).orElse(null);
        var commandNodeDescription = cache.getNodeDescription(CommandNodeDescriptionProvider.NAME).orElse(null);
        var eventNodeDescription = cache.getNodeDescription(EventNodeDescriptionProvider.NAME).orElse(null);

        eventCausedByEdgeDescription.getSourceDescriptions().addAll(List.of(eventNodeDescription));
        eventCausedByEdgeDescription.getTargetDescriptions().addAll(List.of(eventNodeDescription, commandNodeDescription));
        diagramDescription.getEdgeDescriptions().add(eventCausedByEdgeDescription);
    }
}
