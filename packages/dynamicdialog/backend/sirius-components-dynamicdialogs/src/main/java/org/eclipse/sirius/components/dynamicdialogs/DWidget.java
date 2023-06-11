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
package org.eclipse.sirius.components.dynamicdialogs;

import java.util.List;

/**
 * Abstract class to be extended by all the widgets of the dynamic dialog.
 *
 * @author lfasani
 */
public abstract class DWidget {
    protected String id;

    protected String descriptionId;

    protected String parentId;

    protected String label;

    protected String outputVariableName;

    protected List<String> inputVariableNames;

    protected Boolean required;

    protected String initialValue;

    public String getId() {
        return this.id;
    }

    public String getLabel() {
        return this.label;
    }

    public String getDescriptionId() {
        return this.descriptionId;
    }

    public String getParentId() {
        return this.parentId;
    }

    public String getOutputVariableName() {
        return this.outputVariableName;
    }

    public List<String> getInputVariableNames() {
        return this.inputVariableNames;
    }

    public Boolean getRequired() {
        return this.required;
    }

    public String getInitialValue() {
        return this.initialValue;
    }
}
