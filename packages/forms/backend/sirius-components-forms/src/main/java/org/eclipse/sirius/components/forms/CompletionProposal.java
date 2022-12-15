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
 * Represents a text completion proposal that can be inserted into a text field which supports it.
 *
 * @author pcdavid
 */
public final class CompletionProposal {
    private String description;

    private String textToInsert;

    private int charsToReplace;

    public CompletionProposal(String description, String textToInsert, int charsToReplace) {
        this.description = Objects.requireNonNull(description);
        this.textToInsert = Objects.requireNonNull(textToInsert);
        this.charsToReplace = Objects.requireNonNull(charsToReplace);
    }

    public String getDescription() {
        return this.description;
    }

    public String getTextToInsert() {
        return this.textToInsert;
    }

    public int getCharsToReplace() {
        return this.charsToReplace;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'description: {1}, textToInsert: {2}, charsToReplace: {3}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.description, this.textToInsert, this.charsToReplace);
    }

}
