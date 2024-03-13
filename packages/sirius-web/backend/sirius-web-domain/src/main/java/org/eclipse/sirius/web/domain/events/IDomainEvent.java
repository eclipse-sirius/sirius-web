/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.web.domain.events;

import java.time.Instant;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

/**
 * Interface to be implemented by all the events of the domain.
 *
 * @author sbegaudeau
 */
public interface IDomainEvent extends ICause {
    UUID id();
    Instant createdOn();
}
