/*******************************************************************************
 * Copyright (c) 2024 CEA LIST.
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
package org.eclipse.sirius.components.collaborative.tables;

import java.util.Objects;

import org.eclipse.sirius.components.annotations.Immutable;
import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * This class is used because creating tables requires sending at once multiple parameters.
 *
 * @author frouene
 */
@Immutable
public final class TableCreationParameters {

    private String id;

    private TableDescription tableDescription;

    private IEditingContext editingContext;

    private Object targetObject;

    private TableCreationParameters() {
        // Prevent instantiation
    }

    public String getId() {
        return this.id;
    }

    public TableDescription getTableDescription() {
        return this.tableDescription;
    }

    public IEditingContext getEditingContext() {
        return this.editingContext;
    }

    public Object getTargetObject() {
        return this.targetObject;
    }

    public static Builder newTableCreationParameters(String id) {
        return new Builder(id);
    }

    /**
     * The builder of the table creation parameters.
     *
     * @author frouene
     */
    @SuppressWarnings("checkstyle:HiddenField")
    public static final class Builder {

        private final String id;

        private TableDescription tableDescription;

        private IEditingContext editingContext;

        private Object targetObject;

        private Builder(String id) {
            this.id = id;
        }

        public Builder tableDescription(TableDescription tableDescription) {
            this.tableDescription = Objects.requireNonNull(tableDescription);
            return this;
        }

        public Builder editingContext(IEditingContext editingContext) {
            this.editingContext = Objects.requireNonNull(editingContext);
            return this;
        }

        public Builder targetObject(Object targetObject) {
            this.targetObject = Objects.requireNonNull(targetObject);
            return this;
        }

        public TableCreationParameters build() {
            TableCreationParameters tableCreationParameters = new TableCreationParameters();
            tableCreationParameters.id = Objects.requireNonNull(this.id);
            tableCreationParameters.tableDescription = Objects.requireNonNull(this.tableDescription);
            tableCreationParameters.editingContext = Objects.requireNonNull(this.editingContext);
            tableCreationParameters.targetObject = Objects.requireNonNull(this.targetObject);
            return tableCreationParameters;
        }
    }
}
