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
 * Interface of the default workbench configuration service interacting with the editing context.
 *
 * @author gcoutable
 */
public interface IDefaultWorkbenchConfigurationService {

    WorkbenchConfiguration getWorkbenchConfiguration(String editingContextId);
}
