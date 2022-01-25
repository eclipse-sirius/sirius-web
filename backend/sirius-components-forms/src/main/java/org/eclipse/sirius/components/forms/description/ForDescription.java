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

    private Function<VariableManager, List<Object>> iterableProvider;

    private List<IfDescription> ifDescriptions;

    private ForDescription() {
        // Prevent instantiation
    }

    public String getIterator() {
        return this.iterator;
    }

    public Function<VariableManager, List<Object>> getIterableProvider() {
        return this.iterableProvider;
    }

    public List<IfDescription> getIfDescriptions() {
        return this.ifDescriptions;
    }

    public static Builder newForDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, iterator: {2}'}'"; //$NON-NLS-1$
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

        private String iterator;

        private Function<VariableManager, List<Object>> iterableProvider;

        private List<IfDescription> ifDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder iterator(String iterator) {
            this.iterator = Objects.requireNonNull(iterator);
            return this;
        }

        public Builder iterableProvider(Function<VariableManager, List<Object>> iterableProvider) {
            this.iterableProvider = Objects.requireNonNull(iterableProvider);
            return this;
        }

        public Builder ifDescriptions(List<IfDescription> ifDescriptions) {
            this.ifDescriptions = Objects.requireNonNull(ifDescriptions);
            return this;
        }

        public ForDescription build() {
            ForDescription forDescription = new ForDescription();
            forDescription.id = Objects.requireNonNull(this.id);
            forDescription.iterator = Objects.requireNonNull(this.iterator);
            forDescription.iterableProvider = Objects.requireNonNull(this.iterableProvider);
            forDescription.ifDescriptions = Objects.requireNonNull(this.ifDescriptions);
            return forDescription;
        }
    }
}
