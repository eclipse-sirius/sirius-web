/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.application.views.validation.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.springframework.stereotype.Service;

/**
 * Used to provide the description of the validation view.
 *
 * @author sbegaudeau
 */
@Service
public class ValidationDescriptionProvider implements IValidationDescriptionProvider {

    public static final String DESCRIPTION_ID = "validation_description";

    private final List<IValidationService> validationServices;

    public ValidationDescriptionProvider(List<IValidationService> validationServices) {
        this.validationServices = Objects.requireNonNull(validationServices);
    }

    @Override
    public ValidationDescription getDescription() {
        return ValidationDescription.newValidationDescription(DESCRIPTION_ID)
                .label("Validation")
                .canCreatePredicate(variableManager -> false)
                .diagnosticsProvider(this::getDiagnosticsProvider)
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
    }

    private List<Object> getDiagnosticsProvider(VariableManager variableManager) {
        return variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class)
                .map(editingContext -> this.validationServices.stream()
                        .map(validationService -> validationService.validate(editingContext))
                        .flatMap(Collection::stream)
                        .toList())
                .orElseGet(List::of);
    }

    private String kindProvider(Object object) {
        if (object instanceof Diagnostic diagnostic) {
            return switch (diagnostic.getSeverity()) {
                case org.eclipse.emf.common.util.Diagnostic.ERROR -> "Error";
                case org.eclipse.emf.common.util.Diagnostic.WARNING -> "Warning";
                case org.eclipse.emf.common.util.Diagnostic.INFO -> "Info";
                default -> "Unknown";
            };
        }
        return "Unknown";
    }

    private String messageProvider(Object object) {
        return Optional.ofNullable(object)
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .map(Diagnostic::getMessage)
                .orElse("");
    }
}
