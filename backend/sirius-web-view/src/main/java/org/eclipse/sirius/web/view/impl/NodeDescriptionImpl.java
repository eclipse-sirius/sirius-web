/*******************************************************************************
 * Copyright (c) 2021 Obeo.
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
package org.eclipse.sirius.web.view.impl;

import java.util.Collection;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.eclipse.sirius.web.view.ConditionalNodeStyle;
import org.eclipse.sirius.web.view.NodeDescription;
import org.eclipse.sirius.web.view.NodeStyle;
import org.eclipse.sirius.web.view.NodeTool;
import org.eclipse.sirius.web.view.ViewPackage;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Node Description</b></em>'. <!-- end-user-doc
 * -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.web.view.impl.NodeDescriptionImpl#getChildrenDescriptions <em>Children
 * Descriptions</em>}</li>
 * </ul>
 *
 * @generated
 */
public class NodeDescriptionImpl extends DiagramElementDescriptionImpl implements NodeDescription {
    /**
     * The cached value of the '{@link #getChildrenDescriptions() <em>Children Descriptions</em>}' containment reference
     * list. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getChildrenDescriptions()
     * @generated
     * @ordered
     */
    protected EList<NodeDescription> childrenDescriptions;

    /**
     * The cached value of the '{@link #getStyle() <em>Style</em>}' containment reference. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getStyle()
     * @generated
     * @ordered
     */
    protected NodeStyle style;

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
     * The cached value of the '{@link #getConditionalStyles() <em>Conditional Styles</em>}' containment reference list.
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getConditionalStyles()
     * @generated
     * @ordered
     */
    protected EList<ConditionalNodeStyle> conditionalStyles;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected NodeDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return ViewPackage.Literals.NODE_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeDescription> getChildrenDescriptions() {
        if (this.childrenDescriptions == null) {
            this.childrenDescriptions = new EObjectContainmentEList<>(NodeDescription.class, this, ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS);
        }
        return this.childrenDescriptions;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NodeStyle getStyle() {
        return this.style;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetStyle(NodeStyle newStyle, NotificationChain msgs) {
        NodeStyle oldStyle = this.style;
        this.style = newStyle;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_DESCRIPTION__STYLE, oldStyle, newStyle);
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
    public void setStyle(NodeStyle newStyle) {
        if (newStyle != this.style) {
            NotificationChain msgs = null;
            if (this.style != null)
                msgs = ((InternalEObject) this.style).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_DESCRIPTION__STYLE, null, msgs);
            if (newStyle != null)
                msgs = ((InternalEObject) newStyle).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - ViewPackage.NODE_DESCRIPTION__STYLE, null, msgs);
            msgs = this.basicSetStyle(newStyle, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, ViewPackage.NODE_DESCRIPTION__STYLE, newStyle, newStyle));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<NodeTool> getNodeTools() {
        if (this.nodeTools == null) {
            this.nodeTools = new EObjectContainmentEList<>(NodeTool.class, this, ViewPackage.NODE_DESCRIPTION__NODE_TOOLS);
        }
        return this.nodeTools;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public EList<ConditionalNodeStyle> getConditionalStyles() {
        if (this.conditionalStyles == null) {
            this.conditionalStyles = new EObjectContainmentEList<>(ConditionalNodeStyle.class, this, ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES);
        }
        return this.conditionalStyles;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            return ((InternalEList<?>) this.getChildrenDescriptions()).basicRemove(otherEnd, msgs);
        case ViewPackage.NODE_DESCRIPTION__STYLE:
            return this.basicSetStyle(null, msgs);
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
            return ((InternalEList<?>) this.getNodeTools()).basicRemove(otherEnd, msgs);
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            return ((InternalEList<?>) this.getConditionalStyles()).basicRemove(otherEnd, msgs);
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
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            return this.getChildrenDescriptions();
        case ViewPackage.NODE_DESCRIPTION__STYLE:
            return this.getStyle();
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
            return this.getNodeTools();
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            return this.getConditionalStyles();
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
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            this.getChildrenDescriptions().clear();
            this.getChildrenDescriptions().addAll((Collection<? extends NodeDescription>) newValue);
            return;
        case ViewPackage.NODE_DESCRIPTION__STYLE:
            this.setStyle((NodeStyle) newValue);
            return;
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
            this.getNodeTools().clear();
            this.getNodeTools().addAll((Collection<? extends NodeTool>) newValue);
            return;
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
            this.getConditionalStyles().addAll((Collection<? extends ConditionalNodeStyle>) newValue);
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
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            this.getChildrenDescriptions().clear();
            return;
        case ViewPackage.NODE_DESCRIPTION__STYLE:
            this.setStyle((NodeStyle) null);
            return;
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
            this.getNodeTools().clear();
            return;
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            this.getConditionalStyles().clear();
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
        case ViewPackage.NODE_DESCRIPTION__CHILDREN_DESCRIPTIONS:
            return this.childrenDescriptions != null && !this.childrenDescriptions.isEmpty();
        case ViewPackage.NODE_DESCRIPTION__STYLE:
            return this.style != null;
        case ViewPackage.NODE_DESCRIPTION__NODE_TOOLS:
            return this.nodeTools != null && !this.nodeTools.isEmpty();
        case ViewPackage.NODE_DESCRIPTION__CONDITIONAL_STYLES:
            return this.conditionalStyles != null && !this.conditionalStyles.isEmpty();
        }
        return super.eIsSet(featureID);
    }

} // NodeDescriptionImpl
