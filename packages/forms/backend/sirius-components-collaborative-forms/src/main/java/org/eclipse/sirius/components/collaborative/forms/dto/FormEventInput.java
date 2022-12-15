/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IInput;

/**
 * The input of the form event subscription.
 *
 * @author sbegaudeau
 * @author hmarchadour
 */
public final class FormEventInput implements IInput {

    private UUID id;

    private String editingContextId;

    private String formId;

    public FormEventInput() {
        // Used by Jackson
    }

    public FormEventInput(UUID id, String editingContextId, String formId) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.formId = Objects.requireNonNull(formId);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    public String getFormId() {
        return this.formId;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, formId: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.formId);
    }
}
