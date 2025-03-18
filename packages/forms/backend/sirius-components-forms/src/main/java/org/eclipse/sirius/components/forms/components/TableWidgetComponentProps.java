/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.forms.description.TableWidgetDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.ICustomCellDescriptor;

/**
 * The props of the table component.
 *
 * @author lfasani
 */
public class TableWidgetComponentProps implements IProps {

    private final VariableManager variableManager;

    private final TableWidgetDescription tableWidgetDescription;

    private final List<ICustomCellDescriptor> customCellDescriptors;

    public TableWidgetComponentProps(VariableManager variableManager, TableWidgetDescription tableWidgetDescription, List<ICustomCellDescriptor> customCellDescriptors) {
        this.variableManager = Objects.requireNonNull(variableManager);
        this.tableWidgetDescription = Objects.requireNonNull(tableWidgetDescription);
        this.customCellDescriptors = Objects.requireNonNull(customCellDescriptors);
    }

    public VariableManager getVariableManager() {
        return this.variableManager;
    }

    public TableWidgetDescription getTableDescription() {
        return this.tableWidgetDescription;
    }

    public List<ICustomCellDescriptor> getCustomCellDescriptors() {
        return this.customCellDescriptors;
    }

}
