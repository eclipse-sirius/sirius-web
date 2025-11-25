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
package org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto;

import java.util.List;
import java.util.Objects;

/**
 * The configuration of the side panels of the workbench.
 *
 * @author gdaniel
 */
public record WorkbenchSidePanelConfiguration(String id, boolean isOpen, List<IViewConfiguration> views) {
    public WorkbenchSidePanelConfiguration {
        Objects.requireNonNull(id);
        Objects.requireNonNull(views);
    }
}
