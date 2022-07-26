/**
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.components.domain;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Relation</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.domain.Relation#isContainment <em>Containment</em>}</li>
 * <li>{@link org.eclipse.sirius.components.domain.Relation#getTargetType <em>Target Type</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.domain.DomainPackage#getRelation()
 * @model
 * @generated
 */
public interface Relation extends Feature {
    /**
     * Returns the value of the '<em><b>Containment</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Containment</em>' attribute.
     * @see #setContainment(boolean)
     * @see org.eclipse.sirius.components.domain.DomainPackage#getRelation_Containment()
     * @model required="true"
     * @generated
     */
    boolean isContainment();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.domain.Relation#isContainment <em>Containment</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Containment</em>' attribute.
     * @see #isContainment()
     * @generated
     */
    void setContainment(boolean value);

    /**
     * Returns the value of the '<em><b>Target Type</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Type</em>' reference.
     * @see #setTargetType(Entity)
     * @see org.eclipse.sirius.components.domain.DomainPackage#getRelation_TargetType()
     * @model required="true"
     * @generated
     */
    Entity getTargetType();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.domain.Relation#getTargetType <em>Target Type</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Type</em>' reference.
     * @see #getTargetType()
     * @generated
     */
    void setTargetType(Entity value);

} // Relation
