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
package org.eclipse.sirius.web.services.api.representations;

import java.util.Optional;

import org.eclipse.sirius.components.view.View;

/**
 * Used to register views loaded in memory.
 *
 * @author pcdavid
 */
public interface IInMemoryViewRegistry {

    void register(View view);

    Optional<View> findViewById(String viewId);
}
