/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The label style description.
 *
 * @author sbegaudeau
 */
@Immutable
public final class LabelStyleDescription {

    private Function<VariableManager, String> colorProvider;

    private Function<VariableManager, Integer> fontSizeProvider;

    private Function<VariableManager, Boolean> boldProvider;

    private Function<VariableManager, Boolean> italicProvider;

    private Function<VariableManager, Boolean> underlineProvider;

    private Function<VariableManager, Boolean> strikeThroughProvider;

    private Function<VariableManager, List<String>> iconURLProvider;

    private LabelStyleDescription() {
        // Prevent instantiation
    }

    public static Builder newLabelStyleDescription() {
        return new Builder();
    }

    public Function<VariableManager, Integer> getFontSizeProvider() {
        return this.fontSizeProvider;
    }

    public Function<VariableManager, String> getColorProvider() {
        return this.colorProvider;
    }

    public Function<VariableManager, Boolean> getBoldProvider() {
        return this.boldProvider;
    }

    public Function<VariableManager, Boolean> getItalicProvider() {
        return this.italicProvider;
    }

    public Function<VariableManager, Boolean> getUnderlineProvider() {
        return this.underlineProvider;
    }

    public Function<VariableManager, Boolean> getStrikeThroughProvider() {
        return this.strikeThroughProvider;
    }

    public Function<VariableManager, List<String>> getIconURLProvider() {
        return this.iconURLProvider;
    }

    /**
     * The builder used to create a new label description.
     *
     * @author hmarchadour
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private Function<VariableManager, String> colorProvider;

        private Function<VariableManager, Integer> fontSizeProvider;

        private Function<VariableManager, Boolean> boldProvider;

        private Function<VariableManager, Boolean> italicProvider;

        private Function<VariableManager, Boolean> underlineProvider;

        private Function<VariableManager, Boolean> strikeThroughProvider;

        private Function<VariableManager, List<String>> iconURLProvider;

        private Builder() {
        }

        public Builder colorProvider(Function<VariableManager, String> colorProvider) {
            this.colorProvider = Objects.requireNonNull(colorProvider);
            return this;
        }

        public Builder fontSizeProvider(Function<VariableManager, Integer> fontSizeProvider) {
            this.fontSizeProvider = Objects.requireNonNull(fontSizeProvider);
            return this;
        }

        public Builder boldProvider(Function<VariableManager, Boolean> boldProvider) {
            this.boldProvider = boldProvider;
            return this;
        }

        public Builder italicProvider(Function<VariableManager, Boolean> italicProvider) {
            this.italicProvider = italicProvider;
            return this;
        }

        public Builder underlineProvider(Function<VariableManager, Boolean> underlineProvider) {
            this.underlineProvider = underlineProvider;
            return this;
        }

        public Builder strikeThroughProvider(Function<VariableManager, Boolean> strikeThroughProvider) {
            this.strikeThroughProvider = strikeThroughProvider;
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, List<String>> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public LabelStyleDescription build() {
            LabelStyleDescription styleDescription = new LabelStyleDescription();
            styleDescription.colorProvider = Objects.requireNonNull(this.colorProvider);
            styleDescription.fontSizeProvider = Objects.requireNonNull(this.fontSizeProvider);
            styleDescription.boldProvider = Objects.requireNonNull(this.boldProvider);
            styleDescription.italicProvider = Objects.requireNonNull(this.italicProvider);
            styleDescription.strikeThroughProvider = Objects.requireNonNull(this.strikeThroughProvider);
            styleDescription.underlineProvider = Objects.requireNonNull(this.underlineProvider);
            styleDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            return styleDescription;
        }

    }
}
