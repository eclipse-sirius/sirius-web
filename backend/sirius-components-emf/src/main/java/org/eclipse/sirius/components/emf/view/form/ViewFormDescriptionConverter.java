/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.emf.view.form;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.compatibility.forms.WidgetIdProvider;
import org.eclipse.sirius.components.compatibility.utils.StringValueProvider;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.emf.compatibility.DomainClassPredicate;
import org.eclipse.sirius.components.emf.view.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.forms.description.AbstractControlDescription;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.GroupDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.forms.description.TextfieldDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.Failure;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.IRepresentationDescription;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based form description into an equivalent {@link FormDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewFormDescriptionConverter implements IRepresentationDescriptionConverter {

    private static final String DEFAULT_FORM_LABEL = "Form"; //$NON-NLS-1$

    private final IObjectService objectService;

    public ViewFormDescriptionConverter(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.FormDescription;
    }

    @Override
    public IRepresentationDescription convert(RepresentationDescription representationDescription, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.FormDescription viewFormDescription = (org.eclipse.sirius.components.view.FormDescription) representationDescription;
        // @formatter:off
        List<AbstractControlDescription> textfieldDescriptions = viewFormDescription.getWidgets().stream()
                .filter(org.eclipse.sirius.components.view.TextfieldDescription.class::isInstance)
                .map(org.eclipse.sirius.components.view.TextfieldDescription.class::cast)
                .map(viewTextfieldDescription -> this.convertTextfieldDescriptionviewTextfieldDescription(viewTextfieldDescription, interpreter))
                .collect(Collectors.toList());

        Function<VariableManager, List<?>> semanticElementsProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream()
                .collect(Collectors.toList());

        String descriptionId = this.getDescriptionId(viewFormDescription);
        GroupDescription groupDescription = GroupDescription.newGroupDescription(descriptionId + "_group") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .semanticElementsProvider(semanticElementsProvider)
                .controlDescriptions(textfieldDescriptions)
                .build();
        PageDescription pageDescription = PageDescription.newPageDescription(descriptionId + "_page") //$NON-NLS-1$
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .semanticElementsProvider(semanticElementsProvider)
                .canCreatePredicate(variableManager -> true)
                .groupDescriptions(List.of(groupDescription))
                .build();


        // @formatter:on
        List<GroupDescription> groupDescriptions = List.of(groupDescription);
        List<PageDescription> pageDescriptions = List.of(pageDescription);

        // @formatter:off
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            return this.self(variableManager)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .map(this.objectService::getId)
                .orElse(null);
        };

        return FormDescription.newFormDescription(descriptionId)
                .label(Optional.ofNullable(viewFormDescription.getName()).orElse(DEFAULT_FORM_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreatForm(viewFormDescription, variableManager, interpreter))
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(pageDescriptions)
                .groupDescriptions(groupDescriptions)
                .build();
        // @formatter:on
    }

    private String getDescriptionId(EObject description) {
        String descriptionURI = EcoreUtil.getURI(description).toString();
        return UUID.nameUUIDFromBytes(descriptionURI.getBytes()).toString();
    }

    private String computeFormLabel(org.eclipse.sirius.components.view.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        String title = this.evaluateString(interpreter, variableManager, viewFormDescription.getTitleExpression());
        if (title == null || title.isBlank()) {
            return DEFAULT_FORM_LABEL;
        } else {
            return title;
        }
    }

    private String evaluateString(AQLInterpreter interpreter, VariableManager variableManager, String expression) {
        return interpreter.evaluateExpression(variableManager.getVariables(), expression).asString().orElse(""); //$NON-NLS-1$
    }

    private boolean canCreatForm(org.eclipse.sirius.components.view.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        boolean result = false;
        // @formatter:off
        Optional<EClass> optionalEClass = variableManager.get(IRepresentationDescription.CLASS, EClass.class)
                .filter(new DomainClassPredicate(viewFormDescription.getDomainType()));
        // @formatter:on
        if (optionalEClass.isPresent()) {
            String preconditionExpression = viewFormDescription.getPreconditionExpression();
            if (preconditionExpression != null && !preconditionExpression.isBlank()) {
                result = interpreter.evaluateExpression(variableManager.getVariables(), preconditionExpression).asBoolean().orElse(false);
            } else {
                result = true;
            }
        }
        return result;
    }

    private Optional<Object> self(VariableManager variableManager) {
        return variableManager.get(VariableManager.SELF, Object.class);
    }

    private TextfieldDescription convertTextfieldDescriptionviewTextfieldDescription(org.eclipse.sirius.components.view.TextfieldDescription viewTextfieldDescription, AQLInterpreter interpreter) {

        String labelExpression = Optional.ofNullable(viewTextfieldDescription.getLabelExpression()).orElse(""); //$NON-NLS-1$
        StringValueProvider labelProvider = new StringValueProvider(interpreter, labelExpression);

        String valueExpression = Optional.ofNullable(viewTextfieldDescription.getValueExpression()).orElse(""); //$NON-NLS-1$
        StringValueProvider valueProvider = new StringValueProvider(interpreter, valueExpression);

        // @formatter:off
        return TextfieldDescription.newTextfieldDescription(this.getDescriptionId(viewTextfieldDescription))
                .idProvider(new WidgetIdProvider())
                .labelProvider(labelProvider)
                .valueProvider(valueProvider)
                .newValueHandler((variableManager, newValue) -> new Failure("Value Handler not yet supported")) //$NON-NLS-1$
                .diagnosticsProvider(variableManager -> List.of())
                .kindProvider(diagnostic -> "") //$NON-NLS-1$
                .messageProvider(diagnostic -> "") //$NON-NLS-1$
                .build();
        // @formatter:on
    }
}
