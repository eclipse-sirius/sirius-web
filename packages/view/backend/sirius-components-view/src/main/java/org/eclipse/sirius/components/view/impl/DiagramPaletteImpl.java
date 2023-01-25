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
import org.eclipse.sirius.components.view.DiagramPalette;
import org.eclipse.sirius.components.view.DropTool;
import org.eclipse.sirius.components.view.NodeTool;
import org.eclipse.sirius.components.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Diagram Palette</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.impl.DiagramPaletteImpl#getDropTool <em>Drop Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.impl.DiagramPaletteImpl#getSingleTargetTools <em>Single Target
 * Tools</em>}</li>
 * </ul>
 *
 * @generated
 */
public class DiagramPaletteImpl extends MinimalEObjectImpl.Container implements DiagramPalette {
    /**
     * The cached value of the '{@link #getDropTool() <em>Drop Tool</em>}' containment reference. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getDropTool()
     * @generated
     * @ordered
     */
    protected DropTool dropTool;

    /**
     * The cached value of the '{@link #getNodeTools() <em>Node Tools</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNodeTools()
     * @generated
     * @ordered
     */
    protected EList<NodeTool> nodeTools;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected DiagramPaletteImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.DIAGRAM_PALETTE;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropTool getDropTool() {
        return this.dropTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDropTool(DropTool newDropTool, NotificationChain msgs) {
        DropTool oldDropTool = this.dropTool;
        this.dropTool = newDropTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_PALETTE__DROP_TOOL, oldDropTool, newDropTool);
            if (msgs == null)
                msgs = notification;
            else
                msgs.add(notification);
        }
        return msgs;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDropTool(DropTool newDropTool) {
        if (newDropTool != this.dropTool) {
            NotificationChain msgs = null;
            if (this.dropTool != null)
                msgs = ((InternalEObject) this.dropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DIAGRAM_PALETTE__DROP_TOOL, null, msgs);
            if (newDropTool != null)
                msgs = ((InternalEObject) newDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.DIAGRAM_PALETTE__DROP_TOOL, null, msgs);
            msgs = this.basicSetDropTool(newDropTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.DIAGRAM_PALETTE__DROP_TOOL, newDropTool, newDropTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case ViewPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.basicSetDropTool(null, msgs);
            case ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
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
            case ViewPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.getDropTool();
            case ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return this.getNodeTools();
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
            case ViewPackage.DIAGRAM_PALETTE__DROP_TOOL:
                this.setDropTool((DropTool) newValue);
                return;
            case ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
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
            case ViewPackage.DIAGRAM_PALETTE__DROP_TOOL:
                this.setDropTool((DropTool) null);
                return;
            case ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
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
            case ViewPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.dropTool != null;
            case ViewPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // DiagramPaletteImpl
