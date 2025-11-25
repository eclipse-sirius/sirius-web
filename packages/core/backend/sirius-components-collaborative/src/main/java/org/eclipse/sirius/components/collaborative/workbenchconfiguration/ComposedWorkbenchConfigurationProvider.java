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
package org.eclipse.sirius.components.collaborative.workbenchconfiguration;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IDefaultWorkbenchConfigurationProvider;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationProvider;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationProviderDelegate;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IWorkbenchConfigurationProvider} which delegates to {@link IWorkbenchConfigurationProviderDelegate}
 * or fallback to {@link IDefaultWorkbenchConfigurationProvider}.
 *
 * @author gcoutable
 */
@Service
public class ComposedWorkbenchConfigurationProvider implements IWorkbenchConfigurationProvider {

    private final List<IWorkbenchConfigurationProviderDelegate> workbenchConfigurationProviderDelegates;

    private final IDefaultWorkbenchConfigurationProvider defaultWorkbenchConfigurationProvider;

    public ComposedWorkbenchConfigurationProvider(List<IWorkbenchConfigurationProviderDelegate> workbenchConfigurationProviderDelegates, IDefaultWorkbenchConfigurationProvider defaultWorkbenchConfigurationProvider) {
        this.workbenchConfigurationProviderDelegates = Objects.requireNonNull(workbenchConfigurationProviderDelegates);
        this.defaultWorkbenchConfigurationProvider = Objects.requireNonNull(defaultWorkbenchConfigurationProvider);
    }

    @Override
    public WorkbenchConfiguration getWorkbenchConfiguration(String editingContextId) {
        var optionalDelegate = this.workbenchConfigurationProviderDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContextId))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getWorkbenchConfiguration(editingContextId);
        }

        return this.defaultWorkbenchConfigurationProvider.getWorkbenchConfiguration(editingContextId);
    }
}
