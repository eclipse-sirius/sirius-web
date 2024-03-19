/*******************************************************************************
 * Copyright (c) 2024, 2024 Obeo.
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
package org.eclipse.sirius.components.forms.tests.navigation;

import java.util.Objects;

import org.eclipse.sirius.components.forms.Form;

/**
 * Entry point of the navigation in a form representation.
 *
 * @author sbegaudeau
 */
public class FormNavigator {

    private final Form form;

    public FormNavigator(Form form) {
        this.form = Objects.requireNonNull(form);
    }

    public PageNavigator page(String label) {
        return this.form.getPages().stream()
                .filter(page -> page.getLabel().equals(label))
                .findFirst()
                .map(PageNavigator::new)
                .orElseThrow(() -> new IllegalArgumentException("No page found with the given label \"" + label + "\""));
    }
}
