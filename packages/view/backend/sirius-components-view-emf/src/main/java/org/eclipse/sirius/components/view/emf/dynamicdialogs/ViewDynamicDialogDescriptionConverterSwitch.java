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

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IEditService;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.dynamicdialogs.description.AbstractDWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DSelectWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DTextFieldWidgetDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetObjectOutputDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetOutputDescription;
import org.eclipse.sirius.components.dynamicdialogs.description.DWidgetStringOutputDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.util.ViewSwitch;

/**
 * A switch to dispatch View Form Widget Descriptions conversion.
 *
 * @author lfasani
 */
public class ViewDynamicDialogDescriptionConverterSwitch extends ViewSwitch<AbstractDWidgetDescription> {

    private final AQLInterpreter interpreter;

    private final IEditService editService;

    private final IObjectService objectService;

    public ViewDynamicDialogDescriptionConverterSwitch(AQLInterpreter interpreter, IEditService editService, IObjectService objectService) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.editService = Objects.requireNonNull(editService);
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public AbstractDWidgetDescription caseDSelectWidgetDescription(org.eclipse.sirius.components.view.DSelectWidgetDescription viewSelectWidgetDescription) {
        // String descriptionId = this.getDescriptionId(viewSelectWidgetDescription);
        DWidgetOutputDescription outputDescription = this.getOutputDescription(viewSelectWidgetDescription.getOutput());

        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewSelectWidgetDescription.getLabelExpression());
        Function<VariableManager, String> initialValueProvider = this.getSelectInitialValueProvider(viewSelectWidgetDescription.getInitialValueExpression());
        Function<VariableManager, String> optionIdProvider = this.getOptionIdProvider();
        Function<VariableManager, String> optionLabelProvider = this.getOptionLabelProvider(viewSelectWidgetDescription.getOptionLabelExpression(), outputDescription);
        String candidateExpression = viewSelectWidgetDescription.getOptionsExpression();
        Function<VariableManager, List<?>> optionsProvider = this.getMultiValueProvider(candidateExpression);

        // @formatter:off
        List<DWidgetOutputDescription> inputs = viewSelectWidgetDescription.getInputs().stream()
                .map(input -> {
                    DWidgetOutputDescription dWidgetOutputDescription = null;
                    if (input instanceof org.eclipse.sirius.components.view.DWidgetStringOutputDescription viewStringOutput) {
                        dWidgetOutputDescription = new DWidgetStringOutputDescription(viewStringOutput.getName());
                    } else if (input instanceof org.eclipse.sirius.components.view.DWidgetObjectOutputDescription viewObjectOutput) {
                        dWidgetOutputDescription = new DWidgetObjectOutputDescription(viewObjectOutput.getName());
                    }
                    return dWidgetOutputDescription;
                })
                .toList();

        return DSelectWidgetDescription.newDSelectWidgetDescription(viewSelectWidgetDescription.getId())
                .labelProvider(labelProvider)
                .inputs(inputs)
                .output(outputDescription)
                .initialValueProvider(initialValueProvider)
                .optionIdProvider(optionIdProvider)
                .optionLabelProvider(optionLabelProvider)
                .optionsProvider(optionsProvider)
                .build();
        // @formatter:on
    }

    private DWidgetOutputDescription getOutputDescription(org.eclipse.sirius.components.view.DWidgetOutputDescription viewWidgetDescription) {
        DWidgetOutputDescription outputDescription = null;
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.DWidgetStringOutputDescription viewStringOutput) {
            outputDescription = new DWidgetStringOutputDescription(viewStringOutput.getName());
        } else if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.DWidgetObjectOutputDescription viewObjectOutput) {
            outputDescription = new DWidgetObjectOutputDescription(viewObjectOutput.getName());
        }
        return outputDescription;
    }

    @Override
    public AbstractDWidgetDescription caseDTextFieldWidgetDescription(org.eclipse.sirius.components.view.DTextFieldWidgetDescription viewTextFieldWidgetDescription) {
        // String descriptionId = this.getDescriptionId(viewSelectWidgetDescription);
        DWidgetOutputDescription outputDescription = this.getOutputDescription(viewTextFieldWidgetDescription.getOutput());
        WidgetIdProvider idProvider = new WidgetIdProvider();
        StringValueProvider labelProvider = this.getStringValueProvider(viewTextFieldWidgetDescription.getLabelExpression());
        Function<VariableManager, String> initialValueProvider = this.getSelectInitialValueProvider(viewTextFieldWidgetDescription.getInitialValueExpression());

        // @formatter:off
        List<DWidgetOutputDescription> inputs = viewTextFieldWidgetDescription.getInputs().stream()
                .map(input -> {
                    DWidgetOutputDescription dWidgetOutputDescription = null;
                    if (input instanceof org.eclipse.sirius.components.view.DWidgetStringOutputDescription viewStringOutput) {
                        dWidgetOutputDescription = new DWidgetStringOutputDescription(viewStringOutput.getName());
                    } else if (input instanceof org.eclipse.sirius.components.view.DWidgetObjectOutputDescription viewObjectOutput) {
                        dWidgetOutputDescription = new DWidgetObjectOutputDescription(viewObjectOutput.getName());
                    }
                    return dWidgetOutputDescription;
                })
                .toList();

        return DTextFieldWidgetDescription.newDTextFieldWidgetDescription(viewTextFieldWidgetDescription.getId())
                .labelProvider(labelProvider)
                .inputs(inputs)
                .output(outputDescription)
                .initialValueProvider(initialValueProvider)
                .build();
        // @formatter:on
    }

    private StringValueProvider getStringValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return new StringValueProvider(this.interpreter, safeValueExpression);
    }

    private Function<VariableManager, String> getOptionLabelProvider(String valueExpression, DWidgetOutputDescription dWidgetOutputDescription) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            String label = "";
            if (!safeValueExpression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
                label = result.asString().orElse("");
            } else {
                Object candidate = variableManager.getVariables().get(VariableManager.SELF);
                if (dWidgetOutputDescription instanceof DWidgetObjectOutputDescription) {
                    label = this.objectService.getId(candidate);
                } else {
                    label = candidate.toString();
                }
            }

            return label;
        };
    }

    private Function<VariableManager, String> getOptionIdProvider() {
        return variableManager -> {
            Object candidate = variableManager.getVariables().get(VariableManager.SELF);
            // Object candidate = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
            return this.objectService.getId(candidate);
        };
    }

    private Function<VariableManager, String> getSelectInitialValueProvider(String valueExpression) {
        String safeValueExpression = Optional.ofNullable(valueExpression).orElse("");
        return variableManager -> {
            if (!safeValueExpression.isBlank()) {
                Result result = this.interpreter.evaluateExpression(variableManager.getVariables(), safeValueExpression);
                return result.asObject().map(this.objectService::getId).orElse("");
            }
            return "";
        };
    }

    private Function<VariableManager, List<?>> getMultiValueProvider(String expression) {
        String safeExpression = Optional.ofNullable(expression).orElse("");
        return variableManager -> {
            return this.interpreter.evaluateExpression(variableManager.getVariables(), safeExpression).asObjects().orElse(List.of());
        };
    }
    // private String getDescriptionId(EObject description) {
    // String descriptionURI = EcoreUtil.getURI(description).toString();
    // return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    // }

    private boolean matches(String condition, VariableManager variableManager) {
        return this.interpreter.evaluateExpression(variableManager.getVariables(), condition).asBoolean().orElse(Boolean.FALSE);
    }

}
