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
import java.util.function.Function;

import org.eclipse.sirius.web.annotations.Immutable;
import org.eclipse.sirius.web.representations.VariableManager;

/**
 * The description of the option which contribute to define widget with multiple choices like Combobox or Radiobutton.
 *
 * @author lfasani
 */
@Immutable
public final class OptionDescription extends AbstractWidgetDescription {

    private Function<VariableManager, String> labelProvider;

    private Function<VariableManager, Boolean> selectedProvider;

    private OptionDescription() {
        // Prevent instantiation
    }

    @Override
    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public Function<VariableManager, Boolean> getSelectedProvider() {
        return this.selectedProvider;
    }

    public static Builder newOptionDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * Builder used to create the option description.
     *
     * @author lfasani
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> labelProvider;

        private Function<VariableManager, Boolean> selectedProvider;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder labelProvider(Function<VariableManager, String> labelProvider) {
            this.labelProvider = Objects.requireNonNull(labelProvider);
            return this;
        }

        public Builder selectedProvider(Function<VariableManager, Boolean> selectedProvider) {
            this.selectedProvider = Objects.requireNonNull(selectedProvider);
            return this;
        }

        public OptionDescription build() {
            OptionDescription textfieldDescription = new OptionDescription();
            textfieldDescription.id = Objects.requireNonNull(this.id);
            textfieldDescription.labelProvider = Objects.requireNonNull(this.labelProvider);
            textfieldDescription.selectedProvider = Objects.requireNonNull(this.selectedProvider);
            return textfieldDescription;
        }
    }
}
