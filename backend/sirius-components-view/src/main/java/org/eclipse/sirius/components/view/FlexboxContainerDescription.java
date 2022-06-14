/**
 * Copyright (c) 2021, 2022 Obeo.
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *      Obeo - initial API and implementation
 */
package org.eclipse.sirius.components.view;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Flexbox Container Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.FlexboxContainerDescription#getChildren <em>Children</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.FlexboxContainerDescription#getFlexDirection <em>Flex
 * Direction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getFlexboxContainerDescription()
 * @model
 * @generated
 */
public interface FlexboxContainerDescription extends WidgetDescription {
    /**
     * Returns the value of the '<em><b>Children</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.view.WidgetDescription}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Children</em>' containment reference list.
     * @see org.eclipse.sirius.components.view.ViewPackage#getFlexboxContainerDescription_Children()
     * @model containment="true"
     * @generated
     */
    EList<WidgetDescription> getChildren();

    /**
     * Returns the value of the '<em><b>Flex Direction</b></em>' attribute. The default value is <code>"row"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.FlexDirection}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Flex Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.FlexDirection
     * @see #setFlexDirection(FlexDirection)
     * @see org.eclipse.sirius.components.view.ViewPackage#getFlexboxContainerDescription_FlexDirection()
     * @model default="row" required="true"
     * @generated
     */
    FlexDirection getFlexDirection();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.FlexboxContainerDescription#getFlexDirection
     * <em>Flex Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Flex Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.FlexDirection
     * @see #getFlexDirection()
     * @generated
     */
    void setFlexDirection(FlexDirection value);

} // FlexboxContainerDescription
