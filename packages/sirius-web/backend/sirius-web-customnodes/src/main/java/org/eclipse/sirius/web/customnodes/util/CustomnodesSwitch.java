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
package org.eclipse.sirius.web.customnodes.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.diagram.BorderStyle;
import org.eclipse.sirius.components.view.diagram.NodeStyleDescription;
import org.eclipse.sirius.components.view.diagram.Style;
import org.eclipse.sirius.web.customnodes.CustomnodesPackage;
import org.eclipse.sirius.web.customnodes.EllipseNodeStyleDescription;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @generated
 * @see org.eclipse.sirius.web.customnodes.CustomnodesPackage
 */
public class CustomnodesSwitch<T> extends Switch<T> {

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static CustomnodesPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public CustomnodesSwitch() {
        if (modelPackage == null) {
            modelPackage = CustomnodesPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *         the package in question.
     * @return whether this is a switch for the given package.
     * @generated
     */
    @Override
    protected boolean isSwitchFor(EPackage ePackage) {
        return ePackage == modelPackage;
    }

    /**
     * Calls <code>caseXXX</code> for each class of the model until one returns a non null result; it yields that
     * result. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @return the first non-null result returned by a <code>caseXXX</code> call.
     * @generated
     */
    @Override
    protected T doSwitch(int classifierID, EObject theEObject) {
        switch (classifierID) {
            case CustomnodesPackage.ELLIPSE_NODE_STYLE_DESCRIPTION: {
                EllipseNodeStyleDescription ellipseNodeStyleDescription = (EllipseNodeStyleDescription) theEObject;
                T result = this.caseEllipseNodeStyleDescription(ellipseNodeStyleDescription);
                if (result == null)
                    result = this.caseNodeStyleDescription(ellipseNodeStyleDescription);
                if (result == null)
                    result = this.caseStyle(ellipseNodeStyleDescription);
                if (result == null)
                    result = this.caseBorderStyle(ellipseNodeStyleDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Ellipse Node Style Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *         the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Ellipse Node Style Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseEllipseNodeStyleDescription(EllipseNodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Style</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *         the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Style</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseStyle(Style object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Border Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *         the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Border Style</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseBorderStyle(BorderStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Node Style Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *         the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Node Style Description</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     */
    public T caseNodeStyleDescription(NodeStyleDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *         the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @generated
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // CustomnodesSwitch
