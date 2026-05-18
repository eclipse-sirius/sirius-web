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
package org.eclipse.sirius.components.papaya;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Dependency Link</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.DependencyLink#getTargetKind <em>Target Kind</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.DependencyLink#getSourceKind <em>Source Kind</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.DependencyLink#getSource <em>Source</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.DependencyLink#getDuration <em>Duration</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDependencyLink()
 * @model
 * @generated
 */
public interface DependencyLink extends EObject {
    /**
     * Returns the value of the '<em><b>Target Kind</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.papaya.StartOrEnd}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Target Kind</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.StartOrEnd
     * @see #setTargetKind(StartOrEnd)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDependencyLink_TargetKind()
     * @model
     * @generated
     */
    StartOrEnd getTargetKind();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.DependencyLink#getTargetKind <em>Target
     * Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Target Kind</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.StartOrEnd
     * @see #getTargetKind()
     * @generated
     */
    void setTargetKind(StartOrEnd value);

    /**
     * Returns the value of the '<em><b>Source Kind</b></em>' attribute. The literals are from the enumeration
     * {@link org.eclipse.sirius.components.papaya.StartOrEnd}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source Kind</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.StartOrEnd
     * @see #setSourceKind(StartOrEnd)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDependencyLink_SourceKind()
     * @model
     * @generated
     */
    StartOrEnd getSourceKind();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.DependencyLink#getSourceKind <em>Source
     * Kind</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source Kind</em>' attribute.
     * @see org.eclipse.sirius.components.papaya.StartOrEnd
     * @see #getSourceKind()
     * @generated
     */
    void setSourceKind(StartOrEnd value);

    /**
     * Returns the value of the '<em><b>Source</b></em>' reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Source</em>' reference.
     * @see #setSource(Task)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDependencyLink_Source()
     * @model
     * @generated
     */
    Task getSource();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.DependencyLink#getSource <em>Source</em>}'
     * reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Source</em>' reference.
     * @see #getSource()
     * @generated
     */
    void setSource(Task value);

    /**
     * Returns the value of the '<em><b>Duration</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Duration</em>' attribute.
     * @see #setDuration(int)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getDependencyLink_Duration()
     * @model
     * @generated
     */
    int getDuration();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.DependencyLink#getDuration <em>Duration</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Duration</em>' attribute.
     * @see #getDuration()
     * @generated
     */
    void setDuration(int value);

} // DependencyLink
