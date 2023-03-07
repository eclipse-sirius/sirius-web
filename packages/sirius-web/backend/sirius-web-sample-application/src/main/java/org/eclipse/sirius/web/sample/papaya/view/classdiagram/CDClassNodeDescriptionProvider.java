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

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeTool;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodePalette;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Used to create the class node description.
 *
 * @author sbegaudeau
 */
public class CDClassNodeDescriptionProvider implements INodeDescriptionProvider {
    public static final String NAME = "CD Node Class";

    @Override
    public EObject create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#1976D2");
        nodeStyle.setBorderColor("#0d47a1");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(false);

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Class"));

        var nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(NAME);
        nodeDescription.setDomainType(domainType);
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);

        var abstractNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        abstractNodeStyle.setColor("#00796B");
        abstractNodeStyle.setBorderColor("#004D40");
        abstractNodeStyle.setLabelColor("white");
        abstractNodeStyle.setWithHeader(false);

        var abstractConditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        abstractConditionalNodeStyle.setCondition("aql:self.abstract");
        abstractConditionalNodeStyle.setStyle(abstractNodeStyle);
        nodeDescription.getConditionalStyles().add(abstractConditionalNodeStyle);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var classNodeDescription = cache.getNodeDescription(NAME);
        var packageNodeDescription = cache.getNodeDescription(CDPackageNodeDescriptionProvider.NAME);
        packageNodeDescription.getChildrenDescriptions().add(classNodeDescription);

        classNodeDescription.setPalette(this.palette(cache));
    }

    private NodePalette palette(PapayaViewCache cache) {
        var classNodeDescription = cache.getNodeDescription(NAME);
        var interfaceNodeDescription = cache.getNodeDescription(CDInterfaceNodeDescriptionProvider.NAME);

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        classNodeDescription.setPalette(nodePalette);
        nodePalette.getEdgeTools().add(this.createExtendsClassEdgeTool(classNodeDescription));
        nodePalette.getEdgeTools().add(this.createImplementsInterfaceEdgeTool(interfaceNodeDescription));
        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
        return nodePalette;
    }

    private EdgeTool createExtendsClassEdgeTool(NodeDescription classNodeDescription) {
        var extendsClassEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        extendsClassEdgeTool.setName("Extends Class");
        extendsClassEdgeTool.getTargetElementDescriptions().add(classNodeDescription);

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsClassEdgeTool.getBody().add(changeContext);
        return extendsClassEdgeTool;
    }

    private EdgeTool createImplementsInterfaceEdgeTool(NodeDescription interfaceNodeDescription) {
        var implementsInterfaceEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        implementsInterfaceEdgeTool.setName("Implements");
        implementsInterfaceEdgeTool.getTargetElementDescriptions().add(interfaceNodeDescription);

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("implements");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        implementsInterfaceEdgeTool.getBody().add(changeContext);
        return implementsInterfaceEdgeTool;
    }
}
