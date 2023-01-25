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
import org.eclipse.sirius.components.view.LabelEditTool;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Description implements interface.
 *
 * @author sbegaudeau
 */
public class ImplementsInterfaceEdgeDescriptionProvider implements IEdgeDescriptionProvider {

    @Override
    public EdgeDescription create() {
        var implementsInterfaceEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        implementsInterfaceEdgeStyle.setColor("#1a237e");
        implementsInterfaceEdgeStyle.setEdgeWidth(1);
        implementsInterfaceEdgeStyle.setLineStyle(LineStyle.DASH);
        implementsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        implementsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var implementsInterfaceEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        implementsInterfaceEdgeDescription.setName("Edge Implements interface");
        implementsInterfaceEdgeDescription.setLabelExpression("");
        implementsInterfaceEdgeDescription.setBeginLabelExpression("aql:'implements ' + semanticEdgeTarget.name");
        implementsInterfaceEdgeDescription.setBeginLabelEditTool(this.editImplementsInterfaceEdgeBeginLabel());
        implementsInterfaceEdgeDescription.setEndLabelExpression("aql:'implemented by ' + semanticEdgeSource.name");
        implementsInterfaceEdgeDescription.setEndLabelEditTool(this.editImplementsInterfaceEdgeEndLabel());
        implementsInterfaceEdgeDescription.setStyle(implementsInterfaceEdgeStyle);
        implementsInterfaceEdgeDescription.setSourceNodesExpression("aql:self");
        implementsInterfaceEdgeDescription.setTargetNodesExpression("aql:self.implements");
        implementsInterfaceEdgeDescription.setIsDomainBasedEdge(false);

        var implementsInterfaceEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        implementsInterfaceEdgeTool.setName("Implements");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("implements");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        implementsInterfaceEdgeTool.getBody().add(changeContext);

        implementsInterfaceEdgeDescription.getEdgeTools().add(implementsInterfaceEdgeTool);

        return implementsInterfaceEdgeDescription;
    }

    private LabelEditTool editImplementsInterfaceEdgeBeginLabel() {
        var editLabelTool = ViewFactory.eINSTANCE.createLabelEditTool();
        editLabelTool.setName("Edit Begin Label");
        editLabelTool.setInitialDirectEditLabelExpression("aql:semanticEdgeTarget.name");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeTarget");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:newLabel");

        changeContext.getChildren().add(setValue);
        editLabelTool.getBody().add(changeContext);

        return editLabelTool;
    }

    private LabelEditTool editImplementsInterfaceEdgeEndLabel() {
        var editLabelTool = ViewFactory.eINSTANCE.createLabelEditTool();

        editLabelTool.setName("Edit End Label");
        editLabelTool.setInitialDirectEditLabelExpression("aql:semanticEdgeSource.name");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:newLabel");

        changeContext.getChildren().add(setValue);
        editLabelTool.getBody().add(changeContext);

        return editLabelTool;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var implementsInterfaceEdgeDescription = cache.getEdgeDescription("Edge Implements interface");
        var classNodeDescription = cache.getNodeDescription("Node papaya::Class");
        var interfaceNodeDescription = cache.getNodeDescription("Node papaya::Interface");

        diagramDescription.getEdgeDescriptions().add(implementsInterfaceEdgeDescription);
        implementsInterfaceEdgeDescription.getSourceNodeDescriptions().add(classNodeDescription);
        implementsInterfaceEdgeDescription.getTargetNodeDescriptions().add(interfaceNodeDescription);
    }

}
