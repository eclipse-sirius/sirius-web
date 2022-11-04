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
package org.eclipse.sirius.components.formdescriptioneditors;

import java.util.List;

import org.eclipse.sirius.components.formdescriptioneditors.description.StyleProperty;

/**
 * Abstract class to be extended by all the widgets of the form-description-editor representation.
 *
 * @author arichard
 */
public class AbstractFormDescriptionEditorWidget implements IFormDescriptionEditorWidget {

    protected String id;

    protected String kind;

    protected String label;

    protected List<StyleProperty> styleProperties;

    @Override
    public String getId() {
        return this.id;
    }

    @Override
    public String getKind() {
        return this.kind;
    }

    @Override
    public String getLabel() {
        return this.label;
    }

    @Override
    public List<StyleProperty> getStyleProperties() {
        return this.styleProperties;
    }

}
