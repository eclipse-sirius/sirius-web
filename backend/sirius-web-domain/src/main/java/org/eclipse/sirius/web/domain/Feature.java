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
package org.eclipse.sirius.web.domain;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Feature</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.domain.Feature#isOptional <em>Optional</em>}</li>
 * <li>{@link org.eclipse.sirius.web.domain.Feature#isMany <em>Many</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.web.domain.DomainPackage#getFeature()
 * @model abstract="true"
 * @generated
 */
public interface Feature extends NamedElement {
    /**
     * Returns the value of the '<em><b>Optional</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Optional</em>' attribute.
     * @see #setOptional(boolean)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getFeature_Optional()
     * @model default="false" required="true"
     * @generated
     */
    boolean isOptional();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Feature#isOptional <em>Optional</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Optional</em>' attribute.
     * @see #isOptional()
     * @generated
     */
    void setOptional(boolean value);

    /**
     * Returns the value of the '<em><b>Many</b></em>' attribute. The default value is <code>"false"</code>. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Many</em>' attribute.
     * @see #setMany(boolean)
     * @see org.eclipse.sirius.web.domain.DomainPackage#getFeature_Many()
     * @model default="false" required="true"
     * @generated
     */
    boolean isMany();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.web.domain.Feature#isMany <em>Many</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Many</em>' attribute.
     * @see #isMany()
     * @generated
     */
    void setMany(boolean value);

} // Feature
