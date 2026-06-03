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
package org.eclipse.sirius.web.application.views.validation.services;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.eclipse.sirius.components.core.api.IValidationService;
import org.eclipse.sirius.components.validation.description.ValidationDescription;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Tests of the validation description provider.
 */
public class ValidationDescriptionProviderTests {

    @Test
    @DisplayName("Given the validation description provider, when the description is created, then it should expose a fixable predicate")
    public void givenTheValidationDescriptionProviderWhenTheDescriptionIsCreatedThenItShouldExposeAFixablePredicate() {
        ValidationDescriptionProvider validationDescriptionProvider = new ValidationDescriptionProvider(List.of(new IValidationService.NoOp()));

        ValidationDescription validationDescription = validationDescriptionProvider.getDescription();

        assertThat(validationDescription.getFixablePredicate()).isNotNull();
        assertThat(validationDescription.getFixablePredicate().test(new Object())).isFalse();
    }
}
