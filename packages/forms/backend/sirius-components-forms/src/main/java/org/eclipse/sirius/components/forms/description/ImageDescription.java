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
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the Image widget.
 *
 * @author pcdavid
 */
@Immutable
public final class ImageDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> iconURLProvider;

    private Function<VariableManager, String> urlProvider;

    private Function<VariableManager, String> maxWidthProvider;

    private ImageDescription() {
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

    public Function<VariableManager, String> getUrlProvider() {
        return this.urlProvider;
    }

    public Function<VariableManager, String> getMaxWidthProvider() {
        return this.maxWidthProvider;
    }

    public static Builder newImageDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the Image description.
     *
     * @author pcdavid
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, String> iconURLProvider = variableManager -> null;

        private final Function<VariableManager, Boolean> isReadOnlyProvider = variableManager -> true;

        private Function<VariableManager, String> urlProvider;

        private Function<VariableManager, String> maxWidthProvider = variableManager -> null;

        private Function<VariableManager, List<?>> diagnosticsProvider;

        private Function<Object, String> kindProvider;

        private Function<Object, String> messageProvider;

        private Function<VariableManager, String> helpTextProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder idProvider(Function<VariableManager, String> idProvider) {
            this.idProvider = Objects.requireNonNull(idProvider);
            return this;
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
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

        public Builder urlProvider(Function<VariableManager, String> urlProvider) {
            this.urlProvider = Objects.requireNonNull(urlProvider);
            return this;
        }

        public Builder maxWidthProvider(Function<VariableManager, String> maxWidthProvider) {
            this.maxWidthProvider = Objects.requireNonNull(maxWidthProvider);
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

        public Builder helpTextProvider(Function<VariableManager, String> helpTextProvider) {
            this.helpTextProvider = Objects.requireNonNull(helpTextProvider);
            return this;
        }

        public ImageDescription build() {
            ImageDescription imageDescription = new ImageDescription();
            imageDescription.id = Objects.requireNonNull(this.id);
            imageDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            imageDescription.idProvider = Objects.requireNonNull(this.idProvider);
            imageDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            imageDescription.iconURLProvider = Objects.requireNonNull(this.iconURLProvider);
            imageDescription.isReadOnlyProvider = Objects.requireNonNull(this.isReadOnlyProvider);
            imageDescription.urlProvider = Objects.requireNonNull(this.urlProvider);
            imageDescription.maxWidthProvider = Objects.requireNonNull(this.maxWidthProvider);
            imageDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            imageDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            imageDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            imageDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return imageDescription;
        }
    }
}
