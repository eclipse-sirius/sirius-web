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
package org.eclipse.sirius.components.tables.components;

import java.util.Objects;
import java.util.UUID;
import java.util.function.BiFunction;

import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the Checkbox-based cell component.
 *
 * @author arichard
 */
public class CheckboxCellComponentProps implements IProps {

    private final VariableManager variableManager;

    private final BiFunction<VariableManager, String, Object> cellValueProvider;

    private final String featureName;

    private final UUID cellId;

    private final UUID parentLineId;

    private final UUID columnId;

    public CheckboxCellComponentProps(VariableManager variableManager, BiFunction<VariableManager, String, Object> cellValueProvider, String featureName, UUID cellId, UUID parentLineId,
            UUID columnId) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.cellValueProvider = Objects.requireNonNull(cellValueProvider);
        this.featureName = Objects.requireNonNull(featureName);
        this.cellId = Objects.requireNonNull(cellId);
        this.parentLineId = Objects.requireNonNull(parentLineId);
        this.columnId = Objects.requireNonNull(columnId);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public BiFunction<VariableManager, String, Object> getCellValueProvider() {
        return this.cellValueProvider;
    }

    public String getFeatureName() {
        return this.featureName;
    }

    public UUID getCellId() {
        return this.cellId;
    }

    public UUID getParentLineId() {
        return this.parentLineId;
    }

    public UUID getColumnId() {
        return this.columnId;
    }
}
