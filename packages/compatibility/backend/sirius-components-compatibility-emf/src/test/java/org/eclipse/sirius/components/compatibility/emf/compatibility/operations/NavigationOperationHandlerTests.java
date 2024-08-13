/*******************************************************************************
 * Copyright (c) 2022, 2024 Obeo.
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
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
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

        IStatus status = new ChildModelOperationHandler(List.of()).handle(new IObjectService.NoOp(), this.diagramDescriptionIdentifierProvider,
                new AQLInterpreter(List.of(), List.of()), Map.of(IEditingContext.EDITING_CONTEXT, new IEditingContext.NoOp()), List.of(navigation));

        assertThat(status).isInstanceOf(Success.class);
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

        IStatus status = new ChildModelOperationHandler(List.of()).handle(new IObjectService.NoOp(), this.diagramDescriptionIdentifierProvider,
                new AQLInterpreter(List.of(), List.of()), Map.of(IEditingContext.EDITING_CONTEXT, new IEditingContext.NoOp()), List.of(firstNavigation, secondNavigation));

        assertThat(status).isInstanceOf(Success.class);
        // @formatter:on
    }
}
