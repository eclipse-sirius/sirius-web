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

import java.util.Optional;

import org.eclipse.sirius.components.gantt.Gantt;
import org.eclipse.sirius.components.gantt.description.GanttDescription;
import org.eclipse.sirius.components.representations.IProps;
import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The props of the gantt component.
 *
 * @author lfasani
 */
public record GanttComponentProps(VariableManager variableManager, GanttDescription ganttDescription, Optional<Gantt> previousGantt) implements IProps {
}
