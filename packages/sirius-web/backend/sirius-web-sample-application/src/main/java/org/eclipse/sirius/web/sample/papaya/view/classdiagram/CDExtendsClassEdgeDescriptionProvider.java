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
package org.eclipse.sirius.web.sample.papaya.view.classdiagram;

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
 * Used to create the extends class edge description.
 *
 * @author sbegaudeau
 */
public class CDExtendsClassEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "CD Edge Extends Class";

    private final IColorProvider colorProvider;

    public CDExtendsClassEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsClassEdgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor(this.colorProvider.getColor("color_blue"));
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var extendsClassEdgeDescription = DiagramFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName(NAME);
        extendsClassEdgeDescription.setCenterLabelExpression("");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsClassEdgeDescription.setIsDomainBasedEdge(false);

        return extendsClassEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, IViewDiagramElementFinder cache) {
        var optionalExtendsClassEdgeDescription = cache.getEdgeDescription(NAME);
        var optionalClassNodeDescription = cache.getNodeDescription(CDClassNodeDescriptionProvider.NAME);
        if (optionalExtendsClassEdgeDescription.isPresent() && optionalClassNodeDescription.isPresent()) {
            diagramDescription.getEdgeDescriptions().add(optionalExtendsClassEdgeDescription.get());
            optionalExtendsClassEdgeDescription.get().getSourceNodeDescriptions().add(optionalClassNodeDescription.get());
            optionalExtendsClassEdgeDescription.get().getTargetNodeDescriptions().add(optionalClassNodeDescription.get());
        }
    }
}
