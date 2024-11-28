/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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
package org.eclipse.sirius.web.application.project.data.versioning.dto;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * REST DataRequest DTO.
 *
 * @author arichard
 */
@Schema(name = "DataRequest", description = "DataRequest is used by DataVersionRequest in CommitRequest in the createCommit POST request. It's one of ElementRequest, ExternalDataRequest, ExternalRelationshipRequest or ProjectUsageRequest.")
public interface IRestDataRequest {
}
