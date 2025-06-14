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

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.components.core.api.IIdentityService;
import org.eclipse.sirius.components.forms.components.SelectComponent;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Used to compute the id of an option.
 *
 * @author sbegaudeau
 */
public class OptionIdProvider implements Function<VariableManager, String> {

    private final IIdentityService identityService;

    public OptionIdProvider(IIdentityService identityService) {
        this.identityService = Objects.requireNonNull(identityService);
    }

    @Override
    public String apply(VariableManager variableManager) {
        Object candidate = variableManager.getVariables().get(SelectComponent.CANDIDATE_VARIABLE);
        return Optional.ofNullable(this.identityService.getId(candidate)).orElseGet(() -> Optional.ofNullable(candidate).map(Objects::toString).orElse(""));
    }
}
