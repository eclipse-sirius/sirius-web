/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form.converters.widgets;

import static org.eclipse.sirius.components.view.emf.form.ViewFormDescriptionConverter.VARIABLE_MANAGER;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.LinkStyle;
import org.eclipse.sirius.components.forms.WidgetIdProvider;
import org.eclipse.sirius.components.forms.description.AbstractWidgetDescription;
import org.eclipse.sirius.components.forms.description.LinkDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.LinkStyleProvider;
import org.eclipse.sirius.components.view.emf.form.converters.TargetObjectIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticKindProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticMessageProvider;
import org.eclipse.sirius.components.view.emf.form.converters.validation.DiagnosticProvider;
import org.eclipse.sirius.components.view.form.LinkDescriptionStyle;
import org.eclipse.sirius.components.view.form.WidgetDescription;
import org.springframework.stereotype.Service;

/**
 * Used to convert link descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class LinkDescriptionConverter implements IWidgetDescriptionConverter {

    private final IObjectService objectService;

    private final IFormIdProvider widgetIdProvider;

    public LinkDescriptionConverter(IObjectService objectService, IFormIdProvider widgetIdProvider) {
        this.objectService = Objects.requireNonNull(objectService);
        this.widgetIdProvider = Objects.requireNonNull(widgetIdProvider);
    }

    @Override
    public boolean canConvert(WidgetDescription viewWidgetDescription) {
        return viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.LinkDescription;
    }

    @Override
    public Optional<AbstractWidgetDescription> convert(WidgetDescription viewWidgetDescription, AQLInterpreter interpreter) {
        if (viewWidgetDescription instanceof org.eclipse.sirius.components.view.form.LinkDescription viewLinkDescription) {
            String descriptionId = this.widgetIdProvider.getFormElementDescriptionId(viewLinkDescription);

            Function<VariableManager, LinkStyle> styleProvider = variableManager -> {
                VariableManager childVariableManager = variableManager.createChild();
                childVariableManager.put(VARIABLE_MANAGER, variableManager);
                var effectiveStyle = viewLinkDescription.getConditionalStyles().stream()
                        .filter(style -> interpreter.evaluateExpression(childVariableManager.getVariables(), style.getCondition())
                                .asBoolean()
                                .orElse(Boolean.FALSE))
                        .map(LinkDescriptionStyle.class::cast)
                        .findFirst()
                        .orElseGet(viewLinkDescription::getStyle);
                if (effectiveStyle == null) {
                    return null;
                }
                return new LinkStyleProvider(effectiveStyle).apply(childVariableManager);
            };

            var linkDescription = LinkDescription.newLinkDescription(descriptionId)
                    .idProvider(new WidgetIdProvider())
                    .targetObjectIdProvider(new TargetObjectIdProvider(this.objectService))
                    .labelProvider(new StringValueProvider(interpreter, viewLinkDescription.getLabelExpression()))
                    .urlProvider(new StringValueProvider(interpreter, viewLinkDescription.getValueExpression()))
                    .styleProvider(styleProvider)
                    .diagnosticsProvider(new DiagnosticProvider(interpreter, viewLinkDescription.getDiagnosticsExpression()))
                    .kindProvider(new DiagnosticKindProvider())
                    .messageProvider(new DiagnosticMessageProvider())
                    .helpTextProvider(new StringValueProvider(interpreter, viewLinkDescription.getHelpExpression()))
                    .build();

            return Optional.of(linkDescription);
        }
        return Optional.empty();
    }
}
