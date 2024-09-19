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
package org.eclipse.sirius.web.application.project.dto;

import java.util.List;
import java.util.UUID;

import org.eclipse.sirius.web.application.dto.IRestRecord;
import org.eclipse.sirius.web.application.query.dto.RestQuery;

/**
 * REST Project DTO.
 *
 * @author arichard
 */
public record RestProject(
        UUID id,
        String resourceIdentifier,
        List<String> alias,
        String humanIdentifier,
        String decription,
        String name,
        List<RestQuery> queries) implements IRestRecord {

}
