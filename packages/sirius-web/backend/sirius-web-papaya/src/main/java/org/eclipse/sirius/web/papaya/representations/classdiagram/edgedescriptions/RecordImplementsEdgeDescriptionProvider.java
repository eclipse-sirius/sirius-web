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
package org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.RecordNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to provide the record implements edge description.
 *
 * @author sbegaudeau
 */
public class RecordImplementsEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Record#implements";

    private final IColorProvider colorProvider;

    public RecordImplementsEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var implementsEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .centerLabelExpression("")
                .sourceNodesExpression("aql:self")
                .targetNodesExpression("aql:self.implements")
                .isDomainBasedEdge(false)
                .style(implementsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalRecordNodeDescription = cache.getNodeDescription(RecordNodeDescriptionProvider.NAME);
        var optionalInterfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME);
        var optionalRecordImplementsEdgeDescription = cache.getEdgeDescription(NAME);

        if (optionalRecordNodeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent() && optionalRecordImplementsEdgeDescription.isPresent()) {
            var recordNodeDescription = optionalRecordNodeDescription.get();
            var interfaceNodeDescription = optionalInterfaceNodeDescription.get();
            var recordImplementsEdgeDescription = optionalRecordImplementsEdgeDescription.get();

            recordImplementsEdgeDescription.getSourceNodeDescriptions().add(recordNodeDescription);
            recordImplementsEdgeDescription.getTargetNodeDescriptions().add(interfaceNodeDescription);

            diagramDescription.getEdgeDescriptions().add(recordImplementsEdgeDescription);
        }
    }
}
