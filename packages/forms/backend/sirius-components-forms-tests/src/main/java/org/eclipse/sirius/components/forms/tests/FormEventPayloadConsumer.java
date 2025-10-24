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
package org.eclipse.sirius.components.forms.tests;

import static org.assertj.core.api.Assertions.fail;

import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.forms.dto.FormRefreshedEventPayload;
import org.eclipse.sirius.components.forms.Form;

/**
 * Used to handle the form event payload.
 *
 * @author sbegaudeau
 */
public class FormEventPayloadConsumer {
    public static Consumer<Object> assertRefreshedFormThat(Consumer<Form> consumer) {
        return object -> Optional.of(object)
                .filter(FormRefreshedEventPayload.class::isInstance)
                .map(FormRefreshedEventPayload.class::cast)
                .map(FormRefreshedEventPayload::form)
                .ifPresentOrElse(consumer, () -> fail("Missing form"));
    }
}
