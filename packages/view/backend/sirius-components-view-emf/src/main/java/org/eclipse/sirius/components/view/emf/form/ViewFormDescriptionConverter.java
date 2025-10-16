/*******************************************************************************
 * Copyright (c) 2022, 2025 Obeo.
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
package org.eclipse.sirius.components.view.emf.form;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.UnaryOperator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.emf.DomainClassPredicate;
import org.eclipse.sirius.components.forms.description.FormDescription;
import org.eclipse.sirius.components.forms.description.PageDescription;
import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.interpreter.Result;
import org.eclipse.sirius.components.representations.GetOrCreateRandomIdProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.emf.IRepresentationDescriptionConverter;
import org.eclipse.sirius.components.view.emf.ViewConverterResult;
import org.eclipse.sirius.components.view.emf.ViewIconURLsProvider;
import org.eclipse.sirius.components.view.emf.form.api.IFormIdProvider;
import org.eclipse.sirius.components.view.emf.form.converters.api.IPageDescriptionConverter;
import org.eclipse.sirius.components.view.form.FormVariable;
import org.springframework.stereotype.Service;

/**
 * Converts a View-based form description into an equivalent {@link FormDescription}.
 *
 * @author fbarbin
 */
@Service
public class ViewFormDescriptionConverter implements IRepresentationDescriptionConverter {

    public static final String VARIABLE_MANAGER = "variableManager";

    public static final String NEW_VALUE = "newValue";

    private static final String DEFAULT_FORM_LABEL = "Form";

    private final IIdentityService identityService;

    private final IFormIdProvider formIdProvider;

    private final IPageDescriptionConverter pageDescriptionConverter;

    public ViewFormDescriptionConverter(IIdentityService identityService, IFormIdProvider formIdProvider, IPageDescriptionConverter pageDescriptionConverter) {
        this.identityService = Objects.requireNonNull(identityService);
        this.formIdProvider = Objects.requireNonNull(formIdProvider);
        this.pageDescriptionConverter = Objects.requireNonNull(pageDescriptionConverter);
    }

    @Override
    public boolean canConvert(RepresentationDescription representationDescription) {
        return representationDescription instanceof org.eclipse.sirius.components.view.form.FormDescription;
    }

    @Override
    public ViewConverterResult convert(RepresentationDescription representationDescription, List<RepresentationDescription> allRepresentationDescriptions, AQLInterpreter interpreter) {
        org.eclipse.sirius.components.view.form.FormDescription viewFormDescription = (org.eclipse.sirius.components.view.form.FormDescription) representationDescription;

        List<PageDescription> pageDescriptions = viewFormDescription.getPages()
                .stream()
                .map(page -> this.pageDescriptionConverter.convert(page, interpreter))
                .toList();

        Function<VariableManager, String> targetObjectIdProvider = variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .map(this.identityService::getId)
                .orElse(null);

        UnaryOperator<VariableManager> variableManagerInitializer = variableManager -> {
            for (FormVariable formVariable : viewFormDescription.getFormVariables()) {
                Result result = interpreter.evaluateExpression(variableManager.getVariables(), formVariable.getDefaultValueExpression());
                if (result.asObject().isPresent()) {
                    variableManager.put(formVariable.getName(), result.asObject().get());
                }
            }
            return variableManager;
        };

        var formDescription = FormDescription.newFormDescription(this.formIdProvider.getId(viewFormDescription))
                .label(Optional.ofNullable(viewFormDescription.getName()).orElse(DEFAULT_FORM_LABEL))
                .idProvider(new GetOrCreateRandomIdProvider())
                .labelProvider(variableManager -> this.computeFormLabel(viewFormDescription, variableManager, interpreter))
                .canCreatePredicate(variableManager -> this.canCreate(viewFormDescription.getDomainType(), viewFormDescription.getPreconditionExpression(), variableManager, interpreter))
                .targetObjectIdProvider(targetObjectIdProvider)
                .pageDescriptions(pageDescriptions)
                .variableManagerInitializer(variableManagerInitializer)
                .iconURLsProvider(new ViewIconURLsProvider(interpreter, viewFormDescription.getIconExpression()))
                .build();
        return new ViewConverterResult(formDescription, null);
    }

    private String computeFormLabel(org.eclipse.sirius.components.view.form.FormDescription viewFormDescription, VariableManager variableManager, AQLInterpreter interpreter) {
        return variableManager.get(FormDescription.LABEL, String.class)
                .or(() -> interpreter.evaluateExpression(variableManager.getVariables(), viewFormDescription.getTitleExpression()).asString())
                .orElse(DEFAULT_FORM_LABEL);
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
