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
package org.eclipse.sirius.components.forms;

/**
 * Abstract class to be extended by all the WidgetDescriptionStyles with modifiable font.
 *
 * @author arichard
 */
public abstract class AbstractFontStyle {

    protected int fontSize;

    protected boolean italic;

    protected boolean bold;

    protected boolean underline;

    protected boolean strikeThrough;

    public int getFontSize() {
        return this.fontSize;
    }

    public boolean isItalic() {
        return this.italic;
    }

    public boolean isBold() {
        return this.bold;
    }

    public boolean isUnderline() {
        return this.underline;
    }

    public boolean isStrikeThrough() {
        return this.strikeThrough;
    }
}
