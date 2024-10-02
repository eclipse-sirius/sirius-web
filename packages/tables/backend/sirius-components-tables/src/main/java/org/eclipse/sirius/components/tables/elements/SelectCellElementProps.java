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

import org.eclipse.sirius.components.tables.SelectCellOption;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the Select-based cell element.
 *
 * @author arichard
 */
@Immutable
public final class SelectCellElementProps implements IProps {

    public static final String TYPE = "SelectCell"; //$NON-NLS-1$

    private UUID id;

    private UUID parentLineId;

    private UUID columnId;

    private List<SelectCellOption> options;

    private String value;

    private SelectCellElementProps() {
        // Prevent instantiation
    }

    public UUID getId() {
        return this.id;
    }

    public UUID getParentLineId() {
        return this.parentLineId;
    }

    public UUID getColumnId() {
        return this.columnId;
    }

    public List<SelectCellOption> getOptions() {
        return this.options;
    }

    public String getValue() {
        return this.value;
    }

    public static Builder newSelectCellElementProps(UUID id) {
        return new Builder(id);
    }

    @Override
    public String toString() {
        String pattern = "{0} '{'id: {1}, parentLineId: {2}, columnId:{3}, value: {4}, options:{5}'}'"; //$NON-NLS-1$
        return MessageFormat.format(pattern, this.getClass().getSimpleName(), this.id, this.parentLineId, this.columnId, this.value, this.options);
    }

    /**
     * The builder of the cell element props.
     *
     * @author arichard
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {
        private UUID id;

        private UUID parentLineId;

        private UUID columnId;

        private List<SelectCellOption> options;

        private String value;

        private Builder(UUID id) {
            this.id = Objects.requireNonNull(id);
        }

        public Builder parentLineId(UUID parentLineId) {
            this.parentLineId = Objects.requireNonNull(parentLineId);
            return this;
        }

        public Builder columnId(UUID columnId) {
            this.columnId = Objects.requireNonNull(columnId);
            return this;
        }

        public Builder options(List<SelectCellOption> options) {
            this.options = Objects.requireNonNull(options);
            return this;
        }

        public Builder value(String value) {
            this.value = Objects.requireNonNull(value);
            return this;
        }

        public SelectCellElementProps build() {
            SelectCellElementProps cellElementProps = new SelectCellElementProps();
            cellElementProps.id = Objects.requireNonNull(this.id);
            cellElementProps.parentLineId = Objects.requireNonNull(this.parentLineId);
            cellElementProps.columnId = Objects.requireNonNull(this.columnId);
            cellElementProps.options = Objects.requireNonNull(this.options);
            cellElementProps.value = Objects.requireNonNull(this.value);
            return cellElementProps;
        }
    }
}
