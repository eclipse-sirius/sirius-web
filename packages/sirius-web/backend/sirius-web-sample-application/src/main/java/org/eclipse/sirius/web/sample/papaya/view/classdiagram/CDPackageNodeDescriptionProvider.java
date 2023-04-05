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

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramElementDescription;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.SynchronizationPolicy;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.IColorProvider;
import org.eclipse.sirius.web.sample.papaya.view.INodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.PapayaToolsFactory;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewBuilder;
import org.eclipse.sirius.web.sample.papaya.view.PapayaViewCache;

/**
 * Used to create the package node description.
 *
 * @author sbegaudeau
 */
public class CDPackageNodeDescriptionProvider implements INodeDescriptionProvider {

    public static final String NAME = "CD Node Package";

    private final IColorProvider colorProvider;

    public CDPackageNodeDescriptionProvider(IColorProvider colorProvider) {
        this.colorProvider = Objects.requireNonNull(colorProvider);
    }

    @Override
    public NodeDescription create() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor(this.colorProvider.getColor("color_blue_7"));
        nodeStyle.setBorderColor(this.colorProvider.getColor("border_blue_3"));
        nodeStyle.setLabelColor(this.colorProvider.getColor("label_black"));

        var builder = new PapayaViewBuilder();
        var domainType = builder.domainType(builder.entity("Package"));

        var nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(NAME);
        nodeDescription.setDomainType(domainType);
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.getReusedChildNodeDescriptions().add(nodeDescription);
        nodeDescription.setSynchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED);
        nodeDescription.setCollapsible(true);

        return nodeDescription;
    }

    @Override
    public void link(DiagramDescription diagramDescription, PapayaViewCache cache) {
        var packageNodeDescription = cache.getNodeDescription(NAME);
        var classNodeDescription = cache.getNodeDescription(CDClassNodeDescriptionProvider.NAME);
        var interfaceNodeDescription = cache.getNodeDescription(CDInterfaceNodeDescriptionProvider.NAME);

        diagramDescription.getNodeDescriptions().add(packageNodeDescription);

        var nodePalette = ViewFactory.eINSTANCE.createNodePalette();
        packageNodeDescription.setPalette(nodePalette);

        nodePalette.getNodeTools().add(new CDCreateClassNodeToolProvider().create(cache));
        nodePalette.getNodeTools().add(new CDCreateInterfaceNodeToolProvider().create(cache));
        nodePalette.getNodeTools().add(new CDCreatePackageNodeToolProvider().create(cache));

        nodePalette.setLabelEditTool(new PapayaToolsFactory().editName());
        nodePalette.setDeleteTool(new PapayaToolsFactory().deleteTool());
    }

    private NodeTool createNamedElement(String typeName, String containinedFeatureName, String initialName, DiagramElementDescription diagramElementDescription) {
        var nodeTool = ViewFactory.eINSTANCE.createNodeTool();
        nodeTool.setName("New " + initialName);

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(typeName);
        createInstance.setVariableName("newInstance");
        createInstance.setReferenceName(containinedFeatureName);

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:newInstance");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'" + initialName + "'");

        var createView = ViewFactory.eINSTANCE.createCreateView();
        createView.setParentViewExpression("aql:selectedNode");
        createView.setSemanticElementExpression("aql:newInstance");
        createView.setElementDescription(diagramElementDescription);

        nodeTool.getBody().add(createInstance);
        createInstance.getChildren().add(changeContext);
        changeContext.getChildren().add(setValue);
        setValue.getChildren().add(createView);

        return nodeTool;
    }


}
