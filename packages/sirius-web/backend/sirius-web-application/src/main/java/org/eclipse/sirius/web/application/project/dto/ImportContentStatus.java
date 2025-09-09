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
package org.eclipse.sirius.web.application.project.dto;

import java.util.List;

/**
 * Status returned after importing content in a project.
 *
 * @param isSuccess
 *         holds true if the import is a success
 * @param errorMessages
 *         holds a list of error messages
 */
public record ImportContentStatus(boolean isSuccess, List<String> errorMessages) {

}
