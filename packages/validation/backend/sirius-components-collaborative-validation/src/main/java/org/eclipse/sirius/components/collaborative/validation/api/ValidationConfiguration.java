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
package org.eclipse.sirius.components.collaborative.validation.api;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a validation event processor.
 *
 * @author gcoutable
 */
public class ValidationConfiguration implements IRepresentationConfiguration {

    private final String validationId;

    public ValidationConfiguration(String editingContextId) {
        String uniqueId = editingContextId + "validation";
        this.validationId = UUID.nameUUIDFromBytes(uniqueId.getBytes()).toString();
    }

    @Override
    public String getId() {
        return this.validationId;
    }

}
