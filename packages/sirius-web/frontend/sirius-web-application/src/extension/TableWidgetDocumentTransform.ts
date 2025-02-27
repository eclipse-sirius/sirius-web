/*******************************************************************************
 * Copyright (c) 2025 Obeo.
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

import { DocumentTransform } from '@apollo/client';
import { DocumentNode, FragmentDefinitionNode, InlineFragmentNode, Kind, SelectionNode, visit } from 'graphql';

const shouldTransform = (document: DocumentNode) => {
  return document.definitions.some(
    (definition) =>
      definition.kind === Kind.OPERATION_DEFINITION &&
      (definition.name?.value === 'detailsEvent' ||
        definition.name?.value === 'formEvent' ||
        definition.name?.value === 'formDescriptionEditorEvent' ||
        definition.name?.value === 'representationsEvent' ||
        definition.name?.value === 'relatedElementsEvent' ||
        definition.name?.value === 'diagramFilterEvent')
  );
};

const isWidgetFragmentDefinition = (node: FragmentDefinitionNode) => {
  return node.name.value === 'widgetFields';
};

const fieldBuilder = (label: string): SelectionNode => {
  return {
    kind: Kind.FIELD,
    name: {
      kind: Kind.NAME,
      value: label,
    },
  };
};

const fieldWithAliasBuilder = (label: string, alias: string): SelectionNode => {
  return {
    kind: Kind.FIELD,
    name: {
      kind: Kind.NAME,
      value: label,
    },
    alias: {
      kind: Kind.NAME,
      value: alias,
    },
  };
};

const structuredFieldBuilder = (label: string, children: SelectionNode[]): SelectionNode => {
  return {
    kind: Kind.FIELD,
    name: {
      kind: Kind.NAME,
      value: label,
    },
    selectionSet: {
      kind: Kind.SELECTION_SET,
      selections: children,
    },
  };
};

const inlineFragmentBuilder = (typeConditionName: string, children: SelectionNode[]): SelectionNode => {
  return {
    kind: Kind.INLINE_FRAGMENT,
    selectionSet: {
      kind: Kind.SELECTION_SET,
      selections: children,
    },
    typeCondition: {
      kind: Kind.NAMED_TYPE,
      name: {
        kind: Kind.NAME,
        value: typeConditionName,
      },
    },
  };
};

export const tableWidgetDocumentTransform = new DocumentTransform((document) => {
  if (shouldTransform(document)) {
    return visit(document, {
      FragmentDefinition(node) {
        if (!isWidgetFragmentDefinition(node)) {
          return undefined;
        }
        const tableWidgetInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              fieldBuilder('label'),
              fieldBuilder('iconURL'),
              structuredFieldBuilder('table', [
                fieldBuilder('id'),
                structuredFieldBuilder('paginationData', [
                  fieldBuilder('hasPreviousPage'),
                  fieldBuilder('hasNextPage'),
                  fieldBuilder('totalRowCount'),
                ]),
                fieldBuilder('stripeRow'),
                fieldBuilder('globalFilter'),
                structuredFieldBuilder('columnFilters', [fieldBuilder('id'), fieldBuilder('value')]),
                structuredFieldBuilder('columns', [
                  fieldBuilder('id'),
                  fieldBuilder('headerLabel'),
                  fieldBuilder('headerIconURLs'),
                  fieldBuilder('headerIndexLabel'),
                  fieldBuilder('targetObjectId'),
                  fieldBuilder('targetObjectKind'),
                  fieldBuilder('width'),
                  fieldBuilder('isResizable'),
                  fieldBuilder('hidden'),
                  fieldBuilder('filterVariant'),
                ]),
                structuredFieldBuilder('lines', [
                  fieldBuilder('id'),
                  fieldBuilder('targetObjectId'),
                  fieldBuilder('targetObjectKind'),
                  fieldBuilder('headerLabel'),
                  fieldBuilder('headerIconURLs'),
                  fieldBuilder('headerIndexLabel'),
                  fieldBuilder('height'),
                  fieldBuilder('isResizable'),
                  structuredFieldBuilder('cells', [
                    fieldBuilder('id'),
                    fieldBuilder('targetObjectId'),
                    fieldBuilder('targetObjectKind'),
                    fieldBuilder('columnId'),
                    inlineFragmentBuilder('CheckboxCell', [fieldWithAliasBuilder('value', 'booleanValue')]),
                    inlineFragmentBuilder('SelectCell', [
                      fieldBuilder('value'),
                      structuredFieldBuilder('options', [fieldBuilder('id'), fieldBuilder('label')]),
                    ]),
                    inlineFragmentBuilder('MultiSelectCell', [
                      fieldBuilder('values'),
                      structuredFieldBuilder('options', [fieldBuilder('id'), fieldBuilder('label')]),
                    ]),
                    inlineFragmentBuilder('TextfieldCell', [fieldWithAliasBuilder('value', 'stringValue')]),
                    inlineFragmentBuilder('IconLabelCell', [
                      fieldWithAliasBuilder('value', 'label'),
                      fieldBuilder('iconURLs'),
                    ]),
                    inlineFragmentBuilder('TextareaCell ', [fieldWithAliasBuilder('value', 'stringValue')]),
                  ]),
                ]),
              ]),
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'TableWidget',
            },
          },
        };
        return {
          ...node,
          selectionSet: {
            ...node.selectionSet,
            selections: [...node.selectionSet.selections, tableWidgetInlineFragment],
          },
        };
      },
    });
  }
  return document;
});
