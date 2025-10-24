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

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.forms.description.ButtonDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.interpreter.StringValueProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.api.IGroupDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.api.IPageDescriptionConverter;
import org.eclipse.sirius.components.view.emf.form.converters.widgets.api.IWidgetDescriptionConverter;
import org.springframework.stereotype.Service;

/**
 * Used to convert page descriptions.
 *
 * @author sbegaudeau
 */
@Service
public class PageDescriptionConverter implements IPageDescriptionConverter {

    private final IGroupDescriptionConverter groupDescriptionConverter;

    private final IFormIdProvider formIdProvider;

    private final IIdentityService identityService;

    private final List<IWidgetDescriptionConverter> widgetDescriptionConverters;

    public PageDescriptionConverter(IGroupDescriptionConverter groupDescriptionConverter, IFormIdProvider formIdProvider, IIdentityService identityService, List<IWidgetDescriptionConverter> widgetDescriptionConverters) {
        this.groupDescriptionConverter = Objects.requireNonNull(groupDescriptionConverter);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.identityService = Objects.requireNonNull(identityService);
        this.widgetDescriptionConverters = Objects.requireNonNull(widgetDescriptionConverters);
    }

    @Override
    public PageDescription convert(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, AQLInterpreter interpreter) {
        List<GroupDescription> groupDescriptions = viewPageDescription.getGroups().stream()
                .map(groupDescription -> this.groupDescriptionConverter.convert(groupDescription, interpreter))
                .toList();

        List<ButtonDescription> toolbarActionDescriptions = viewPageDescription.getToolbarActions().stream()
                .map(buttonDescription -> this.widgetDescriptionConverters.stream()
                        .filter(converter -> converter.canConvert(buttonDescription))
                        .findFirst()
                        .flatMap(converter -> converter.convert(buttonDescription, interpreter)))
                .flatMap(Optional::stream)
                .filter(ButtonDescription.class::isInstance)
                .map(ButtonDescription.class::cast)
                .toList();

        String descriptionId = this.formIdProvider.getFormElementDescriptionId(viewPageDescription);
        return PageDescription.newPageDescription(descriptionId)
                .idProvider(this.getIdProvider(descriptionId))
                .labelProvider(new StringValueProvider(interpreter, viewPageDescription.getLabelExpression()))
                .semanticElementsProvider(variableManager -> this.getSemanticElementsProvider(viewPageDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewPageDescription.getDomainType(), viewPageDescription.getPreconditionExpression(), variableManager, interpreter))
                .groupDescriptions(groupDescriptions)
                .toolbarActionDescriptions(toolbarActionDescriptions)
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

    private List<?> getSemanticElementsProvider(org.eclipse.sirius.components.view.form.PageDescription viewPageDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        Result result = interpreter.evaluateExpression(variableManager.getVariables(), viewPageDescription.getSemanticCandidatesExpression());
        List<Object> candidates = result.asObjects().orElse(List.of());
        return candidates.stream()
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .toList();
    }

    private boolean canCreate(String domainType, String preconditionExpression, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        Optional<EClass> optionalEClass = variableManager.get(VariableManager.SELF, EObject.class)
                .map(EObject::eClass)
                .filter(new DomainClassPredicate(domainType));
        if (optionalEClass.isPresent()) {
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }
}
