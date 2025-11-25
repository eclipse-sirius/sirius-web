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
package org.eclipse.sirius.components.collaborative.workbenchconfiguration.api;

import org.eclipse.sirius.components.collaborative.workbenchconfiguration.dto.WorkbenchConfiguration;

/**
 * Used to change the workbench configuration for a specific editing context.
 *
 * @author gcoutable
 */
public interface IWorkbenchConfigurationProviderDelegate {

    boolean canHandle(String editingContext);

    WorkbenchConfiguration getWorkbenchConfiguration(String editingContext);

}
