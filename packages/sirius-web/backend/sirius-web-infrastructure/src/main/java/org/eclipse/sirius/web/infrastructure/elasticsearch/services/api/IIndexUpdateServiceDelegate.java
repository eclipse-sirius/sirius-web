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
package org.eclipse.sirius.web.infrastructure.elasticsearch.services.api;

import org.eclipse.sirius.components.core.api.IEditingContext;

/**
 * Delegate that updates the content of an editing context index in specific contexts.
 *
 * @author gdaniel
 */
public interface IIndexUpdateServiceDelegate {

    boolean canHandle(IEditingContext editingContext);

    void updateIndex(IEditingContext editingContext);
}
