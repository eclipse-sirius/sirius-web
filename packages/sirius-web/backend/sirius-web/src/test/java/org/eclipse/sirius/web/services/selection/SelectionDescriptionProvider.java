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
package org.eclipse.sirius.web.services.selection;

import java.util.List;
import java.util.Objects;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IEditingContextProcessor;
import org.eclipse.sirius.components.emf.services.JSONResourceFactory;
import org.eclipse.sirius.components.emf.services.api.IEMFEditingContext;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.selection.description.SelectionDescription;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewFactory;
import org.eclipse.sirius.components.view.builder.generated.SelectionDialogDescriptionBuilder;
import org.eclipse.sirius.components.view.diagram.DiagramDescription;
import org.eclipse.sirius.components.view.diagram.DiagramFactory;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.NodeTool;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.emf.IViewConverter;
import org.eclipse.sirius.web.application.editingcontext.EditingContext;
import org.springframework.stereotype.Service;

/**
 * Used to provide a description for the selection representation.
 *
 * @author sbegaudeau
 */
@Service
public class SelectionDescriptionProvider implements IEditingContextProcessor {

    public static final String LABEL = "Selection";

    public static final String REPRESENTATION_DESCRIPTION_ID = "siriusComponents://representationDescription?kind=selectionDescription&sourceElementId=flat";

    public static final String REPRESENTATION_DESCRIPTION_AS_TREE_ID = "siriusComponents://representationDescription?kind=selectionDescription&sourceElementId=tree";

    private final IViewConverter viewConverter;

    public SelectionDescriptionProvider(IViewConverter viewConverter) {
        this.viewConverter = Objects.requireNonNull(viewConverter);
    }

    @Override
    public void preProcess(IEditingContext editingContext) {
        if (editingContext instanceof EditingContext siriusWebEditingContext) {
            siriusWebEditingContext.getRepresentationDescriptions().put(REPRESENTATION_DESCRIPTION_ID, this.getSelectionDescription(false));
            siriusWebEditingContext.getRepresentationDescriptions().put(REPRESENTATION_DESCRIPTION_AS_TREE_ID, this.getSelectionDescription(true));
        }
    }

    private SelectionDescription getSelectionDescription(boolean isTree) {
        SelectionDialogDescription dialogDescription = new SelectionDialogDescriptionBuilder()
                .displayedAsTree(isTree)
                .expandedAtOpening(isTree)
                .selectionCandidatesExpression("aql:self.eContents()")
                .selectionMessage("Please select an element")
                .build();
        NodeTool nodeTool = DiagramFactory.eINSTANCE.createNodeTool();
        nodeTool.setDialogDescription(dialogDescription);
        DiagramPalette diagramPalette = DiagramFactory.eINSTANCE.createDiagramPalette();
        diagramPalette.getNodeTools().add(nodeTool);
        DiagramDescription diagramDescription = DiagramFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setPalette(diagramPalette);
        View view = ViewFactory.eINSTANCE.createView();
        view.getDescriptions().add(diagramDescription);

        Resource res = new JSONResourceFactory().createResource(URI.createURI(IEMFEditingContext.RESOURCE_SCHEME + ":///fixture"));
        res.getContents().add(view);
        new ResourceSetImpl().getResources().add(res);

        List<IRepresentationDescription> repDesscriptions = this.viewConverter.convert(List.of(view), List.of(EcorePackage.eINSTANCE));

        return repDesscriptions.stream()
                .filter(SelectionDescription.class::isInstance)
                .map(SelectionDescription.class::cast)
                .findFirst()
                .orElse(null);

    }
}
