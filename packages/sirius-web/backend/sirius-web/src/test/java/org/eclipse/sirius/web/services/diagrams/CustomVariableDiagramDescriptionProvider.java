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
import org.eclipse.sirius.components.view.builder.generated.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.DiagramVariableBuilder;
import org.eclipse.sirius.components.view.builder.generated.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodePaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.NodeToolBuilder;
import org.eclipse.sirius.components.view.builder.generated.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test custom variables in diagrams.
 *
 * @author arichard
 */
@Service
@Conditional(OnStudioTests.class)
public class CustomVariableDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    private NodeTool changeCustomVariableNodeTool;

    public CustomVariableDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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

    public String getCustomVariableNodeToolId() {
        return UUID.nameUUIDFromBytes(EcoreUtil.getURI(this.changeCustomVariableNodeTool).toString().getBytes()).toString();
    }

    private View createView() {
        ViewBuilder viewBuilder = new ViewBuilder();
        View visibilityView = viewBuilder.build();
        visibilityView.getDescriptions().add(this.createDiagramDescription());
        visibilityView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("CustomVariableDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("CustomVariableDiagramDescription"));
        resource.getContents().add(visibilityView);

        return visibilityView;
    }

    private DiagramDescription createDiagramDescription() {
        this.changeCustomVariableNodeTool = new NodeToolBuilder()
                .name("Change value of Custom Variable to false")
                .body(
                        new ChangeContextBuilder()
                            .expression("aql:self.setMyCustomVariableToFalse(variableManager)")
                            .build()
                )
                .build();

        var nodePalette = new NodePaletteBuilder()
                .nodeTools(this.changeCustomVariableNodeTool)
                .build();

        var nodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .build();

        var insideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:self.name + ' - ' + myCustomVariable")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .palette(nodePalette)
                .build();

        var diagramVariable = new DiagramVariableBuilder()
                .defaultValueExpression("aql:true")
                .name("myCustomVariable")
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'CustomVariableDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(nodeDescription)
                .edgeDescriptions()
                .autoLayout(false)
                .diagramVariables(diagramVariable)
                .build();

        return this.diagramDescription;
    }
}
