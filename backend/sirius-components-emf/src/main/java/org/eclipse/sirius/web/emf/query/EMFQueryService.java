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
package org.eclipse.sirius.web.emf.query;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.sirius.web.core.api.ErrorPayload;
import org.eclipse.sirius.web.core.api.IEditingContext;
import org.eclipse.sirius.web.core.api.IPayload;
import org.eclipse.sirius.web.emf.services.IEditingContextEPackageService;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.interpreter.Result;
import org.eclipse.sirius.web.spring.collaborative.api.IQueryService;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedBooleanInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedBooleanSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedIntSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectsInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedObjectsSuccessPayload;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedStringInput;
import org.eclipse.sirius.web.spring.collaborative.dto.QueryBasedStringSuccessPayload;
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

    public EMFQueryService(IEditingContextEPackageService editingContextEPackageService) {
        this.editingContextEPackageService = Objects.requireNonNull(editingContextEPackageService);
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
        List<Class<?>> classes = List.of(EditingContextServices.class);
        List<EPackage> ePackages = this.editingContextEPackageService.getEPackages(editingContext.getId());

        Map<String, Object> variables = new HashMap<>(providedVariables);
        variables.put(IEditingContext.EDITING_CONTEXT, editingContext);

        var interpreter = new AQLInterpreter(classes, ePackages);
        return interpreter.evaluateExpression(variables, query);
    }

}
