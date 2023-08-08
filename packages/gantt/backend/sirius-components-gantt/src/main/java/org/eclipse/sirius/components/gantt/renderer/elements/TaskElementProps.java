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
package org.eclipse.sirius.components.gantt.renderer.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.gantt.TaskDetail;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the task.
 *
 * @author lfasani
 */

public record TaskElementProps(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, TaskDetail detail, List<Element> children) implements IProps {

    public static final String TYPE = "Task";

    public TaskElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(detail);
        Objects.requireNonNull(children);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }
}
