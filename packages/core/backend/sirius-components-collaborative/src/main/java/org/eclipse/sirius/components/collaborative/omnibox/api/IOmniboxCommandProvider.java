/*******************************************************************************
 * Copyright (c) 2025, 2025 Obeo.
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

import java.util.List;

import org.eclipse.sirius.components.collaborative.omnibox.dto.OmniboxCommand;

/**
 * Provides commands for the omnibox.
 *
 * @author gdaniel
 */
public interface IOmniboxCommandProvider {

    List<OmniboxCommand> getCommands(String editingContextId, List<String> selectedObjectIds, String query);

}
