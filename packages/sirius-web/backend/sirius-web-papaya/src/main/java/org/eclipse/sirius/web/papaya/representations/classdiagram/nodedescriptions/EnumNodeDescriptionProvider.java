/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the enum node description.
 *
 * @author sbegaudeau
 */
public class EnumNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "Enum";

    public static final String ENUM_LITERAL_NAME = "Enum Literal";

    private final IColorProvider colorProvider;

    public EnumNodeDescriptionProvider(IColorProvider colorProvider) {
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

        var enumNodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(1)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .childrenLayoutStrategy(childrenLayoutStrategy)
                .build();

        var enumLiteralNodeDescription = this.enumLiteralNodeDescription();

        return new DiagramBuilders().newNodeDescription()
                .name(NAME)
                .domainType("papaya::Enum")
                .semanticCandidatesExpression("aql:editingContext.getSynchronizedObjects(semanticElementIds)")
                .insideLabel(insideLabel)
                .style(enumNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .childrenDescriptions(enumLiteralNodeDescription)
                .description("Represents the Papaya::Enum concept as a Node. An Enum Node can contain enum Literals.")
                .build();
    }

    private NodeDescription enumLiteralNodeDescription() {
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

        var enumLiteralNodeStyle = new DiagramBuilders().newIconLabelNodeStyleDescription()
                .background(this.colorProvider.getColor(PapayaColorPaletteProvider.BACKGROUND))
                .borderColor(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .borderSize(0)
                .borderRadius(0)
                .borderLineStyle(LineStyle.SOLID)
                .build();

        return new DiagramBuilders().newNodeDescription()
                .name(ENUM_LITERAL_NAME)
                .domainType("papaya:EnumLiteral")
                .semanticCandidatesExpression("aql:self.literals")
                .insideLabel(insideLabel)
                .style(enumLiteralNodeStyle)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .description("Represents the Papaya::EnumLiteral concept as in the EENum node.")
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalEnumNodeDescription = cache.getNodeDescription(NAME);
        if (optionalEnumNodeDescription.isPresent()) {
            var enumNodeDescription = optionalEnumNodeDescription.get();
            diagramDescription.getNodeDescriptions().add(enumNodeDescription);
        }
    }
}
