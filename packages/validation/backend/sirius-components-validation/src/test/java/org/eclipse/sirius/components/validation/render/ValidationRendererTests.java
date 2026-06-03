/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.components.validation.render;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.validation.Validation;
import org.eclipse.sirius.components.validation.ValidationDiagnostic;
import org.eclipse.sirius.components.validation.components.ValidationComponent;
import org.eclipse.sirius.components.validation.components.ValidationComponentProps;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests of the validation renderer.
 */
public class ValidationRendererTests {

    @Test
    @DisplayName("Given a fixable diagnostic, when rendering the validation, then the diagnostic should keep its fixable flag")
    public void givenAFixableDiagnosticWhenRenderingTheValidationThenTheDiagnosticShouldKeepItsFixableFlag() {
        Object diagnostic = new Object();

        ValidationDescription validationDescription = ValidationDescription.newValidationDescription("validation-description")
                .label("Validation")
                .canCreatePredicate(variableManager -> false)
                .targetObjectIdProvider(variableManager -> "target-object-id")
                .diagnosticsProvider(variableManager -> List.of(diagnostic))
                .kindProvider(object -> "Error")
                .messageProvider(object -> "The message")
                .fixablePredicate(object -> true)
                .iconURLsProvider(variableManager -> List.of())
                .build();

        VariableManager variableManager = new VariableManager();
        ValidationComponentProps validationComponentProps = new ValidationComponentProps(variableManager, validationDescription, Optional.empty());

        Element element = new Element(ValidationComponent.class, validationComponentProps);
        Validation validation = new ValidationRenderer().render(element);

        assertThat(validation).isNotNull();
        assertThat(validation.getDiagnostics()).hasSize(1);

        ValidationDiagnostic validationDiagnostic = validation.getDiagnostics().get(0);
        assertThat(validationDiagnostic.getKind()).isEqualTo("Error");
        assertThat(validationDiagnostic.getMessage()).isEqualTo("The message");
        assertThat(validationDiagnostic.getFixable()).isTrue();
    }
}
