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
package org.eclipse.sirius.web.domain.boundedcontexts.representationdata.services.api;

import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;
import org.eclipse.sirius.web.domain.services.IResult;

/**
 * Used to update existing representation content.
 *
 * @author sbegaudeau
 */
public interface IRepresentationContentUpdateService {

    IResult<Void> updateContentByRepresentationId(ICause cause, UUID representationId, String content);

    IResult<Void> updateContentByRepresentationIdWithMigrationData(ICause cause, UUID representationId, String content, String lastMigrationPerformed, String migrationVersion);
}
