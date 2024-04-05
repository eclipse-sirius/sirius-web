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
package org.eclipse.sirius.web.services.validation;

import java.util.List;
import java.util.function.Predicate;

import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.sirius.components.collaborative.validation.api.IValidationDescriptionProvider;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.springframework.stereotype.Service;

/**
 * This class is used to provide the description of the validation representation.
 *
 * @author gcoutable
 */
@Service
public class ValidationDescriptionProvider implements IValidationDescriptionProvider {

    public static final String LABEL = "Validation";

    public static final String DESCRIPTION_ID = "validation_description";

    private final IValidationService validationService;

    public ValidationDescriptionProvider(IValidationService validationService) {
        this.validationService = validationService;
    }

    @Override
    public ValidationDescription getDescription() {
        // This predicate will NOT be used while creating the explorer but we don't want to see the description of the
        // explorer in the list of representations that can be created. Thus, we will return false all the time.
        Predicate<VariableManager> canCreatePredicate = variableManager -> false;

        // @formatter:off
        return ValidationDescription.newValidationDescription(DESCRIPTION_ID)
                .label(LABEL)
                .canCreatePredicate(canCreatePredicate)
                .diagnosticsProvider(this::getDiagnosticsProvider)
                .kindProvider(this::kindProvider)
                .messageProvider(this::messageProvider)
                .build();
        // @formatter:on
    }

    private List<Object> getDiagnosticsProvider(VariableManager variableManager) {
        var optionaEditingContext = variableManager.get(IEditingContext.EDITING_CONTEXT, IEditingContext.class);

        // @formatter:off
        return optionaEditingContext
                .map(this.validationService::validate)
                .orElseGet(List::of);
        // @formatter:on
    }

    private String kindProvider(Object object) {
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
    }

    private String messageProvider(Object object) {
        if (object instanceof Diagnostic diagnostic) {
            return diagnostic.getMessage();
        }
        return "";
    }

}
