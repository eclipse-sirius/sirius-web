/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.studio.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.migration.MigrationService;
import org.eclipse.sirius.components.emf.migration.api.IMigrationParticipant;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.EdgeDescription;
import org.eclipse.sirius.components.view.diagram.EdgeStyle;
import org.eclipse.sirius.components.view.diagram.EdgeTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelDescription;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.diagram.provider.DefaultToolsFactory;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.studio.services.api.IDefaultViewResourceProvider;
import org.springframework.stereotype.Service;

/**
 * Provides the content of a default view resource.
 *
 * @author sbegaudeau
 */
@Service
public class DefaultViewResourceProvider implements IDefaultViewResourceProvider {

    private static final String VIEW_DOCUMENT_NAME = "ViewNewModel";
    private static final String COLOR_NAME_DARK = "color_dark";
    private static final String COLOR_NAME_TRANSPARENT = "color_transparent";

    private final List<IMigrationParticipant> migrationParticipants;

    public DefaultViewResourceProvider(List<IMigrationParticipant> migrationParticipants) {
        this.migrationParticipants = Objects.requireNonNull(migrationParticipants);
    }

    @Override
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    public Resource getResource(String domainName) {
        View view = ViewFactory.eINSTANCE.createView();
        DefaultToolsFactory defaultToolsFactory = new DefaultToolsFactory();

        org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        viewDiagramDescription.setName(domainName + " Diagram Description");
        viewDiagramDescription.setDomainType(domainName + "::Root");
        viewDiagramDescription.setTitleExpression(domainName + " diagram");
        viewDiagramDescription.setPalette(defaultToolsFactory.createDefaultDiagramPalette());
        view.getDescriptions().add(viewDiagramDescription);

        view.getColorPalettes().add(this.createColorPalette());

        var entity1Node = this.createNodeDescriptionsEntity1(view, domainName, defaultToolsFactory);

        viewDiagramDescription.getNodeDescriptions().add(entity1Node);
        viewDiagramDescription.getPalette().getNodeTools().add(this.createNewInstanceTool(domainName + "::Entity1", "entity1s"));

        var entity2Node = this.createNodeDescriptionsEntity2(view, domainName, defaultToolsFactory);

        viewDiagramDescription.getNodeDescriptions().add(entity2Node);
        viewDiagramDescription.getPalette().getNodeTools().add(this.createNewInstanceTool(domainName + "::Entity2", "entity2s"));

        this.addEdgeTool(entity1Node, entity2Node);

        this.addEdgeDescription(entity1Node, entity2Node, defaultToolsFactory, viewDiagramDescription, view);

        JsonResource resource = new JSONResourceFactory().createResourceFromPath(UUID.randomUUID().toString());
        var resourceMetadataAdapter = new ResourceMetadataAdapter(VIEW_DOCUMENT_NAME);
        var migrationService = new MigrationService(this.migrationParticipants);

        resourceMetadataAdapter.setMigrationData(migrationService.getMostRecentParticipantMigrationData());
        resource.getContents().add(view);
        resource.eAdapters().add(resourceMetadataAdapter);

        return resource;
    }

    private NodeDescription createNodeDescriptionsEntity1(View view, String domainName, DefaultToolsFactory defaultToolsFactory) {
        NodeDescription entity1Node = DiagramFactory.eINSTANCE.createNodeDescription();
        entity1Node.setName("Entity1 Node");
        entity1Node.setDomainType(domainName + "::Entity1");
        entity1Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity1Node.setInsideLabel(this.createInsideLabelDescription(view));
        entity1Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        entity1Node.setStyle(this.createRectangularNodeStyle(view, "color_blue", "border_blue"));
        entity1Node.setPalette(defaultToolsFactory.createDefaultNodePalette());
        return entity1Node;
    }

    private NodeDescription createNodeDescriptionsEntity2(View view, String domainName, DefaultToolsFactory defaultToolsFactory) {
        NodeDescription entity2Node = DiagramFactory.eINSTANCE.createNodeDescription();
        entity2Node.setName("Entity2 Node");
        entity2Node.setDomainType(domainName + "::Entity2");
        entity2Node.setSemanticCandidatesExpression("aql:self.eContents()");
        entity2Node.setInsideLabel(this.createInsideLabelDescription(view));
        entity2Node.setSynchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED);
        entity2Node.setStyle(this.createRectangularNodeStyle(view, "color_green", "border_green"));
        entity2Node.setPalette(defaultToolsFactory.createDefaultNodePalette());
        return entity2Node;
    }

    private void addEdgeTool(NodeDescription entity1Node, NodeDescription entity2Node) {
        EdgeTool createLinkTo = DiagramFactory.eINSTANCE.createEdgeTool();
        createLinkTo.setName("Link to");
        createLinkTo.getTargetElementDescriptions().add(entity2Node);
        ChangeContext gotoSemanticSource = ViewFactory.eINSTANCE.createChangeContext();
        gotoSemanticSource.setExpression("aql:semanticEdgeSource");
        createLinkTo.getBody().add(gotoSemanticSource);
        SetValue setLink = ViewFactory.eINSTANCE.createSetValue();
        setLink.setFeatureName("linkedTo");
        setLink.setValueExpression("aql:semanticEdgeTarget");
        gotoSemanticSource.getChildren().add(setLink);
        entity1Node.getPalette().getEdgeTools().add(createLinkTo);
    }

    private void addEdgeDescription(NodeDescription entity1Node, NodeDescription entity2Node, DefaultToolsFactory defaultToolsFactory, org.eclipse.sirius.components.view.diagram.DiagramDescription viewDiagramDescription, View view) {
        EdgeDescription linkedToEdge = DiagramFactory.eINSTANCE.createEdgeDescription();
        linkedToEdge.setName("LinkedTo Edge");
        linkedToEdge.setSemanticCandidatesExpression("");
        linkedToEdge.setCenterLabelExpression("");
        linkedToEdge.getSourceNodeDescriptions().add(entity1Node);
        linkedToEdge.setSourceNodesExpression("aql:self");
        linkedToEdge.getTargetNodeDescriptions().add(entity2Node);
        linkedToEdge.setTargetNodesExpression("aql:self.linkedTo");
        linkedToEdge.setPalette(defaultToolsFactory.createDefaultEdgePalette());
        viewDiagramDescription.getEdgeDescriptions().add(linkedToEdge);

        EdgeStyle edgeStyle = DiagramFactory.eINSTANCE.createEdgeStyle();
        edgeStyle.setColor(this.getColorFromPalette(view, COLOR_NAME_DARK));
        edgeStyle.setBackground(this.getColorFromPalette(view, COLOR_NAME_TRANSPARENT));
        edgeStyle.setBorderColor(this.getColorFromPalette(view, COLOR_NAME_DARK));
        edgeStyle.setBorderSize(0);
        linkedToEdge.setStyle(edgeStyle);
    }

    private InsideLabelDescription createInsideLabelDescription(View view) {
        var insideLabel = DiagramFactory.eINSTANCE.createInsideLabelDescription();
        insideLabel.setLabelExpression("aql:self.name");
        insideLabel.setPosition(InsideLabelPosition.TOP_CENTER);
        var insideLabelStyle = DiagramFactory.eINSTANCE.createInsideLabelStyle();
        insideLabelStyle.setLabelColor(this.getColorFromPalette(view, COLOR_NAME_DARK));
        insideLabelStyle.setBackground(this.getColorFromPalette(view, COLOR_NAME_TRANSPARENT));
        insideLabelStyle.setBorderColor(this.getColorFromPalette(view, COLOR_NAME_DARK));
        insideLabelStyle.setBorderSize(0);
        insideLabel.setStyle(insideLabelStyle);
        return insideLabel;
    }

    private RectangularNodeStyleDescription createRectangularNodeStyle(View view, String color, String borderColor) {
        RectangularNodeStyleDescription entity2Style = DiagramFactory.eINSTANCE.createRectangularNodeStyleDescription();
        entity2Style.setBackground(this.getColorFromPalette(view, color));
        entity2Style.setBorderColor(this.getColorFromPalette(view, borderColor));
        entity2Style.setBorderRadius(3);
        return entity2Style;
    }

    private NodeTool createNewInstanceTool(String typeName, String referenceName) {
        String simpleName = typeName.split("::")[1];
        NodeTool tool = DiagramFactory.eINSTANCE.createNodeTool();
        tool.setName("New " + simpleName);

        CreateInstance createInstance = ViewFactory.eINSTANCE.createCreateInstance();
        createInstance.setReferenceName(referenceName);
        createInstance.setTypeName(typeName);
        createInstance.setVariableName("newInstance");
        tool.getBody().add(createInstance);

        ChangeContext gotoNewInstance = ViewFactory.eINSTANCE.createChangeContext();
        gotoNewInstance.setExpression("aql:newInstance");
        createInstance.getChildren().add(gotoNewInstance);

        SetValue setInitialName = ViewFactory.eINSTANCE.createSetValue();
        setInitialName.setFeatureName("name");
        setInitialName.setValueExpression("New" + simpleName);
        gotoNewInstance.getChildren().add(setInitialName);

        return tool;
    }

    private ColorPalette createColorPalette() {
        var colorPalette = ViewFactory.eINSTANCE.createColorPalette();

        colorPalette.getColors().add(this.createFixedColor(COLOR_NAME_DARK, "#002639"));
        colorPalette.getColors().add(this.createFixedColor("color_blue", "#E5F5F8"));
        colorPalette.getColors().add(this.createFixedColor("color_green", "#B1D8B7"));
        colorPalette.getColors().add(this.createFixedColor("border_blue", "#33B0C3"));
        colorPalette.getColors().add(this.createFixedColor("border_green", "#76B947"));
        colorPalette.getColors().add(this.createFixedColor(COLOR_NAME_TRANSPARENT, "transparent"));

        return colorPalette;
    }

    private FixedColor createFixedColor(String name, String value) {
        var fixedColor = ViewFactory.eINSTANCE.createFixedColor();
        fixedColor.setName(name);
        fixedColor.setValue(value);
        return fixedColor;
    }

    private UserColor getColorFromPalette(View view, String colorName) {
        return view.getColorPalettes()
                .stream()
                .findFirst()
                .map(ColorPalette::getColors)
                .stream()
                .flatMap(Collection::stream)
                .filter(userColor -> userColor.getName().equals(colorName))
                .findFirst()
                .orElse(null);
    }
}
