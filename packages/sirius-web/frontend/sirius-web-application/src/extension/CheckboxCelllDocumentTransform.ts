/*******************************************************************************
 * Copyright (c) 2025 CEA LIST.
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
import { DocumentNode, FieldNode, InlineFragmentNode, Kind, SelectionNode, visit } from 'graphql';

const shouldTransform = (document: DocumentNode) => {
  return document.definitions.some(
    (definition) =>
      definition.kind === Kind.OPERATION_DEFINITION &&
      (definition.name?.value === 'detailsEvent' ||
        definition.name?.value === 'formEvent' ||
        definition.name?.value === 'formDescriptionEditorEvent' ||
        definition.name?.value === 'representationsEvent' ||
        definition.name?.value === 'relatedElementsEvent' ||
        definition.name?.value === 'diagramFilterEvent' ||
        definition.name?.value === 'tableEvent')
  );
};

const isCellFragment = (field: FieldNode) => {
  if (field.name.value === 'cells') {
    const inLinesFragment = field.selectionSet.selections
      .filter((selection): selection is InlineFragmentNode => selection.kind === Kind.INLINE_FRAGMENT)
      .map((inlineFragment: InlineFragmentNode) => inlineFragment.typeCondition.name.value);
    if (inLinesFragment.includes('TextfieldCell') && inLinesFragment.includes('TextareaCell')) {
      return true;
    }
  }
  return false;
};

const booleanValueField: SelectionNode = {
  kind: Kind.FIELD,
  alias: {
    kind: Kind.NAME,
    value: 'booleanValue',
  },
  name: {
    kind: Kind.NAME,
    value: 'value',
  },
};

export const checkboxCellDocumentTransform = new DocumentTransform((document) => {
  if (shouldTransform(document)) {
    const transformedDocument = visit(document, {
      Field(field) {
        if (!isCellFragment(field)) {
          return undefined;
        }

        const selections = field.selectionSet?.selections ?? [];

        const checkboxCellInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [booleanValueField],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'CheckboxCell',
            },
          },
        };

        return {
          ...field,
          selectionSet: {
            ...field.selectionSet,
            selections: [...selections, checkboxCellInlineFragment],
          },
        };
      },
    });

    return transformedDocument;
  }
  return document;
});
