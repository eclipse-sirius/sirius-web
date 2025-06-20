/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.DiagramPaletteBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.FreeFormLayoutStrategyDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.InsideLabelDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.NodeDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.diagram.RectangularNodeStyleDescriptionBuilder;
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.diagram.SynchronizationPolicy;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;

/**
 * Used to provide a view based diagram description to test ancestors variable in diagrams.
 *
 * @author sbegaudeau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class AncestorsVariableDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public AncestorsVariableDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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
        View unsynchronizedView = viewBuilder.build();
        unsynchronizedView.getDescriptions().add(this.createDiagramDescription());

        unsynchronizedView.eAllContents().forEachRemaining(eObject -> {
            eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes())));
        });

        String resourcePath = UUID.nameUUIDFromBytes("AncestorsVariableDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("AncestorsVariableDiagramDescription"));
        resource.getContents().add(unsynchronizedView);

        return unsynchronizedView;
    }

    private DiagramDescription createDiagramDescription() {
        var leafNodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .childrenLayoutStrategy(new FreeFormLayoutStrategyDescriptionBuilder().build())
                .build();

        var childNodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .childrenLayoutStrategy(new FreeFormLayoutStrategyDescriptionBuilder().build())
                .build();

        var rootNodeStyle = new RectangularNodeStyleDescriptionBuilder()
                .childrenLayoutStrategy(new FreeFormLayoutStrategyDescriptionBuilder().build())
                .build();

        var leafInsideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:ancestors.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var childInsideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:ancestors.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var rootInsideLabel = new InsideLabelDescriptionBuilder()
                .labelExpression("aql:ancestors.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var leafNodeDescription = new NodeDescriptionBuilder()
                .name("Class")
                .domainType("papaya:Class")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(leafInsideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(leafNodeStyle)
                .build();

        var childNodeDescription = new NodeDescriptionBuilder()
                .name("Package")
                .domainType("papaya:Package")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(childInsideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(childNodeStyle)
                .childrenDescriptions(leafNodeDescription)
                .build();

        var rootNodeDescription = new NodeDescriptionBuilder()
                .name("Component")
                .domainType("papaya:Component")
                .semanticCandidatesExpression("aql:self.eContents()")
                .insideLabel(rootInsideLabel)
                .synchronizationPolicy(SynchronizationPolicy.SYNCHRONIZED)
                .style(rootNodeStyle)
                .childrenDescriptions(childNodeDescription)
                .build();

        var diagramPalette = new DiagramPaletteBuilder()
                .build();

        this.diagramDescription = new DiagramDescriptionBuilder()
                .name("Diagram")
                .titleExpression("aql:'AncestorsVariableDiagram'")
                .domainType("papaya:Project")
                .nodeDescriptions(rootNodeDescription)
                .edgeDescriptions()
                .palette(diagramPalette)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }
}
