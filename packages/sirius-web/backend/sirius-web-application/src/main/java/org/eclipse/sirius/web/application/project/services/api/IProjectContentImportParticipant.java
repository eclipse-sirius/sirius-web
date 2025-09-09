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
package org.eclipse.sirius.web.application.project.services.api;

import org.eclipse.sirius.web.application.project.dto.IProjectBinaryContent;
import org.eclipse.sirius.web.application.project.dto.ImportContentStatus;
import org.eclipse.sirius.web.domain.events.IDomainEvent;

/**
 * Participant that is able to import some content in a Project.
 *
 * @author Arthur Daussy
 */
public interface IProjectContentImportParticipant {

    /**
     * Checks if the participant can handle the given event.
     *
     * @param event
     *         domain event that might need a reaction
     * @return true if the participant need to do something with this event
     */
    boolean canHandle(IDomainEvent event);

    /**
     * Handles the given event.
     *
     * @param event
     *         the event to handle
     * @param projectContent
     *         the project content
     * @return a status
     */
    ImportContentStatus handle(IDomainEvent event, IProjectBinaryContent projectContent);
}
