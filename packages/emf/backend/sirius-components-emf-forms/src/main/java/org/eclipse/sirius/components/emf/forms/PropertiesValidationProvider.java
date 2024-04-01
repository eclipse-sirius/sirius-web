/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.emf.forms;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.emf.forms.api.IPropertiesValidationProvider;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to provide validation support to all the widgets of the default properties description.
 *
 * @author sbegaudeau
 */
@Service
public class PropertiesValidationProvider implements IPropertiesValidationProvider {

    private final List<IValidationService> validationServices;

    public PropertiesValidationProvider(List<IValidationService> validationServices) {
        this.validationServices = Objects.requireNonNull(validationServices);
    }

    @Override
    public Function<VariableManager, List<?>> getDiagnosticsProvider() {
        return variableManager -> {
            var optionalEObject = variableManager.get(VariableManager.SELF, EObject.class);
            var optionalEStructuralFeature = variableManager.get(EMFFormDescriptionProvider.ESTRUCTURAL_FEATURE, EStructuralFeature.class);
            if (optionalEObject.isPresent() && optionalEStructuralFeature.isPresent()) {
                var eObject = optionalEObject.get();
                var eStructuralFeature = optionalEStructuralFeature.get();
                return this.validationServices.stream()
                        .map(validationService -> validationService.validate(eObject, eStructuralFeature))
                        .flatMap(Collection::stream)
                        .toList();
            }

            return List.of();
        };
    }

    @Override
    public Function<Object, String> getKindProvider() {
        return object -> {
            if (object instanceof Diagnostic diagnostic) {
                return switch (diagnostic.getSeverity()) {
                    case org.eclipse.emf.common.util.Diagnostic.ERROR -> "Error";
                    case org.eclipse.emf.common.util.Diagnostic.WARNING -> "Warning";
                    case org.eclipse.emf.common.util.Diagnostic.INFO -> "Info";
                    default -> "Unknown";
                };
            }
            return "Unknown";
        };
    }

    @Override
    public Function<Object, String> getMessageProvider() {
        return object -> Optional.ofNullable(object)
                .filter(Diagnostic.class::isInstance)
                .map(Diagnostic.class::cast)
                .map(Diagnostic::getMessage)
                .orElse("");
    }
}
