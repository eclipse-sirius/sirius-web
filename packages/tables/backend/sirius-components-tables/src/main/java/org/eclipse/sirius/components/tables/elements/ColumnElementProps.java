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
package org.eclipse.sirius.components.tables.elements;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Properties of the column element.
 *
 * @author arichard
 */
@Immutable
public final class ColumnElementProps implements IProps {

    public static final String TYPE = "Column"; //$NON-NLS-1$

    private UUID id;

    private UUID descriptionId;

    private String label;

    private String featureName;

    private BiFunction<VariableManager, String, String> cellTypeProvider;

    private BiFunction<VariableManager, String, Object> cellValueProvider;

    private ColumnElementProps() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getDescriptionId() {
        return this.descriptionId;
    }

    public String getLabel() {
        return this.label;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public BiFunction<VariableManager, String, String> getCellTypeProvider() {
        return this.cellTypeProvider;
    }

    public BiFunction<VariableManager, String, Object> getCellValueProvider() {
        return this.cellValueProvider;
    }

    public static Builder newColumnElementProps(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, descriptionId: {2}, label: {3}, featureName: {4}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.descriptionId, this.label, this.featureName);
    }

    /**
     * The builder of the column element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private UUID descriptionId;

        private String label;

        private String featureName;

        private BiFunction<VariableManager, String, String> cellTypeProvider;

        private BiFunction<VariableManager, String, Object> cellValueProvider;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder descriptionId(UUID descriptionId) {
            this.descriptionId = Objects.requireNonNull(descriptionId);
            return this;
        }

        public Builder label(String label) {
            this.label = Objects.requireNonNull(label);
            return this;
        }

        public Builder featureName(String featureName) {
            this.featureName = Objects.requireNonNull(featureName);
            return this;
        }

        public Builder cellTypeProvider(BiFunction<VariableManager, String, String> cellTypeProvider) {
            this.cellTypeProvider = Objects.requireNonNull(cellTypeProvider);
            return this;
        }

        public Builder cellValueProvider(BiFunction<VariableManager, String, Object> cellValueProvider) {
            this.cellValueProvider = Objects.requireNonNull(cellValueProvider);
            return this;
        }

        public ColumnElementProps build() {
            ColumnElementProps columnElementProps = new ColumnElementProps();
            columnElementProps.id = Objects.requireNonNull(this.id);
            columnElementProps.descriptionId = Objects.requireNonNull(this.descriptionId);
            columnElementProps.label = Objects.requireNonNull(this.label);
            columnElementProps.featureName = Objects.requireNonNull(this.featureName);
            columnElementProps.cellTypeProvider = Objects.requireNonNull(this.cellTypeProvider);
            columnElementProps.cellValueProvider = Objects.requireNonNull(this.cellValueProvider);
            return columnElementProps;
        }
    }

}
