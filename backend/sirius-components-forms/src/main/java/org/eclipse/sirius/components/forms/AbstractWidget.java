/*******************************************************************************
 * Copyright (c) 2019, 2022 Obeo.
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

import java.util.List;

import org.eclipse.sirius.components.forms.validation.Diagnostic;

/**
 * Abstract class to be extended by all the widgets of the form-based representation.
 *
 * @author sbegaudeau
 */
public abstract class AbstractWidget {
    protected String id;

    protected String label;

    protected String iconURL;

    protected List<Diagnostic> diagnostics;

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getIconURL() {
        return this.iconURL;
    }

    public List<Diagnostic> getDiagnostics() {
        return this.diagnostics;
    }
}
