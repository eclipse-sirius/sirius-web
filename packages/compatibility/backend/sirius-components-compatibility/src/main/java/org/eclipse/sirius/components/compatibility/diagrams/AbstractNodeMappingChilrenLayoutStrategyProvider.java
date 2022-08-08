/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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

import org.eclipse.sirius.components.diagrams.FreeFormLayoutStrategy;
import org.eclipse.sirius.components.diagrams.ILayoutStrategy;
import org.eclipse.sirius.components.diagrams.LayoutDirection;
import org.eclipse.sirius.components.diagrams.ListLayoutStrategy;
import org.eclipse.sirius.components.representations.VariableManager;
import org.eclipse.sirius.diagram.business.api.query.ContainerMappingQuery;
import org.eclipse.sirius.diagram.description.AbstractNodeMapping;
import org.eclipse.sirius.diagram.description.ContainerMapping;

/**
 * Used to compute the children layout strategy using the definition of an abstract node mapping.
 *
 * @author gcoutable
 */
public class AbstractNodeMappingChilrenLayoutStrategyProvider implements Function<VariableManager, ILayoutStrategy> {

    private final AbstractNodeMapping abstractNodeMapping;

    public AbstractNodeMappingChilrenLayoutStrategyProvider(AbstractNodeMapping abstractNodeMapping) {
        this.abstractNodeMapping = Objects.requireNonNull(abstractNodeMapping);
    }

    @Override
    public ILayoutStrategy apply(VariableManager variableManager) {
        return this.getChildrenLayoutStrategy(variableManager);
    }

    private ILayoutStrategy getChildrenLayoutStrategy(VariableManager variableManager) {
        ILayoutStrategy childrenLayoutStrategy = new FreeFormLayoutStrategy();

        if (this.abstractNodeMapping instanceof ContainerMapping) {
            ContainerMapping containerMapping = (ContainerMapping) this.abstractNodeMapping;
            ContainerMappingQuery containerMappingQuery = new ContainerMappingQuery(containerMapping);
            if (containerMappingQuery.isListContainer() || containerMappingQuery.isVerticalStackContainer()) {
                childrenLayoutStrategy = ListLayoutStrategy.newListLayoutStrategy().direction(LayoutDirection.COLUMN).build();
            }
        }

        return childrenLayoutStrategy;
    }

}
