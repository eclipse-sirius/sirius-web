/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services;

import java.util.UUID;

/**
 * Utility class to easily track all those who will rely on the composite id.
 *
 * @author sbegaudeau
 *
 * @technical-debt This class only exists to keep in mind which part of the code should be refactored once we will start
 * our migration to Spring Boot 4. With Spring Boot 3.x, we cannot use composite id for our entities and are thus stuck
 * with having to create this composite id manually. This id will be removed in the near future in favor of a proper UUID
 * based id and this service will be deleted.
 */
@Deprecated(forRemoval = false)
public class RepresentationCompositeIdProvider {
    public String getId(UUID semanticDataId, UUID representationMetadataId) {
        return semanticDataId + "#" + representationMetadataId;
    }
}
