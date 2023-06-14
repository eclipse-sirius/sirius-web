/*******************************************************************************
 * Copyright (c) 2022, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.forms.ButtonStyle;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the button widget.
 *
 * @author arichard
 */
@Immutable
public final class ButtonDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, String> buttonLabelProvider;

    private Function<VariableManager, String> imageURLProvider;

    private Function<VariableManager, IStatus> pushButtonHandler;

    private Function<VariableManager, ButtonStyle> styleProvider;

    private ButtonDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getIconURLProvider() {
        return this.iconURLProvider;
    }

    public Function<VariableManager, String> getButtonLabelProvider() {
        return this.buttonLabelProvider;
    }

    public Function<VariableManager, String> getImageURLProvider() {
        return this.imageURLProvider;
    }

    public Function<VariableManager, IStatus> getPushButtonHandler() {
        return this.pushButtonHandler;
    }

    public Function<VariableManager, ButtonStyle> getStyleProvider() {
        return this.styleProvider;
    }

    public static Builder newButtonDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the textfield description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> false;

        private Function<VariableManager, String> buttonLabelProvider;

        private Function<VariableManager, String> imageURLProvider;

        private Function<VariableManager, IStatus> pushButtonHandler;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Function<VariableManager, ButtonStyle> styleProvider = vm -> null;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder iconURLProvider(Function<VariableManager, String> iconURLProvider) {
            this.iconURLProvider = Objects.requireNonNull(iconURLProvider);
            return this;
        }

        public Builder isReadOnlyProvider(Function<VariableManager, Boolean> isReadOnlyProvider) {
            this.isReadOnlyProvider = Objects.requireNonNull(isReadOnlyProvider);
            return this;
        }

        public Builder buttonLabelProvider(Function<VariableManager, String> buttonLabelProvider) {
            this.buttonLabelProvider = Objects.requireNonNull(buttonLabelProvider);
            return this;
        }

        public Builder imageURLProvider(Function<VariableManager, String> imageProvider) {
            this.imageURLProvider = Objects.requireNonNull(imageProvider);
            return this;
        }

        public Builder pushButtonHandler(Function<VariableManager, IStatus> pushButtonHandler) {
            this.pushButtonHandler = Objects.requireNonNull(pushButtonHandler);
            return this;
        }

        public Builder diagnosticsProvider(Function<VariableManager, List<?>> diagnosticsProvider) {
            this.diagnosticsProvider = Objects.requireNonNull(diagnosticsProvider);
            return this;
        }

        public Builder kindProvider(Function<Object, String> kindProvider) {
            this.kindProvider = Objects.requireNonNull(kindProvider);
            return this;
        }

        public Builder messageProvider(Function<Object, String> messageProvider) {
            this.messageProvider = Objects.requireNonNull(messageProvider);
            return this;
        }

        public Builder styleProvider(Function<VariableManager, ButtonStyle> styleProvider) {
            this.styleProvider = Objects.requireNonNull(styleProvider);
            return this;
        }

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public ButtonDescription build() {
            ButtonDescription buttonDescription = new ButtonDescription();
            buttonDescription.id = Objects.requireNonNull(this.id);
            buttonDescription.idProvider = Objects.requireNonNull(this.idProvider);
            buttonDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            buttonDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            buttonDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            buttonDescription.buttonLabelProvider = Objects.requireNonNull(this.buttonLabelProvider);
            buttonDescription.imageURLProvider = Objects.requireNonNull(this.imageURLProvider);
            buttonDescription.pushButtonHandler = Objects.requireNonNull(this.pushButtonHandler);
            buttonDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            buttonDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            buttonDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            buttonDescription.styleProvider = Objects.requireNonNull(this.styleProvider);
            buttonDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return buttonDescription;
        }

    }
}
