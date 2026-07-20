/*******************************************************************************
 * Copyright (c) 2024, 2026 Obeo.
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
package org.eclipse.sirius.components.view.tree.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.components.view.KeyBinding;
import org.eclipse.sirius.components.view.tree.TreeItemContextMenuEntry;
import org.eclipse.sirius.components.view.tree.TreePackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Item Context Menu Entry</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getName <em>Name</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getPreconditionExpression <em>Precondition Expression</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.tree.impl.TreeItemContextMenuEntryImpl#getKeyBindings <em>Key Bindings</em>}</li>
 * </ul>
 *
 * @generated
 */
public abstract class TreeItemContextMenuEntryImpl extends MinimalEObjectImpl.Container implements TreeItemContextMenuEntry {

    /**
     * The default value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected static final String NAME_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getName() <em>Name</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getName()
     * @generated
     * @ordered
     */
    protected String name = NAME_EDEFAULT;

    /**
     * The default value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected static final String PRECONDITION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getPreconditionExpression() <em>Precondition Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getPreconditionExpression()
     * @generated
     * @ordered
     */
    protected String preconditionExpression = PRECONDITION_EXPRESSION_EDEFAULT;

    /**
     * The cached value of the '{@link #getKeyBindings() <em>Key Bindings</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getKeyBindings()
     * @generated
     * @ordered
     */
    protected EList<KeyBinding> keyBindings;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected TreeItemContextMenuEntryImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return TreePackage.Literals.TREE_ITEM_CONTEXT_MENU_ENTRY;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getName() {
		return name;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME, oldName, name));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getPreconditionExpression() {
		return preconditionExpression;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setPreconditionExpression(String newPreconditionExpression) {
		String oldPreconditionExpression = preconditionExpression;
		preconditionExpression = newPreconditionExpression;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION, oldPreconditionExpression, preconditionExpression));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public EList<KeyBinding> getKeyBindings() {
		if (keyBindings == null)
		{
			keyBindings = new EObjectContainmentEList<KeyBinding>(KeyBinding.class, this, TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS);
		}
		return keyBindings;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch (featureID)
		{
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS:
				return ((InternalEList<?>)getKeyBindings()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
				return getName();
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
				return getPreconditionExpression();
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS:
				return getKeyBindings();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
				setName((String)newValue);
				return;
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
				setPreconditionExpression((String)newValue);
				return;
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS:
				getKeyBindings().clear();
				getKeyBindings().addAll((Collection<? extends KeyBinding>)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eUnset(int featureID) {
		switch (featureID)
		{
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
				setName(NAME_EDEFAULT);
				return;
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
				setPreconditionExpression(PRECONDITION_EXPRESSION_EDEFAULT);
				return;
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS:
				getKeyBindings().clear();
				return;
		}
		super.eUnset(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean eIsSet(int featureID) {
		switch (featureID)
		{
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__PRECONDITION_EXPRESSION:
				return PRECONDITION_EXPRESSION_EDEFAULT == null ? preconditionExpression != null : !PRECONDITION_EXPRESSION_EDEFAULT.equals(preconditionExpression);
			case TreePackage.TREE_ITEM_CONTEXT_MENU_ENTRY__KEY_BINDINGS:
				return keyBindings != null && !keyBindings.isEmpty();
		}
		return super.eIsSet(featureID);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuilder result = new StringBuilder(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", preconditionExpression: ");
		result.append(preconditionExpression);
		result.append(')');
		return result.toString();
	}

} // TreeItemContextMenuEntryImpl
