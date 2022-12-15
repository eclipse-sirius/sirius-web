/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.collaborative.forms.api.IFormInput;

/**
 * Represents a request for code completion inside a text field which supports it.
 *
 * @author pcdavid
 */
public class CompletionRequestInput implements IFormInput {
    private UUID id;

    private String editingContextId;

    private String representationId;

    private String widgetId;

    private String currentText;

    private int cursorPosition;

    public CompletionRequestInput() {
        // Used by Jackson
    }

    public CompletionRequestInput(UUID id, String editingContextId, String representationId, String widgetId, String currentText, int cursorPosition) {
        this.id = Objects.requireNonNull(id);
        this.editingContextId = Objects.requireNonNull(editingContextId);
        this.representationId = Objects.requireNonNull(representationId);
        this.widgetId = Objects.requireNonNull(widgetId);
        this.currentText = Objects.requireNonNull(currentText);
        this.cursorPosition = cursorPosition;
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    public String getEditingContextId() {
        return this.editingContextId;
    }

    @Override
    public String getRepresentationId() {
        return this.representationId;
    }

    public String getWidgetId() {
        return this.widgetId;
    }

    public String getCurrentText() {
        return this.currentText;
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, editingContextId: {2}, representationId: {3}, widgetId: {4}, currentText: {5}, cursorPosition: {6}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.editingContextId, this.representationId, this.widgetId, this.currentText, this.cursorPosition);
    }
}
