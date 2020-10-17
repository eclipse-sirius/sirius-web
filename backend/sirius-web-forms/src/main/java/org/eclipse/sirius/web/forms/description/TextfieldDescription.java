/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.forms.description;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.representations.Status;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The description of the textfield widget.
 *
 * @author sbegaudeau
 */
@Immutable
public final class TextfieldDescription extends AbstractWidgetDescription {
    private Function<VariableManager, String> idProvider;

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, String> valueProvider;

    private BiFunction<VariableManager, String, Status> newValueHandler;

    private TextfieldDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, String> getIdProvider() {
        return this.idProvider;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, String> getValueProvider() {
        return this.valueProvider;
    }

    public BiFunction<VariableManager, String, Status> getNewValueHandler() {
        return this.newValueHandler;
    }

    public static Builder newTextfieldDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
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

        private Function<VariableManager, String> valueProvider;

        private BiFunction<VariableManager, String, Status> newValueHandler;

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

        public Builder valueProvider(Function<VariableManager, String> valueProvider) {
            this.valueProvider = Objects.requireNonNull(valueProvider);
            return this;
        }

        public Builder newValueHandler(BiFunction<VariableManager, String, Status> newValueHandler) {
            this.newValueHandler = Objects.requireNonNull(newValueHandler);
            return this;
        }

        public TextfieldDescription build() {
            TextfieldDescription textfieldDescription = new TextfieldDescription();
            textfieldDescription.id = Objects.requireNonNull(this.id);
            textfieldDescription.idProvider = Objects.requireNonNull(this.idProvider);
            textfieldDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            textfieldDescription.valueProvider = Objects.requireNonNull(this.valueProvider);
            textfieldDescription.newValueHandler = Objects.requireNonNull(this.newValueHandler);
            return textfieldDescription;
        }
    }
}
