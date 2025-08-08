/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

import org.eclipse.acceleo.query.runtime.AcceleoQueryEvaluationException;
import org.eclipse.acceleo.query.runtime.IQueryEnvironment;
import org.eclipse.acceleo.query.runtime.IService;
import org.eclipse.acceleo.query.runtime.Query;
import org.eclipse.acceleo.query.runtime.ServiceUtils;
import org.eclipse.sirius.components.interpreter.api.IInterpreter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A specialized interpreter which can only directly invoke Java service methods.
 *
 * @author gdaniel
 */
public class ServiceInterpreter implements IInterpreter {

    private static final String SERVICE_PREFIX = "service:";

    private static final String RECEIVER_SEPARATOR = ".";

    private static final Pattern SPLIT_PATTERN = Pattern.compile("[(,)]");

    private final Logger logger = LoggerFactory.getLogger(ServiceInterpreter.class);

    private final IQueryEnvironment queryEnvironment;

    public ServiceInterpreter(List<Class<?>> classes, List<Object> instances) {
        this.queryEnvironment = Query.newEnvironmentWithDefaultServices(new SimpleCrossReferenceProvider());
        for (Class<?> aClass : classes) {
            var services = ServiceUtils.getServices(this.queryEnvironment, aClass);
            ServiceUtils.registerServices(this.queryEnvironment, services);
        }
        for (Object instance : instances) {
            var services = ServiceUtils.getServices(this.queryEnvironment, instance);
            ServiceUtils.registerServices(this.queryEnvironment, services);
        }
    }

    @Override
    public Result evaluateExpression(Map<String, Object> variables, String expressionBody) {
        Result result = new Result(Optional.empty(), Status.ERROR);
        if (expressionBody != null && expressionBody.startsWith(SERVICE_PREFIX)) {
            Object evaluation;
            String serviceCall = expressionBody.substring(SERVICE_PREFIX.length()).trim();
            Optional<String> receiverVariableName = this.getReceiverVariableName(serviceCall);
            Optional<Object> optionalReceiver = Optional.empty();
            String serviceName = serviceCall;
            if (receiverVariableName.isPresent()) {
                serviceCall = serviceCall.substring(receiverVariableName.get().length() + 1);
                optionalReceiver = Optional.ofNullable(variables.get(receiverVariableName.get()));
            }
            int indexOfParenthesis = serviceCall.indexOf("(");
            if (indexOfParenthesis != -1) {
                serviceName = serviceCall.substring(0, indexOfParenthesis);
            } else {
                serviceName = serviceCall;
            }

            List<Object> parameters = new ArrayList<>();
            if (optionalReceiver.isPresent()) {
                parameters.add(optionalReceiver.get());
            }
            if (indexOfParenthesis != -1) {
                String[] values = SPLIT_PATTERN.split(serviceCall);
                for (int i = 1; i < values.length; i++) {
                    parameters.add(variables.get(values[i].trim()));
                }
            }
            try {
                evaluation = this.callService(parameters.toArray(), serviceName);
                result = new Result(Optional.ofNullable(evaluation), Status.OK);
            } catch (AcceleoQueryEvaluationException exception) {
                this.logger.warn(exception.getMessage());
            }
        }
        return result;
    }

    private Optional<String> getReceiverVariableName(String expression) {
        Optional<String> result = Optional.empty();
        int indexOfServiceName = expression.indexOf(RECEIVER_SEPARATOR);
        if (indexOfServiceName != -1) {
            String receiverVariableName = expression.substring(0, indexOfServiceName);
            result = Optional.of(receiverVariableName);
        }
        return result;
    }

    private Object callService(Object[] target, String serviceName) throws AcceleoQueryEvaluationException {
        // TODO maybe this can't be a list, check with polymorphic services
        List<IService<?>> services = this.queryEnvironment.getLookupEngine().getRegisteredServices().stream().filter(service -> Objects.equals(service.getName(), serviceName)).toList();
        if (!services.isEmpty()) {
            return services.get(0).invoke(target);
        }
        return null;
    }

}
