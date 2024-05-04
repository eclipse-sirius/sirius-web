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
 * <!-- begin-user-doc --> A representation of the model object '<em><b>Contribution</b></em>'. <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.papaya.Contribution#getRelatedTasks <em>Related Tasks</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Contribution#getTargets <em>Targets</em>}</li>
 * <li>{@link org.eclipse.sirius.components.papaya.Contribution#isDone <em>Done</em>}</li>
 * </ul>
 *
 * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContribution()
 * @model
 * @generated
 */
public interface Contribution extends NamedElement {
    /**
     * Returns the value of the '<em><b>Related Tasks</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.Task}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Related Tasks</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContribution_RelatedTasks()
     * @model
     * @generated
     */
    EList<Task> getRelatedTasks();

    /**
     * Returns the value of the '<em><b>Targets</b></em>' reference list. The list contents are of type
     * {@link org.eclipse.sirius.components.papaya.ModelElement}. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Targets</em>' reference list.
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContribution_Targets()
     * @model
     * @generated
     */
    EList<ModelElement> getTargets();

    /**
     * Returns the value of the '<em><b>Done</b></em>' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the value of the '<em>Done</em>' attribute.
     * @see #setDone(boolean)
     * @see org.eclipse.sirius.components.papaya.PapayaPackage#getContribution_Done()
     * @model
     * @generated
     */
    boolean isDone();

    /**
     * Sets the value of the '{@link org.eclipse.sirius.components.papaya.Contribution#isDone <em>Done</em>}' attribute.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param value
     *            the new value of the '<em>Done</em>' attribute.
     * @see #isDone()
     * @generated
     */
    void setDone(boolean value);

} // Contribution
