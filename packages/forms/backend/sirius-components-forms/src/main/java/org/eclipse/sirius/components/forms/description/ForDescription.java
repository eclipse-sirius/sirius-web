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
package org.eclipse.sirius.components.forms.description;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The description used to create multiple elements at once.
 *
 * @author sbegaudeau
 */
@Immutable
public final class ForDescription extends AbstractControlDescription {

    private String iterator;

    private Function<VariableManager, List<?>> iterableProvider;

    private List<AbstractControlDescription> controlDescriptions;

    private ForDescription() {
        // Prevent instantiation
    }

    public String getIterator() {
        return this.iterator;
    }

    public Function<VariableManager, List<?>> getIterableProvider() {
        return this.iterableProvider;
    }

    public List<AbstractControlDescription> getControlDescriptions() {
        return this.controlDescriptions;
    }

    public static Builder newForDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, iterator: {2}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.getId(), this.iterator);
    }

    /**
     * The builder used to create the for description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private String iterator;

        private Function<VariableManager, List<?>> iterableProvider;

        private List<AbstractControlDescription> controlDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder iterator(String iterator) {
            this.iterator = Objects.requireNonNull(iterator);
            return this;
        }

        public Builder iterableProvider(Function<VariableManager, List<?>> iterableProvider) {
            this.iterableProvider = Objects.requireNonNull(iterableProvider);
            return this;
        }

        public Builder controlDescriptions(List<AbstractControlDescription> controlDescriptions) {
            this.controlDescriptions = Objects.requireNonNull(controlDescriptions);
            return this;
        }

        public ForDescription build() {
            ForDescription forDescription = new ForDescription();
            forDescription.id = Objects.requireNonNull(this.id);
            forDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            forDescription.iterator = Objects.requireNonNull(this.iterator);
            forDescription.iterableProvider = Objects.requireNonNull(this.iterableProvider);
            forDescription.controlDescriptions = Objects.requireNonNull(this.controlDescriptions);
            return forDescription;
        }
    }
}
