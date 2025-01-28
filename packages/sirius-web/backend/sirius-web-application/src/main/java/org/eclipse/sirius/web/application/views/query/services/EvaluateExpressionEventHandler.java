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
package org.eclipse.sirius.web.application.views.query.services;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.collaborative.api.ChangeKind;
import org.eclipse.sirius.components.collaborative.api.IEditingContextEventHandler;
import org.eclipse.sirius.components.collaborative.api.Monitoring;
import org.eclipse.sirius.components.core.api.ErrorPayload;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IInput;
import org.eclipse.sirius.components.core.api.IObjectSearchService;
import org.eclipse.sirius.components.core.api.IPayload;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.Status;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.web.application.views.query.dto.BooleanExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionInput;
import org.eclipse.sirius.web.application.views.query.dto.EvaluateExpressionSuccessPayload;
import org.eclipse.sirius.web.application.views.query.dto.IntExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.ObjectExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.ObjectsExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.StringExpressionResult;
import org.eclipse.sirius.web.application.views.query.dto.VoidExpressionResult;
import org.eclipse.sirius.web.application.views.query.services.api.IAQLInterpreterProvider;
import org.eclipse.sirius.web.domain.services.api.IMessageService;
import org.springframework.stereotype.Service;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import reactor.core.publisher.Sinks;

/**
 * Used to evaluate an expression for the interpreter view.
 *
 * @author sbegaudeau
 */
@Service
public class EvaluateExpressionEventHandler implements IEditingContextEventHandler {

    private final IAQLInterpreterProvider aqlInterpreterProvider;

    private final IObjectSearchService objectSearchService;

    private final IMessageService messageService;

    private final IFeedbackMessageService feedbackMessageService;

    private final Counter counter;

    public EvaluateExpressionEventHandler(IAQLInterpreterProvider aqlInterpreterProvider, IObjectSearchService objectSearchService, IMessageService messageService, IFeedbackMessageService feedbackMessageService, MeterRegistry meterRegistry) {
        this.aqlInterpreterProvider = Objects.requireNonNull(aqlInterpreterProvider);
        this.objectSearchService = Objects.requireNonNull(objectSearchService);
        this.messageService = Objects.requireNonNull(messageService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
        this.counter = Counter.builder(Monitoring.EVENT_HANDLER)
                .tag(Monitoring.NAME, this.getClass().getSimpleName())
                .register(meterRegistry);
    }

    @Override
    public boolean canHandle(IEditingContext editingContext, IInput input) {
        return input instanceof EvaluateExpressionInput;
    }

    @Override
    public void handle(Sinks.One<IPayload> payloadSink, Sinks.Many<ChangeDescription> changeDescriptionSink, IEditingContext editingContext, IInput input) {
        this.counter.increment();

        String message = this.messageService.invalidInput(input.getClass().getSimpleName(), EvaluateExpressionInput.class.getSimpleName());
        IPayload payload = new ErrorPayload(input.id(), message);
        ChangeDescription changeDescription = new ChangeDescription(ChangeKind.NOTHING, editingContext.getId(), input);

        if (input instanceof EvaluateExpressionInput evaluateExpressionInput) {
            var interpreter = this.aqlInterpreterProvider.getInterpreter(editingContext);


            var selection = evaluateExpressionInput.selectedObjectIds().stream()
                    .map(objectId -> this.objectSearchService.getObject(editingContext, objectId))
                    .flatMap(Optional::stream)
                    .toList();
            var self = selection.stream()
                    .findFirst()
                    .orElse(null);

            var variableManager = new VariableManager();
            variableManager.put(IEditingContext.EDITING_CONTEXT, editingContext);
            variableManager.put(VariableManager.SELF, self);
            variableManager.put("selection", selection);
            var evaluationResult = interpreter.evaluateExpression(variableManager.getVariables(), evaluateExpressionInput.expression());

            payload = this.toPayload(input.id(), evaluationResult);
            changeDescription = new ChangeDescription(ChangeKind.SEMANTIC_CHANGE, editingContext.getId(), input);
        }

        payloadSink.tryEmitValue(payload);
        changeDescriptionSink.tryEmitNext(changeDescription);
    }

    private IPayload toPayload(UUID inputId, Result evaluationResult) {
        IPayload payload = null;
        if (evaluationResult.getStatus() == Status.ERROR) {
            payload = new ErrorPayload(inputId, this.messageService.unexpectedError());
        } else if (!this.feedbackMessageService.getFeedbackMessages().isEmpty()) {
            payload = new ErrorPayload(inputId, this.feedbackMessageService.getFeedbackMessages());
        } else {
            var optionalObject = evaluationResult.asObject();
            if (optionalObject.isPresent()) {
                var object = optionalObject.get();
                if (object instanceof Collection<?> collectionValue) {
                    var value = collectionValue.stream().map(Object.class::cast).toList();
                    payload = new EvaluateExpressionSuccessPayload(inputId, new ObjectsExpressionResult(value));
                } else if (object instanceof Boolean booleanValue) {
                    payload = new EvaluateExpressionSuccessPayload(inputId, new BooleanExpressionResult(booleanValue));
                } else if (object instanceof String stringValue) {
                    payload = new EvaluateExpressionSuccessPayload(inputId, new StringExpressionResult(stringValue));
                } else if (object instanceof Integer intValue) {
                    payload = new EvaluateExpressionSuccessPayload(inputId, new IntExpressionResult(intValue));
                } else {
                    payload = new EvaluateExpressionSuccessPayload(inputId, new ObjectExpressionResult(object));
                }
            } else {
                payload = new EvaluateExpressionSuccessPayload(inputId, new VoidExpressionResult());
            }
        }
        return payload;
    }
}
