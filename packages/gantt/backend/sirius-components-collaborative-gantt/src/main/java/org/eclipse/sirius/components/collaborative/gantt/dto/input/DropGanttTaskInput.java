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
package org.eclipse.sirius.components.collaborative.gantt.dto.input;

import java.util.UUID;

import org.eclipse.sirius.components.collaborative.gantt.api.IGanttInput;

/**
 * The class of the inputs for the "Drop task" mutation.
 *
 * @param droppedTaskId
 *            The target task in which the dragged task is dropped.</br>
 *            It may be null, if the task is dropped as a root task of the Gantt
 * @param dropIndex
 *            The index among the children of the target Task where the task is dropped</br>
 *            Its value may be -1 if no information is provided
 * @author lfasani
 */
public record DropGanttTaskInput(UUID id, String editingContextId, String representationId, String droppedTaskId, String targetTaskId, int dropIndex) implements IGanttInput {
}
