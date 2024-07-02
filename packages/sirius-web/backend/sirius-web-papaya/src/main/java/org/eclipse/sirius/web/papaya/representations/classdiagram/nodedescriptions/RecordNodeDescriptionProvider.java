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
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the record node description.
 *
 * @author sbegaudeau
 */
public class RecordNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Record";

    public static final String RECORD_COMPONENT_NAME = "Record Component";

    private final IColorProvider colorProvider;

    public RecordNodeDescriptionProvider(IColorProvider colorProvider) {
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

        var recordNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        var recordComponentNodeDescription = this.recordComponentNodeDescription();

        var childrenLayoutStrategy = new DiagramBuilders().newListLayoutStrategyDescription()
                .areChildNodesDraggableExpression("aql:false")
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Record")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(recordNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .childrenDescriptions(recordComponentNodeDescription)
                .build();
    }

    private NodeDescription recordComponentNodeDescription() {
        var insideLabelStyle = new DiagramBuilders().newInsideLabelStyle()
                .showIcon(true)
                .labelColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.label()")
                .style(insideLabelStyle)
                .build();

        var recordComponentNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.DEFAULT_BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PALETTE_TEXT_PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(RECORD_COMPONENT_NAME)
                .domainType("papaya:RecordComponent")
                .semanticCandidatesExpression("aql:self.components")
                .insideLabel(insideLabel)
                .style(recordComponentNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalRecordNodeDescription = cache.getNodeDescription(NAME);
        if (optionalRecordNodeDescription.isPresent()) {
            var recordNodeDescription = optionalRecordNodeDescription.get();
            diagramDescription.getNodeDescriptions().add(recordNodeDescription);
        }
    }
}
