/*******************************************************************************
 * Copyright (c) 2019, 2021 Obeo.
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
package org.eclipse.sirius.web.compat.services.diagrams;

import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

import org.eclipse.sirius.web.core.api.IObjectService;
import org.eclipse.sirius.web.diagrams.description.DiagramDescription.Builder;
import org.eclipse.sirius.web.interpreter.AQLInterpreter;
import org.eclipse.sirius.web.representations.VariableManager;
import org.springframework.stereotype.Service;

/**
 * Used to populate the targetObjectIdProvider of the diagram description builder.
 *
 * @author sbegaudeau
 */
@Service
public class DiagramDescriptionTargetObjectIdProviderPopulator implements IDiagramDescriptionPopulator {

    private final IObjectService objectService;

    public DiagramDescriptionTargetObjectIdProviderPopulator(IObjectService objectService) {
        this.objectService = Objects.requireNonNull(objectService);
    }

    @Override
    public Builder populate(Builder builder, org.eclipse.sirius.diagram.description.DiagramDescription siriusDiagramDescription, AQLInterpreter interpreter) {
        Function<VariableManager, String> targetObjectIdProvider = variableManager -> {
            Object object = variableManager.getVariables().get(VariableManager.SELF);
            return Optional.ofNullable(object).map(this.objectService::getId).orElse(null);
        };
        return builder.targetObjectIdProvider(targetObjectIdProvider);
    }

}
