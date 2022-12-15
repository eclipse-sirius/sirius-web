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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

/**
 * Represents a completion request for a text field.
 *
 * @author pcdavid
 */
public final class CompletionRequest {
    /**
     * The name of the variable used to pass the current text value to the completion provider implementation.
     */
    public static final String CURRENT_TEXT = "currentText";

    /**
     * The name of the variable used to pass the cursor position to the completion provider implementation.
     */
    public static final String CURSOR_POSITION = "cursorPosition";

    private final String currentText;

    private final int cursorPosition;

    public CompletionRequest(String currentText, int cursorPosition) {
        this.currentText = Objects.requireNonNull(currentText);
        this.cursorPosition = Objects.checkIndex(cursorPosition, currentText.length() + 1);
    }

    public String getCurrentText() {
        return this.currentText;
    }

    public int getCursorPosition() {
        return this.cursorPosition;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'currentText: {1}, cursorPosition: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.currentText, this.cursorPosition);
    }

}
