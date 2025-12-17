/*******************************************************************************
 * Copyright (c) 2019, 2025 Obeo.
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
package org.eclipse.sirius.components.representations;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.eclipse.sirius.components.annotations.PublicApi;

/**
 * Used to manage the variables.
 *
 * <p>
 *     The variable manager is used to give access to some variables to a piece of behavior.
 *     Contrary to a regular map of variables, the variable manager contains a notion of scope.
 *     It allows a piece of behavior to retrieve its relevant variables but also variables from its parent scopes.
 *     It also let us redefine a variable by creating a new variable with the same name in a child scope.
 * </p>
 *
 * <p>
 *     This concept was first introduced and validated in Eclipse EEF and leveraged in Sirius Desktop.
 * </p>
 *
 * @author sbegaudeau
 * @v0.1.0
 */
@PublicApi
public class VariableManager {

    /**
     * The name of the variable used as the context of the execution of a piece of behavior.
     *
     * <p>
     *     While it is not a strict requirement of Sirius Components, one could make the assumption that every piece of
     *     behavior executed with a variable manager should have access to a variable named <strong>self</strong>.
     *     This variable would then contain the main semantic element that should be used as the context of the execution.
     * </p>
     */
    public static final String SELF = "self";

    /**
     * The parent variable manager.
     */
    private VariableManager parent;

    /**
     * The variables.
     */
    private Map<String, Object> variables = new HashMap<>();

    /**
     * The constructor.
     */
    public VariableManager() {
        // do nothing
    }

    /**
     * The constructor.
     *
     * @param parent
     *            The parent of this VariableManager.
     */
    public VariableManager(VariableManager parent) {
        this.parent = parent;
    }

    public Object put(String name, Object value) {
        Object previous = this.variables.put(name, value);
        return previous;
    }

    public Map<String, Object> getVariables() {
        if (this.parent != null) {
            Map<String, Object> parentVariables = this.parent.getVariables();
            parentVariables.putAll(this.variables);
            return parentVariables;
        }
        return new HashMap<>(this.variables);
    }

    public <T> Optional<T> get(String name, Class<T> expectedType) {
        return Optional.ofNullable(this.get(name))
                       .filter(expectedType::isInstance)
                       .map(expectedType::cast);
    }

    private Object get(String name) {
        Object value = this.variables.get(name);
        if (value == null && this.parent != null) {
            value = this.parent.get(name);
        }
        return value;
    }

    public VariableManager getParent() {
        return this.parent;
    }

    /**
     * Checks whether this specific VariableManager has a definition for a variable with the given name.
     * This does <em>not</em> consider parent variable managers.
     *
     * @param name
     *            the variable name to check.
     * @return true if there is a local definition for a variable with the given name.
     */
    public boolean hasLocalVariable(String name) {
        return this.variables.containsKey(name);
    }

    public VariableManager createChild() {
        return new VariableManager(this);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<String, Object> var : this.getVariables().entrySet()) {
            String name = var.getKey();
            Object value = var.getValue();
            boolean local = this.variables.containsKey(name);
            stringBuilder.append(name).append(" = ").append(value);
            if (!local) {
                stringBuilder.append(" [inherited]");
            }
            stringBuilder.append(System.lineSeparator());
        }
        return stringBuilder.toString();
    }
}
