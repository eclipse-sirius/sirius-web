/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo and others.
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
package org.eclipse.sirius.components.compatibility.emf.modeloperations;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.compatibility.api.IIdentifierProvider;
import org.eclipse.sirius.components.compatibility.api.IModelOperationHandler;
import org.eclipse.sirius.components.compatibility.emf.api.IExternalJavaActionProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IRepresentationMetadataSearchService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.tools.api.ui.IExternalJavaAction;
import org.eclipse.sirius.viewpoint.description.tool.ExternalJavaAction;
import org.eclipse.sirius.viewpoint.description.tool.ExternalJavaActionParameter;
import org.eclipse.sirius.viewpoint.description.tool.ModelOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Handle the {@link ExternalJavaAction} model operation.
 *
 * @author Charles Wu
 */
public class ExternalJavaActionOperationHandler implements IModelOperationHandler {

    private final Logger logger = LoggerFactory.getLogger(ExternalJavaActionOperationHandler.class);

    private final IObjectService objectService;

    private final IRepresentationMetadataSearchService representationMetadataSearchService;

    private final IIdentifierProvider identifierProvider;

    private final AQLInterpreter interpreter;

    private final ChildModelOperationHandler childModelOperationHandler;

    private final List<IExternalJavaActionProvider> externalJavaActionProviders;

    private final ExternalJavaAction externalJavaAction;

    public ExternalJavaActionOperationHandler(IObjectService objectService, IRepresentationMetadataSearchService representationSearchService, IIdentifierProvider identifierProvider,
            AQLInterpreter interpreter, ChildModelOperationHandler childModelOperationHandler, List<IExternalJavaActionProvider> externalJavaActionProviders, ExternalJavaAction externalJavaAction) {
        this.objectService = Objects.requireNonNull(objectService);
        this.representationMetadataSearchService = Objects.requireNonNull(representationSearchService);
        this.identifierProvider = Objects.requireNonNull(identifierProvider);
        this.interpreter = Objects.requireNonNull(interpreter);
        this.childModelOperationHandler = Objects.requireNonNull(childModelOperationHandler);
        this.externalJavaActionProviders = Objects.requireNonNull(externalJavaActionProviders);
        this.externalJavaAction = Objects.requireNonNull(externalJavaAction);
    }

    @Override
    public IStatus handle(Map<String, Object> variables) {
        // @formatter:off
        var optionalExternalJavaAction = this.externalJavaActionProviders.stream()
                .map(provider -> provider.findById(this.externalJavaAction.getId()))
                .flatMap(Optional::stream)
                .findFirst();
        // @formatter:on

        if (optionalExternalJavaAction.isEmpty()) {
            this.logger.warn("Unable to find external java action from id:{}", this.externalJavaAction.getId());
            return new Failure("");
        } else {
            IExternalJavaAction javaAction = optionalExternalJavaAction.get();

            Map<String, Object> parameters = new HashMap<>();
            for (ExternalJavaActionParameter parameter : this.externalJavaAction.getParameters()) {
                Optional<Object> value = this.interpreter.evaluateExpression(variables, parameter.getValue()).asObject();
                value.ifPresent(it -> parameters.put(parameter.getName(), it));
            }

            Object object = variables.get(VariableManager.SELF);

            if (object instanceof EObject && javaAction.canExecute(List.of((EObject) object))) {
                javaAction.execute(List.of((EObject) object), parameters);
            }
        }

        List<ModelOperation> subModelOperations = this.externalJavaAction.getSubModelOperations();
        return this.childModelOperationHandler.handle(this.objectService, this.representationMetadataSearchService, this.identifierProvider, this.interpreter, variables, subModelOperations);
    }

}
