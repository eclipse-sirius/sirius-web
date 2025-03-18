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
package org.eclipse.sirius.web.application.views.explorer.services;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.web.application.views.explorer.services.api.IDefaultExplorerLabelService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerLabelService;
import org.eclipse.sirius.web.application.views.explorer.services.api.IExplorerLabelServiceDelegate;
import org.springframework.stereotype.Service;

/**
 * Used to manipulate labels in the explorer.
 *
 * @author sbegaudeau
 */
@Service
public class ComposedExplorerLabelService implements IExplorerLabelService {

    private final IDefaultExplorerLabelService defaultExplorerLabelService;

    private final List<IExplorerLabelServiceDelegate> explorerLabelServiceDelegates;

    public ComposedExplorerLabelService(IDefaultExplorerLabelService defaultExplorerLabelService, List<IExplorerLabelServiceDelegate> explorerLabelServiceDelegates) {
        this.defaultExplorerLabelService = Objects.requireNonNull(defaultExplorerLabelService);
        this.explorerLabelServiceDelegates = Objects.requireNonNull(explorerLabelServiceDelegates);
    }

    @Override
    public boolean isEditable(Object self) {
        return this.explorerLabelServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(self))
                .findFirst().map(delegate -> delegate.isEditable(self))
                .orElseGet(() -> this.defaultExplorerLabelService.isEditable(self));
    }

    @Override
    public void editLabel(Object self, String newValue) {
        this.explorerLabelServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(self))
                .findFirst()
                .ifPresentOrElse(
                    delegate -> delegate.editLabel(self, newValue),
                    () -> this.defaultExplorerLabelService.editLabel(self, newValue));
    }
}
