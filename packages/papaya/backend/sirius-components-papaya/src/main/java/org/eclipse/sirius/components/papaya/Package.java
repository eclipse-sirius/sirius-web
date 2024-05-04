/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.common.util.EList;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Package</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Package#getQualifiedName <em>Qualified Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Package#getTypes <em>Types</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Package#getPackages <em>Packages</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPackage()
 * @model
 * @generated
 */
public interface Package extends NamedElement, AnnotableElement {
    /**
     * Returns the value of the '<em><b>Qualified Name</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @return the value of the '<em>Qualified Name</em>' attribute.
     * @see #isSetQualifiedName()
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPackage_QualifiedName()
     * @model unsettable="true" required="true" transient="true" changeable="false" volatile="true" derived="true"
     * @generated
     */
    String getQualifiedName();

    /**
     * Returns whether the value of the '{@link org.eclipse.sirius.components.papaya.Package#getQualifiedName
     * <em>Qualified Name</em>}' attribute is set. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return whether the value of the '<em>Qualified Name</em>' attribute is set.
     * @see #getQualifiedName()
     * @generated
     */
    boolean isSetQualifiedName();

    /**
     * Returns the value of the '<em><b>Types</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Type}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Types</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPackage_Types()
     * @model containment="true"
     * @generated
     */
    EList<Type> getTypes();

    /**
     * Returns the value of the '<em><b>Packages</b></em>' containment reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Package}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Packages</em>' containment reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getPackage_Packages()
     * @model containment="true"
     * @generated
     */
    EList<Package> getPackages();

} // Package
