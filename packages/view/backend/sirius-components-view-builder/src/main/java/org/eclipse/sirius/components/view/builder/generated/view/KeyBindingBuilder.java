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
package org.eclipse.sirius.components.view.builder.generated.view;

/**
 * Builder for KeyBindingBuilder.
 *
 * @author BuilderGenerator
 * @generated
 */
public class KeyBindingBuilder {

    /**
     * Create instance org.eclipse.sirius.components.view.KeyBinding.
     * @generated
     */
    private org.eclipse.sirius.components.view.KeyBinding keyBinding = org.eclipse.sirius.components.view.ViewFactory.eINSTANCE.createKeyBinding();

    /**
     * Return instance org.eclipse.sirius.components.view.KeyBinding.
     * @generated
     */
    protected org.eclipse.sirius.components.view.KeyBinding getKeyBinding() {
        return this.keyBinding;
    }

    /**
     * Return instance org.eclipse.sirius.components.view.KeyBinding.
     * @generated
     */
    public org.eclipse.sirius.components.view.KeyBinding build() {
        return this.getKeyBinding();
    }

    /**
     * Setter for Ctrl.
     *
     * @generated
     */
    public KeyBindingBuilder ctrl(java.lang.Boolean value) {
        this.getKeyBinding().setCtrl(value);
        return this;
    }
    /**
     * Setter for Alt.
     *
     * @generated
     */
    public KeyBindingBuilder alt(java.lang.Boolean value) {
        this.getKeyBinding().setAlt(value);
        return this;
    }
    /**
     * Setter for Meta.
     *
     * @generated
     */
    public KeyBindingBuilder meta(java.lang.Boolean value) {
        this.getKeyBinding().setMeta(value);
        return this;
    }
    /**
     * Setter for Key.
     *
     * @generated
     */
    public KeyBindingBuilder key(java.lang.String value) {
        this.getKeyBinding().setKey(value);
        return this;
    }

}

