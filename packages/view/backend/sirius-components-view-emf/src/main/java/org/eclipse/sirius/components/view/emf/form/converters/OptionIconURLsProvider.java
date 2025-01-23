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
package org.eclipse.sirius.components.view.emf.form.converters;

import java.util.List;
import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IObjectService;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the icon of an option.
 *
 * @author sbegaudeau
 */
public class OptionIconURLsProvider implements Function<VariableManager, List<String>> {

    private final IObjectService objectService;

    public OptionIconURLsProvider(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public List<String> apply(VariableManager variableManager) {
        return variableManager.get(SelectComponent.CANDIDATE_VARIABLE, Object.class)
                .map(this.objectService::getImagePath)
                .orElse(List.of());
    }
}
