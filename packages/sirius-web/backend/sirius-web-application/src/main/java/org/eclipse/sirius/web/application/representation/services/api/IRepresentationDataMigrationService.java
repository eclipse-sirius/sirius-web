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
package org.eclipse.sirius.web.application.representation.services.api;

import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Optional;

import org.eclipse.sirius.web.domain.boundedcontexts.representationdata.projections.RepresentationDataContentOnly;

/**
 * Used to retrieved the migrated content of the representation data.
 *
 * @author sbegaudeau
 */
public interface IRepresentationDataMigrationService {

    Optional<ObjectNode> getMigratedContent(RepresentationDataContentOnly representationData);
}
