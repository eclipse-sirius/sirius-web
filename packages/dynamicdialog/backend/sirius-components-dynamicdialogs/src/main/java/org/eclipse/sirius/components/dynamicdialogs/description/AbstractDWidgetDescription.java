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
package org.eclipse.sirius.components.dynamicdialogs.description;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The common superclass of all the widget descriptions.
 *
 * @author lfasani
 */
public abstract class AbstractDWidgetDescription {

    protected String id;

    protected DWidgetOutputDescription output;

    protected List<DWidgetOutputDescription> inputs;

    protected Function<VariableManager, String> labelProvider;

    protected Function<VariableManager, String> initialValueProvider;

    public String getId() {
        return this.id;
    }

    public Function<VariableManager, String> getLabelProvider() {
        return this.labelProvider;
    }

    public DWidgetOutputDescription getOutput() {
        return this.output;
    }

    public List<DWidgetOutputDescription> getInputs() {
        return this.inputs;
    }

    public Function<VariableManager, String> getInitialValueProvider() {
        return this.initialValueProvider;
    }
}
