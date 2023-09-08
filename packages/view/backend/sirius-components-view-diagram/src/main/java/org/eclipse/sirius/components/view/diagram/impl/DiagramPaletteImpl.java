/*******************************************************************************
 * Copyright (c) 2023 Obeo.
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
package org.eclipse.sirius.components.view.diagram.impl;

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
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.DiagramPalette;
import org.eclipse.sirius.components.view.diagram.DiagramToolSection;
import org.eclipse.sirius.components.view.diagram.DropNodeTool;
import org.eclipse.sirius.components.view.diagram.DropTool;
import org.eclipse.sirius.components.view.diagram.NodeTool;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Palette</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl#getDropTool <em>Drop Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl#getDropNodeTool <em>Drop Node
 * Tool</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl#getNodeTools <em>Node Tools</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.DiagramPaletteImpl#getToolSections <em>Tool
 * Sections</em>}</li>
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
     * The cached value of the '{@link #getDropNodeTool() <em>Drop Node Tool</em>}' containment reference. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDropNodeTool()
     * @generated
     * @ordered
     */
    protected DropNodeTool dropNodeTool;

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
     * The cached value of the '{@link #getToolSections() <em>Tool Sections</em>}' containment reference list. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getToolSections()
     * @generated
     * @ordered
     */
    protected EList<DiagramToolSection> toolSections;

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
        return DiagramPackage.Literals.DIAGRAM_PALETTE;
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
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL, oldDropTool, newDropTool);
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
                msgs = ((InternalEObject) this.dropTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL, null, msgs);
            if (newDropTool != null)
                msgs = ((InternalEObject) newDropTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL, null, msgs);
            msgs = this.basicSetDropTool(newDropTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL, newDropTool, newDropTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public DropNodeTool getDropNodeTool() {
        return this.dropNodeTool;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetDropNodeTool(DropNodeTool newDropNodeTool, NotificationChain msgs) {
        DropNodeTool oldDropNodeTool = this.dropNodeTool;
        this.dropNodeTool = newDropNodeTool;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL, oldDropNodeTool, newDropNodeTool);
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
    public void setDropNodeTool(DropNodeTool newDropNodeTool) {
        if (newDropNodeTool != this.dropNodeTool) {
            NotificationChain msgs = null;
            if (this.dropNodeTool != null)
                msgs = ((InternalEObject) this.dropNodeTool).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL, null, msgs);
            if (newDropNodeTool != null)
                msgs = ((InternalEObject) newDropNodeTool).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL, null, msgs);
            msgs = this.basicSetDropNodeTool(newDropNodeTool, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL, newDropNodeTool, newDropNodeTool));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<DiagramToolSection> getToolSections() {
        if (this.toolSections == null) {
            this.toolSections = new EObjectContainmentEList<>(DiagramToolSection.class, this, DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS);
        }
        return this.toolSections;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.basicSetDropTool(null, msgs);
            case DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL:
                return this.basicSetDropNodeTool(null, msgs);
            case DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
            case DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS:
                return ((InternalEList<?>) this.getToolSections()).basicRemove(otherEnd, msgs);
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
            case DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.getDropTool();
            case DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL:
                return this.getDropNodeTool();
            case DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return this.getNodeTools();
            case DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS:
                return this.getToolSections();
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
            case DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL:
                this.setDropTool((DropTool) newValue);
                return;
            case DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL:
                this.setDropNodeTool((DropNodeTool) newValue);
                return;
            case DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
                return;
            case DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS:
                this.getToolSections().clear();
                this.getToolSections().addAll((Collection<? extends DiagramToolSection>) newValue);
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
            case DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL:
                this.setDropTool((DropTool) null);
                return;
            case DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL:
                this.setDropNodeTool((DropNodeTool) null);
                return;
            case DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                this.getNodeTools().clear();
                return;
            case DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS:
                this.getToolSections().clear();
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
            case DiagramPackage.DIAGRAM_PALETTE__DROP_TOOL:
                return this.dropTool != null;
            case DiagramPackage.DIAGRAM_PALETTE__DROP_NODE_TOOL:
                return this.dropNodeTool != null;
            case DiagramPackage.DIAGRAM_PALETTE__NODE_TOOLS:
                return this.nodeTools != null && !this.nodeTools.isEmpty();
            case DiagramPackage.DIAGRAM_PALETTE__TOOL_SECTIONS:
                return this.toolSections != null && !this.toolSections.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // DiagramPaletteImpl
