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
package org.eclipse.sirius.components.collaborative.omnibox.api;

import org.eclipse.sirius.components.collaborative.omnibox.dto.ExecuteOmniboxCommandInput;
import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Used to execute omnibox commands.
 *
 * @author sbegaudeau
 */
public interface IOmniboxCommandExecutor {
    IPayload execute(ExecuteOmniboxCommandInput input);
}
