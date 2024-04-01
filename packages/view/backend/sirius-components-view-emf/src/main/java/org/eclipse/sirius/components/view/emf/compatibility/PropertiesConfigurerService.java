/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.emf.compatibility;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Configuration for the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
@Service
public class PropertiesConfigurerService implements IPropertiesConfigurerService {

    private final IObjectService objectService;

    private final List<IValidationService> validationServices;

    public PropertiesConfigurerService(IObjectService objectService, List<IValidationService> validationServices) {
        this.objectService = Objects.requireNonNull(objectService);
        this.validationServices = Objects.requireNonNull(validationServices);
    }

    @Override
    public Function<VariableManager, List<?>> getSemanticElementsProvider() {
        return variableManager -> variableManager.get(VariableManager.SELF, Object.class).stream().toList();
    }

    @Override
    public Function<VariableManager, String> getSemanticTargetIdProvider() {
        return variableManager -> variableManager.get(VariableManager.SELF, Object.class).map(objectService::getId).orElse(null);
    }
    @Override
    public Function<VariableManager, List<?>> getDiagnosticsProvider(Object feature) {
        return variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);

            if (optionalSelf.isPresent()) {
                EObject self = optionalSelf.get();
                return this.validationServices.stream()
                        .map(validationService -> validationService.validate(self, feature))
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
