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

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>List Layout Strategy Description</b></em>'. <!--
 * end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.ListLayoutStrategyDescription#getDirection <em>Direction</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.ViewPackage#getListLayoutStrategyDescription()
 * @model
 * @generated
 */
public interface ListLayoutStrategyDescription extends LayoutStrategyDescription {
    /**
     * Returns the value of the '<em><b>Direction</b></em>' attribute. The default value is <code>"Column"</code>. The
     * literals are from the enumeration {@link org.eclipse.sirius.components.view.LayoutDirection}. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.LayoutDirection
     * @see #setDirection(LayoutDirection)
     * @see org.eclipse.sirius.components.view.ViewPackage#getListLayoutStrategyDescription_Direction()
     * @model default="Column"
     * @generated
     */
    LayoutDirection getDirection();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.view.ListLayoutStrategyDescription#getDirection
     * <em>Direction</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Direction</em>' attribute.
     * @see org.eclipse.sirius.components.view.LayoutDirection
     * @see #getDirection()
     * @generated
     */
    void setDirection(LayoutDirection value);

} // ListLayoutStrategyDescription
