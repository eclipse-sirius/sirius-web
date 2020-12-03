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
package org.eclipse.sirius.web.collaborative.forms.api;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.collaborative.api.services.IRepresentationConfiguration;

/**
 * The configuration used to create a properties event processor.
 *
 * @author hmarchadour
 */
public class PropertiesConfiguration implements IRepresentationConfiguration {

    private static final String PROPERTIES_PREFIX = "properties:"; //$NON-NLS-1$

    private final UUID formId;

    private final String objectId;

    public PropertiesConfiguration(String objectId) {
        this.objectId = Objects.requireNonNull(objectId);
        this.formId = UUID.nameUUIDFromBytes((PROPERTIES_PREFIX + objectId).getBytes());
    }

    @Override
    public UUID getId() {
        return this.formId;
    }

    public String getObjectId() {
        return this.objectId;
    }
}
