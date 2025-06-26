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
package org.eclipse.sirius.components.portals.tests;

import static org.assertj.core.api.Assertions.fail;

import java.util.Optional;
import java.util.function.Consumer;

import org.eclipse.sirius.components.collaborative.portals.dto.PortalRefreshedEventPayload;
import org.eclipse.sirius.components.portals.Portal;

/**
 * Used to handle the portal event payloads.
 *
 * @author sbegaudeau
 */
public class PortalEventPayloadConsumer {
    public static Consumer<Object> assertRefreshedPortalThat(Consumer<Portal> consumer) {
        return object -> Optional.of(object)
                .filter(PortalRefreshedEventPayload.class::isInstance)
                .map(PortalRefreshedEventPayload.class::cast)
                .map(PortalRefreshedEventPayload::portal)
                .ifPresentOrElse(consumer, () -> fail("Missing portal"));
    }
}
