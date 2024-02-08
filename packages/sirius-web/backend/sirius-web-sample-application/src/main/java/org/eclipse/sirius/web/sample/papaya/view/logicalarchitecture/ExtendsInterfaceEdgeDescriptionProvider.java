/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

import java.util.Objects;

import org.eclipse.sirius.components.view.builder.IViewDiagramElementFinder;
import org.eclipse.sirius.components.view.builder.providers.IColorProvider;
import org.eclipse.sirius.components.view.builder.providers.IEdgeDescriptionProvider;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.LineStyle;

/**
 * Description of extends interface.
 *
 * @author sbegaudeau
 */
public class ExtendsInterfaceEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    private final IColorProvider colorProvider;

    public ExtendsInterfaceEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsInterfaceEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        extendsInterfaceEdgeStyle.setColor(this.colorProvider.getColor("color_blue_2"));
        extendsInterfaceEdgeStyle.setEdgeWidth(1);
        extendsInterfaceEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var extendsInterfaceEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        extendsInterfaceEdgeDescription.setName("Edge Extends interface");
        extendsInterfaceEdgeDescription.setCenterLabelExpression("aql:'Extends'");
        extendsInterfaceEdgeDescription.setStyle(extendsInterfaceEdgeStyle);
        extendsInterfaceEdgeDescription.setSourceNodesExpression("aql:self");
        extendsInterfaceEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsInterfaceEdgeDescription.setIsDomainBasedEdge(false);

        return extendsInterfaceEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalExtendsInterfaceEdgeDescription = cache.getEdgeDescription("Edge Extends interface");
        var optionalInterfaceNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Interface");

        if (optionalExtendsInterfaceEdgeDescription.isPresent() && optionalInterfaceNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalExtendsInterfaceEdgeDescription.get());
            optionalExtendsInterfaceEdgeDescription.get().getSourceNodeDescriptions().add(optionalInterfaceNodeDescription.get());
            optionalExtendsInterfaceEdgeDescription.get().getTargetNodeDescriptions().add(optionalInterfaceNodeDescription.get());
        }
    }

}
