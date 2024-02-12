/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * Children provider for Explorer view.
 *
 * @author arichard
 */
public interface IExplorerChildrenProvider {
    boolean hasChildren(VariableManager variableManager);

    List<Object> getChildren(VariableManager variableManager);
}
