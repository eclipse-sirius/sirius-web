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
 * The configuration used to create a links event processor.
 *
 * @author ldelaigue
 */
public class LinksConfiguration implements IRepresentationConfiguration {

    private static final String LINKS_PREFIX = "links:"; //$NON-NLS-1$

    private final UUID formId;

    private final String objectId;

    public LinksConfiguration(String objectId) {
        this.objectId = Objects.requireNonNull(objectId);
        this.formId = UUID.nameUUIDFromBytes((LINKS_PREFIX + objectId).getBytes());
    }

    @Override
    public UUID getId() {
        return this.formId;
    }

    public String getObjectId() {
        return this.objectId;
    }
}
