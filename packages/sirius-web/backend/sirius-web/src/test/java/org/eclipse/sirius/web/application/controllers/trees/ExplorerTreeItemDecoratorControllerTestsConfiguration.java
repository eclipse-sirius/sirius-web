/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.application.controllers.trees;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.domain.Domain;
import org.eclipse.sirius.components.trees.TreeItemDecorator;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerDecoratorServiceDelegate;
import org.eclipse.sirius.web.data.StudioIdentifiers;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;

/**
 * Contributes two ordered decorator delegates for the sample studio's domain object.
 *
 * @author pcdavid
 */
public class ExplorerTreeItemDecoratorControllerTestsConfiguration {

    @Bean
    @Order(2)
    public IExplorerDecoratorServiceDelegate sharingDecoratorDelegate() {
        return this.decorateDomain(List.of(new TreeItemDecorator("decorators/team.svg", "Shared")));
    }

    @Bean
    @Order(1)
    public IExplorerDecoratorServiceDelegate statusDecoratorDelegate() {
        return this.decorateDomain(List.of(
                new TreeItemDecorator("decorators/status.svg", "Reviewed"),
                new TreeItemDecorator("decorators/synchronization.svg", null)));
    }

    private IExplorerDecoratorServiceDelegate decorateDomain(List<TreeItemDecorator> decorators) {
        return new IExplorerDecoratorServiceDelegate() {
            @Override
            public boolean canHandle(IEditingContext editingContext, Object object) {
                return StudioIdentifiers.SAMPLE_STUDIO_EDITING_CONTEXT_ID.equals(editingContext.getId()) && object instanceof Domain;
            }

            @Override
            public List<TreeItemDecorator> getDecorators(IEditingContext editingContext, Object object) {
                return decorators;
            }
        };
    }
}
