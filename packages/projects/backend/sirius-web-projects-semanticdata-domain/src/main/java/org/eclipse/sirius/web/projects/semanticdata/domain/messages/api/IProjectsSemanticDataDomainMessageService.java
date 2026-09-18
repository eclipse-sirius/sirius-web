/*******************************************************************************
 * Copyright (c) 2026 Obeo.
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
package org.eclipse.sirius.web.projects.semanticdata.domain.messages.api;

/**
 * Used to compute internationalized messages for the projects semantic data domain.
 *
 * @author sbegaudeau
 * @since 2026.11.0
 */
public interface IProjectsSemanticDataDomainMessageService {

    /**
     * Provides the message used when a name is invalid.
     *
     * @return the invalid name message
     */
    String invalidName();

    /**
     * Provides the message used when project semantic data cannot be found.
     *
     * @return the not found message
     */
    String notFound();

    /**
     * Provides the message used when project semantic data cannot be renamed.
     *
     * @return the failed to rename message
     */
    String failedToRename();
}
