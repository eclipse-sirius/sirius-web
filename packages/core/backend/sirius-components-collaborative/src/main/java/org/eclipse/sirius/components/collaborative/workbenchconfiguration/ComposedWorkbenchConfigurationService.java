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

import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IDefaultWorkbenchConfigurationService;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationService;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationServiceDelegate;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link IWorkbenchConfigurationService} which delegates to {@link IWorkbenchConfigurationServiceDelegate} or fallback to {@link IDefaultWorkbenchConfigurationService}.
 *
 * @author gcoutable
 */
@Service
public class ComposedWorkbenchConfigurationService implements IWorkbenchConfigurationService {

    private final List<IWorkbenchConfigurationServiceDelegate> workbenchServiceDelegates;

    private final IDefaultWorkbenchConfigurationService defaultWorkbenchConfigurationService;

    public ComposedWorkbenchConfigurationService(List<IWorkbenchConfigurationServiceDelegate> workbenchServiceDelegates, IDefaultWorkbenchConfigurationService defaultWorkbenchConfigurationService) {
        this.workbenchServiceDelegates = Objects.requireNonNull(workbenchServiceDelegates);
        this.defaultWorkbenchConfigurationService = Objects.requireNonNull(defaultWorkbenchConfigurationService);
    }

    @Override
    public WorkbenchConfiguration getWorkbenchConfiguration(String editingContextId) {
        var optionalDelegate = this.workbenchServiceDelegates.stream()
                .filter(delegate -> delegate.canHandle(editingContextId))
                .findFirst();
        if (optionalDelegate.isPresent()) {
            return optionalDelegate.get().getWorkbenchConfiguration(editingContextId);
        }

        return this.defaultWorkbenchConfigurationService.getWorkbenchConfiguration(editingContextId);
    }
}
