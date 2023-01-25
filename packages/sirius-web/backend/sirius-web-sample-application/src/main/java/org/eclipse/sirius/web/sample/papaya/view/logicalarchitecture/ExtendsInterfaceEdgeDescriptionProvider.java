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
 * Description of extends interface.
 *
 * @author sbegaudeau
 */
public class ExtendsInterfaceEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    @Override
    public EdgeDescription create() {
        var extendsInterfaceEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsInterfaceEdgeStyle.setColor("#1a237e");
        extendsInterfaceEdgeStyle.setEdgeWidth(1);
        extendsInterfaceEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var extendsInterfaceEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsInterfaceEdgeDescription.setName("Edge Extends interface");
        extendsInterfaceEdgeDescription.setLabelExpression("aql:'Extends'");
        extendsInterfaceEdgeDescription.setStyle(extendsInterfaceEdgeStyle);
        extendsInterfaceEdgeDescription.setSourceNodesExpression("aql:self");
        extendsInterfaceEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsInterfaceEdgeDescription.setIsDomainBasedEdge(false);

        var extendsInterfaceEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        extendsInterfaceEdgeTool.setName("Extends");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsInterfaceEdgeTool.getBody().add(changeContext);

        extendsInterfaceEdgeDescription.getEdgeTools().add(extendsInterfaceEdgeTool);

        return extendsInterfaceEdgeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var extendsInterfaceEdgeDescription = cache.getEdgeDescription("Edge Extends interface");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya::Interface");

        diagramDescription.getEdgeDescriptions().add(extendsInterfaceEdgeDescription);
        extendsInterfaceEdgeDescription.getSourceNodeDescriptions().add(interfaceNodeDescription);
        extendsInterfaceEdgeDescription.getTargetNodeDescriptions().add(interfaceNodeDescription);
    }

}
