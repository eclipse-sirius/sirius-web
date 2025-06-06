/*******************************************************************************
 * Copyright (c) 2024, 2025 Obeo.
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
package org.eclipse.sirius.components.view.tree.util;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.Switch;
import org.eclipse.sirius.components.view.RepresentationDescription;
import org.eclipse.sirius.components.view.tree.CustomTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.FetchTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.ForTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.IfTreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.SingleClickTreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeDescription;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreeItemLabelDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelElementDescription;
import org.eclipse.sirius.components.view.tree.TreeItemLabelFragmentDescription;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> The <b>Switch</b> for the model's inheritance hierarchy. It supports the call
 * {@link #doSwitch(EObject) doSwitch(object)} to invoke the <code>caseXXX</code> method for each class of the model,
 * starting with the actual class of the object and proceeding up the inheritance hierarchy until a non-null result is
 * returned, which is the result of the switch. <!-- end-user-doc -->
 *
 * @see org.eclipse.sirius.components.view.tree.TreePackage
 * @generated
 */
public class TreeSwitch<T> extends Switch<T> {

    /**
     * The cached model package <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected static TreePackage modelPackage;

    /**
     * Creates an instance of the switch. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public TreeSwitch() {
        if (modelPackage == null) {
            modelPackage = TreePackage.eINSTANCE;
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
            case TreePackage.TREE_DESCRIPTION: {
                TreeDescription treeDescription = (TreeDescription) theEObject;
                T result = this.caseTreeDescription(treeDescription);
                if (result == null)
                    result = this.caseRepresentationDescription(treeDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.TREE_ITEM_LABEL_DESCRIPTION: {
                TreeItemLabelDescription treeItemLabelDescription = (TreeItemLabelDescription) theEObject;
                T result = this.caseTreeItemLabelDescription(treeItemLabelDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.IF_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION: {
                IfTreeItemLabelElementDescription ifTreeItemLabelElementDescription = (IfTreeItemLabelElementDescription) theEObject;
                T result = this.caseIfTreeItemLabelElementDescription(ifTreeItemLabelElementDescription);
                if (result == null)
                    result = this.caseTreeItemLabelElementDescription(ifTreeItemLabelElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.FOR_TREE_ITEM_LABEL_ELEMENT_DESCRIPTION: {
                ForTreeItemLabelElementDescription forTreeItemLabelElementDescription = (ForTreeItemLabelElementDescription) theEObject;
                T result = this.caseForTreeItemLabelElementDescription(forTreeItemLabelElementDescription);
                if (result == null)
                    result = this.caseTreeItemLabelElementDescription(forTreeItemLabelElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.TREE_ITEM_LABEL_FRAGMENT_DESCRIPTION: {
                TreeItemLabelFragmentDescription treeItemLabelFragmentDescription = (TreeItemLabelFragmentDescription) theEObject;
                T result = this.caseTreeItemLabelFragmentDescription(treeItemLabelFragmentDescription);
                if (result == null)
                    result = this.caseTreeItemLabelElementDescription(treeItemLabelFragmentDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.TREE_ITEM_LABEL_ELEMENT_DESCRIPTION: {
                TreeItemLabelElementDescription treeItemLabelElementDescription = (TreeItemLabelElementDescription) theEObject;
                T result = this.caseTreeItemLabelElementDescription(treeItemLabelElementDescription);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY: {
                TreeItemContextMenuEntry treeItemContextMenuEntry = (TreeItemContextMenuEntry) theEObject;
                T result = this.caseTreeItemContextMenuEntry(treeItemContextMenuEntry);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.SINGLE_CLICK_TREE_ITEM_CONTEXT_MENU_ENTRY: {
                SingleClickTreeItemContextMenuEntry singleClickTreeItemContextMenuEntry = (SingleClickTreeItemContextMenuEntry) theEObject;
                T result = this.caseSingleClickTreeItemContextMenuEntry(singleClickTreeItemContextMenuEntry);
                if (result == null)
                    result = this.caseTreeItemContextMenuEntry(singleClickTreeItemContextMenuEntry);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.FETCH_TREE_ITEM_CONTEXT_MENU_ENTRY: {
                FetchTreeItemContextMenuEntry fetchTreeItemContextMenuEntry = (FetchTreeItemContextMenuEntry) theEObject;
                T result = this.caseFetchTreeItemContextMenuEntry(fetchTreeItemContextMenuEntry);
                if (result == null)
                    result = this.caseTreeItemContextMenuEntry(fetchTreeItemContextMenuEntry);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            case TreePackage.CUSTOM_TREE_ITEM_CONTEXT_MENU_ENTRY: {
                CustomTreeItemContextMenuEntry customTreeItemContextMenuEntry = (CustomTreeItemContextMenuEntry) theEObject;
                T result = this.caseCustomTreeItemContextMenuEntry(customTreeItemContextMenuEntry);
                if (result == null)
                    result = this.caseTreeItemContextMenuEntry(customTreeItemContextMenuEntry);
                if (result == null)
                    result = this.defaultCase(theEObject);
                return result;
            }
            default:
                return this.defaultCase(theEObject);
        }
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Description</em>'. <!-- begin-user-doc -->
     * This implementation returns null; returning a non-null result will terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTreeDescription(TreeDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Label Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Label Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTreeItemLabelDescription(TreeItemLabelDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>If Tree Item Label Element
     * Description</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>If Tree Item Label Element
     *         Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseIfTreeItemLabelElementDescription(IfTreeItemLabelElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>For Tree Item Label Element
     * Description</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will
     * terminate the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>For Tree Item Label Element
     *         Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseForTreeItemLabelElementDescription(ForTreeItemLabelElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Label Fragment Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Label Fragment Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTreeItemLabelFragmentDescription(TreeItemLabelFragmentDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Label Element Description</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Label Element Description</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTreeItemLabelElementDescription(TreeItemLabelElementDescription object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Item Context Menu Entry</em>'. <!--
     * begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch. <!--
     * end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Item Context Menu Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseTreeItemContextMenuEntry(TreeItemContextMenuEntry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Single Click Tree Item Context Menu
     * Entry</em>'. <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate
     * the switch. <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Single Click Tree Item Context Menu
     *         Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseSingleClickTreeItemContextMenuEntry(SingleClickTreeItemContextMenuEntry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Fetch Tree Item Context Menu Entry</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Fetch Tree Item Context Menu Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseFetchTreeItemContextMenuEntry(FetchTreeItemContextMenuEntry object) {
        return null;
    }

    /**
     * Returns the result of interpreting the object as an instance of '<em>Custom Tree Item Context Menu Entry</em>'.
     * <!-- begin-user-doc --> This implementation returns null; returning a non-null result will terminate the switch.
     * <!-- end-user-doc -->
     *
     * @param object
     *            the target of the switch.
     * @return the result of interpreting the object as an instance of '<em>Custom Tree Item Context Menu Entry</em>'.
     * @see #doSwitch(org.eclipse.emf.ecore.EObject) doSwitch(EObject)
     * @generated
     */
    public T caseCustomTreeItemContextMenuEntry(CustomTreeItemContextMenuEntry object) {
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

} // TreeSwitch
