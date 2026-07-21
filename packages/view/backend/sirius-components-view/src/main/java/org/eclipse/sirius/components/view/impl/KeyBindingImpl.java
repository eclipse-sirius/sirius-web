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
package org.eclipse.sirius.components.view.impl;

import java.util.Objects;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;
import org.eclipse.sirius.components.view.KeyBinding;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Key Binding</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link org.eclipse.sirius.components.view.impl.KeyBindingImpl#isCtrl <em>Ctrl</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.KeyBindingImpl#isAlt <em>Alt</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.KeyBindingImpl#isMeta <em>Meta</em>}</li>
 *   <li>{@link org.eclipse.sirius.components.view.impl.KeyBindingImpl#getKey <em>Key</em>}</li>
 * </ul>
 *
 * @generated
 */
public class KeyBindingImpl extends MinimalEObjectImpl.Container implements KeyBinding {
    /**
     * The default value of the '{@link #isCtrl() <em>Ctrl</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isCtrl()
     * @generated
     * @ordered
     */
    protected static final boolean CTRL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isCtrl() <em>Ctrl</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isCtrl()
     * @generated
     * @ordered
     */
    protected boolean ctrl = CTRL_EDEFAULT;

    /**
	 * The default value of the '{@link #isAlt() <em>Alt</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAlt()
	 * @generated
	 * @ordered
	 */
    protected static final boolean ALT_EDEFAULT = false;

    /**
	 * The cached value of the '{@link #isAlt() <em>Alt</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #isAlt()
	 * @generated
	 * @ordered
	 */
    protected boolean alt = ALT_EDEFAULT;

    /**
     * The default value of the '{@link #isMeta() <em>Meta</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMeta()
     * @generated
     * @ordered
     */
    protected static final boolean META_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMeta() <em>Meta</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #isMeta()
     * @generated
     * @ordered
     */
    protected boolean meta = META_EDEFAULT;

    /**
     * The default value of the '{@link #getKey() <em>Key</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getKey()
     * @generated
     * @ordered
     */
    protected static final String KEY_EDEFAULT = null;

    /**
	 * The cached value of the '{@link #getKey() <em>Key</em>}' attribute.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @see #getKey()
	 * @generated
	 * @ordered
	 */
    protected String key = KEY_EDEFAULT;

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    protected KeyBindingImpl() {
		super();
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    protected EClass eStaticClass() {
		return ViewPackage.Literals.KEY_BINDING;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isCtrl() {
		return ctrl;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setCtrl(boolean newCtrl) {
		boolean oldCtrl = ctrl;
		ctrl = newCtrl;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.KEY_BINDING__CTRL, oldCtrl, ctrl));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isAlt() {
		return alt;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setAlt(boolean newAlt) {
		boolean oldAlt = alt;
		alt = newAlt;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.KEY_BINDING__ALT, oldAlt, alt));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public boolean isMeta() {
		return meta;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setMeta(boolean newMeta) {
		boolean oldMeta = meta;
		meta = newMeta;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.KEY_BINDING__META, oldMeta, meta));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public String getKey() {
		return key;
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void setKey(String newKey) {
		String oldKey = key;
		key = newKey;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.KEY_BINDING__KEY, oldKey, key));
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID)
		{
			case ViewPackage.KEY_BINDING__CTRL:
				return isCtrl();
			case ViewPackage.KEY_BINDING__ALT:
				return isAlt();
			case ViewPackage.KEY_BINDING__META:
				return isMeta();
			case ViewPackage.KEY_BINDING__KEY:
				return getKey();
		}
		return super.eGet(featureID, resolve, coreType);
	}

    /**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * @generated
	 */
    @Override
    public void eSet(int featureID, Object newValue) {
		switch (featureID)
		{
			case ViewPackage.KEY_BINDING__CTRL:
				setCtrl((Boolean)newValue);
				return;
			case ViewPackage.KEY_BINDING__ALT:
				setAlt((Boolean)newValue);
				return;
			case ViewPackage.KEY_BINDING__META:
				setMeta((Boolean)newValue);
				return;
			case ViewPackage.KEY_BINDING__KEY:
				setKey((String)newValue);
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
			case ViewPackage.KEY_BINDING__CTRL:
				setCtrl(CTRL_EDEFAULT);
				return;
			case ViewPackage.KEY_BINDING__ALT:
				setAlt(ALT_EDEFAULT);
				return;
			case ViewPackage.KEY_BINDING__META:
				setMeta(META_EDEFAULT);
				return;
			case ViewPackage.KEY_BINDING__KEY:
				setKey(KEY_EDEFAULT);
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
			case ViewPackage.KEY_BINDING__CTRL:
				return ctrl != CTRL_EDEFAULT;
			case ViewPackage.KEY_BINDING__ALT:
				return alt != ALT_EDEFAULT;
			case ViewPackage.KEY_BINDING__META:
				return meta != META_EDEFAULT;
			case ViewPackage.KEY_BINDING__KEY:
				return KEY_EDEFAULT == null ? key != null : !KEY_EDEFAULT.equals(key);
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
		result.append(" (ctrl: ");
		result.append(ctrl);
		result.append(", alt: ");
		result.append(alt);
		result.append(", meta: ");
		result.append(meta);
		result.append(", key: ");
		result.append(key);
		result.append(')');
		return result.toString();
	}

} // KeyBindingImpl
