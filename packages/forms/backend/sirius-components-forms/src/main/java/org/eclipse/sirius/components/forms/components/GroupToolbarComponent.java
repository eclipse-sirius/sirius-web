/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
package org.eclipse.sirius.components.forms.components;

import java.util.Objects;

import org.eclipse.sirius.components.forms.GroupToolbar;
import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.FragmentProps;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * Intermediate fragment-like component to distinguish the children of a group which correspond to its toolbar.
 *
 * @author pcdavid
 */
public class GroupToolbarComponent implements IComponent {
    private final FragmentProps props;

    public GroupToolbarComponent(FragmentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        return new Element(GroupToolbar.TYPE, this.props);
    }

}
