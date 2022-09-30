/*******************************************************************************
 * Copyright (c) 2021, 2022 Obeo.
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
package org.eclipse.sirius.web.services.api.id;

import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used to parse safely an identifier.
 *
 * @author sbegaudeau
 */
public class IDParser {

    private final Logger logger = LoggerFactory.getLogger(IDParser.class);

    public Optional<UUID> parse(String id) {
        try {
            UUID uuid = UUID.fromString(id);
            return Optional.of(uuid);
        } catch (IllegalArgumentException exception) {
            this.logger.warn(exception.getMessage(), exception);
        }
        return Optional.empty();
    }
}
