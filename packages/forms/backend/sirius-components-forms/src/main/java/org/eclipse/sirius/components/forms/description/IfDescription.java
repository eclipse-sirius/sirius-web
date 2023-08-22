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
 * The description used to conditionally create an element.
 *
 * @author sbegaudeau
 */
@Immutable
public final class IfDescription extends AbstractControlDescription {

    private Function<VariableManager, Boolean> predicate;

    private List<AbstractControlDescription> controlDescriptions;

    private IfDescription() {
        // Prevent instantiation
    }

    public Function<VariableManager, Boolean> getPredicate() {
        return this.predicate;
    }

    public List<AbstractControlDescription> getControlDescriptions() {
        return this.controlDescriptions;
    }

    public static Builder newIfDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'";
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the if description.
     *
     * @author sbegaudeau
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private String id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, Boolean> predicate;

        private List<AbstractControlDescription> controlDescriptions;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder predicate(Function<VariableManager, Boolean> predicate) {
            this.predicate = Objects.requireNonNull(predicate);
            return this;
        }

        public Builder controlDescriptions(List<AbstractControlDescription> controlDescriptions) {
            this.controlDescriptions = Objects.requireNonNull(controlDescriptions);
            return this;
        }

        public IfDescription build() {
            IfDescription ifDescription = new IfDescription();
            ifDescription.id = Objects.requireNonNull(this.id);
            ifDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            ifDescription.predicate = Objects.requireNonNull(this.predicate);
            ifDescription.controlDescriptions = Objects.requireNonNull(this.controlDescriptions);
            return ifDescription;
        }
    }
}
