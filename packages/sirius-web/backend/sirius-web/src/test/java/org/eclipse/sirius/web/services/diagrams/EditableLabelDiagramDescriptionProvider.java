/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.diagram.LabelEditToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilders;
import org.eclipse.sirius.components.view.diagram.ArrowStyle;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramLayoutOption;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.LineStyle;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test the label edition on nodes.
 *
 * @author pcdavid
 */
@Service
@Conditional(OnStudioTests.class)
public class EditableLabelDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public EditableLabelDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View directEditLabelView = viewBuilder.build();
        directEditLabelView.getDescriptions().add(this.createDiagramDescription());
        directEditLabelView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("EditableLabelDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("EditableLabelDiagramDescription"));
        resource.getContents().add(directEditLabelView);

        return directEditLabelView;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name + '-suffix'")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var labelEditTool = new LabelEditToolBuilder()
                .name("Edit Label")
                .initialDirectEditLabelExpression("aql:self.name")
                .body(
                        new SetValueBuilder()
                                .featureName("name")
                                .valueExpression("aql:newLabel")
                                .build()
                )
                .build();


        var nodePalette = new NodePaletteBuilder()
                .labelEditTool(labelEditTool)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .palette(nodePalette)
                .build();

        var dependencyEdgeStyle = new DiagramBuilders().newEdgeStyle()
                .sourceArrowStyle(ArrowStyle.NONE)
                .targetArrowStyle(ArrowStyle.INPUT_FILL_CLOSED_ARROW)
                .lineStyle(LineStyle.SOLID)
                .edgeWidth(1)
                .borderSize(0)
                .build();

        var edgeCenterLabelEditTool = new DiagramBuilders().newLabelEditTool()
                .name("Edit Begin Label")
                .initialDirectEditLabelExpression("aql:semanticEdgeSource.name")
                .body(
                        new ViewBuilders().newChangeContext()
                                .expression("aql:semanticEdgeSource")
                                .children(
                                        new SetValueBuilder()
                                                .featureName("name")
                                                .valueExpression("aql:newLabel")
                                                .build()
                                )
                                .build()
                )
                .build();
        var edgePalette = new DiagramBuilders().newEdgePalette()
                .centerLabelEditTool(edgeCenterLabelEditTool)
                .build();

        var edgeDescription = new DiagramBuilders().newEdgeDescription()
                .name("Dependency")
                .sourceDescriptions(nodeDescription)
                .targetDescriptions(nodeDescription)
                .centerLabelExpression("")
                .sourceExpression("aql:self")
                .targetExpression("aql:self.dependencies")
                .isDomainBasedEdge(false)
                .beginLabelExpression("aql:'source ' + semanticEdgeSource.name")
                .centerLabelExpression("aql:semanticEdgeSource.name + ' - ' + semanticEdgeTarget.name")
                .endLabelExpression("aql:'target ' + semanticEdgeTarget.name")
                .palette(edgePalette)
                .style(dependencyEdgeStyle)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'EditableLabelDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions(edgeDescription)
                .layoutOption(DiagramLayoutOption.NONE)
                .build();

        return this.diagramDescription;

    }
}
