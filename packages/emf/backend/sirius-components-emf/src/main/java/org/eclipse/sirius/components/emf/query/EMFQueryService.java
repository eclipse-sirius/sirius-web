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
package org.eclipse.sirius.components.emf.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.components.collaborative.api.IQueryService;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedBooleanInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedBooleanSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedIntSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectsInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedObjectsSuccessPayload;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedStringInput;
import org.eclipse.sirius.components.collaborative.dto.QueryBasedStringSuccessPayload;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.emf.query.api.IQueryJavaServiceProvider;
import org.eclipse.sirius.components.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.springframework.stereotype.Service;

/**
 * A specific implementation of {@link IQueryService} for EMF.
 *
 * @author fbarbin
 */
@Service
public class EMFQueryService implements IQueryService {

    private static final String EVALUATION_ERROR_MESSAGE = "An error occured while evaluating the expression. Status : "; //$NON-NLS-1$

    private final IEditingContextEPackageService editingContextEPackageService;

    private final List<IQueryJavaServiceProvider> queryJavaServiceProviders;

    public EMFQueryService(IEditingContextEPackageService editingContextEPackageService, List<IQueryJavaServiceProvider> queryJavaServiceProviders) {
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
        this.queryJavaServiceProviders = Objects.requireNonNull(queryJavaServiceProviders);
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedStringInput input) {
        Result result = this.executeQuery(editingContext, input.getQuery(), input.getVariables());
        Optional<String> optionalString = result.asString();
        if (optionalString.isPresent()) {
            return new QueryBasedStringSuccessPayload(input.getId(), optionalString.get());
        } else {
            return new ErrorPayload(input.getId(), EVALUATION_ERROR_MESSAGE + result.getStatus());
        }
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedBooleanInput input) {
        Result result = this.executeQuery(editingContext, input.getQuery(), input.getVariables());
        Optional<Boolean> optionalBoolean = result.asBoolean();
        if (optionalBoolean.isPresent()) {
            return new QueryBasedBooleanSuccessPayload(input.getId(), optionalBoolean.get().booleanValue());
        } else {
            return new ErrorPayload(input.getId(), EVALUATION_ERROR_MESSAGE + result.getStatus());
        }
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedIntInput input) {
        Result result = this.executeQuery(editingContext, input.getQuery(), input.getVariables());
        OptionalInt optionalInt = result.asInt();
        if (optionalInt.isPresent()) {
            return new QueryBasedIntSuccessPayload(input.getId(), optionalInt.getAsInt());
        } else {
            return new ErrorPayload(input.getId(), EVALUATION_ERROR_MESSAGE + result.getStatus());
        }
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedObjectInput input) {
        Result result = this.executeQuery(editingContext, input.getQuery(), input.getVariables());
        Optional<Object> optionalObject = result.asObject();
        if (optionalObject.isPresent()) {
            return new QueryBasedObjectSuccessPayload(input.getId(), optionalObject.get());
        } else {
            return new ErrorPayload(input.getId(), EVALUATION_ERROR_MESSAGE + result.getStatus());
        }
    }

    @Override
    public IPayload execute(IEditingContext editingContext, QueryBasedObjectsInput input) {
        Result result = this.executeQuery(editingContext, input.getQuery(), input.getVariables());
        Optional<List<Object>> optionalObjects = result.asObjects();
        if (optionalObjects.isPresent()) {
            return new QueryBasedObjectsSuccessPayload(input.getId(), optionalObjects.get());
        } else {
            return new ErrorPayload(input.getId(), EVALUATION_ERROR_MESSAGE + result.getStatus());
        }
    }

    private Result executeQuery(IEditingContext editingContext, String query, Map<String, Object> providedVariables) {
        List<Class<?>> classes = this.queryJavaServiceProviders.stream().flatMap(provider -> provider.getClasses(editingContext).stream()).collect(Collectors.toList());
        classes.add(EditingContextServices.class);
        List<EPackage> ePackages = this.editingContextEPackageService.getEPackages(editingContext.getId());

        Map<String, Object> variables = new HashMap<>(providedVariables);
        variables.put(IEditingContext.EDITING_CONTEXT, editingContext);

        var interpreter = new AQLInterpreter(classes, ePackages);
        return interpreter.evaluateExpression(variables, query);
    }

}
