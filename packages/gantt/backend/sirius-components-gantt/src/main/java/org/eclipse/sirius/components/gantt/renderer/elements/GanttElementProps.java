/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.gantt.renderer.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.gantt.GanttColumn;
import org.eclipse.sirius.components.gantt.GanttDateRounding;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the Gantt element.
 *
 * @author lfasani
 */
public record GanttElementProps(String id, String descriptionId, String targetObjectId, List<Element> children, List<GanttColumn> columns, GanttDateRounding dateRounding) implements IProps {

    public static final String TYPE = "Gantt";

    public GanttElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(children);
        Objects.requireNonNull(columns);
        Objects.requireNonNull(dateRounding);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }

}
