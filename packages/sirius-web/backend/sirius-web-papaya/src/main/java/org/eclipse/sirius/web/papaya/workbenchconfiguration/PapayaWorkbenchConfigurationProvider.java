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
package org.eclipse.sirius.web.papaya.workbenchconfiguration;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.collaborative.workbenchconfiguration.api.IWorkbenchConfigurationProviderDelegate;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.DefaultViewConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchMainPanelConfiguration;
import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchSidePanelConfiguration;
import org.eclipse.sirius.web.papaya.services.api.IPapayaCapableEditingContextPredicate;
import org.springframework.stereotype.Service;

/**
 * Service used to configure the workbench for papaya projects.
 *
 * @author gcoutable
 */
@Service
public class PapayaWorkbenchConfigurationProvider implements IWorkbenchConfigurationProviderDelegate {

    private final IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate;

    public PapayaWorkbenchConfigurationProvider(IPapayaCapableEditingContextPredicate papayaCapableEditingContextPredicate) {
        this.papayaCapableEditingContextPredicate = Objects.requireNonNull(papayaCapableEditingContextPredicate);
    }

    @Override
    public boolean canHandle(String editingContextId) {
        return this.papayaCapableEditingContextPredicate.test(editingContextId);
    }

    @Override
    public WorkbenchConfiguration getWorkbenchConfiguration(String editingContextId) {
        return new WorkbenchConfiguration(
                new WorkbenchMainPanelConfiguration("main", List.of()),
                List.of(
                        new WorkbenchSidePanelConfiguration("left", true, List.of(
                                new DefaultViewConfiguration("explorer", true),
                                new DefaultViewConfiguration("validation", false),
                                new DefaultViewConfiguration("search", false)
                        )),
                        new WorkbenchSidePanelConfiguration("right", true, List.of(
                                new DefaultViewConfiguration("details", true),
                                new DefaultViewConfiguration("query", false),
                                new DefaultViewConfiguration("representations", false),
                                new DefaultViewConfiguration("related-elements", false),
                                new DefaultViewConfiguration("papaya-view", true)
                        ))
                )
        );
    }
}
