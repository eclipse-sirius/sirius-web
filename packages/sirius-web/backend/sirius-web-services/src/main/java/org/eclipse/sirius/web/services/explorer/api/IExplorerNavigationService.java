/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.web.services.explorer.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Interface of the service for the navigation through the Sirius Web Explorer.
 *
 * @author arichard
 */
public interface IExplorerNavigationService {

    List<String> getAncestors(IEditingContext editingContext, String treeItemId);

}
