/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.collaborative.forms.dto;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import org.eclipse.sirius.components.core.api.IPayload;

/**
 * Payload used to indicates that the subscribers of a widget have been updated.
 *
 * @author sbegaudeau
 */
public record WidgetSubscriptionsUpdatedEventPayload(UUID id, List<WidgetSubscription> widgetSubscriptions) implements IPayload {
    public WidgetSubscriptionsUpdatedEventPayload {
        Objects.requireNonNull(id);
        Objects.requireNonNull(widgetSubscriptions);
    }
}
