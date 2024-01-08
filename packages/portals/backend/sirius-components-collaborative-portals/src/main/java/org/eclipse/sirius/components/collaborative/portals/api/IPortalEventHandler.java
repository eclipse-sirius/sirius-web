/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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

import org.eclipse.sirius.components.collaborative.api.ChangeDescription;
import org.eclipse.sirius.components.core.api.IPayload;

import reactor.core.publisher.Sinks.Many;
import reactor.core.publisher.Sinks.One;

/**
 * Interface of all the portal event handlers.
 *
 * @author pcdavid
 */
public interface IPortalEventHandler {
    String NEXT_PORTAL_PARAMETER = "nextPortal";

    boolean canHandle(IPortalInput portalInput);

    void handle(One<IPayload> payloadSink, Many<ChangeDescription> changeDescriptionSink, PortalContext context);

}
