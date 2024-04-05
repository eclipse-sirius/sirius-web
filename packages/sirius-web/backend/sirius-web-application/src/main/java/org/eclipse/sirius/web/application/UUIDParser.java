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
package org.eclipse.sirius.web.application;

import java.util.Optional;
import java.util.UUID;

/**
 * Used to parse safely a UUID based identifier.
 *
 * @author sbegaudeau
 */
public class UUIDParser {

    public Optional<UUID> parse(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.of(uuid);
        } catch (IllegalArgumentException exception) {
            // Ignore, the information that the id is invalid is returned as an empty Optional.
        }
        return Optional.empty();
    }

}
