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
import org.eclipse.sirius.components.view.builder.generated.ChangeContextBuilder;
import org.eclipse.sirius.components.view.builder.generated.CreateInstanceBuilder;
import org.eclipse.sirius.components.view.builder.generated.CreateViewBuilder;
import org.eclipse.sirius.components.view.builder.generated.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.DropToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.SetValueBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeContainmentKind;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test unsynchronized diagrams.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class UnsynchronizedDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool createNodeTool;

    private DropTool dropOnDiagramTool;

    public UnsynchronizedDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    public String getCreateNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.createNodeTool).toString().getBytes()).toString();
    }

    public String getDropToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.dropOnDiagramTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("UnsynchronizedDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("UnsynchronizedDiagramDescription"));
        resource.getContents().add(unsynchronizedView);

        return unsynchronizedView;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .synchronizationPolicy(SynchronizationPolicy.UNSYNCHRONIZED)
                .style(nodeStyle)
                .build();

        var createNewComponent = new CreateInstanceBuilder()
                .typeName("papaya:Component")
                .referenceName("components")
                .variableName("newInstance")
                .children(
                        new SetValueBuilder()
                                .featureName("components")
                                .valueExpression("aql:newInstance")
                                .children(
                                        new CreateViewBuilder()
                                                .elementDescription(nodeDescription)
                                                .semanticElementExpression("aql:newInstance")
                                                .parentViewExpression("aql:selectedNode")
                                                .containmentKind(NodeContainmentKind.CHILD_NODE)
                                                .build()
                                )
                                .build()
                )
                .build();

        this.createNodeTool = new NodeToolBuilder()
                .name("Create Component")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .children(createNewComponent)
                                .build()
                )
                .build();

        var createNewComponentView = new CreateViewBuilder()
                .elementDescription(nodeDescription)
                .semanticElementExpression("aql:self")
                .parentViewExpression("aql:selectedNode")
                .containmentKind(NodeContainmentKind.CHILD_NODE)
                .build();

        this.dropOnDiagramTool = new DropToolBuilder()
                .name("Drop on diagram")
                .body(
                        new ChangeContextBuilder()
                                .expression("aql:self")
                                .children(createNewComponentView)
                                .build()
                )
                .build();

        var diagramPalette = new DiagramPaletteBuilder()
                .nodeTools(this.createNodeTool)
                .dropTool(dropOnDiagramTool)
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'UnsynchronizedDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .palette(diagramPalette)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }
}
