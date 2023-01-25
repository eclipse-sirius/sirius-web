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
package org.eclipse.sirius.web.sample.papaya.view.operationalanalysis;

import java.util.List;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;
import org.eclipse.sirius.web.sample.papaya.view.PapyaViewBuilder;

/**
 * Description of the operational entity.
 *
 * @author sbegaudeau
 */
public class OperationalEntityNodeDescriptionProvider implements INodeDescriptionProvider {

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#e0e0e0");
        nodeStyle.setBorderColor("#616161");
        nodeStyle.setLabelColor("#1212121");

        var builder = new PapyaViewBuilder();

        var nodeDescription = builder.createNodeDescription("OperationalEntity");
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalEntities");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);

        var defaultNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        defaultNodeTool.setName("New Operational Entity");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self");

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName("operationalEntities");
        createInstance.setTypeName(builder.domainType(builder.entity("OperationalEntity")));
        createInstance.setVariableName("self");

        var createView = ViewFactory.eINSTANCE.createCreateView();
        createView.setElementDescription(nodeDescription);
        createView.setSemanticElementExpression("aql:self");
        createView.setParentViewExpression("aql:selectedNode");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'Operational Entity'");

        createView.getChildren().add(setValue);
        createInstance.getChildren().add(createView);
        changeContext.getChildren().add(createInstance);
        defaultNodeTool.getBody().add(changeContext);

        nodeDescription.getNodeTools().addAll(List.of(this.getInitializeNodeTool(), defaultNodeTool));
        nodeDescription.setLabelEditTool(new PapayaToolsFactory().editName());
        nodeDescription.setDeleteTool(new PapayaToolsFactory().deleteTool());

        return nodeDescription;
    }

    private NodeTool getInitializeNodeTool() {
        var initializeNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        initializeNodeTool.setName("Initialize Data");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.initialize(diagramContext, convertedNodes)");

        initializeNodeTool.getBody().add(changeContext);

        return initializeNodeTool;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var operationalEntityNodeDescription = cache.getNodeDescription("Node papaya::OperationalEntity");
        var operationalPerimeterNodeDescription = cache.getNodeDescription("Node papaya::OperationalPerimeter");
        var operationalActorNodeDescription = cache.getNodeDescription("Node papaya::OperationalActor");

        diagramDescription.getNodeDescriptions().add(operationalEntityNodeDescription);
        operationalEntityNodeDescription.getChildrenDescriptions().add(operationalPerimeterNodeDescription);
        operationalEntityNodeDescription.getReusedChildNodeDescriptions().add(operationalActorNodeDescription);
    }

}
