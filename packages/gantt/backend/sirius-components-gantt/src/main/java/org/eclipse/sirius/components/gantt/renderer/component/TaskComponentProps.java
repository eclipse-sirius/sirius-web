/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.gantt.renderer.component;

import java.util.List;
import java.util.Map;

import org.eclipse.sirius.components.gantt.Task;
import org.eclipse.sirius.components.gantt.description.TaskDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the task component.
 *
 * @author lfasani
 */
public record TaskComponentProps(VariableManager variableManager, TaskDescription taskDescription, List<Task> previousTasks, String parentElementId, Map<String, TaskDescription> id2tasksDescription) implements IProps {
}
