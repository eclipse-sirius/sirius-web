/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.compatibility.diagrams;

import java.util.Objects;
import java.util.function.Function;

import org.eclipse.sirius.components.interpreter.AQLInterpreter;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;
import org.eclipse.sirius.diagram.description.style.FlatContainerStyleDescription;
import org.eclipse.sirius.viewpoint.description.style.LabelStyleDescription;

/**
 * Used to return if the node mapping has a header.
 *
 * @author gcoutable
 */
public class AbstractNodeMappingIsHeaderProvider implements Function<VariableManager, Boolean> {

    private final AQLInterpreter interpreter;

    private final AbstractNodeMapping abstractNodeMapping;

    public AbstractNodeMappingIsHeaderProvider(AQLInterpreter interpreter, AbstractNodeMapping abstractNodeMapping) {
        this.interpreter = Objects.requireNonNull(interpreter);
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
    }

    @Override
    public Boolean apply(VariableManager variableManager) {
        LabelStyleDescription labelStyleDescription = new LabelStyleDescriptionProvider(this.interpreter, this.abstractNodeMapping).apply(variableManager);
        return this.isHeader(variableManager, labelStyleDescription);
    }

    private Boolean isHeader(VariableManager variableManager, LabelStyleDescription labelStyleDescription) {
        if (labelStyleDescription instanceof FlatContainerStyleDescription flatContainerStyleDescription) {
            return this.abstractNodeMapping instanceof ContainerMapping && new ContainerMappingQuery((ContainerMapping) this.abstractNodeMapping).isListContainer();
        }
        return false;
    }

}
