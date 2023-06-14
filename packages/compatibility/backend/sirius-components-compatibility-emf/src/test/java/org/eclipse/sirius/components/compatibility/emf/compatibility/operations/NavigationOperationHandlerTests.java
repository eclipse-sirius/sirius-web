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
package org.eclipse.sirius.components.compatibility.emf.compatibility.operations;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.components.core.RepresentationMetadata;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.diagrams.Diagram;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.WorkbenchSelection;
import org.eclipse.sirius.components.representations.WorkbenchSelectionEntry;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.DiagramDescription;
import org.eclipse.sirius.diagram.description.tool.Navigation;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the navigation operation handler.
 *
 * @author sbegaudeau
 */
public class NavigationOperationHandlerTests {

    private static final String FIRST_DIAGRAM_ID = "First id";

    private static final String FIRST_DIAGRAM_LABEL = "First label";

    private static final String SECOND_DIAGRAM_ID = "Second id";

    private static final String SECOND_DIAGRAM_LABEL = "Second label";

    private static final String THIRD_DIAGRAM_ID = "Third id";

    private static final String THIRD_DIAGRAM_LABEL = "Third label";

    private static final String FIRST_DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();

    private static final String SECOND_DIAGRAM_DESCRIPTION_ID = UUID.randomUUID().toString();

    private final IRepresentationMetadataSearchService representationMetadataSearchService = new IRepresentationMetadataSearchService.NoOp() {
        @Override
        public List<RepresentationMetadata> findAllByTargetObjectId(IEditingContext editingContext, String targetObjectId) {
            var firstRepresentationMetadata = new RepresentationMetadata(FIRST_DIAGRAM_ID, Diagram.KIND, FIRST_DIAGRAM_LABEL, FIRST_DIAGRAM_DESCRIPTION_ID);
            var secondRepresentationMetadata = new RepresentationMetadata(SECOND_DIAGRAM_ID, Diagram.KIND, SECOND_DIAGRAM_LABEL, FIRST_DIAGRAM_DESCRIPTION_ID);
            var thirdRepresentationMetadata = new RepresentationMetadata(THIRD_DIAGRAM_ID, Diagram.KIND, THIRD_DIAGRAM_LABEL, SECOND_DIAGRAM_DESCRIPTION_ID);
            return List.of(firstRepresentationMetadata, secondRepresentationMetadata, thirdRepresentationMetadata);
        }
    };

    private final IIdentifierProvider diagramDescriptionIdentifierProvider = new IIdentifierProvider.NoOp() {
        @Override
        public String getIdentifier(Object element) {
            DiagramDescription diagramDescription = (DiagramDescription) element;
            return UUID.fromString(diagramDescription.getLabel()).toString();
        }
    };

    @Test
    public void testChildModelOperationWithSelection() {
        DiagramDescription diagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        diagramDescription.setLabel(FIRST_DIAGRAM_DESCRIPTION_ID.toString());

        Navigation navigation = ToolFactory.eINSTANCE.createNavigation();
        navigation.setCreateIfNotExistent(false);
        navigation.setDiagramDescription(diagramDescription);

        IStatus status = new ChildModelOperationHandler(List.of()).handle(new IObjectService.NoOp(), this.representationMetadataSearchService, this.diagramDescriptionIdentifierProvider,
                new AQLInterpreter(List.of(), List.of()), Map.of(IEditingContext.EDITING_CONTEXT, new IEditingContext.NoOp()), List.of(navigation));

        assertThat(status).isInstanceOf(Success.class);

        Success success = (Success) status;
        Object selectionParameter = success.getParameters().get(Success.NEW_SELECTION);
        assertThat(selectionParameter).isInstanceOf(WorkbenchSelection.class);

        WorkbenchSelection selection = (WorkbenchSelection) selectionParameter;
        // @formatter:off
        assertThat(selection).isEqualTo(new WorkbenchSelection(List.of(
                new WorkbenchSelectionEntry(FIRST_DIAGRAM_ID, FIRST_DIAGRAM_LABEL, Diagram.KIND),
                new WorkbenchSelectionEntry(SECOND_DIAGRAM_ID, SECOND_DIAGRAM_LABEL, Diagram.KIND)
        )));
        // @formatter:on
    }

    @Test
    public void testMultipleChildModelOperationWithSelection() {
        DiagramDescription firstDiagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        firstDiagramDescription.setLabel(FIRST_DIAGRAM_DESCRIPTION_ID.toString());
        Navigation firstNavigation = ToolFactory.eINSTANCE.createNavigation();
        firstNavigation.setCreateIfNotExistent(false);
        firstNavigation.setDiagramDescription(firstDiagramDescription);

        DiagramDescription secondDiagramDescription = DescriptionFactory.eINSTANCE.createDiagramDescription();
        secondDiagramDescription.setLabel(SECOND_DIAGRAM_DESCRIPTION_ID.toString());
        Navigation secondNavigation = ToolFactory.eINSTANCE.createNavigation();
        secondNavigation.setCreateIfNotExistent(false);
        secondNavigation.setDiagramDescription(secondDiagramDescription);

        IStatus status = new ChildModelOperationHandler(List.of()).handle(new IObjectService.NoOp(), this.representationMetadataSearchService, this.diagramDescriptionIdentifierProvider,
                new AQLInterpreter(List.of(), List.of()), Map.of(IEditingContext.EDITING_CONTEXT, new IEditingContext.NoOp()), List.of(firstNavigation, secondNavigation));

        assertThat(status).isInstanceOf(Success.class);

        Success success = (Success) status;
        Object selectionParameter = success.getParameters().get(Success.NEW_SELECTION);
        assertThat(selectionParameter).isInstanceOf(WorkbenchSelection.class);

        WorkbenchSelection selection = (WorkbenchSelection) selectionParameter;
        // @formatter:off
        assertThat(selection).isEqualTo(new WorkbenchSelection(List.of(
                new WorkbenchSelectionEntry(FIRST_DIAGRAM_ID, FIRST_DIAGRAM_LABEL, Diagram.KIND),
                new WorkbenchSelectionEntry(SECOND_DIAGRAM_ID, SECOND_DIAGRAM_LABEL, Diagram.KIND),
                new WorkbenchSelectionEntry(THIRD_DIAGRAM_ID, THIRD_DIAGRAM_LABEL, Diagram.KIND)
        )));
        // @formatter:on
    }
}
