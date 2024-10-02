/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.tables.descriptions;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;
import java.util.function.Function;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IStatus;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 *
 * The description of the line.
 *
 * @author arichard
 */
@Immutable
public final class LineDescription {

    private UUID id;

    private Function<VariableManager, String> targetObjectIdProvider;

    private Function<VariableManager, List<Object>> semanticElementsProvider;

    private BiFunction<VariableManager, Object, IStatus> newCellValueHandler;

    public UUID getId() {
        return this.id;
    }

    public Function<VariableManager, String> getTargetObjectIdProvider() {
        return this.targetObjectIdProvider;
    }

    public Function<VariableManager, List<Object>> getSemanticElementsProvider() {
        return this.semanticElementsProvider;
    }

    public BiFunction<VariableManager, Object, IStatus> getNewCellValueHandler() {
        return this.newCellValueHandler;
    }

    public static Builder newLineDescription(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id);
    }

    /**
     * The builder used to create the line description.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private Function<VariableManager, String> targetObjectIdProvider;

        private Function<VariableManager, List<Object>> semanticElementsProvider;

        private BiFunction<VariableManager, Object, IStatus> newCellValueHandler;

        public Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder targetObjectIdProvider(Function<VariableManager, String> targetObjectIdProvider) {
            this.targetObjectIdProvider = Objects.requireNonNull(targetObjectIdProvider);
            return this;
        }

        public Builder semanticElementsProvider(Function<VariableManager, List<Object>> semanticElementsProvider) {
            this.semanticElementsProvider = Objects.requireNonNull(semanticElementsProvider);
            return this;
        }

        public Builder newCellValueHandler(BiFunction<VariableManager, Object, IStatus> newCellValueHandler) {
            this.newCellValueHandler = Objects.requireNonNull(newCellValueHandler);
            return this;
        }

        public LineDescription build() {
            LineDescription lineDescription = new LineDescription();
            lineDescription.id = Objects.requireNonNull(this.id);
            lineDescription.targetObjectIdProvider = Objects.requireNonNull(this.targetObjectIdProvider);
            lineDescription.semanticElementsProvider = Objects.requireNonNull(this.semanticElementsProvider);
            lineDescription.newCellValueHandler = Objects.requireNonNull(this.newCellValueHandler);
            return lineDescription;
        }
    }
}
