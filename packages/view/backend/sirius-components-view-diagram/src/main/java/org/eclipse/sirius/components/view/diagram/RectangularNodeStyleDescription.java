/**
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Rectangular Node Style Description</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#isWithHeader <em>With
 * Header</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getRectangularNodeStyleDescription()
 * @model
 * @generated
 */
public interface RectangularNodeStyleDescription extends NodeStyleDescription {
    /**
     * Returns the value of the '<em><b>With Header</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>With Header</em>' attribute.
     * @see #setWithHeader(boolean)
     * @see org.eclipse.sirius.components.view.diagram.DiagramPackage#getRectangularNodeStyleDescription_WithHeader()
     * @model
     * @generated
     */
    boolean isWithHeader();

    /**
     * Sets the value of the
     * '{@link org.eclipse.sirius.components.view.diagram.RectangularNodeStyleDescription#isWithHeader <em>With
     * Header</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>With Header</em>' attribute.
     * @see #isWithHeader()
     * @generated
     */
    void setWithHeader(boolean value);

} // RectangularNodeStyleDescription
