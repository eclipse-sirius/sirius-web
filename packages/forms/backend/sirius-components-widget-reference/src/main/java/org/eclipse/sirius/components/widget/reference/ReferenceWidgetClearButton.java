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

package org.eclipse.sirius.components.widget.reference;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The clear button of a reference widget.
 *
 * @author tgiraudet
 */
@Immutable
public final class ReferenceWidgetClearButton {

    public static final String TYPE = "ReferenceWidgetClearButton";

    private String id;

    private ReferenceWidgetClearButton() {
        // Prevent instantiation
    }

    public static ReferenceWidgetClearButton.Builder newReferenceWidgetClearButton(String id) {
        return new ReferenceWidgetClearButton.Builder(id);
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the ReferenceWidgetClearButton.
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        public Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public ReferenceWidgetClearButton build() {
            var referenceWidgetClearButton = new ReferenceWidgetClearButton();
            referenceWidgetClearButton.id = Objects.requireNonNull(this.id);
            return referenceWidgetClearButton;
        }

    }

}
