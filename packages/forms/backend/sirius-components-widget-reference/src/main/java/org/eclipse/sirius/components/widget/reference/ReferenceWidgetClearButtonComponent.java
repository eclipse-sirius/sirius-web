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

package org.eclipse.sirius.components.widget.reference;

import java.util.Objects;

import org.eclipse.sirius.components.representations.Element;
import org.eclipse.sirius.components.representations.IComponent;

/**
 * Renders a reference widget clear button.
 *
 * @author tgiraudet
 */
public class ReferenceWidgetClearButtonComponent implements IComponent {

    private final ReferenceWidgetClearButtonComponentProps props;

    public ReferenceWidgetClearButtonComponent(ReferenceWidgetClearButtonComponentProps props) {
        this.props = Objects.requireNonNull(props);
    }

    @Override
    public Element render() {
        return new Element(ReferenceWidgetClearButton.TYPE, this.props);
    }

}
