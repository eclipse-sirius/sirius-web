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

import java.util.ArrayList;
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
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

import graphql.language.Argument;
import graphql.language.Field;
import graphql.language.Selection;
import graphql.language.SelectionSet;
import graphql.language.StringValue;
import graphql.language.Value;
import graphql.schema.DataFetchingEnvironment;

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
        DataFetchingEnvironment environment = input.getEnvironment();
        Object fieldValue = this.getFieldValue(environment.getField(), editingContext, new HashMap<>());
        if (fieldValue instanceof List<?>) {
            List<Object> objects = new ArrayList<>((List<?>) fieldValue);
            return new QueryBasedObjectsSuccessPayload(input.getId(), objects);
        }
        return new QueryBasedObjectsSuccessPayload(input.getId(), List.of());
    }

    private Object getFieldValue(Field field, IEditingContext editingContext, Map<String, Object> variables) {
        Map<String, Value> arguments = field.getArguments().stream().collect(Collectors.toMap(Argument::getName, Argument::getValue));

        if (field.getName().equals("queryBasedObjects")) { //$NON-NLS-1$
            String query = this.getStringArgument("query", arguments).orElse(""); //$NON-NLS-1$//$NON-NLS-2$
            String variableName = this.getStringArgument("variableName", arguments).orElse(VariableManager.SELF); //$NON-NLS-1$

            List<Object> results = new ArrayList<>();

            Result result = this.executeQuery(editingContext, query, variables);
            List<Object> objects = result.asObjects().orElse(List.of());
            for (Object object : objects) {
                Map<String, Object> objectResult = new HashMap<>();

                SelectionSet selectionSet = field.getSelectionSet();
                List<Selection> selections = selectionSet.getSelections();
                for (Selection selection : selections) {
                    if (selection instanceof Field) {
                        Field childField = (Field) selection;

                        Map<String, Object> childVariables = new HashMap<>(variables);
                        childVariables.put(variableName, object);
                        Object fieldValue = this.getFieldValue(childField, editingContext, childVariables);

                        objectResult.put(childField.getResultKey(), fieldValue);
                    }
                }
                results.add(objectResult);
            }

            return results;
        } else if (field.getName().equals("queryBasedString")) { //$NON-NLS-1$
            String query = this.getStringArgument("query", arguments).orElse(""); //$NON-NLS-1$//$NON-NLS-2$

            Result result = this.executeQuery(editingContext, query, variables);
            String value = result.asString().orElse(null);
            return value;
        } else if (field.getName().equals("queryBasedBoolean")) { //$NON-NLS-1$
            String query = this.getStringArgument("query", arguments).orElse(""); //$NON-NLS-1$//$NON-NLS-2$

            Result result = this.executeQuery(editingContext, query, variables);
            Boolean value = result.asBoolean().orElse(Boolean.FALSE);
            return value;
        }
        return null;
    }

    private Optional<String> getStringArgument(String name, Map<String, Value> arguments) {
        // @formatter:off
        return Optional.ofNullable(arguments.get(name))
                .filter(StringValue.class::isInstance)
                .map(StringValue.class::cast)
                .map(StringValue::getValue);
        // @formatter:on
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
