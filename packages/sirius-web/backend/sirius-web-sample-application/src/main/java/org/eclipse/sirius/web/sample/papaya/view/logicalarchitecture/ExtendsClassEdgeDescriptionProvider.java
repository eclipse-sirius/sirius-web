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
package org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture;

import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description of extends class.
 *
 * @author sbegaudeau
 */
public class ExtendsClassEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    @Override
    public EdgeDescription create() {
        var extendsClassEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor("#0d47a1");
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var extendsClassEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName("Edge Extends class");
        extendsClassEdgeDescription.setLabelExpression("");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsClassEdgeDescription.setIsDomainBasedEdge(false);

        return extendsClassEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var extendsClassEdgeDescription = cache.getEdgeDescription("Edge Extends class");
        var classNodeDescription = cache.getNodeDescription("Node papaya_logical_architecture::Class");

        diagramDescription.getEdgeDescriptions().add(extendsClassEdgeDescription);
        extendsClassEdgeDescription.getSourceNodeDescriptions().add(classNodeDescription);
        extendsClassEdgeDescription.getTargetNodeDescriptions().add(classNodeDescription);
    }

}
