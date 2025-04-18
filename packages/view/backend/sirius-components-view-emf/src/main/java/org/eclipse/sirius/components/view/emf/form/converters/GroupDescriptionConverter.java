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
package org.eclipse.sirius.components.view.emf.form.converters;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.ContainerBorderStyle;
import org.eclipse.sirius.components.forms.GroupDisplayMode;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.ContainerBorderStyleProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.api.IGroupDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.formelements.api.IComposedFormElementDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Used to convert a group description.
 *
 * @author sbegaudeau
 */
@Service
public class GroupDescriptionConverter implements IGroupDescriptionConverter {

    private final IIdentityService identityService;

    private final IFormIdProvider formIdProvider;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    private final IComposedFormElementDescriptionConverter composedFormElementDescriptionConverter;

    public GroupDescriptionConverter(IIdentityService identityService, IFormIdProvider formIdProvider, List<IWidgetDescriptionConverter> widgetDescriptionConverters, IComposedFormElementDescriptionConverter composedFormElementDescriptionConverter) {
        this.identityService = Objects.requireNonNull(identityService);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
        this.composedFormElementDescriptionConverter = Objects.requireNonNull(composedFormElementDescriptionConverter);
    }

    @Override
    public GroupDescription convert(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription, AQLInterpreter interpreter) {
        List<AbstractControlDescription> controlDescriptions = viewGroupDescription.getChildren().stream()
                .map(formElementDescription -> this.composedFormElementDescriptionConverter.convert(formElementDescription, interpreter))
                .flatMap(Optional::stream)
                .toList();

        List<ButtonDescription> toolbarActionDescriptions = viewGroupDescription.getToolbarActions().stream()
                .map(buttonDescription -> this.widgetDescriptionConverters.stream()
                        .filter(converter -> converter.canConvert(buttonDescription))
                        .findFirst()
                        .flatMap(converter -> converter.convert(buttonDescription, interpreter)))
                .flatMap(Optional::stream)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast)
                .toList();


        Function<VariableManager, ContainerBorderStyle> borderStyleProvider = variableManager -> {
            var effectiveStyle = viewGroupDescription.getConditionalBorderStyles().stream()
                    .filter(style -> interpreter.evaluateExpression(variableManager.getVariables(), style.getCondition())
                            .asBoolean()
                            .orElse(Boolean.FALSE))
                    .map(org.eclipse.sirius.components.view.form.ContainerBorderStyle.class::cast)
                    .findFirst()
                    .orElseGet(viewGroupDescription::getBorderStyle);
            if (effectiveStyle == null) {
                return null;
            }
            return new ContainerBorderStyleProvider(effectiveStyle).apply(variableManager);
        };

        var descriptionId = this.formIdProvider.getFormElementDescriptionId(viewGroupDescription);
        return GroupDescription.newGroupDescription(descriptionId)
                .idProvider(this.getIdProvider(descriptionId))
                .labelProvider(new StringValueProvider(interpreter, viewGroupDescription.getLabelExpression()))
                .semanticElementsProvider(variableManager -> this.getSemanticElementsProvider(viewGroupDescription, variableManager, interpreter))
                .controlDescriptions(controlDescriptions)
                .toolbarActionDescriptions(toolbarActionDescriptions)
                .displayModeProvider(variableManager -> GroupDisplayMode.valueOf(viewGroupDescription.getDisplayMode().getLiteral()))
                .borderStyleProvider(borderStyleProvider)
                .build();
    }

    private Function<VariableManager, String> getIdProvider(String descriptionId) {
        return variableManager -> {
            String selfId = variableManager.get(VariableManager.SELF, Object.class)
                    .map(this.identityService::getId)
                    .orElse("");
            return UUID.nameUUIDFromBytes((selfId + descriptionId).getBytes()).toString();
        };
    }

    private List<?> getSemanticElementsProvider(org.eclipse.sirius.components.view.form.GroupDescription viewGroupDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewGroupDescription.getSemanticCandidatesExpression());
        List<Object> candidates = result.asObjects().orElse(List.of());
        return candidates.stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }

}
