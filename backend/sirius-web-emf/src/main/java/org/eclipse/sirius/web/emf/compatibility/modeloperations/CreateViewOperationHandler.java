/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.emf.compatibility.modeloperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.diagram.description.DiagramElementMapping;
import org.eclipse.sirius.diagram.description.tool.CreateView;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.Diagram;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.ViewCreationRequest;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;

/**
 * Handles the create view model operation.
 *
 * @author sbegaudeau
 */
public class CreateViewOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final CreateView createView;

    public CreateViewOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler,
            CreateView createView) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.createView = Objects.requireNonNull(createView);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String containerViewExpression = this.createView.getContainerViewExpression();

        var optionalParentElementId = this.interpreter.evaluateExpression(variables, containerViewExpression).asObject().flatMap(parentElement -> {
            Optional<UUID> optionalElementId = Optional.empty();
            if (parentElement instanceof Diagram) {
                Diagram diagram = (Diagram) parentElement;
                optionalElementId = Optional.of(diagram.getId());
            } else if (parentElement instanceof Node) {
                Node node = (Node) parentElement;
                optionalElementId = Optional.of(node.getId());
            }
            return optionalElementId;
        });

        DiagramElementMapping diagramElementMapping = this.createView.getMapping();
        String descriptionId = this.identifierProvider.getIdentifier(diagramElementMapping);

        String targetObjectId = this.objectService.getId(variables.get(VariableManager.SELF));

        // @formatter:off
        ViewCreationRequest viewCreationRequest = ViewCreationRequest.newViewCreationRequest()
                .parentElementId(optionalParentElementId.get())
                .descriptionId(UUID.fromString(descriptionId))
                .targetObjectId(targetObjectId)
                .build();

        Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                .filter(IDiagramContext.class::isInstance)
                .map(IDiagramContext.class::cast)
                .ifPresent(diagramContext -> diagramContext.getViewCreationRequests().add(viewCreationRequest));
        // @formatter:on

        Map<String, Object> childVariables = new HashMap<>(variables);
        List<ModelOperation> subModelOperations = this.createView.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.interpreter, childVariables, subModelOperations);
    }

}
