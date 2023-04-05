/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Used to create the extends interface edge description.
 *
 * @author sbegaudeau
 */
public class CDExtendsInterfaceEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    public static final String NAME = "CD Edge Extends Interface";

    private final IColorProvider colorProvider;

    public CDExtendsInterfaceEdgeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public EdgeDescription create() {
        var extendsInterfaceEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsInterfaceEdgeStyle.setColor(this.colorProvider.getColor("color_blue_2"));
        extendsInterfaceEdgeStyle.setEdgeWidth(1);
        extendsInterfaceEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var extendsInterfaceEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsInterfaceEdgeDescription.setName(NAME);
        extendsInterfaceEdgeDescription.setLabelExpression("aql:'Extends'");
        extendsInterfaceEdgeDescription.setStyle(extendsInterfaceEdgeStyle);
        extendsInterfaceEdgeDescription.setSourceNodesExpression("aql:self");
        extendsInterfaceEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsInterfaceEdgeDescription.setIsDomainBasedEdge(false);

        return extendsInterfaceEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var extendsInterfaceEdgeDescription = cache.getEdgeDescription(NAME);
        var interfaceNodeDescription = cache.getNodeDescription(CDInterfaceNodeDescriptionProvider.NAME);

        diagramDescription.getEdgeDescriptions().add(extendsInterfaceEdgeDescription);
        extendsInterfaceEdgeDescription.getSourceNodeDescriptions().add(interfaceNodeDescription);
        extendsInterfaceEdgeDescription.getTargetNodeDescriptions().add(interfaceNodeDescription);
    }
}
