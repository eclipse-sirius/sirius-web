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
package org.eclipse.sirius.components.deck.renderer.elements;

import java.util.List;
import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IProps;

/**
 * Properties of the lane element.
 *
 * @author fbarbin
 */

public record LaneElementProps(String id, String descriptionId, String targetObjectId, String targetObjectKind, String targetObjectLabel, String title, String label, List<Element> children)
        implements IProps {

    public static final String TYPE = "Lane";

    public LaneElementProps {
        Objects.requireNonNull(id);
        Objects.requireNonNull(targetObjectId);
        Objects.requireNonNull(targetObjectKind);
        Objects.requireNonNull(targetObjectLabel);
        Objects.requireNonNull(descriptionId);
        Objects.requireNonNull(title);
        Objects.requireNonNull(label);
        Objects.requireNonNull(children);
    }

    @Override
    public List<Element> getChildren() {
        return this.children;
    }
}
