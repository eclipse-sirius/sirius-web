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
package org.eclipse.sirius.web.sample.configuration;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.domain.Entity;
import org.eclipse.sirius.components.view.ArrowStyle;
import org.eclipse.sirius.components.view.DiagramDescription;
import org.eclipse.sirius.components.view.EdgeDescription;
import org.eclipse.sirius.components.view.LineStyle;
import org.eclipse.sirius.components.view.NodeDescription;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;

/**
 * Used to create the test view.
 *
 * @author sbegaudeau
 */
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class TestViewProvider {
    private Domain domain = new TestDomainProvider().getDomain();

    private DiagramDescription diagramDescription;

    private NodeDescription operationalEntityNodeDescription;

    private NodeDescription operationalPerimeterNodeDescription;

    private NodeDescription operationalActorNodeDescription;

    private NodeDescription operationalActivityNodeDescription;

    private NodeDescription componentNodeDescription;

    private NodeDescription providedServiceNodeDescription;

    private NodeDescription requiredServiceNodeDescription;

    private NodeDescription packageNodeDescription;

    private NodeDescription interfaceNodeDescription;

    private NodeDescription interfaceOperationNodeDescription;

    private NodeDescription classNodeDescription;

    private NodeDescription classAttributesNodeDescription;

    private NodeDescription classAttributeNodeDescription;

    private NodeDescription classOperationsNodeDescription;

    private NodeDescription classOperationNodeDescription;

    private NodeDescription dataTypeNodeDescription;

    private NodeDescription enumNodeDescription;

    private NodeDescription enumLiteralNodeDescription;

    public View getView() {
        View view = ViewFactory.eINSTANCE.createView();

        this.diagramDescription = ViewFactory.eINSTANCE.createDiagramDescription();
        this.diagramDescription.setDomainType(this.domainType(this.entity("Root")));
        this.diagramDescription.setName("Diagram");
        this.diagramDescription.setTitleExpression(this.domain.getName() + " Diagram");
        this.diagramDescription.setAutoLayout(false);
        view.getDescriptions().add(this.diagramDescription);

        this.createNodeDescriptions();
        this.linkNodeDescriptions();
        this.createEdgeDescriptions();

        return view;
    }

    private void createNodeDescriptions() {
        this.operationalEntityNodeDescription = this.createOperationalEntityNodeDescription();
        this.operationalPerimeterNodeDescription = this.createOperationalPerimeterNodeDescription();
        this.operationalActorNodeDescription = this.createOperationalActorNodeDescription();
        this.operationalActivityNodeDescription = this.createOperationalActivityNodeDescription();
        this.componentNodeDescription = this.createComponentNodeDescription();
        this.providedServiceNodeDescription = this.createProvidedServiceNodeDescription();
        this.requiredServiceNodeDescription = this.createRequiredServiceNodeDescription();
        this.packageNodeDescription = this.createPackageNodeDescription();
        this.interfaceNodeDescription = this.createInterfaceNodeDescription();
        this.interfaceOperationNodeDescription = this.createInterfaceOperationNodeDescription();
        this.classNodeDescription = this.createClassNodeDescription();
        this.classAttributesNodeDescription = this.createClassAttributesNodeDescription();
        this.classAttributeNodeDescription = this.createClassAttributeNodeDescription();
        this.classOperationsNodeDescription = this.createClassOperationsNodeDescription();
        this.classOperationNodeDescription = this.createClassOperationNodeDescription();
        this.dataTypeNodeDescription = this.createDataTypeNodeDescription();
        this.enumNodeDescription = this.createEnumNodeDescription();
        this.enumLiteralNodeDescription = this.createEnumLiteralNodeDescription();

        this.diagramDescription.getNodeDescriptions().add(this.operationalEntityNodeDescription);
        this.diagramDescription.getNodeDescriptions().add(this.operationalActorNodeDescription);
        this.diagramDescription.getNodeDescriptions().add(this.componentNodeDescription);
    }

    private void linkNodeDescriptions() {
        this.operationalEntityNodeDescription.getChildrenDescriptions().add(this.operationalPerimeterNodeDescription);

        this.operationalPerimeterNodeDescription.getChildrenDescriptions().add(this.operationalActivityNodeDescription);

        this.operationalEntityNodeDescription.getReusedChildNodeDescriptions().add(this.operationalActorNodeDescription);

        this.operationalActorNodeDescription.getReusedChildNodeDescriptions().add(this.operationalActivityNodeDescription);

        this.componentNodeDescription.getBorderNodesDescriptions().add(this.providedServiceNodeDescription);
        this.componentNodeDescription.getBorderNodesDescriptions().add(this.requiredServiceNodeDescription);
        this.componentNodeDescription.getChildrenDescriptions().add(this.packageNodeDescription);

        this.packageNodeDescription.getChildrenDescriptions().add(this.interfaceNodeDescription);
        this.packageNodeDescription.getReusedChildNodeDescriptions().add(this.packageNodeDescription);

        this.interfaceNodeDescription.getChildrenDescriptions().add(this.interfaceOperationNodeDescription);

        this.packageNodeDescription.getChildrenDescriptions().add(this.classNodeDescription);
        this.packageNodeDescription.getChildrenDescriptions().add(this.dataTypeNodeDescription);
        this.packageNodeDescription.getChildrenDescriptions().add(this.enumNodeDescription);

        this.classNodeDescription.getChildrenDescriptions().add(this.classAttributesNodeDescription);
        this.classNodeDescription.getChildrenDescriptions().add(this.classOperationsNodeDescription);

        this.classAttributesNodeDescription.getChildrenDescriptions().add(this.classAttributeNodeDescription);
        this.classOperationsNodeDescription.getChildrenDescriptions().add(this.classOperationNodeDescription);

        this.enumNodeDescription.getChildrenDescriptions().add(this.enumLiteralNodeDescription);
    }

    private void createEdgeDescriptions() {
        var interactionEdgeDescription = this.createInteractionEdgeDescription();
        interactionEdgeDescription.getSourceNodeDescriptions().add(this.operationalActivityNodeDescription);
        interactionEdgeDescription.getTargetNodeDescriptions().add(this.operationalActivityNodeDescription);

        var realizedByEdgeDescription = this.createRealizedByEdgeDescription();
        realizedByEdgeDescription.getSourceNodeDescriptions().add(this.operationalActivityNodeDescription);
        realizedByEdgeDescription.getTargetNodeDescriptions().add(this.componentNodeDescription);

        var dependsOnEdgeDescription = this.createDependsOnEdgeDescription();
        dependsOnEdgeDescription.getSourceNodeDescriptions().add(this.requiredServiceNodeDescription);
        dependsOnEdgeDescription.getTargetNodeDescriptions().add(this.providedServiceNodeDescription);

        var fulfillsContractEdgeDescription = this.createFulfillsContractEdgeDescription();
        fulfillsContractEdgeDescription.getSourceNodeDescriptions().add(this.providedServiceNodeDescription);
        fulfillsContractEdgeDescription.getSourceNodeDescriptions().add(this.requiredServiceNodeDescription);
        fulfillsContractEdgeDescription.getTargetNodeDescriptions().add(this.interfaceNodeDescription);

        var extendsInterfaceEdgeDescription = this.createExtendsInterfaceEdgeDescription();
        extendsInterfaceEdgeDescription.getSourceNodeDescriptions().add(this.interfaceNodeDescription);
        extendsInterfaceEdgeDescription.getTargetNodeDescriptions().add(this.interfaceNodeDescription);

        var implementsInterfaceEdgeDescription = this.createImplementsInterfaceEdgeDescription();
        implementsInterfaceEdgeDescription.getSourceNodeDescriptions().add(this.classNodeDescription);
        implementsInterfaceEdgeDescription.getTargetNodeDescriptions().add(this.interfaceNodeDescription);

        var extendsClassEdgeDescription = this.createExtendsClassEdgeDescription();
        extendsClassEdgeDescription.getSourceNodeDescriptions().add(this.classNodeDescription);
        extendsClassEdgeDescription.getTargetNodeDescriptions().add(this.classNodeDescription);

        var referencesClassEdgeDescription = this.createReferencesClassEdgeDescription();
        referencesClassEdgeDescription.getSourceNodeDescriptions().add(this.classNodeDescription);
        referencesClassEdgeDescription.getTargetNodeDescriptions().add(this.classNodeDescription);

        this.diagramDescription.getEdgeDescriptions().add(interactionEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(realizedByEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(dependsOnEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(fulfillsContractEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(extendsInterfaceEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(implementsInterfaceEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(extendsClassEdgeDescription);
        this.diagramDescription.getEdgeDescriptions().add(referencesClassEdgeDescription);
    }

    private NodeDescription createOperationalEntityNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#e0e0e0");
        nodeStyle.setBorderColor("#616161");
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("OperationalEntity")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalEntities");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        var defaultNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        defaultNodeTool.setName("New Operational Entity");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self");

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName("operationalEntities");
        createInstance.setTypeName(this.domainType(this.entity("OperationalEntity")));

        changeContext.getChildren().add(createInstance);
        defaultNodeTool.getBody().add(changeContext);

        nodeDescription.getNodeTools().addAll(List.of(defaultNodeTool, this.getInitializeNodeTool()));

        return nodeDescription;
    }

    private NodeTool getInitializeNodeTool() {
        var initializeNodeTool = ViewFactory.eINSTANCE.createNodeTool();
        initializeNodeTool.setName("Initialize Data");

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self.initialize()");

        initializeNodeTool.getBody().add(changeContext);

        return initializeNodeTool;
    }

    private NodeDescription createOperationalPerimeterNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#bdbdbd");
        nodeStyle.setBorderColor("#424242");
        nodeStyle.setLabelColor("1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("OperationalPerimeter")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalPerimeters");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        return nodeDescription;
    }

    private NodeDescription createOperationalActorNodeDescription() {
        var operationalActorNodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        operationalActorNodeStyle.setColor("#e0e0e0");
        operationalActorNodeStyle.setBorderColor("#616161");
        operationalActorNodeStyle.setLabelColor("#1212121");

        var operationalActorEmptyNodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        operationalActorEmptyNodeStyle.setShape("4d9a22c0-dc36-31c9-bb6a-c18c66b51d93");
        operationalActorEmptyNodeStyle.setColor("white");
        operationalActorEmptyNodeStyle.setBorderColor("");
        operationalActorEmptyNodeStyle.setBorderSize(0);
        operationalActorEmptyNodeStyle.setLabelColor("#1212121");

        var conditionalNodeStyle = ViewFactory.eINSTANCE.createConditionalNodeStyle();
        conditionalNodeStyle.setCondition("aql:self.operationalActivities->size() = 0");
        conditionalNodeStyle.setStyle(operationalActorEmptyNodeStyle);

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("OperationalActor")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActors");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(operationalActorNodeStyle);
        nodeDescription.getConditionalStyles().add(conditionalNodeStyle);

        return nodeDescription;
    }

    private NodeDescription createOperationalActivityNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#ffcc80");
        nodeStyle.setBorderColor("#fb8c00");
        nodeStyle.setLabelColor("#212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("OperationalActivity")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.operationalActivities");
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());

        return nodeDescription;
    }

    private EdgeDescription createInteractionEdgeDescription() {
        var interactionEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        interactionEdgeStyle.setColor("#212121");
        interactionEdgeStyle.setEdgeWidth(1);
        interactionEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        interactionEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var interactionEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        interactionEdgeDescription.setName("Interaction");
        interactionEdgeDescription.setLabelExpression("aql:'interacts with'");
        interactionEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        interactionEdgeDescription.setDomainType(this.domainType(this.entity("Interaction")));
        interactionEdgeDescription.setIsDomainBasedEdge(true);
        interactionEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        interactionEdgeDescription.setTargetNodesExpression("aql:self.target");
        interactionEdgeDescription.setStyle(interactionEdgeStyle);

        var interactionEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        interactionEdgeTool.setName("Interacts with");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setTypeName(this.domainType(this.entity("Interaction")));
        createInstance.setReferenceName("interactions");
        createInstance.setVariableName("self");

        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("target");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        createInstance.getChildren().add(setTargetValue);
        changeContext.getChildren().add(createInstance);
        interactionEdgeTool.getBody().add(changeContext);

        interactionEdgeDescription.getEdgeTools().add(interactionEdgeTool);

        return interactionEdgeDescription;
    }

    private NodeDescription createComponentNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#b0bec5");
        nodeStyle.setBorderColor("#455a64");
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Component")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.components");
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createProvidedServiceNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("f13acf89-e0bc-3b42-a0f6-c39e459e311e");
        nodeStyle.setColor("white");
        nodeStyle.setBorderColor("");
        nodeStyle.setBorderSize(0);
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("ProvidedService")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.providedServices");
        nodeDescription.setLabelExpression("aql:if self.contract = null then 'undefined' else self.contract.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createRequiredServiceNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createImageNodeStyleDescription();
        nodeStyle.setShape("67e2ae35-e8e7-3aaa-9ab0-c74df3410888");
        nodeStyle.setColor("white");
        nodeStyle.setBorderColor("");
        nodeStyle.setBorderSize(0);
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("RequiredService")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.requiredServices");
        nodeDescription.setLabelExpression("aql:if self.contract = null then 'undefined' else self.contract.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private EdgeDescription createRealizedByEdgeDescription() {
        var realizedByEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        realizedByEdgeStyle.setColor("#fb8c00");
        realizedByEdgeStyle.setEdgeWidth(1);
        realizedByEdgeStyle.setLineStyle(LineStyle.SOLID);
        realizedByEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        realizedByEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_ARROW);

        var realizedByEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        realizedByEdgeDescription.setName("Realized by");
        realizedByEdgeDescription.setLabelExpression("aql:self.name + ' is realized by ' + self.realizedBy.name");
        realizedByEdgeDescription.setStyle(realizedByEdgeStyle);
        realizedByEdgeDescription.setSourceNodesExpression("aql:self");
        realizedByEdgeDescription.setTargetNodesExpression("aql:self.realizedBy");
        realizedByEdgeDescription.setIsDomainBasedEdge(false);

        var realizedByEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        realizedByEdgeTool.setName("Realized by");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("realizedBy");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        realizedByEdgeTool.getBody().add(changeContext);

        realizedByEdgeDescription.getEdgeTools().add(realizedByEdgeTool);

        return realizedByEdgeDescription;
    }

    private EdgeDescription createDependsOnEdgeDescription() {
        var dependsOnEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        dependsOnEdgeStyle.setColor("#1b5e20");
        dependsOnEdgeStyle.setEdgeWidth(1);
        dependsOnEdgeStyle.setSourceArrowStyle(ArrowStyle.OUTPUT_FILL_CLOSED_ARROW);
        dependsOnEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var dependsOnEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        dependsOnEdgeDescription.setName("Depends on");
        dependsOnEdgeDescription.setStyle(dependsOnEdgeStyle);
        dependsOnEdgeDescription.setSourceNodesExpression("aql:self");
        dependsOnEdgeDescription.setTargetNodesExpression("aql:self.eResource().getContents(" + this.domainType(this.entity("ProvidedService"))
                + ")->select(providedService | providedService.contract <> null and providedService.contract = self.contract)->first()");

        return dependsOnEdgeDescription;
    }

    private EdgeDescription createFulfillsContractEdgeDescription() {
        var fulfillsContractEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        fulfillsContractEdgeStyle.setColor("#3f51b5");
        fulfillsContractEdgeStyle.setEdgeWidth(1);
        fulfillsContractEdgeStyle.setLineStyle(LineStyle.DOT);
        fulfillsContractEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        fulfillsContractEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var fulfillsContractEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        fulfillsContractEdgeDescription.setName("Fulfills contract");
        fulfillsContractEdgeDescription.setLabelExpression("aql:'fulfills'");
        fulfillsContractEdgeDescription.setStyle(fulfillsContractEdgeStyle);
        fulfillsContractEdgeDescription.setSourceNodesExpression("aql:self");
        fulfillsContractEdgeDescription.setTargetNodesExpression("aql:self.contract");
        fulfillsContractEdgeDescription.setIsDomainBasedEdge(false);

        var fulfillsContractEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        fulfillsContractEdgeTool.setName("Fulfills contract");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("contract");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        fulfillsContractEdgeTool.getBody().add(changeContext);

        fulfillsContractEdgeDescription.getEdgeTools().add(fulfillsContractEdgeTool);

        return fulfillsContractEdgeDescription;
    }

    private NodeDescription createPackageNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#d1c4e9");
        nodeStyle.setBorderColor("#5e35b1");
        nodeStyle.setLabelColor("#1212121");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Package")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.eContents()");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createFreeFormLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createInterfaceNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#3f51b5");
        nodeStyle.setBorderColor("#1a237e");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(true);

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Interface")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createInterfaceOperationNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("#3f51b5");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Operation")));
        nodeDescription.setStyle(nodeStyle);
        nodeDescription.setSemanticCandidatesExpression("aql:self.operations");
        nodeDescription.setLabelExpression("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif");

        var createNodeTool = ViewFactory.eINSTANCE.createNodeTool();

        var setValue = ViewFactory.eINSTANCE.createSetValue();
        setValue.setFeatureName("name");
        setValue.setValueExpression("aql:'performOperation'");

        var createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName("operations");
        createInstance.setTypeName(this.domainType(this.entity("Operation")));
        createInstance.setVariableName("operation");
        createInstance.getChildren().add(setValue);

        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:self");
        changeContext.getChildren().add(createInstance);

        createNodeTool.getBody().add(changeContext);

        return nodeDescription;
    }

    private EdgeDescription createExtendsInterfaceEdgeDescription() {
        var extendsInterfaceEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsInterfaceEdgeStyle.setColor("#1a237e");
        extendsInterfaceEdgeStyle.setEdgeWidth(1);
        extendsInterfaceEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var extendsInterfaceEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsInterfaceEdgeDescription.setName("Extends Interface");
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

    private NodeDescription createClassNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#2196f3");
        nodeStyle.setBorderColor("#0d47a1");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(true);

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Class")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createClassAttributesNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#2196f3");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Class")));
        nodeDescription.setName(nodeDescription.getName() + " - Attributes");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createClassAttributeNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("#2196f3");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Attribute")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.attributes");
        nodeDescription.setLabelExpression("aql:self.name + ': ' + self.type.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createClassOperationsNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#2196f3");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Class")));
        nodeDescription.setName(nodeDescription.getName() + " - Operations");
        nodeDescription.setSemanticCandidatesExpression("aql:self");
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setLabelExpression("");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createClassOperationNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("#2196f3");
        nodeStyle.setBorderColor("");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Operation")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.attributes");
        nodeDescription.setLabelExpression("aql:self.name + '(): ' + if self.type = null then 'void' else self.type.name endif");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private EdgeDescription createImplementsInterfaceEdgeDescription() {
        var implementsInterfaceEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        implementsInterfaceEdgeStyle.setColor("#1a237e");
        implementsInterfaceEdgeStyle.setEdgeWidth(1);
        implementsInterfaceEdgeStyle.setLineStyle(LineStyle.SOLID);
        implementsInterfaceEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        implementsInterfaceEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_CLOSED_ARROW);

        var implementsInterfaceEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        implementsInterfaceEdgeDescription.setName("Implements");
        implementsInterfaceEdgeDescription.setLabelExpression("");
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

    private EdgeDescription createExtendsClassEdgeDescription() {
        var extendsClassEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor("#0d47a1");
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW);

        var extendsClassEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName("Extends Class");
        extendsClassEdgeDescription.setLabelExpression("");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.extends");
        extendsClassEdgeDescription.setIsDomainBasedEdge(false);

        var extendsClassEdgeTool = ViewFactory.eINSTANCE.createEdgeTool();
        extendsClassEdgeTool.setName("Extends");
        var changeContext = ViewFactory.eINSTANCE.createChangeContext();
        changeContext.setExpression("aql:semanticEdgeSource");
        var setTargetValue = ViewFactory.eINSTANCE.createSetValue();
        setTargetValue.setFeatureName("extends");
        setTargetValue.setValueExpression("aql:semanticEdgeTarget");

        changeContext.getChildren().add(setTargetValue);
        extendsClassEdgeTool.getBody().add(changeContext);

        extendsClassEdgeDescription.getEdgeTools().add(extendsClassEdgeTool);

        return extendsClassEdgeDescription;
    }

    private EdgeDescription createReferencesClassEdgeDescription() {
        var extendsClassEdgeStyle = ViewFactory.eINSTANCE.createEdgeStyle();
        extendsClassEdgeStyle.setColor("#0d47a1");
        extendsClassEdgeStyle.setEdgeWidth(1);
        extendsClassEdgeStyle.setLineStyle(LineStyle.SOLID);
        extendsClassEdgeStyle.setSourceArrowStyle(ArrowStyle.NONE);
        extendsClassEdgeStyle.setTargetArrowStyle(ArrowStyle.DIAMOND);

        var extendsClassEdgeDescription = ViewFactory.eINSTANCE.createEdgeDescription();
        extendsClassEdgeDescription.setName("References Class");
        extendsClassEdgeDescription.setLabelExpression("aql:self.name");
        extendsClassEdgeDescription.setStyle(extendsClassEdgeStyle);
        extendsClassEdgeDescription.setSemanticCandidatesExpression("aql:self.eAllContents()");
        extendsClassEdgeDescription.setDomainType("papaya::Reference");
        extendsClassEdgeDescription.setSourceNodesExpression("aql:self.eContainer()");
        extendsClassEdgeDescription.setTargetNodesExpression("aql:self.type");
        extendsClassEdgeDescription.setIsDomainBasedEdge(true);

        return extendsClassEdgeDescription;
    }

    private NodeDescription createDataTypeNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#0097a7");
        nodeStyle.setBorderColor("#006064");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("DataType")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createEnumNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createRectangularNodeStyleDescription();
        nodeStyle.setColor("#26a69a");
        nodeStyle.setBorderColor("#00695c");
        nodeStyle.setLabelColor("white");
        nodeStyle.setWithHeader(true);

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("Enum")));
        nodeDescription.setChildrenLayoutStrategy(ViewFactory.eINSTANCE.createListLayoutStrategyDescription());
        nodeDescription.setSemanticCandidatesExpression("aql:self.types");
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private NodeDescription createEnumLiteralNodeDescription() {
        var nodeStyle = ViewFactory.eINSTANCE.createIconLabelNodeStyleDescription();
        nodeStyle.setColor("#26a69a");
        nodeStyle.setBorderColor("#00695c");
        nodeStyle.setLabelColor("white");

        var nodeDescription = this.createNodeDescription(this.domainType(this.entity("EnumLiteral")));
        nodeDescription.setSemanticCandidatesExpression("aql:self.enumLiterals");
        nodeDescription.setLabelExpression("aql:self.name");
        nodeDescription.setStyle(nodeStyle);

        return nodeDescription;
    }

    private Entity entity(String name) {
        return this.domain.getTypes().stream().filter(entity -> name.equals(entity.getName())).findFirst().orElse(null);
    }

    private String domainType(Entity entity) {
        // @formatter:off
        return Optional.ofNullable(entity)
                .map(Entity::eContainer)
                .filter(Domain.class::isInstance)
                .map(Domain.class::cast)
                .map(Domain::getName)
                .map(domainName -> domainName + "::" + entity.getName())
                .orElse("");
        // @formatter:on
    }

    private NodeDescription createNodeDescription(String domainType) {
        var nodeDescription = ViewFactory.eINSTANCE.createNodeDescription();
        nodeDescription.setName(domainType + " node");
        nodeDescription.setDomainType(domainType);
        return nodeDescription;
    }
}
