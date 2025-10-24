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
package org.eclipse.sirius.web.application.project.services;

import java.util.Map;
import java.util.UUID;

import org.eclipse.sirius.components.events.ICause;

/**
 * Cause used to notify the copy of semantic data.
 *
 * @author adaussy
 */
public record CopySemanticDataCause(UUID id, ICause causedBy, Map<String, String> semanticDataIds, Map<String, String> documentIds) implements ICause {

}
