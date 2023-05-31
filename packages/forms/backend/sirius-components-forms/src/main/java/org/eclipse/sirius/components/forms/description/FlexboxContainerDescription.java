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
import org.eclipse.sirius.components.forms.FlexDirection;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the flexbox container widget.
 *
 * @author arichard
 */
@Immutable
public final class FlexboxContainerDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private FlexDirection flexDirection;

    private List<AbstractWidgetDescription> children;

    private FlexboxContainerDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public FlexDirection getFlexDirection() {
        return this.flexDirection;
    }

    public List<AbstractWidgetDescription> getChildren() {
        return this.children;
    }

    public static Builder newFlexboxContainerDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the flexbox container description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> idProvider;

        private Function<VariableManager, String> labelProvider;

        private FlexDirection flexDirection;

        private List<AbstractWidgetDescription> children;

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

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder flexDirection(FlexDirection flexDirection) {
            this.flexDirection = Objects.requireNonNull(flexDirection);
            return this;
        }

        public Builder children(List<AbstractWidgetDescription> children) {
            this.children = Objects.requireNonNull(children);
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

        public FlexboxContainerDescription build() {
            FlexboxContainerDescription flexboxContainerDescription = new FlexboxContainerDescription();
            flexboxContainerDescription.id = Objects.requireNonNull(this.id);
            flexboxContainerDescription.idProvider = Objects.requireNonNull(this.idProvider);
            flexboxContainerDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            flexboxContainerDescription.flexDirection = Objects.requireNonNull(this.flexDirection);
            flexboxContainerDescription.children = Objects.requireNonNull(this.children);
            flexboxContainerDescription.diagnosticsProvider = Objects.requireNonNull(this.diagnosticsProvider);
            flexboxContainerDescription.kindProvider = Objects.requireNonNull(this.kindProvider);
            flexboxContainerDescription.messageProvider = Objects.requireNonNull(this.messageProvider);
            flexboxContainerDescription.helpTextProvider = this.helpTextProvider; // Optional on purpose
            return flexboxContainerDescription;
        }

    }
}
