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
package org.eclipse.sirius.components.view.emf.compatibility;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Configuration for the properties view for some of the View DSL elements.
 *
 * @author mcharfadi
 */
@Service
public class PropertiesConfigurerService implements IPropertiesConfigurerService {

    private final IValidationService validationService;

    public PropertiesConfigurerService(IValidationService validationService) {
        this.validationService = Objects.requireNonNull(validationService);
    }

    @Override
    public Function<VariableManager, String> getTargetObjectIdProvider() {
        // @formatter:off
        return variableManager -> variableManager.get(VariableManager.SELF, Object.class)
                .filter(self -> self instanceof List<?>)
                .map(self -> (List<?>) self)
                .flatMap(self -> self.stream().findFirst())
                .filter(EObject.class::isInstance)
                .map(EObject.class::cast)
                .map(obj -> EcoreUtil.getURI(obj).toString())
                .orElse(null);
        // @formatter:on
    }

    @Override
    public Function<VariableManager, List<?>> getDiagnosticsProvider(Object feature) {
        return variableManager -> {
            var optionalSelf = variableManager.get(VariableManager.SELF, EObject.class);

            if (optionalSelf.isPresent()) {
                EObject self = optionalSelf.get();
                List<Object> diagnostics = this.validationService.validate(self, feature);
                return diagnostics;
            }

            return List.of();
        };
    }

    @Override
    public Function<Object, String> getKindProvider() {
        return object -> {
            String kind = "Unknown";
            if (object instanceof Diagnostic) {
                Diagnostic diagnostic = (Diagnostic) object;
                switch (diagnostic.getSeverity()) {
                    case org.eclipse.emf.common.util.Diagnostic.ERROR:
                        kind = "Error";
                        break;
                    case org.eclipse.emf.common.util.Diagnostic.WARNING:
                        kind = "Warning";
                        break;
                    case org.eclipse.emf.common.util.Diagnostic.INFO:
                        kind = "Info";
                        break;
                    default:
                        kind = "Unknown";
                        break;
                }
            }
            return kind;
        };
    }

    @Override
    public Function<Object, String> getMessageProvider() {
        return object -> {
            if (object instanceof Diagnostic) {
                Diagnostic diagnostic = (Diagnostic) object;
                return diagnostic.getMessage();
            }
            return "";
        };
    }
}
