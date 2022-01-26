/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.emf.compatibility.modeloperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.viewpoint.description.tool.ChangeContext;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;

/**
 * Handles the change context model operation.
 *
 * @author sbegaudeau
 */
public class ChangeContextOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final ChangeContext changeContext;

    public ChangeContextOperationHandler(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IIdentifierProvider identifierProvider,
            AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, ChangeContext changeContext) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.changeContext = Objects.requireNonNull(changeContext);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String browseExpression = this.changeContext.getBrowseExpression();
        Map<String, Object> childVariables = new HashMap<>(variables);
        if (browseExpression != null && !browseExpression.isBlank()) {
            Optional<Object> optionalObject = this.interpreter.evaluateExpression(variables, browseExpression).asObject();
            optionalObject.ifPresent(object -> childVariables.put(VariableManager.SELF, object));
        }

        List<ModelOperation> subModelOperations = this.changeContext.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
    }

}
