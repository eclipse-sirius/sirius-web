/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.web.services.diagrams;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.ResourceMetadataAdapter;
import org.eclipse.sirius.components.emf.services.IDAdapter;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramBuilders;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramLayoutOption;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeDescription;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test hide, reveal, and fade operations in diagrams.
 *
 * @author gdaniel
 */
@Service
@Conditional(OnStudioTests.class)
public class VisibilityDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool hideNodeTool;

    private NodeTool revealNodeTool;

    private NodeTool fadeNodeTool;

    private NodeTool unfadeNodeTool;

    private NodeTool resetNodeTool;

    private NodeTool revealWithConnectorTool;

    public VisibilityDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
        this.diagramIdProvider = Objects.requireNonNull(diagramIdProvider);
        this.view = this.createView();
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getViews().add(this.view);
        }
    }

    public String getRepresentationDescriptionId() {
        return this.diagramIdProvider.getId(this.diagramDescription);
    }

    public String getHideNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.hideNodeTool).toString().getBytes()).toString();
    }

    public String getRevealNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.revealNodeTool).toString().getBytes()).toString();
    }

    public String getFadeNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.fadeNodeTool).toString().getBytes()).toString();
    }

    public String getUnfadeNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.unfadeNodeTool).toString().getBytes()).toString();
    }

    public String getResetNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.resetNodeTool).toString().getBytes()).toString();
    }

    /**
     * Returns the identifier of the connector tool which renames its source and reveals its target's child node.
     *
     * @return the connector tool identifier
     */
    public String getRevealWithConnectorToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.revealWithConnectorTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View visibilityView = viewBuilder.build();
        visibilityView.getDescriptions().add(this.createDiagramDescription());
        visibilityView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("VisibilityDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("VisibilityDiagramDescription"));
        resource.getContents().add(visibilityView);

        return visibilityView;
    }

    /**
     * Creates a diagram description with hidden nodes and tools for visibility operations.
     *
     * @return the visibility diagram description
     */
    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        this.hideNodeTool = new NodeToolBuilder()
                .name("Hide")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.hide(Sequence{ selectedNode })")
                                .build()
                )
                .build();

        this.revealNodeTool = new NodeToolBuilder()
                .name("Reveal")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.reveal(Sequence{ selectedNode })")
                                .build()
                )
                .build();

        this.fadeNodeTool = new NodeToolBuilder()
                .name("Fade")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.fade(Sequence{ selectedNode })")
                                .build()
                )
                .build();

        this.unfadeNodeTool = new NodeToolBuilder()
                .name("Unfade")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.unfade(Sequence{ selectedNode })")
                                .build()
                )
                .build();

        this.resetNodeTool = new NodeToolBuilder()
                .name("Reset Visibility")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.resetViewModifiers(Sequence{ selectedNode })")
                                .build()
                )
                .build();


        var nodePalette = new NodePaletteBuilder()
                .nodeTools(this.hideNodeTool, this.revealNodeTool, this.fadeNodeTool, this.unfadeNodeTool, this.resetNodeTool)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .isHiddenByDefaultExpression("aql:self.name.endsWith('-domain')")
                .isFadedByDefaultExpression("aql:self.name.endsWith('-application')")
                .palette(nodePalette)
                .childrenDescriptions(this.createDetailsCompartment())
                .build();

        this.revealWithConnectorTool = this.createRevealWithConnectorTool();
        nodeDescription.getEdgeTools().add(new DiagramBuilders().newEdgeTool()
                .name("Connect and reveal")
                .targetElementDescriptions(nodeDescription)
                .palette(new DiagramBuilders().newNodePalette()
                        .nodeTools(this.revealWithConnectorTool)
                        .build())
                .build());

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'VisibilityDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .layoutOption(DiagramLayoutOption.NONE)
                .style(new DiagramBuilders().newDiagramStyleDescription().build())
                .build();

        return this.diagramDescription;

    }

    /**
     * Creates a connector tool which renames its source and reveals its target's child node.
     *
     * @return the connector tool
     */
    private NodeTool createRevealWithConnectorTool() {
        return new NodeToolBuilder()
                .name("Rename source and reveal target child node")
                .body(new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:'Connected component'")
                                .build(),
                        new ChangeContextBuilder()
                                .expression("aql:diagramServices.reveal(edgeTarget.getChildNodes())")
                                .build())
                .build();
    }

    /**
     * Creates a node description for nodes hidden by default using its parent component as the semantic element.
     *
     * @return the Details node description
     */
    private NodeDescription createDetailsCompartment() {
        return new NodeDescriptionBuilder()
                .name("Details")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self")
                .insideLabel(new InsideLabelDescriptionBuilder()
                        .labelExpression("Details")
                        .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                        .build())
                .style(new RectangularNodeStyleDescriptionBuilder()
                        .childrenLayoutStrategy(new DiagramBuilders().newListLayoutStrategyDescription().build())
                        .build())
                .isHiddenByDefaultExpression("aql:true")
                .build();
    }
}
