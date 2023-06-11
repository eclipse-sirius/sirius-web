/**
 * Copyright (c) 2021, 2023 Obeo.
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
package org.eclipse.sirius.components.view.impl;

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
import org.eclipse.sirius.components.view.DynamicDialogDescription;
import org.eclipse.sirius.components.view.DynamicDialogFolder;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Dynamic Dialog Folder</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogFolderImpl#getSubFolders <em>Sub Folders</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogFolderImpl#getName <em>Name</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DynamicDialogFolderImpl#getDynamicDialogs <em>Dynamic
 * Dialogs</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DynamicDialogFolderImpl extends MinimalEObjectImpl.Container implements DynamicDialogFolder {
    /**
     * The cached value of the '{@link #getSubFolders() <em>Sub Folders</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSubFolders()
     * @generated
     * @ordered
     */
    protected EList<DynamicDialogFolder> subFolders;

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
     * The cached value of the '{@link #getDynamicDialogs() <em>Dynamic Dialogs</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDynamicDialogs()
     * @generated
     * @ordered
     */
    protected EList<DynamicDialogDescription> dynamicDialogs;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DynamicDialogFolderImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DYNAMIC_DIALOG_FOLDER;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DynamicDialogFolder> getSubFolders() {
        if (this.subFolders == null) {
            this.subFolders = new EObjectContainmentEList<>(DynamicDialogFolder.class, this, ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS);
        }
        return this.subFolders;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getName() {
        return this.name;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setName(String newName) {
        String oldName = this.name;
        this.name = newName;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DYNAMIC_DIALOG_FOLDER__NAME, oldName, this.name));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DynamicDialogDescription> getDynamicDialogs() {
        if (this.dynamicDialogs == null) {
            this.dynamicDialogs = new EObjectContainmentEList<>(DynamicDialogDescription.class, this, ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS);
        }
        return this.dynamicDialogs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS:
                return ((InternalEList<?>) this.getSubFolders()).basicRemove(otherEnd, msgs);
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS:
                return ((InternalEList<?>) this.getDynamicDialogs()).basicRemove(otherEnd, msgs);
        }
        return super.eInverseRemove(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public Object eGet(int featureID, boolean resolve, boolean coreType) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS:
                return this.getSubFolders();
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__NAME:
                return this.getName();
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS:
                return this.getDynamicDialogs();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @SuppressWarnings("unchecked")
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS:
                this.getSubFolders().clear();
                this.getSubFolders().addAll((Collection<? extends DynamicDialogFolder>) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__NAME:
                this.setName((String) newValue);
                return;
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS:
                this.getDynamicDialogs().clear();
                this.getDynamicDialogs().addAll((Collection<? extends DynamicDialogDescription>) newValue);
                return;
        }
        super.eSet(featureID, newValue);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eUnset(int featureID) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS:
                this.getSubFolders().clear();
                return;
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__NAME:
                this.setName(NAME_EDEFAULT);
                return;
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS:
                this.getDynamicDialogs().clear();
                return;
        }
        super.eUnset(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean eIsSet(int featureID) {
        switch (featureID) {
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__SUB_FOLDERS:
                return this.subFolders != null && !this.subFolders.isEmpty();
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__NAME:
                return NAME_EDEFAULT == null ? this.name != null : !NAME_EDEFAULT.equals(this.name);
            case ViewPackage.DYNAMIC_DIALOG_FOLDER__DYNAMIC_DIALOGS:
                return this.dynamicDialogs != null && !this.dynamicDialogs.isEmpty();
        }
        return super.eIsSet(featureID);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String toString() {
        if (this.eIsProxy())
            return super.toString();

        StringBuilder result = new StringBuilder(super.toString());
        result.append(" (name: ");
        result.append(this.name);
        result.append(')');
        return result.toString();
    }

} // DynamicDialogFolderImpl
