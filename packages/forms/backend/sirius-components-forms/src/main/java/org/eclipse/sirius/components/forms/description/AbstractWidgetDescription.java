/*******************************************************************************
 * Copyright (c) 2019, 2023 Obeo.
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
package org.eclipse.sirius.components.forms.description;

import java.util.List;
import java.util.function.Function;

import org.eclipse.sirius.components.representations.VariableManager;

/**
 * The common superclass of all the widget descriptions.
 *
 * @author sbegaudeau
 */
public abstract class AbstractWidgetDescription extends AbstractControlDescription {

    protected Function<VariableManager, List<?>> diagnosticsProvider;

    protected Function<Object, String> kindProvider;

    protected Function<Object, String> messageProvider;

    protected Function<VariableManager, String> helpTextProvider;

    public Function<VariableManager, List<?>> getDiagnosticsProvider() {
        return this.diagnosticsProvider;
    }

    public Function<Object, String> getKindProvider() {
        return this.kindProvider;
    }

    public Function<Object, String> getMessageProvider() {
        return this.messageProvider;
    }

    public Function<VariableManager, String> getHelpTextProvider() {
        return this.helpTextProvider;
    }
}
