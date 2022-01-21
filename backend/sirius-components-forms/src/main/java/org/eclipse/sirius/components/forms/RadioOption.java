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
package org.eclipse.sirius.components.forms;

import java.text.MessageFormat;
import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;

/**
 * The radio option.
 *
 * @author lfasani
 */
@Immutable
public final class RadioOption {
    private String id;

    private String label;

    private boolean selected;

    private RadioOption() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public boolean isSelected() {
        return this.selected;
    }

    public static Builder newRadioOption(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, selected: {3}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.selected);
    }

    /**
     * The builder used to create the radio option.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private String label;

        private boolean selected;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder selected(boolean selected) {
            this.selected = selected;
            return this;
        }

        public RadioOption build() {
            RadioOption radioOption = new RadioOption();
            radioOption.id = Objects.requireNonNull(this.id);
            radioOption.label = Objects.requireNonNull(this.label);
            radioOption.selected = Objects.requireNonNull(this.selected);
            return radioOption;
        }
    }
}
