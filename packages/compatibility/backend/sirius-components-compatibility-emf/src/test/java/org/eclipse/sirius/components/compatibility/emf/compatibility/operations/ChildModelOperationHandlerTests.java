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
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.emf.compatibility.diagrams.TestDiagramBuilder;
import org.eclipse.sirius.components.compatibility.emf.modeloperations.ChildModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.diagram.description.DescriptionFactory;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.diagram.description.tool.ToolFactory;
import org.junit.jupiter.api.Test;

/**
 * Tests of the child model operation handlers.
 *
 * @author sbegaudeau
 */
public class ChildModelOperationHandlerTests {

    @Test
    public void testChildModelOperationWithoutSelection() {
        CreateView createView = ToolFactory.eINSTANCE.createCreateView();
        createView.setContainerViewExpression("");
        createView.setVariableName("");
        createView.setMapping(DescriptionFactory.eINSTANCE.createNodeMapping());

        IObjectService objectService = new IObjectService.NoOp() {
            @Override
            public String getId(Object object) {
                return "";
            }
        };

        IIdentifierProvider identifierProvider = new IIdentifierProvider.NoOp() {
            @Override
            public String getIdentifier(Object element) {
                return UUID.nameUUIDFromBytes("node".getBytes()).toString();
            }
        };

        AQLInterpreter interpreter = new AQLInterpreter(List.of(), List.of()) {
            @Override
            public Result evaluateExpression(Map<String, Object> variables, String expressionBody) {
                Object value = new TestDiagramBuilder().getDiagram("diagramId");
                return new Result(Optional.of(value), Status.OK);
            }
        };

        IStatus status = new ChildModelOperationHandler(List.of()).handle(objectService, identifierProvider, interpreter, Map.of(),
                List.of(createView));

        assertThat(status).isInstanceOf(Success.class);

        Success success = (Success) status;
        Object selectionParameter = success.getParameters().get(Success.NEW_SELECTION);
        assertThat(selectionParameter).isNull();
    }
}
