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
package org.eclipse.sirius.components.dynamicdialogs.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description of the text field widget for dynamic dialog.
 *
 * @author lfasani
 */
@Immutable
public final class DTextFieldWidgetDescription extends AbstractDWidgetDescription {

    private DTextFieldWidgetDescription() {
        // Prevent instantiation
    }

    @Override
    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public static Builder newDTextFieldWidgetDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId());
    }

    /**
     * Builder used to create the text field description for dynamic dialog.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> labelProvider;

        private DWidgetOutputDescription output;

        private List<DWidgetOutputDescription> inputs;

        private Function<VariableManager, String> initialValueProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder id(Function<VariableManager, String> idProvider) {
            this.id = Objects.requireNonNull(this.id);
            return this;
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder inputs(List<DWidgetOutputDescription> inputs) {
            this.inputs = Objects.requireNonNull(inputs);
            return this;
        }

        public Builder output(DWidgetOutputDescription output) {
            this.output = Objects.requireNonNull(output);
            return this;
        }

        public Builder initialValueProvider(Function<VariableManager, String> initialValueProvider) {
            this.initialValueProvider = Objects.requireNonNull(initialValueProvider);
            return this;
        }

        public DTextFieldWidgetDescription build() {
            DTextFieldWidgetDescription description = new DTextFieldWidgetDescription();
            description.id = Objects.requireNonNull(this.id);
            description.labelProvider = Objects.requireNonNull(this.labelProvider);
            description.inputs = Objects.requireNonNull(this.inputs);
            description.output = Objects.requireNonNull(this.output);
            description.initialValueProvider = Objects.requireNonNull(this.initialValueProvider);
            return description;
        }

    }
}
