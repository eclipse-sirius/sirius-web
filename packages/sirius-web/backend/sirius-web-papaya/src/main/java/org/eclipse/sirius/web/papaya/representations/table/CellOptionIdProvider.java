/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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

import org.eclipse.emf.ecore.EEnumLiteral;
import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.core.api.ILabelService;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.components.tables.components.SelectCellComponent;

/**
 * Used to compute the identifier of the option.
 *
 * @author sbegaudeau
 */
public class CellOptionIdProvider implements Function<VariableManager, String> {

    private final IIdentityService identityService;

    private final ILabelService labelService;

    public CellOptionIdProvider(IIdentityService identityService, ILabelService labelService) {
        this.identityService = Objects.requireNonNull(identityService);
        this.labelService = Objects.requireNonNull(labelService);
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(SelectCellComponent.CANDIDATE_VARIABLE);
        if (candidate instanceof EEnumLiteral) {
            return this.labelService.getStyledLabel(candidate).toString();
        }
        return this.identityService.getId(candidate);
    }
}
