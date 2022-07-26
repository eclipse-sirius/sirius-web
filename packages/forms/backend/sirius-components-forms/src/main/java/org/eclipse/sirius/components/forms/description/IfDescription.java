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
public final class IfDescription {
    private String id;

    private Function<VariableManager, Boolean> predicate;

    private AbstractWidgetDescription widgetDescription;

    private IfDescription() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, Boolean> getPredicate() {
        return this.predicate;
    }

    public AbstractWidgetDescription getWidgetDescription() {
        return this.widgetDescription;
    }

    public static Builder newIfDescription(String id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
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

        private Function<VariableManager, Boolean> predicate;

        private AbstractWidgetDescription widgetDescription;

        private Builder(String id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder predicate(Function<VariableManager, Boolean> predicate) {
            this.predicate = Objects.requireNonNull(predicate);
            return this;
        }

        public Builder widgetDescription(AbstractWidgetDescription widgetDescription) {
            this.widgetDescription = Objects.requireNonNull(widgetDescription);
            return this;
        }

        public IfDescription build() {
            IfDescription ifDescription = new IfDescription();
            ifDescription.id = Objects.requireNonNull(this.id);
            ifDescription.predicate = Objects.requireNonNull(this.predicate);
            ifDescription.widgetDescription = Objects.requireNonNull(this.widgetDescription);
            return ifDescription;
        }
    }
}
