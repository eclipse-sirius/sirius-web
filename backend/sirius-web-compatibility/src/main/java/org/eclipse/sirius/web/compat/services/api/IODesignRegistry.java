/*******************************************************************************
 * Copyright (c) 2019, 2020 Obeo.
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
package org.eclipse.sirius.web.compat.services.api;

import java.util.List;

import org.eclipse.sirius.viewpoint.description.Group;

/**
 * The registry of all the odesign managed by the Sirius compatibility layer.
 *
 * @author sbegaudeau
 */
public interface IODesignRegistry {
    List<Group> getODesigns();
}
