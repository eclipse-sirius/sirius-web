/*******************************************************************************
 * Copyright (c) 2026 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     Obeo - initial API and implementation
 ******************************************************************************/

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
import org.eclipse.sirius.components.view.builder.generated.view.ViewBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.InsideLabelPosition;
import org.eclipse.sirius.components.view.emf.diagram.IDiagramIdProvider;
import org.eclipse.sirius.emfjson.resource.JsonResource;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.eclipse.sirius.web.services.OnStudioTests;
import org.springframework.context.annotation.Conditional;
import org.springframework.stereotype.Service;
/**
 * Used to provide a view based diagram description to test layout configurations.
 *
 * @author ocailleau
 */
@Service
@Conditional(OnStudioTests.class)
@SuppressWarnings("checkstyle:MultipleStringLiterals")
public class LayoutConfigurationDiagramDescriptionProvider implements IEditingContextProcessor {

    private final IDiagramIdProvider diagramIdProvider;

    private final View view;

    private DiagramDescription diagramDescription;

    public LayoutConfigurationDiagramDescriptionProvider(IDiagramIdProvider diagramIdProvider) {
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
        View layoutConfigurationViewDesc = viewBuilder.build();
        layoutConfigurationViewDesc.getDescriptions().add(this.createDiagramDescription());
        layoutConfigurationViewDesc.eAllContents().forEachRemaining(eObject -> eObject.eAdapters().add(new IDAdapter(UUID.nameUUIDFromBytes(EcoreUtil.getURI(eObject).toString().getBytes()))));

        String resourcePath = UUID.nameUUIDFromBytes("LayoutConfigurationDiagramDescription".getBytes()).toString();
        JsonResource resource = new JSONResourceFactory().createResourceFromPath(resourcePath);
        resource.eAdapters().add(new ResourceMetadataAdapter("LayoutConfigurationDiagramDescription"));
        resource.getContents().add(layoutConfigurationViewDesc);

        return layoutConfigurationViewDesc;
    }

    private DiagramDescription createDiagramDescription() {
        var nodeStyle = new DiagramBuilders().newRectangularNodeStyleDescription()
                .build();

        var insideLabel = new DiagramBuilders().newInsideLabelDescription()
                .labelExpression("aql:self.name")
                .style(DiagramFactory.eINSTANCE.createInsideLabelStyle())
                .position(InsideLabelPosition.TOP_CENTER)
                .build();

        var nodeDescription = new DiagramBuilders().newNodeDescription()
                .name("Domain Node")
                .domainType("domain:Entity")
                .semanticCandidatesExpression("aql:self.types")
                .insideLabel(insideLabel)
                .style(nodeStyle)
                .build();

        this.diagramDescription = new DiagramBuilders().newDiagramDescription()
                .name("LayoutDiagram")
                .titleExpression("aql:'LayoutConfigurationDiagram'")
                .domainType("domain:domain")
                .nodeDescriptions(nodeDescription)
                .autoLayout(false)
                .build();

        return this.diagramDescription;
    }
}