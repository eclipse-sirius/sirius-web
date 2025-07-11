/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.diagrams.description;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The label description.
 *
 * @author sbegaudeau
 */
@Immutable
public final class LabelDescription {

    private String id;

    private Function<VariableManager, String> textProvider;

    private Function<VariableManager, LabelStyleDescription> styleDescriptionProvider;

    private LabelDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTextProvider() {
        return this.textProvider;
    }

    public static Builder newLabelDescription(String id) {
        return new Builder(id);
    }

    public Function<VariableManager, LabelStyleDescription> getStyleDescriptionProvider() {
        return this.styleDescriptionProvider;
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create a new label description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> textProvider;

        private Function<VariableManager, LabelStyleDescription> styleDescriptionProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder textProvider(Function<VariableManager, String> textProvider) {
            this.textProvider = Objects.requireNonNull(textProvider);
            return this;
        }

        public Builder styleDescriptionProvider(Function<VariableManager, LabelStyleDescription> styleDescriptionProvider) {
            this.styleDescriptionProvider = Objects.requireNonNull(styleDescriptionProvider);
            return this;
        }

        public LabelDescription build() {
            LabelDescription labelDescription = new LabelDescription();
            labelDescription.id = Objects.requireNonNull(this.id);
            labelDescription.textProvider = Objects.requireNonNull(this.textProvider);
            labelDescription.styleDescriptionProvider = Objects.requireNonNull(this.styleDescriptionProvider);
            return labelDescription;
        }
    }
}
