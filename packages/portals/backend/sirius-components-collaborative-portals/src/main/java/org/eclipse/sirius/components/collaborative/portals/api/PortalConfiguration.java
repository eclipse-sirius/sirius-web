/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.portals.api;

import java.util.Objects;

import org.eclipse.sirius.components.collaborative.api.IRepresentationConfiguration;

/**
 * The configuration used to create a portal event processor.
 *
 * @author pcdavid
 */
public class PortalConfiguration implements IRepresentationConfiguration {

    private final String portalId;

    public PortalConfiguration(String portalId) {
        this.portalId = Objects.requireNonNull(portalId);
    }

    @Override
    public String getId() {
        return this.portalId;
    }

}
