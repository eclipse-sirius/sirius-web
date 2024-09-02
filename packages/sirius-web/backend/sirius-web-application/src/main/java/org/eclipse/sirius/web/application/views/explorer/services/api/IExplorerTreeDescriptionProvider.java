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
package org.eclipse.sirius.web.application.views.explorer.services.api;

import java.util.List;

import org.eclipse.sirius.components.core.api.IEditingContext;
import org.eclipse.sirius.components.trees.description.TreeDescription;

/**
 * Interface to provide tree descriptions that could be used inside the explorer of Sirius web.
 *
 * @author Jerome Gout
 */
public interface IExplorerTreeDescriptionProvider {

    List<TreeDescription> getDescriptions(IEditingContext editingContext);
}
