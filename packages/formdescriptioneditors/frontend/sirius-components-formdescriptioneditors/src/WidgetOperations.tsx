/*******************************************************************************
 * Copyright (c) 2022 Obeo.
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
import { GQLFlexboxContainer, GQLGroup, GQLToolbarAction, GQLWidget } from '@eclipse-sirius/sirius-components-forms';
import { GQLFormDescriptionEditor } from './FormDescriptionEditorEventFragment.types';
import { Kind } from './FormDescriptionEditorRepresentation.types';

export const isKind = (value: string): value is Kind => {
  return (
    value === 'Textfield' ||
    value === 'TextArea' ||
    value === 'Checkbox' ||
    value === 'Radio' ||
    value === 'Select' ||
    value === 'MultiSelect' ||
    value === 'Button' ||
    value === 'Label' ||
    value === 'Link' ||
    value === 'List' ||
    value === 'BarChart' ||
    value === 'PieChart' ||
    value === 'FlexboxContainer' ||
    value === 'Image' ||
    value === 'RichText'
  );
};

export const isGroup = (element: GQLWidget | GQLGroup): boolean => {
  return element.__typename === 'Group';
};

export const isFlexboxContainer = (element: GQLWidget | GQLGroup): boolean => {
  return element.__typename === 'FlexboxContainer';
};

export const getAllWidgets = (formDescriptionEditor: GQLFormDescriptionEditor): GQLWidget[] => {
  let allWidgets: GQLWidget[] = [];
  formDescriptionEditor.groups.forEach((group: GQLGroup) => {
    group.widgets.forEach((widget: GQLWidget) => {
      if (isFlexboxContainer(widget)) {
        allWidgets = allWidgets.concat(getAllFlexboxContainerWidgets(widget as GQLFlexboxContainer));
      }
      allWidgets.push(widget);
    });
  });
  return allWidgets;
};

const getAllFlexboxContainerWidgets = (flexboxContainer: GQLFlexboxContainer): GQLWidget[] => {
  let allWidgets: GQLWidget[] = [];
  flexboxContainer.children.forEach((widget: GQLWidget) => {
    if (isFlexboxContainer(widget)) {
      allWidgets = allWidgets.concat(getAllFlexboxContainerWidgets(widget as GQLFlexboxContainer));
    }
    allWidgets.push(widget);
  });
  return allWidgets;
};

export const getAllToolbarActions = (formDescriptionEditor: GQLFormDescriptionEditor): GQLToolbarAction[] => {
  return formDescriptionEditor.groups.flatMap((g: GQLGroup) => g.toolbarActions);
};
