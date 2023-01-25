/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.web.sample.papaya.view;

import java.util.List;

import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ClassNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ComponentNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.DataTypeNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.DependsOnEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.EnumNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ExtendsClassEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ExtendsInterfaceEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.FulfillsContractEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ImplementsInterfaceEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.InterfaceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.PackageNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ProvidedServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.RealizedByEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.ReferencesClassEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.logicalarchitecture.RequiredServiceNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.InteractionEdgeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalActivityNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalActorNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalEntityNodeDescriptionProvider;
import org.eclipse.sirius.web.sample.papaya.view.operationalanalysis.OperationalPerimeterNodeDescriptionProvider;

/**
 * Used to create the test view.
 *
 * @author sbegaudeau
 */
public class PapayaViewProvider {

    private DiagramDescription diagramDescription;

    public View getView() {
        View view = ViewFactory.eINSTANCE.createView();

        this.diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        this.diagramDescription.setDomainType("papaya_core::Root");
        this.diagramDescription.setName("Diagram");
        this.diagramDescription.setTitleExpression("Papaya Diagram");
        this.diagramDescription.setAutoLayout(false);

        var dropTool = ViewFactory.eINSTANCE.createDropTool();
        dropTool.setName("Drop Tool");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.drop(selectedNode, diagramContext, convertedNodes)");
        dropTool.getBody().add(changeContext);
        DiagramPalette palette = ViewFactory.eINSTANCE.createDiagramPalette();
        palette.setDropTool(dropTool);
        this.diagramDescription.setPalette(palette);

        view.getDescriptions().add(this.diagramDescription);

        var cache = new PapayaViewCache();
        // @formatter:off
        var diagramElementDescriptionProviders = List.of(
                new OperationalEntityNodeDescriptionProvider(),
                new OperationalPerimeterNodeDescriptionProvider(),
                new OperationalActorNodeDescriptionProvider(),
                new OperationalActivityNodeDescriptionProvider(),
                new InteractionEdgeDescriptionProvider(),
                new RealizedByEdgeDescriptionProvider(),
                new ComponentNodeDescriptionProvider(),
                new ProvidedServiceNodeDescriptionProvider(),
                new RequiredServiceNodeDescriptionProvider(),
                new PackageNodeDescriptionProvider(),
                new ClassNodeDescriptionProvider(),
                new InterfaceNodeDescriptionProvider(),
                new EnumNodeDescriptionProvider(),
                new DataTypeNodeDescriptionProvider(),
                new DependsOnEdgeDescriptionProvider(),
                new FulfillsContractEdgeDescriptionProvider(),
                new ExtendsClassEdgeDescriptionProvider(),
                new ReferencesClassEdgeDescriptionProvider(),
                new ImplementsInterfaceEdgeDescriptionProvider(),
                new ExtendsInterfaceEdgeDescriptionProvider(),
                new ImplementsInterfaceEdgeDescriptionProvider()
        );
        // @formatter:on

        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> {
            var nodeDescription = diagramElementDescriptionProvider.create();
            cache.put(nodeDescription);
        });
        diagramElementDescriptionProviders.forEach(diagramElementDescriptionProvider -> diagramElementDescriptionProvider.link(this.diagramDescription, cache));

        var builder = new PapayaViewBuilder();
        var newOperationalEntityNodeTool = this.createNewOperationalEntityTool(builder, cache.getNodeDescription("Node papaya_operational_analysis::OperationalEntity"));
        palette.getNodeTools().addAll(List.of(this.getInitializeNodeTool(), newOperationalEntityNodeTool));

        var newComponentNodeTool = new PapayaToolsFactory().createNamedElement("papaya_logical_architecture::Component", "components", "Component");
        newComponentNodeTool.setName("New Component");
        palette.getNodeTools().add(newComponentNodeTool);

        var newOperationalActorNodeTool = new PapayaToolsFactory().createNamedElement("papaya_operational_analysis::OperationalActor", "operationalActors", "Operational Actor");
        newOperationalActorNodeTool.setName("New Operational Actor");
        palette.getNodeTools().add(newOperationalActorNodeTool);

        return view;
    }

    private NodeTool createNewOperationalEntityTool(PapayaViewBuilder builder, NodeDescription nodeDescription) {
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
        createView.setParentViewExpression("aql:false");

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'Operational Entity'");

        createView.getChildren().add(setValue);
        createInstance.getChildren().add(createView);
        changeContext.getChildren().add(createInstance);
        defaultNodeTool.getBody().add(changeContext);
        return defaultNodeTool;
    }

    private NodeTool getInitializeNodeTool() {
        var initializeNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        initializeNodeTool.setName("Initialize Data");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.initialize(diagramContext, convertedNodes)");

        initializeNodeTool.getBody().add(changeContext);

        return initializeNodeTool;
    }
}
