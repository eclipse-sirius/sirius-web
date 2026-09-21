/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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

package org.eclipse.sirius.components.widget.reference;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Describes the presence of a clear button on a reference widget.
 *
 * @author tgiraudet
 */
@Immutable
public final class ReferenceWidgetClearButtonDescription {

    private Function<VariableManager, Boolean> preconditionProvider;

    private ReferenceWidgetClearButtonDescription() {
        // Prevent instantiation
    }

    public static Builder newReferenceWidgetClearButtonDescription() {
        return new Builder();
    }

    public Function<VariableManager, Boolean> getPreconditionProvider() {
        return this.preconditionProvider;
    }

    /**
     * Builder used to create the reference widget clear button description.
     *
     * @author tgiraudet
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private Function<VariableManager, Boolean> preconditionProvider = variableManager -> true;

        private Builder() {
            // Prevent instantiation
        }

        public Builder preconditionProvider(Function<VariableManager, Boolean> preconditionProvider) {
            this.preconditionProvider = Objects.requireNonNull(preconditionProvider);
            return this;
        }

        public ReferenceWidgetClearButtonDescription build() {
            var referenceWidgetClearButtonDescription = new ReferenceWidgetClearButtonDescription();
            referenceWidgetClearButtonDescription.preconditionProvider = this.preconditionProvider;
            return referenceWidgetClearButtonDescription;
        }
    }

}
