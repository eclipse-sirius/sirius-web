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
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.viewpoint.description.tool.For;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;

/**
 * Handle the {@link For} model operation.
 *
 * @author lfasani
 */
public class ForOperationHandler implements IModelOperationHandler {

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final For forOperation;

    public ForOperationHandler(IObjectService objectService, IRepresentationMetadataSearchService representationMetadataSearchService, IIdentifierProvider identifierProvider,
            AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, For forOperation) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationMetadataSearchService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.forOperation = Objects.requireNonNull(forOperation);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        String expression = this.forOperation.getExpression();
        String iteratedVariableName = this.forOperation.getIteratorName();

        if (expression != null && !expression.isBlank() && iteratedVariableName != null && !iteratedVariableName.isBlank()) {
            Optional<List<Object>> optionalObjectsToIterate = this.interpreter.evaluateExpression(variables, expression).asObjects();

            if (optionalObjectsToIterate.isPresent()) {
                List<Object> objectsToIterate = optionalObjectsToIterate.get();

                for (Object object : objectsToIterate) {
                    Map<String, Object> childVariables = new HashMap<>(variables);
                    childVariables.put(iteratedVariableName, object);

                    List<ModelOperation> subModelOperations = this.forOperation.getSubModelOperations();
                    this.childModelOperationHandler.handle(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, childVariables, subModelOperations);
                }
            }
        }

        return new Success();
    }

}
