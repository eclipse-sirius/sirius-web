/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.spring.collaborative.validation.api;

import java.util.UUID;

import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a validation event processor.
 *
 * @author gcoutable
 */
public class ValidationConfiguration implements IRepresentationConfiguration {

    private final UUID validationId;

    public ValidationConfiguration(UUID editingContextId) {
        String uniqueId = editingContextId.toString() + "validation"; //$NON-NLS-1$
        this.validationId = UUID.nameUUIDFromBytes(uniqueId.getBytes());
    }

    @Override
    public UUID getId() {
        return this.validationId;
    }

}
