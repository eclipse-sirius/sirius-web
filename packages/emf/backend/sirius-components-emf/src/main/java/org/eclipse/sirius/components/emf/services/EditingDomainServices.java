/*******************************************************************************
 * Copyright (c) 2016, 2025 Obeo.
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
package org.eclipse.sirius.components.emf.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.eclipse.emf.common.command.Command;
import org.eclipse.emf.common.command.CommandStack;
import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.AdapterFactory;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.provider.EcoreItemProviderAdapterFactory;
import org.eclipse.emf.edit.command.AddCommand;
import org.eclipse.emf.edit.command.CommandParameter;
import org.eclipse.emf.edit.command.MoveCommand;
import org.eclipse.emf.edit.command.RemoveCommand;
import org.eclipse.emf.edit.command.ReplaceCommand;
import org.eclipse.emf.edit.command.SetCommand;
import org.eclipse.emf.edit.domain.AdapterFactoryEditingDomain;
import org.eclipse.emf.edit.domain.EditingDomain;
import org.eclipse.emf.edit.provider.ComposedAdapterFactory;
import org.eclipse.emf.edit.provider.IItemColorProvider;
import org.eclipse.emf.edit.provider.IItemFontProvider;
import org.eclipse.emf.edit.provider.IItemLabelProvider;
import org.eclipse.emf.edit.provider.IItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.IItemPropertySource;
import org.eclipse.emf.edit.provider.IStructuredItemContentProvider;
import org.eclipse.emf.edit.provider.ITableItemColorProvider;
import org.eclipse.emf.edit.provider.ITableItemFontProvider;
import org.eclipse.emf.edit.provider.ITableItemLabelProvider;
import org.eclipse.emf.edit.provider.ITreeItemContentProvider;
import org.eclipse.emf.edit.provider.ItemPropertyDescriptor;
import org.eclipse.emf.edit.provider.ReflectiveItemProviderAdapterFactory;
import org.eclipse.emf.edit.provider.resource.ResourceItemProviderAdapterFactory;

/**
 * Provides access to common {@link EditingDomain} and {@link AdapterFactoryEditingDomain} methods in a way that is
 * compatible with "Java services" invocation from Sirius interpreted expressions.
 * <p>
 * <h4>General EditingDomain-related Services</h4>
 * <p>
 * The following general services are made available:
 * <ul>
 * <li>{@link #getEditingDomain(EObject)}</li>
 * <li>{@link #isStale(EObject)}</li>
 * <li>{@link #isControlled(EObject)}</li>
 * <li>{@link #getAdapterFactory(EObject)}</li>
 * <li>{@link #getAdapter(EObject, Class)}</li>
 * </ul>
 * <p>
 * <h4>Item Providers Services</h4>
 * <p>
 * Most public methods of the following interfaces are available as services on any {@link EObject} (typically from a
 * Sirius session):
 * <ul>
 * <li>{@link org.eclipse.emf.edit.provider.IItemLabelProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.IItemColorProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.IItemFontProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.IStructuredItemContentProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.ITreeItemContentProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.ITableItemLabelProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.ITableItemColorProvider}</li>
 * <li>{@link org.eclipse.emf.edit.provider.ITableItemFontProvider}</li>
 * </ul>
 * <p>
 * In all cases, a {@code getXXXProvider()} service method is also made available (for example
 * {@link #getLabelProvider(EObject)}, which returns the item provider itself (or <code>null</code>). The item provider
 * is located using the {@code ItemProviderAdapter} returned by the {@link AdapterFactory} associated to the element's
 * editing domain. This assumes said domain is an {@link AdapterFactoryEditingDomain} (which is true in practice in
 * Sirius, which uses a <code>TransactionalEditingDomain</code> that inherits from this).
 * <p>
 * All service methods return an explicit <code>null</code> value (or <code>false</code> for services that return a
 * boolean) if the corresponding item provider could not be found. Note that this is indistinguishable from an item
 * provider method which actually returns <code>null</code>/ <code>false</code>.
 * <h4>Item Property-Related Services</h4>
 * <p>
 * The {@link IItemPropertySource} associated with an element and its {@link IItemPropertyDescriptor}s can be obtained
 * using the following services:
 * <ul>
 * <li>{@link #getItemPropertySource(EObject)}</li>
 * <li>{@link #getPropertyDescriptors(EObject)} to obtain <em>all</em> the {@link IItemPropertyDescriptor} of the
 * element.</li>
 * <li>{@link #getPropertyDescriptorForFeature(EObject, String)} to obtain the {@link IItemPropertyDescriptor} of an
 * element's specific feature</li>
 * </ul>
 * <p>
 * The following services, which all take a feature name as a second argument (the first explicit one when invoked as a
 * service) give access to the corresponding methods in the {@link IItemPropertyDescriptor} associated to the
 * corresponding element's feature:
 * <ul>
 * <li>{@link #getPropertyDescriptorPropertyValue(EObject, String)}</li>
 * <li>{@link #isPropertyDescriptorPropertySet(EObject, String)}</li>
 * <li>{@link #canSetPropertyDescriptorProperty(EObject, String)}</li>
 * <li>{@link #resetPropertyDescriptorPropertyValue(EObject, String)}</li>
 * <li>{@link #setPropertyDescriptorPropertyValue(EObject, String, Object)}</li>
 * <li>{@link #getPropertyDescriptorCategory(EObject, String, String)}</li>
 * <li>{@link #getPropertyDescriptorDescription(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorDisplayName(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorFilterFlags(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorHelpContextIds(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorId(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorLabelProvider(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorFeature(EObject, String)}</li>
 * <li>{@link #isPropertyDescriptorMany(EObject, String)}</li>
 * <li>{@link #getPropertyDescriptorChoiceOfValues(EObject, String)}</li>
 * <li>{@link #isPropertyDescriptorMultiLine(EObject, String)}</li>
 * <li>{@link #isPropertyDescriptorSortChoices(EObject, String)}</li>
 * </ul>
 * <p>
 * <h4>Command-related Services</h4>
 * <p>
 * A series of service methods can be used to invoke the standard EMF Commands available from
 * {@code ItemProviderAdapter}'s various {@code createXXXCommand()} methods. Note that contrary to the
 * {@code createXXXCommand()} methods which simply returns a {@link Command} instance, the service methods exposed in
 * this class will directly <em>execute</em> the command on the editing domains {@link CommandStack}. Their names follow
 * the {@code perfomXXXCommand()} pattern to reflect this. They all return the {@code self} element (the {@link EObject}
 * on which the service was invoked.
 * <p>
 * The command-related services available are:
 * <ul>
 * <li>{@link #performSetCommand(EObject, String, Object, int)}</li>
 * <li>{@link #performUnsetCommand(EObject, String, int)}</li>
 * <li>{@link #performAddCommand(EObject, String, List, int)}</li>
 * <li>{@link #performRemoveCommand(EObject, String, List)}</li>
 * <li>{@link #performReplaceCommand(EObject, String, Object, List)}</li>
 * <li>{@link #performMoveCommand(EObject, String, Object, int)}</li>
 * </ul>
 * <p>
 * The implementation of these services delegate to the {@link EditingDomain#createCommand(Class, CommandParameter)}
 * which delegates to the IEditingDomainItemProvider#createCommand(Object, EditingDomain, Class, CommandParameter)
 * method, so any customization made in the {@code IEditingDomainItemProvider}'s implementation will be taken into
 * account.
 * 
 * @author pcdavid
 */
public class EditingDomainServices {

    private final AdapterFactory defaultAdapterFactory;

    public EditingDomainServices() {
        List<AdapterFactory> factories = new ArrayList<AdapterFactory>();
        factories.add(new ResourceItemProviderAdapterFactory());
        factories.add(new EcoreItemProviderAdapterFactory());
        factories.add(new ReflectiveItemProviderAdapterFactory());
        defaultAdapterFactory = new ComposedAdapterFactory(factories);
    }

    public EditingDomain getEditingDomain(EObject self) {
        return AdapterFactoryEditingDomain.getEditingDomainFor(self);
    }

    // Services from AdapterFactoryEditingDomain

    public boolean isStale(EObject self) {
        return AdapterFactoryEditingDomain.isStale(self);
    }

    public boolean isControlled(EObject self) {
        return AdapterFactoryEditingDomain.isControlled(self);
    }

    public AdapterFactory getAdapterFactory(EObject self) {
        EditingDomain domain = getEditingDomain(self);
        if (domain instanceof AdapterFactoryEditingDomain adapterFactoryEditingDomain) {
            return adapterFactoryEditingDomain.getAdapterFactory();
        } else {
            return defaultAdapterFactory;
        }
    }

    private <T> T getAdapter(EObject self, Class<T> type) {
        AdapterFactory af = getAdapterFactory(self);
        if (af != null) {
            Adapter adapter = af.adapt(self, type);
            if (type.isInstance(adapter)) {
                return type.cast(adapter);
            }
        }
        return null;
    }

    public EObject createInstance(EClass eClass) {
        return eClass.getEPackage().getEFactoryInstance().create(eClass);
    }

    // Services from IItemLabelProvider

    public IItemLabelProvider getLabelProvider(EObject self) {
        return getAdapter(self, IItemLabelProvider.class);
    }

    public String getLabelProviderText(EObject self) {
        IItemLabelProvider lp = getLabelProvider(self);
        if (lp != null) {
            return lp.getText(self);
        } else {
            return null;
        }
    }

    public Object getLabelProviderImage(EObject self) {
        IItemLabelProvider lp = getLabelProvider(self);
        if (lp != null) {
            return lp.getImage(self);
        } else {
            return null;
        }
    }

    // Services from IItemColorProvider

    public IItemColorProvider getColorProvider(EObject self) {
        return getAdapter(self, IItemColorProvider.class);
    }

    public Object getColorProviderForeground(EObject self) {
        IItemColorProvider cp = getColorProvider(self);
        if (cp != null) {
            return cp.getForeground(self);
        } else {
            return null;
        }
    }

    public Object getColorProviderBackground(EObject self) {
        IItemColorProvider cp = getColorProvider(self);
        if (cp != null) {
            return cp.getBackground(self);
        } else {
            return null;
        }
    }

    // Services from IItemFontProvider

    public IItemFontProvider getFontProvider(EObject self) {
        return getAdapter(self, IItemFontProvider.class);
    }

    public Object getFontProviderFont(EObject self) {
        IItemFontProvider fp = getFontProvider(self);
        if (fp != null) {
            return fp.getFont(self);
        } else {
            return null;
        }
    }

    // Services from IStructuredItemContentProvider

    public IStructuredItemContentProvider getStructuredItemContentProvider(EObject self) {
        return getAdapter(self, IStructuredItemContentProvider.class);
    }

    public List<?> getStructuredItemContentProviderElements(EObject self) {
        IStructuredItemContentProvider scp = getStructuredItemContentProvider(self);
        if (scp != null) {
            return new ArrayList<>(scp.getElements(self));
        } else {
            return null;
        }
    }

    // Services from ITreeItemContentProvider

    public ITreeItemContentProvider getTreeContentProvider(EObject self) {
        return getAdapter(self, ITreeItemContentProvider.class);
    }

    public List<?> getTreeContentProviderChildren(EObject self) {
        ITreeItemContentProvider tp = getTreeContentProvider(self);
        if (tp != null) {
            return new ArrayList<>(tp.getChildren(self));
        } else {
            return null;
        }
    }

    public boolean hasTreeContentProviderChildren(EObject self) {
        ITreeItemContentProvider tp = getTreeContentProvider(self);
        if (tp != null) {
            return tp.hasChildren(self);
        } else {
            return false;
        }
    }

    public Object getTreeContentProviderParent(EObject self) {
        ITreeItemContentProvider tp = getTreeContentProvider(self);
        if (tp != null) {
            return tp.getParent(self);
        } else {
            return false;
        }
    }

    // Services from ITableItemLabelProvider

    public ITableItemLabelProvider getTableLabelProvider(EObject self) {
        return getAdapter(self, ITableItemLabelProvider.class);
    }

    public String getTableLabelProviderColumnText(EObject self, int columnIndex) {
        ITableItemLabelProvider tlp = getTableLabelProvider(self);
        if (tlp != null) {
            return tlp.getColumnText(self, columnIndex);
        } else {
            return null;
        }
    }

    public Object getTableLabelProviderColumnImage(EObject self, int columnIndex) {
        ITableItemLabelProvider tlp = getTableLabelProvider(self);
        if (tlp != null) {
            return tlp.getColumnImage(self, columnIndex);
        } else {
            return null;
        }
    }

    // Services from ITableItemColorProvider

    public ITableItemColorProvider getTableColorProvider(EObject self) {
        return getAdapter(self, ITableItemColorProvider.class);
    }

    public Object getTableColorProviderForeground(EObject self, int columnIndex) {
        ITableItemColorProvider tcp = getTableColorProvider(self);
        if (tcp != null) {
            return tcp.getForeground(self, columnIndex);
        } else {
            return null;
        }
    }

    public Object getTableColorProviderBackground(EObject self, int columnIndex) {
        ITableItemColorProvider tcp = getTableColorProvider(self);
        if (tcp != null) {
            return tcp.getBackground(self, columnIndex);
        } else {
            return null;
        }
    }

    // Services from ITableItemFontProvider

    public ITableItemFontProvider getTableFontProvider(EObject self) {
        return getAdapter(self, ITableItemFontProvider.class);
    }

    public Object getTableFontProviderFont(EObject self, int columnIndex) {
        ITableItemFontProvider tfp = getTableFontProvider(self);
        if (tfp != null) {
            return tfp.getFont(self, columnIndex);
        } else {
            return null;
        }
    }

    // Services from IItemPropertySource & IItemPropertyDescriptor

    public IItemPropertySource getItemPropertySource(EObject self) {
        return getAdapter(self, IItemPropertySource.class);
    }

    public List<IItemPropertyDescriptor> getPropertyDescriptors(EObject self) {
        IItemPropertySource ips = getItemPropertySource(self);
        if (ips != null) {
            return ips.getPropertyDescriptors(self);
        } else {
            return null;
        }
    }

    public IItemPropertyDescriptor getPropertyDescriptor(EObject self, Object propertyID) {
        IItemPropertySource ips = getItemPropertySource(self);
        if (ips != null) {
            return ips.getPropertyDescriptor(self, propertyID);
        } else {
            return null;
        }
    }

    public IItemPropertyDescriptor getPropertyDescriptorForFeature(EObject self, EStructuralFeature feature) {
        if (feature.getEContainingClass().isSuperTypeOf(self.eClass())) {
            IItemPropertySource ips = getItemPropertySource(self);
            if (ips != null) {
                for (IItemPropertyDescriptor propertyDescriptor : ips.getPropertyDescriptors(self)) {
                    if ((propertyDescriptor.getFeature(self)) == feature) {
                        return propertyDescriptor;
                    }
                }
            }
        }
        return null;
    }

    public IItemPropertyDescriptor getPropertyDescriptorForFeature(EObject self, String featureName) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            return getPropertyDescriptorForFeature(self, feature);
        } else {
            return null;
        }
    }

    public Object getPropertyDescriptorPropertyValue(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);

        Object result = null;
        if (desc != null) {
            result = desc.getPropertyValue(self);
            if (result instanceof ItemPropertyDescriptor.PropertyValueWrapper propertyValueWrapper) {
                return propertyValueWrapper.getEditableValue(self);
            }
        }
        return result;
    }

    public boolean isPropertyDescriptorPropertySet(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.isPropertySet(self);
        } else {
            return false;
        }
    }

    public boolean canSetPropertyDescriptorProperty(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.canSetProperty(self);
        } else {
            return false;
        }
    }

    public void resetPropertyDescriptorPropertyValue(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            desc.resetPropertyValue(self);
        }
    }

    public void setPropertyDescriptorPropertyValue(EObject self, String featureName, Object value) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            desc.setPropertyValue(self, value);
        }
    }

    public String getPropertyDescriptorCategory(EObject self, String featureName, String defaultCategoryName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            String category = desc.getCategory(self);
            if (category != null) {
                return category;
            }
        }
        return defaultCategoryName;
    }

    public String getPropertyDescriptorDescription(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getDescription(self);
        } else {
            return null;
        }
    }

    public String getPropertyDescriptorDisplayName(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getDisplayName(self);
        } else {
            return null;
        }
    }

    public String[] getPropertyDescriptorFilterFlags(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getFilterFlags(self);
        } else {
            return null;
        }
    }

    public Object getPropertyDescriptorHelpContextIds(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getHelpContextIds(self);
        } else {
            return null;
        }
    }

    public String getPropertyDescriptorId(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getId(self);
        } else {
            return null;
        }
    }

    public IItemLabelProvider getPropertyDescriptorLabelProvider(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getLabelProvider(self);
        } else {
            return null;
        }
    }

    public Object getPropertyDescriptorFeature(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.getFeature(self);
        } else {
            return null;
        }
    }

    public boolean isPropertyDescriptorMany(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.isMany(self);
        } else {
            return false;
        }
    }

    public List<?> getPropertyDescriptorChoiceOfValues(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            Collection<?> choices = desc.getChoiceOfValues(self);
            if (choices != null) {
                return new ArrayList<>(choices);
            }
        }
        return null;
    }

    public boolean isPropertyDescriptorMultiLine(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.isMultiLine(self);
        } else {
            return false;
        }
    }

    public boolean isPropertyDescriptorSortChoices(EObject self, String featureName) {
        IItemPropertyDescriptor desc = getPropertyDescriptorForFeature(self, featureName);
        if (desc != null) {
            return desc.isSortChoices(self);
        } else {
            return false;
        }
    }

    // Commands: createXXXCommand from EditingDomain.createCommand

    private void performCommand(EObject self, Class<? extends Command> cmdClass, CommandParameter param) {
        EditingDomain domain = getEditingDomain(self);
        if (domain != null) {
            Command cmd = domain.createCommand(cmdClass, param);
            CommandStack stack = domain.getCommandStack();
            stack.execute(cmd);
        }
    }

    public EObject performSetCommand(EObject self, String featureName, Object value, int index) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, SetCommand.class, new CommandParameter(self, feature, value, index));
        }
        return self;
    }

    public EObject performUnsetCommand(EObject self, String featureName, int index) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, SetCommand.class, new CommandParameter(self, feature, SetCommand.UNSET_VALUE, index));
        }
        return self;
    }

    public EObject performSetCommand(EObject self, String featureName, Object value) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, SetCommand.class, new CommandParameter(self, feature, value));
        }
        return self;
    }

    public EObject performUnsetCommand(EObject self, String featureName) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, SetCommand.class, new CommandParameter(self, feature, SetCommand.UNSET_VALUE));
        }
        return self;
    }

    public EObject performAddCommand(EObject self, String featureName, List<?> list, int index) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {

            performCommand(self, AddCommand.class, new CommandParameter(self, feature, list, index));
        }
        return self;
    }

    public EObject performAddCommand(EObject self, String featureName, List<?> list) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, AddCommand.class, new CommandParameter(self, feature, list));
        }
        return self;
    }

    public EObject performRemoveCommand(EObject self, String featureName, List<?> list) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, RemoveCommand.class, new CommandParameter(self, feature, list));
        }
        return self;
    }

    public EObject performReplaceCommand(EObject self, String featureName, Object value, List<?> list) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, ReplaceCommand.class, new CommandParameter(self, feature, value, list));
        }
        return self;
    }

    public EObject performMoveCommand(EObject self, String featureName, Object value, int index) {
        EStructuralFeature feature = self.eClass().getEStructuralFeature(featureName);
        if (feature != null) {
            performCommand(self, MoveCommand.class, new CommandParameter(self, feature, value, index));
        }
        return self;
    }
}
