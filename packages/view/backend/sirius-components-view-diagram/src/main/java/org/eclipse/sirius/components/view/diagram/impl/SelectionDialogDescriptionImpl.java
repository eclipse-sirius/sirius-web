/*******************************************************************************
 * Copyright (c) 2021, 2026 Obeo.
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

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.sirius.components.view.diagram.DiagramPackage;
import org.eclipse.sirius.components.view.diagram.SelectionDialogDescription;
import org.eclipse.sirius.components.view.diagram.SelectionDialogTreeDescription;

/**
 * <!-- begin-user-doc --> An implementation of the model object '<em><b>Selection Dialog Description</b></em>'. <!--
 * end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionDialogTreeDescription
 * <em>Selection Dialog Tree Description</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isMultiple
 * <em>Multiple</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#isOptional
 * <em>Optional</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getDefaultTitleExpression
 * <em>Default Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionTitleExpression
 * <em>No Selection Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionTitleExpression
 * <em>With Selection Title Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getDescriptionExpression
 * <em>Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionLabelExpression
 * <em>No Selection Action Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionDescriptionExpression
 * <em>No Selection Action Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionActionLabelExpression
 * <em>With Selection Action Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getWithSelectionActionDescriptionExpression
 * <em>With Selection Action Description Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionActionStatusMessageExpression
 * <em>No Selection Action Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithoutSelectionStatusMessageExpression
 * <em>Selection Required Without Selection Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithSelectionStatusMessageExpression
 * <em>Selection Required With Selection Status Message Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getNoSelectionConfirmButtonLabelExpression
 * <em>No Selection Confirm Button Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression
 * <em>Selection Required Without Selection Confirm Button Label Expression</em>}</li>
 * <li>{@link org.eclipse.sirius.components.view.diagram.impl.SelectionDialogDescriptionImpl#getSelectionRequiredWithSelectionConfirmButtonLabelExpression
 * <em>Selection Required With Selection Confirm Button Label Expression</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SelectionDialogDescriptionImpl extends DialogDescriptionImpl implements SelectionDialogDescription {
    /**
     * The cached value of the '{@link #getSelectionDialogTreeDescription() <em>Selection Dialog Tree Description</em>}'
     * containment reference. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionDialogTreeDescription()
     * @generated
     * @ordered
     */
    protected SelectionDialogTreeDescription selectionDialogTreeDescription;

    /**
     * The default value of the '{@link #isMultiple() <em>Multiple</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isMultiple()
     * @generated
     * @ordered
     */
    protected static final boolean MULTIPLE_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isMultiple() <em>Multiple</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isMultiple()
     * @generated
     * @ordered
     */
    protected boolean multiple = MULTIPLE_EDEFAULT;

    /**
     * The default value of the '{@link #isOptional() <em>Optional</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected static final boolean OPTIONAL_EDEFAULT = false;

    /**
     * The cached value of the '{@link #isOptional() <em>Optional</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #isOptional()
     * @generated
     * @ordered
     */
    protected boolean optional = OPTIONAL_EDEFAULT;

    /**
     * The default value of the '{@link #getDefaultTitleExpression() <em>Default Title Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDefaultTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String DEFAULT_TITLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDefaultTitleExpression() <em>Default Title Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDefaultTitleExpression()
     * @generated
     * @ordered
     */
    protected String defaultTitleExpression = DEFAULT_TITLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getNoSelectionTitleExpression() <em>No Selection Title Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String NO_SELECTION_TITLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNoSelectionTitleExpression() <em>No Selection Title Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionTitleExpression()
     * @generated
     * @ordered
     */
    protected String noSelectionTitleExpression = NO_SELECTION_TITLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getWithSelectionTitleExpression() <em>With Selection Title Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionTitleExpression()
     * @generated
     * @ordered
     */
    protected static final String WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWithSelectionTitleExpression() <em>With Selection Title Expression</em>}'
     * attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionTitleExpression()
     * @generated
     * @ordered
     */
    protected String withSelectionTitleExpression = WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getDescriptionExpression() <em>Description Expression</em>}' attribute. <!--
     * begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getDescriptionExpression()
     * @generated
     * @ordered
     */
    protected String descriptionExpression = DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getNoSelectionActionLabelExpression() <em>No Selection Action Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNoSelectionActionLabelExpression() <em>No Selection Action Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionLabelExpression()
     * @generated
     * @ordered
     */
    protected String noSelectionActionLabelExpression = NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getNoSelectionActionDescriptionExpression() <em>No Selection Action Description
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNoSelectionActionDescriptionExpression() <em>No Selection Action Description
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionDescriptionExpression()
     * @generated
     * @ordered
     */
    protected String noSelectionActionDescriptionExpression = NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getWithSelectionActionLabelExpression() <em>With Selection Action Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionActionLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWithSelectionActionLabelExpression() <em>With Selection Action Label
     * Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionActionLabelExpression()
     * @generated
     * @ordered
     */
    protected String withSelectionActionLabelExpression = WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getWithSelectionActionDescriptionExpression() <em>With Selection Action
     * Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionActionDescriptionExpression()
     * @generated
     * @ordered
     */
    protected static final String WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getWithSelectionActionDescriptionExpression() <em>With Selection Action
     * Description Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getWithSelectionActionDescriptionExpression()
     * @generated
     * @ordered
     */
    protected String withSelectionActionDescriptionExpression = WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getNoSelectionActionStatusMessageExpression() <em>No Selection Action Status
     * Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected static final String NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNoSelectionActionStatusMessageExpression() <em>No Selection Action Status
     * Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionActionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected String noSelectionActionStatusMessageExpression = NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionRequiredWithoutSelectionStatusMessageExpression() <em>Selection
     * Required Without Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getSelectionRequiredWithoutSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionRequiredWithoutSelectionStatusMessageExpression() <em>Selection
     * Required Without Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc
     * -->
     *
     * @see #getSelectionRequiredWithoutSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected String selectionRequiredWithoutSelectionStatusMessageExpression = SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionRequiredWithSelectionStatusMessageExpression() <em>Selection
     * Required With Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionRequiredWithSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionRequiredWithSelectionStatusMessageExpression() <em>Selection
     * Required With Selection Status Message Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getSelectionRequiredWithSelectionStatusMessageExpression()
     * @generated
     * @ordered
     */
    protected String selectionRequiredWithSelectionStatusMessageExpression = SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getNoSelectionConfirmButtonLabelExpression() <em>No Selection Confirm Button
     * Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getNoSelectionConfirmButtonLabelExpression() <em>No Selection Confirm Button
     * Label Expression</em>}' attribute. <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @see #getNoSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected String noSelectionConfirmButtonLabelExpression = NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
     * <em>Selection Required Without Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc
     * --> <!-- end-user-doc -->
     *
     * @see #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression() <em>Selection
     * Required Without Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected String selectionRequiredWithoutSelectionConfirmButtonLabelExpression = SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
     * The default value of the '{@link #getSelectionRequiredWithSelectionConfirmButtonLabelExpression() <em>Selection
     * Required With Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getSelectionRequiredWithSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected static final String SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT = null;

    /**
     * The cached value of the '{@link #getSelectionRequiredWithSelectionConfirmButtonLabelExpression() <em>Selection
     * Required With Selection Confirm Button Label Expression</em>}' attribute. <!-- begin-user-doc --> <!--
     * end-user-doc -->
     *
     * @see #getSelectionRequiredWithSelectionConfirmButtonLabelExpression()
     * @generated
     * @ordered
     */
    protected String selectionRequiredWithSelectionConfirmButtonLabelExpression = SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT;

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    protected SelectionDialogDescriptionImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return DiagramPackage.Literals.SELECTION_DIALOG_DESCRIPTION;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public SelectionDialogTreeDescription getSelectionDialogTreeDescription() {
        return this.selectionDialogTreeDescription;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    public NotificationChain basicSetSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription, NotificationChain msgs) {
        SelectionDialogTreeDescription oldSelectionDialogTreeDescription = this.selectionDialogTreeDescription;
        this.selectionDialogTreeDescription = newSelectionDialogTreeDescription;
        if (this.eNotificationRequired()) {
            ENotificationImpl notification = new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION,
                    oldSelectionDialogTreeDescription, newSelectionDialogTreeDescription);
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
    public void setSelectionDialogTreeDescription(SelectionDialogTreeDescription newSelectionDialogTreeDescription) {
        if (newSelectionDialogTreeDescription != this.selectionDialogTreeDescription) {
            NotificationChain msgs = null;
            if (this.selectionDialogTreeDescription != null)
                msgs = ((InternalEObject) this.selectionDialogTreeDescription).eInverseRemove(this,
                        EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, null, msgs);
            if (newSelectionDialogTreeDescription != null)
                msgs = ((InternalEObject) newSelectionDialogTreeDescription).eInverseAdd(this, EOPPOSITE_FEATURE_BASE - DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION,
                        null, msgs);
            msgs = this.basicSetSelectionDialogTreeDescription(newSelectionDialogTreeDescription, msgs);
            if (msgs != null)
                msgs.dispatch();
        } else if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION, newSelectionDialogTreeDescription,
                    newSelectionDialogTreeDescription));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isMultiple() {
        return this.multiple;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setMultiple(boolean newMultiple) {
        boolean oldMultiple = this.multiple;
        this.multiple = newMultiple;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE, oldMultiple, this.multiple));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public boolean isOptional() {
        return this.optional;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setOptional(boolean newOptional) {
        boolean oldOptional = this.optional;
        this.optional = newOptional;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL, oldOptional, this.optional));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDefaultTitleExpression() {
        return this.defaultTitleExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDefaultTitleExpression(String newDefaultTitleExpression) {
        String oldDefaultTitleExpression = this.defaultTitleExpression;
        this.defaultTitleExpression = newDefaultTitleExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION, oldDefaultTitleExpression, this.defaultTitleExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getNoSelectionTitleExpression() {
        return this.noSelectionTitleExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNoSelectionTitleExpression(String newNoSelectionTitleExpression) {
        String oldNoSelectionTitleExpression = this.noSelectionTitleExpression;
        this.noSelectionTitleExpression = newNoSelectionTitleExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION, oldNoSelectionTitleExpression,
                    this.noSelectionTitleExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getWithSelectionTitleExpression() {
        return this.withSelectionTitleExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWithSelectionTitleExpression(String newWithSelectionTitleExpression) {
        String oldWithSelectionTitleExpression = this.withSelectionTitleExpression;
        this.withSelectionTitleExpression = newWithSelectionTitleExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION, oldWithSelectionTitleExpression,
                    this.withSelectionTitleExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getDescriptionExpression() {
        return this.descriptionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setDescriptionExpression(String newDescriptionExpression) {
        String oldDescriptionExpression = this.descriptionExpression;
        this.descriptionExpression = newDescriptionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION, oldDescriptionExpression, this.descriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getNoSelectionActionLabelExpression() {
        return this.noSelectionActionLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNoSelectionActionLabelExpression(String newNoSelectionActionLabelExpression) {
        String oldNoSelectionActionLabelExpression = this.noSelectionActionLabelExpression;
        this.noSelectionActionLabelExpression = newNoSelectionActionLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION, oldNoSelectionActionLabelExpression,
                    this.noSelectionActionLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getNoSelectionActionDescriptionExpression() {
        return this.noSelectionActionDescriptionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNoSelectionActionDescriptionExpression(String newNoSelectionActionDescriptionExpression) {
        String oldNoSelectionActionDescriptionExpression = this.noSelectionActionDescriptionExpression;
        this.noSelectionActionDescriptionExpression = newNoSelectionActionDescriptionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION,
                    oldNoSelectionActionDescriptionExpression, this.noSelectionActionDescriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getWithSelectionActionLabelExpression() {
        return this.withSelectionActionLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWithSelectionActionLabelExpression(String newWithSelectionActionLabelExpression) {
        String oldWithSelectionActionLabelExpression = this.withSelectionActionLabelExpression;
        this.withSelectionActionLabelExpression = newWithSelectionActionLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION, oldWithSelectionActionLabelExpression,
                    this.withSelectionActionLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getWithSelectionActionDescriptionExpression() {
        return this.withSelectionActionDescriptionExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setWithSelectionActionDescriptionExpression(String newWithSelectionActionDescriptionExpression) {
        String oldWithSelectionActionDescriptionExpression = this.withSelectionActionDescriptionExpression;
        this.withSelectionActionDescriptionExpression = newWithSelectionActionDescriptionExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION,
                    oldWithSelectionActionDescriptionExpression, this.withSelectionActionDescriptionExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getNoSelectionActionStatusMessageExpression() {
        return this.noSelectionActionStatusMessageExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNoSelectionActionStatusMessageExpression(String newNoSelectionActionStatusMessageExpression) {
        String oldNoSelectionActionStatusMessageExpression = this.noSelectionActionStatusMessageExpression;
        this.noSelectionActionStatusMessageExpression = newNoSelectionActionStatusMessageExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION,
                    oldNoSelectionActionStatusMessageExpression, this.noSelectionActionStatusMessageExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionRequiredWithoutSelectionStatusMessageExpression() {
        return this.selectionRequiredWithoutSelectionStatusMessageExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionRequiredWithoutSelectionStatusMessageExpression(String newSelectionRequiredWithoutSelectionStatusMessageExpression) {
        String oldSelectionRequiredWithoutSelectionStatusMessageExpression = this.selectionRequiredWithoutSelectionStatusMessageExpression;
        this.selectionRequiredWithoutSelectionStatusMessageExpression = newSelectionRequiredWithoutSelectionStatusMessageExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION,
                    oldSelectionRequiredWithoutSelectionStatusMessageExpression, this.selectionRequiredWithoutSelectionStatusMessageExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionRequiredWithSelectionStatusMessageExpression() {
        return this.selectionRequiredWithSelectionStatusMessageExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionRequiredWithSelectionStatusMessageExpression(String newSelectionRequiredWithSelectionStatusMessageExpression) {
        String oldSelectionRequiredWithSelectionStatusMessageExpression = this.selectionRequiredWithSelectionStatusMessageExpression;
        this.selectionRequiredWithSelectionStatusMessageExpression = newSelectionRequiredWithSelectionStatusMessageExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION,
                    oldSelectionRequiredWithSelectionStatusMessageExpression, this.selectionRequiredWithSelectionStatusMessageExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getNoSelectionConfirmButtonLabelExpression() {
        return this.noSelectionConfirmButtonLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setNoSelectionConfirmButtonLabelExpression(String newNoSelectionConfirmButtonLabelExpression) {
        String oldNoSelectionConfirmButtonLabelExpression = this.noSelectionConfirmButtonLabelExpression;
        this.noSelectionConfirmButtonLabelExpression = newNoSelectionConfirmButtonLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
                    oldNoSelectionConfirmButtonLabelExpression, this.noSelectionConfirmButtonLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression() {
        return this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(String newSelectionRequiredWithoutSelectionConfirmButtonLabelExpression) {
        String oldSelectionRequiredWithoutSelectionConfirmButtonLabelExpression = this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression;
        this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression = newSelectionRequiredWithoutSelectionConfirmButtonLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
                    oldSelectionRequiredWithoutSelectionConfirmButtonLabelExpression, this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public String getSelectionRequiredWithSelectionConfirmButtonLabelExpression() {
        return this.selectionRequiredWithSelectionConfirmButtonLabelExpression;
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void setSelectionRequiredWithSelectionConfirmButtonLabelExpression(String newSelectionRequiredWithSelectionConfirmButtonLabelExpression) {
        String oldSelectionRequiredWithSelectionConfirmButtonLabelExpression = this.selectionRequiredWithSelectionConfirmButtonLabelExpression;
        this.selectionRequiredWithSelectionConfirmButtonLabelExpression = newSelectionRequiredWithSelectionConfirmButtonLabelExpression;
        if (this.eNotificationRequired())
            this.eNotify(new ENotificationImpl(this, Notification.SET, DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION,
                    oldSelectionRequiredWithSelectionConfirmButtonLabelExpression, this.selectionRequiredWithSelectionConfirmButtonLabelExpression));
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
        switch (featureID) {
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.basicSetSelectionDialogTreeDescription(null, msgs);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.getSelectionDialogTreeDescription();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
                return this.isMultiple();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
                return this.isOptional();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
                return this.getDefaultTitleExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
                return this.getNoSelectionTitleExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
                return this.getWithSelectionTitleExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return this.getDescriptionExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
                return this.getNoSelectionActionLabelExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                return this.getNoSelectionActionDescriptionExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
                return this.getWithSelectionActionLabelExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                return this.getWithSelectionActionDescriptionExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
                return this.getNoSelectionActionStatusMessageExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
                return this.getSelectionRequiredWithoutSelectionStatusMessageExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
                return this.getSelectionRequiredWithSelectionStatusMessageExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return this.getNoSelectionConfirmButtonLabelExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return this.getSelectionRequiredWithoutSelectionConfirmButtonLabelExpression();
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return this.getSelectionRequiredWithSelectionConfirmButtonLabelExpression();
        }
        return super.eGet(featureID, resolve, coreType);
    }

    /**
     * <!-- begin-user-doc --> <!-- end-user-doc -->
     *
     * @generated
     */
    @Override
    public void eSet(int featureID, Object newValue) {
        switch (featureID) {
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                this.setSelectionDialogTreeDescription((SelectionDialogTreeDescription) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
                this.setMultiple((Boolean) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
                this.setOptional((Boolean) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
                this.setDefaultTitleExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
                this.setNoSelectionTitleExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
                this.setWithSelectionTitleExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
                this.setNoSelectionActionLabelExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                this.setNoSelectionActionDescriptionExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
                this.setWithSelectionActionLabelExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                this.setWithSelectionActionDescriptionExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
                this.setNoSelectionActionStatusMessageExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
                this.setSelectionRequiredWithoutSelectionStatusMessageExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
                this.setSelectionRequiredWithSelectionStatusMessageExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setNoSelectionConfirmButtonLabelExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression((String) newValue);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setSelectionRequiredWithSelectionConfirmButtonLabelExpression((String) newValue);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                this.setSelectionDialogTreeDescription((SelectionDialogTreeDescription) null);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
                this.setMultiple(MULTIPLE_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
                this.setOptional(OPTIONAL_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
                this.setDefaultTitleExpression(DEFAULT_TITLE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
                this.setNoSelectionTitleExpression(NO_SELECTION_TITLE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
                this.setWithSelectionTitleExpression(WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                this.setDescriptionExpression(DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
                this.setNoSelectionActionLabelExpression(NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                this.setNoSelectionActionDescriptionExpression(NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
                this.setWithSelectionActionLabelExpression(WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                this.setWithSelectionActionDescriptionExpression(WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
                this.setNoSelectionActionStatusMessageExpression(NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
                this.setSelectionRequiredWithoutSelectionStatusMessageExpression(SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
                this.setSelectionRequiredWithSelectionStatusMessageExpression(SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setNoSelectionConfirmButtonLabelExpression(NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setSelectionRequiredWithoutSelectionConfirmButtonLabelExpression(SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
                return;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                this.setSelectionRequiredWithSelectionConfirmButtonLabelExpression(SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT);
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
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_DIALOG_TREE_DESCRIPTION:
                return this.selectionDialogTreeDescription != null;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__MULTIPLE:
                return this.multiple != MULTIPLE_EDEFAULT;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__OPTIONAL:
                return this.optional != OPTIONAL_EDEFAULT;
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DEFAULT_TITLE_EXPRESSION:
                return DEFAULT_TITLE_EXPRESSION_EDEFAULT == null ? this.defaultTitleExpression != null : !DEFAULT_TITLE_EXPRESSION_EDEFAULT.equals(this.defaultTitleExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_TITLE_EXPRESSION:
                return NO_SELECTION_TITLE_EXPRESSION_EDEFAULT == null ? this.noSelectionTitleExpression != null : !NO_SELECTION_TITLE_EXPRESSION_EDEFAULT.equals(this.noSelectionTitleExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_TITLE_EXPRESSION:
                return WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT == null ? this.withSelectionTitleExpression != null
                        : !WITH_SELECTION_TITLE_EXPRESSION_EDEFAULT.equals(this.withSelectionTitleExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__DESCRIPTION_EXPRESSION:
                return DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.descriptionExpression != null : !DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.descriptionExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_LABEL_EXPRESSION:
                return NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT == null ? this.noSelectionActionLabelExpression != null
                        : !NO_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT.equals(this.noSelectionActionLabelExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                return NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.noSelectionActionDescriptionExpression != null
                        : !NO_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.noSelectionActionDescriptionExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_LABEL_EXPRESSION:
                return WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT == null ? this.withSelectionActionLabelExpression != null
                        : !WITH_SELECTION_ACTION_LABEL_EXPRESSION_EDEFAULT.equals(this.withSelectionActionLabelExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION:
                return WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT == null ? this.withSelectionActionDescriptionExpression != null
                        : !WITH_SELECTION_ACTION_DESCRIPTION_EXPRESSION_EDEFAULT.equals(this.withSelectionActionDescriptionExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION:
                return NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? this.noSelectionActionStatusMessageExpression != null
                        : !NO_SELECTION_ACTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(this.noSelectionActionStatusMessageExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION:
                return SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? this.selectionRequiredWithoutSelectionStatusMessageExpression != null
                        : !SELECTION_REQUIRED_WITHOUT_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(this.selectionRequiredWithoutSelectionStatusMessageExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION:
                return SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT == null ? this.selectionRequiredWithSelectionStatusMessageExpression != null
                        : !SELECTION_REQUIRED_WITH_SELECTION_STATUS_MESSAGE_EXPRESSION_EDEFAULT.equals(this.selectionRequiredWithSelectionStatusMessageExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? this.noSelectionConfirmButtonLabelExpression != null
                        : !NO_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(this.noSelectionConfirmButtonLabelExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression != null
                        : !SELECTION_REQUIRED_WITHOUT_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression);
            case DiagramPackage.SELECTION_DIALOG_DESCRIPTION__SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION:
                return SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT == null ? this.selectionRequiredWithSelectionConfirmButtonLabelExpression != null
                        : !SELECTION_REQUIRED_WITH_SELECTION_CONFIRM_BUTTON_LABEL_EXPRESSION_EDEFAULT.equals(this.selectionRequiredWithSelectionConfirmButtonLabelExpression);
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
        result.append(" (multiple: ");
        result.append(this.multiple);
        result.append(", optional: ");
        result.append(this.optional);
        result.append(", defaultTitleExpression: ");
        result.append(this.defaultTitleExpression);
        result.append(", noSelectionTitleExpression: ");
        result.append(this.noSelectionTitleExpression);
        result.append(", withSelectionTitleExpression: ");
        result.append(this.withSelectionTitleExpression);
        result.append(", descriptionExpression: ");
        result.append(this.descriptionExpression);
        result.append(", noSelectionActionLabelExpression: ");
        result.append(this.noSelectionActionLabelExpression);
        result.append(", noSelectionActionDescriptionExpression: ");
        result.append(this.noSelectionActionDescriptionExpression);
        result.append(", withSelectionActionLabelExpression: ");
        result.append(this.withSelectionActionLabelExpression);
        result.append(", withSelectionActionDescriptionExpression: ");
        result.append(this.withSelectionActionDescriptionExpression);
        result.append(", noSelectionActionStatusMessageExpression: ");
        result.append(this.noSelectionActionStatusMessageExpression);
        result.append(", selectionRequiredWithoutSelectionStatusMessageExpression: ");
        result.append(this.selectionRequiredWithoutSelectionStatusMessageExpression);
        result.append(", selectionRequiredWithSelectionStatusMessageExpression: ");
        result.append(this.selectionRequiredWithSelectionStatusMessageExpression);
        result.append(", noSelectionConfirmButtonLabelExpression: ");
        result.append(this.noSelectionConfirmButtonLabelExpression);
        result.append(", selectionRequiredWithoutSelectionConfirmButtonLabelExpression: ");
        result.append(this.selectionRequiredWithoutSelectionConfirmButtonLabelExpression);
        result.append(", selectionRequiredWithSelectionConfirmButtonLabelExpression: ");
        result.append(this.selectionRequiredWithSelectionConfirmButtonLabelExpression);
        result.append(')');
        return result.toString();
    }

} // SelectionDialogDescriptionImpl
