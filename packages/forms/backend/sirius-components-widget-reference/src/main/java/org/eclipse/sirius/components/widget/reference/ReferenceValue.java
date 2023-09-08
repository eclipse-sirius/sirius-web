/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
import java.util.function.Function;
import java.util.function.Supplier;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.ClickEventKind;
import org.eclipse.sirius.components.representations.IStatus;

/**
 * Represents a single value for a reference. Multi-valued references can have multiple of these.
 *
 * @author pcdavid
 */
@Immutable
public final class ReferenceValue {

    private String id;

    private String label;

    private String kind;

    private String iconURL;

    private Function<ClickEventKind, IStatus> clickHandler;

    private Supplier<IStatus> removeHandler;

    private ReferenceValue() {
        // Prevent instantiation
    }

    public static Builder newReferenceValue(String id) {
        return new Builder(id);
    }

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getKind() {
        return this.kind;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public Function<ClickEventKind, IStatus> getClickHandler() {
        return this.clickHandler;
    }

    public Supplier<IStatus> getRemoveHandler() {
        return this.removeHandler;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, label: {2}, kind: {3}, imageURL: {4}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.label, this.kind, this.iconURL);
    }

    /**
     * The builder used to create the reference value.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private String label;

        private String kind;

        private String iconURL;

        private Function<ClickEventKind, IStatus> clickHandler;

        private Supplier<IStatus> removeHandler;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder kind(String kind) {
            this.kind = Objects.requireNonNull(kind);
            return this;
        }

        public Builder iconURL(String iconURL) {
            this.iconURL = Objects.requireNonNull(iconURL);
            return this;
        }

        public Builder clickHandler(Function<ClickEventKind, IStatus> clickHandler) {
            this.clickHandler = Objects.requireNonNull(clickHandler);
            return this;
        }

        public Builder removeHandler(Supplier<IStatus> removeHandler) {
            this.removeHandler = Objects.requireNonNull(removeHandler);
            return this;
        }

        public ReferenceValue build() {
            ReferenceValue referenceValue = new ReferenceValue();
            referenceValue.id = Objects.requireNonNull(this.id);
            referenceValue.label = Objects.requireNonNull(this.label);
            referenceValue.kind = Objects.requireNonNull(this.kind);
            referenceValue.iconURL = this.iconURL;
            referenceValue.clickHandler = this.clickHandler; // Optional on purpose
            referenceValue.removeHandler = this.removeHandler; // Optional on purpose
            return referenceValue;
        }
    }
}
