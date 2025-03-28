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
package org.eclipse.sirius.web.papaya.representations.classdiagram.edgedescriptions;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.web.papaya.representations.classdiagram.nodedescriptions.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.papaya.services.PapayaColorPaletteProvider;

/**
 * Used to create the interface extends edge description.
 *
 * @author sbegaudeau
 */
public class InterfaceExtendsEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "Interface#extends";

    private final IColorProvider colorProvider;

    public InterfaceExtendsEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var implementsEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .color(this.colorProvider.getColor(PapayaColorPaletteProvider.PRIMARY))
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        return new DiagramBuilders().newEdgeDescription()
                .name(NAME)
                .centerLabelExpression("")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.extends")
                .isDomainBasedEdge(false)
                .style(implementsEdgeStyle)
                .build();
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalInterfaceNodeDescription = cache.getNodeDescription(InterfaceNodeDescriptionProvider.NAME);
        var optionalInterfaceExtendsEdgeDescription = cache.getEdgeDescription(NAME);

        if (optionalInterfaceNodeDescription.isPresent() && optionalInterfaceExtendsEdgeDescription.isPresent()) {
            var interfaceNodeDescription = optionalInterfaceNodeDescription.get();
            var interfaceExtendsEdgeDescription = optionalInterfaceExtendsEdgeDescription.get();

            interfaceExtendsEdgeDescription.getSourceDescriptions().add(interfaceNodeDescription);
            interfaceExtendsEdgeDescription.getTargetDescriptions().add(interfaceNodeDescription);

            diagramDescription.getEdgeDescriptions().add(interfaceExtendsEdgeDescription);
        }
    }
}
