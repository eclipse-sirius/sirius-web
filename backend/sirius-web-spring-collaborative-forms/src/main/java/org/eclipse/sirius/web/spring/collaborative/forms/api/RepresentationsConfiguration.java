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
package org.eclipse.sirius.web.spring.collaborative.forms.api;

import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.web.spring.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create the representations event processor.
 *
 * @author gcoutable
 */
public class RepresentationsConfiguration implements IRepresentationConfiguration {

    private static final String REPRESENTATIONS_PREFIX = "representations:"; //$NON-NLS-1$

    private final String objectId;

    private UUID formId;

    public RepresentationsConfiguration(String objectId) {
        this.objectId = Objects.requireNonNull(objectId);
        this.formId = UUID.nameUUIDFromBytes((REPRESENTATIONS_PREFIX + objectId).getBytes());
    }

    @Override
    public UUID getId() {
        return this.formId;
    }

    public String getObjectId() {
        return this.objectId;
    }

}
