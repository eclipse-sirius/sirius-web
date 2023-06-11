/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.emf.dynamicdialogs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IFeedbackMessageService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.dynamicdialogs.DynamicDialogValidationMessage;
import org.eclipse.sirius.components.dynamicdialogs.MessageSeverityEnum;
import org.eclipse.sirius.components.dynamicdialogs.description.AbstractDWidgetDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.Message;
import org.eclipse.sirius.components.representations.MessageLevel;
import org.eclipse.sirius.components.representations.Success;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.emf.OperationInterpreter;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based dynamic dialog description into an equivalent {@link DynamicDialogDescription}.
 *
 * @author lfasani
 */
@Service
public class ViewDynamicDialogDescriptionConverter {

    public static final String NEW_VALUE = "newValue";

    private static final String DEFAULT_DIALOG_TITLE = "Dynamic Dialog Title";

    private static final String DEFAULT_DIALOG_DESCRIPTION = "";

    private final IObjectService objectService;

    private final IEditService editService;

    private final IFeedbackMessageService feedbackMessageService;

    public ViewDynamicDialogDescriptionConverter(IObjectService objectService, IEditService editService, IFeedbackMessageService feedbackMessageService) {
        this.objectService = Objects.requireNonNull(objectService);
        this.editService = Objects.requireNonNull(editService);
        this.feedbackMessageService = Objects.requireNonNull(feedbackMessageService);
    }

    public org.eclipse.sirius.components.dynamicdialogs.description.DynamicDialogDescription convert(DynamicDialogDescription viewDynamicDialogDescription, AQLInterpreter interpreter) {
        ViewDynamicDialogDescriptionConverterSwitch dispatcher = new ViewDynamicDialogDescriptionConverterSwitch(interpreter, this.editService, this.objectService);

        // @formatter:off
        List<AbstractDWidgetDescription> widgetDescriptions = viewDynamicDialogDescription.getWidgetDescriptions().stream()
                .map(dispatcher::doSwitch)
                .toList();

        String descriptionId = this.getDescriptionId(viewDynamicDialogDescription);
        descriptionId = viewDynamicDialogDescription.getId();


        // @formatter:off
        return org.eclipse.sirius.components.dynamicdialogs.description.DynamicDialogDescription.newDynamicDialogDescription(descriptionId)
                .titleProvider(variableManager -> this.computeTitle(viewDynamicDialogDescription, variableManager, interpreter))
                .descriptionProvider(variableManager -> this.computeDescription(viewDynamicDialogDescription, variableManager, interpreter))
                .applyDialogProvider(variableManager -> this.computeApplyDialog(viewDynamicDialogDescription, variableManager, interpreter))
                .validationMessagesProvider(variableManager -> this.computeValidationMessages(viewDynamicDialogDescription, variableManager, interpreter))
                .dynamicWidgetDescriptions(widgetDescriptions)
                .build();
        // @formatter:on
    }

    private List<DynamicDialogValidationMessage> computeValidationMessages(DynamicDialogDescription viewDynamicDialogDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        List<DynamicDialogValidationMessage> messages = viewDynamicDialogDescription.getValidationMessages().stream()//
                .map(validationMessage -> {
                    Boolean precondition = this.evaluateSBoolean(interpreter, variableManager, validationMessage.getPreCondition());
                    if (precondition) {
                        String message = this.evaluateString(interpreter, variableManager, validationMessage.getMessageExpression());
                        if (message != null && !message.isBlank()) {

                            return DynamicDialogValidationMessage.newDynamicDialogValidationMessage()//
                                    .message(message)//
                                    .blocksApplyDialog(validationMessage.isBlocksApplyDialog())//
                                    .severity(MessageSeverityEnum.valueOf(validationMessage.getSeverity().name()))//
                                    .build();
                        }
                    }
                    return null;
                })//
                .filter(Objects::nonNull)//
                .toList();
        return messages;
    }

    private IStatus computeApplyDialog(DynamicDialogDescription viewDynamicDialogDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        OperationInterpreter operationInterpreter = new OperationInterpreter(interpreter, this.editService);
        Optional<VariableManager> optionalVariableManager = operationInterpreter.executeOperations(viewDynamicDialogDescription.getBody(), variableManager);
        if (optionalVariableManager.isEmpty()) {
            return this.buildFailureWithFeedbackMessages("Something went wrong while handling the apply dynamic dialog execution.");
        } else {
            return this.buildSuccessWithFeedbackMessages();
        }
    }

    private Failure buildFailureWithFeedbackMessages(String technicalMessage) {
        List<Message> errorMessages = new ArrayList<>();
        errorMessages.add(new Message(technicalMessage, MessageLevel.ERROR));
        errorMessages.addAll(this.feedbackMessageService.getFeedbackMessages());
        return new Failure(errorMessages);
    }

    private Success buildSuccessWithFeedbackMessages() {
        return new Success(this.feedbackMessageService.getFeedbackMessages());
    }

    private String computeTitle(org.eclipse.sirius.components.view.DynamicDialogDescription viewDynamicDialogDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = this.evaluateString(interpreter, variableManager, viewDynamicDialogDescription.getTitleExpression());
        if (title == null || title.isBlank()) {
            return DEFAULT_DIALOG_TITLE;
        } else {
            return title;
        }
    }

    private String computeDescription(org.eclipse.sirius.components.view.DynamicDialogDescription viewDynamicDialogDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String description = this.evaluateString(interpreter, variableManager, viewDynamicDialogDescription.getDescriptionExpression());
        if (description == null || description.isBlank()) {
            return DEFAULT_DIALOG_DESCRIPTION;
        } else {
            return description;
        }
    }

    private Function<VariableManager, List<?>> getSemanticElementsProvider(org.eclipse.sirius.components.view.GroupDescription viewGroupDescription, AQLInterpreter interpreter) {
        return variableManager -> {
            Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewGroupDescription.getSemanticCandidatesExpression());
            List<Object> candidates = result.asObjects().orElse(List.of());
            // @formatter:off
            return candidates.stream()
                    .filter(EObject.class::isInstance)
                    .map(EObject.class::cast)
                    .toList();
            // @formatter:on
        };
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse("");
    }

    private Boolean evaluateSBoolean(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asBoolean().orElse(Boolean.FALSE);
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }
}
