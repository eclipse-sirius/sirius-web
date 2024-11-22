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
package org.eclipse.sirius.web.papaya.representations.table;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.descriptions.TableDescription;

/**
 * Used to compute the label of the table.
 *
 * @author sbegaudeau
 */
public class TableLabelProvider implements Function<VariableManager, String> {

    private final ILabelService labelService;

    public TableLabelProvider(ILabelService labelService) {
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public String apply(VariableManager variableManager) {
        return variableManager.get(TableDescription.LABEL, String.class)
                .orElseGet(() -> variableManager.get(VariableManager.SELF, Object.class)
                        .map(this.labelService::getLabel)
                        .orElse(null));
    }
}
