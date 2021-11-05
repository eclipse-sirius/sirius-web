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

import org.eclipse.sirius.viewpoint.description.tool.DeleteView;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.eclipse.sirius.web.compat.api.IIdentifierProvider;
import org.eclipse.sirius.web.compat.api.IModelOperationHandler;
import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.Node;
import org.eclipse.sirius.web.diagrams.ViewDeletionRequest;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.Failure;
import org.eclipse.sirius.web.representations.IStatus;
import org.eclipse.sirius.web.representations.VariableManager;
import org.eclipse.sirius.web.spring.collaborative.diagrams.api.IDiagramContext;

/**
 * Handles the delete view model operation.
 *
 * @author arichard
 */
public class DeleteViewOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final DeleteView deleteView;

    public DeleteViewOperationHandler(IObjectService objectService, IIdentifierProvider identifierProvider, AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler,
            DeleteView deleteView) {
        this.objectService = Objects.requireNonNull(objectService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.deleteView = Objects.requireNonNull(deleteView);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        Object self = variables.get(VariableManager.SELF);
        if (self instanceof Node) {
            UUID elementId = ((Node) self).getId();
            // @formatter:off
            ViewDeletionRequest viewDeletionRequest = ViewDeletionRequest.newViewDeletionRequest()
                    .elementId(elementId)
                    .build();

            Optional.ofNullable(variables.get(IDiagramContext.DIAGRAM_CONTEXT))
                    .filter(IDiagramContext.class::isInstance)
                    .map(IDiagramContext.class::cast)
                    .ifPresent(diagramContext -> diagramContext.getViewDeletionRequests().add(viewDeletionRequest));
            // @formatter:on

            Map<String, Object> childVariables = new HashMap<>(variables);
            List<ModelOperation> subModelOperations = this.deleteView.getSubModelOperations();
            return this.childModelOperationHandler.handle(this.objectService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
        }
        return new Failure(""); //$NON-NLS-1$
    }

}
