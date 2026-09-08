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
package org.eclipse.sirius.components.collaborative.forms;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.Form;
import org.eclipse.sirius.components.forms.description.FormDescription;

/**
 * Used to keep the form representation and its rendering inputs in memory.
 *
 * @author sbegaudeau
 */
public record FormContext(String id, Form form, FormDescription formDescription, Object object, List<Object> selection) {

    public FormContext {
        Objects.requireNonNull(id);
        Objects.requireNonNull(formDescription);
        Objects.requireNonNull(object);
        selection = List.copyOf(selection);
    }

    public FormContext withForm(Form newForm) {
        return new FormContext(this.id, newForm, this.formDescription, this.object, this.selection);
    }
}
