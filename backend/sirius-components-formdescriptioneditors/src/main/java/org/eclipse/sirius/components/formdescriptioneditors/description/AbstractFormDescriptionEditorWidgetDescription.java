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
package org.eclipse.sirius.components.formdescriptioneditors.description;

/**
 * The Textfield concept of the description of a form description editor representation.
 *
 * @author arichard
 */
public abstract class AbstractFormDescriptionEditorWidgetDescription {

    protected String id;

    protected String label;

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }
}
