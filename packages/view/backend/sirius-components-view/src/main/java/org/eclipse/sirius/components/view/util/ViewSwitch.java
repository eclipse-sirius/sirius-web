/*******************************************************************************
 * Copyright (c) 2021, 2024 Obeo.
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
package org.eclipse.sirius.components.view.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.ChangeContext;
import org.eclipse.sirius.components.view.ColorPalette;
import org.eclipse.sirius.components.view.Conditional;
import org.eclipse.sirius.components.view.CreateInstance;
import org.eclipse.sirius.components.view.DeleteElement;
import org.eclipse.sirius.components.view.FixedColor;
import org.eclipse.sirius.components.view.For;
import org.eclipse.sirius.components.view.If;
import org.eclipse.sirius.components.view.LabelStyle;
import org.eclipse.sirius.components.view.Let;
import org.eclipse.sirius.components.view.Operation;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.SetValue;
import org.eclipse.sirius.components.view.UnsetValue;
import org.eclipse.sirius.components.view.UserColor;
import org.eclipse.sirius.components.view.View;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.ViewPackage
 * @generated
 */
public class ViewSwitch<T> extends Switch<T> {
    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static ViewPackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public ViewSwitch() {
        if (modelPackage == null) {
            modelPackage = ViewPackage.eINSTANCE;
        }
    }

    /**
     * Checks whether this is a switch for the given package. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @param ePackage
     *            the package in question.
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
            case ViewPackage.VIEW: {
                View view = (View) theEObject;
                T result = this.caseView(view);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.COLOR_PALETTE: {
                ColorPalette colorPalette = (ColorPalette) theEObject;
                T result = this.caseColorPalette(colorPalette);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.FIXED_COLOR: {
                FixedColor fixedColor = (FixedColor) theEObject;
                T result = this.caseFixedColor(fixedColor);
                if (result == null)
                    result = this.caseUserColor(fixedColor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.USER_COLOR: {
                UserColor userColor = (UserColor) theEObject;
                T result = this.caseUserColor(userColor);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.REPRESENTATION_DESCRIPTION: {
                RepresentationDescription representationDescription = (RepresentationDescription) theEObject;
                T result = this.caseRepresentationDescription(representationDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LABEL_STYLE: {
                LabelStyle labelStyle = (LabelStyle) theEObject;
                T result = this.caseLabelStyle(labelStyle);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.OPERATION: {
                Operation operation = (Operation) theEObject;
                T result = this.caseOperation(operation);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CHANGE_CONTEXT: {
                ChangeContext changeContext = (ChangeContext) theEObject;
                T result = this.caseChangeContext(changeContext);
                if (result == null)
                    result = this.caseOperation(changeContext);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CREATE_INSTANCE: {
                CreateInstance createInstance = (CreateInstance) theEObject;
                T result = this.caseCreateInstance(createInstance);
                if (result == null)
                    result = this.caseOperation(createInstance);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.SET_VALUE: {
                SetValue setValue = (SetValue) theEObject;
                T result = this.caseSetValue(setValue);
                if (result == null)
                    result = this.caseOperation(setValue);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.UNSET_VALUE: {
                UnsetValue unsetValue = (UnsetValue) theEObject;
                T result = this.caseUnsetValue(unsetValue);
                if (result == null)
                    result = this.caseOperation(unsetValue);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.DELETE_ELEMENT: {
                DeleteElement deleteElement = (DeleteElement) theEObject;
                T result = this.caseDeleteElement(deleteElement);
                if (result == null)
                    result = this.caseOperation(deleteElement);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.LET: {
                Let let = (Let) theEObject;
                T result = this.caseLet(let);
                if (result == null)
                    result = this.caseOperation(let);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.IF: {
                If if_ = (If) theEObject;
                T result = this.caseIf(if_);
                if (result == null)
                    result = this.caseOperation(if_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.CONDITIONAL: {
                Conditional conditional = (Conditional) theEObject;
                T result = this.caseConditional(conditional);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case ViewPackage.FOR: {
                For for_ = (For) theEObject;
                T result = this.caseFor(for_);
                if (result == null)
                    result = this.caseOperation(for_);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>View</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>View</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseView(View object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Color Palette</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Color Palette</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseColorPalette(ColorPalette object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fixed Color</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fixed Color</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFixedColor(FixedColor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>User Color</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>User Color</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUserColor(UserColor object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Representation Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Representation Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseRepresentationDescription(RepresentationDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Label Style</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Label Style</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLabelStyle(LabelStyle object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Operation</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Operation</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseOperation(Operation object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Change Context</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Change Context</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseChangeContext(ChangeContext object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Create Instance</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Create Instance</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCreateInstance(CreateInstance object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Set Value</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Set Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSetValue(SetValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Unset Value</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Unset Value</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseUnsetValue(UnsetValue object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Delete Element</em>'. <!-- begin-user-doc
     * --> This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc
     * -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Delete Element</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseDeleteElement(DeleteElement object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Let</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Let</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseLet(Let object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>If</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>If</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIf(If object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Conditional</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Conditional</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseConditional(Conditional object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>For</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>For</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFor(For object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>EObject</em>'. <!-- begin-user-doc --> This
     * implementation returns null; returning a non-null result will terminate the switch, but this is the last case
     * anyway. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>EObject</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject)
     * @generated
     */
    @Override
    public T defaultCase(EObject object) {
        return null;
    }

} // ViewSwitch
