/*******************************************************************************
 * Copyright (c) 2023, 2024 Obeo.
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
package org.eclipse.sirius.components.view.widget.reference;

import org.eclipse.emf.ecore.EFactory;

/**
 * <!-- begin-user-doc --> The <b>Factory</b> for the model. It provides a create method for each non-abstract class of
 * the model. <!-- end-user-doc -->
 *
 * @see ReferencePackage
 * @generated
 */
public interface ReferenceFactory extends EFactory {
    /**
     * The singleton instance of the factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    ReferenceFactory eINSTANCE = org.eclipse.sirius.components.view.widget.reference.impl.ReferenceFactoryImpl.init();

    /**
     * Returns a new object of class '<em>Widget Description</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Widget Description</em>'.
     * @generated
     */
    ReferenceWidgetDescription createReferenceWidgetDescription();

    /**
     * Returns a new object of class '<em>Widget Description Style</em>'. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Widget Description Style</em>'.
     * @generated
     */
    ReferenceWidgetDescriptionStyle createReferenceWidgetDescriptionStyle();

    /**
     * Returns a new object of class '<em>Conditional Reference Widget Description Style</em>'. <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     *
     * @return a new object of class '<em>Conditional Reference Widget Description Style</em>'.
     * @generated
     */
    ConditionalReferenceWidgetDescriptionStyle createConditionalReferenceWidgetDescriptionStyle();

    /**
     * Returns the package supported by this factory. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the package supported by this factory.
     * @generated
     */
    ReferencePackage getReferencePackage();

} // ReferenceFactory
