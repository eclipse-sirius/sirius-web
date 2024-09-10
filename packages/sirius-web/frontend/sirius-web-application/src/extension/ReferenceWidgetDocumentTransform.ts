/*******************************************************************************
 * Copyright (c) 2024 Obeo.
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

const labelField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'label',
  },
};

const iconURLField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'iconURL',
  },
};

const ownerIdField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'ownerId',
  },
};

const descriptionIdField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'descriptionId',
  },
};

const ownerKindField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'ownerKind',
  },
};

const referenceKindField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'referenceKind',
  },
};

const containmentField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'containment',
  },
};

const manyValuedField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'manyValued',
  },
};

const referenceField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'reference',
  },
  selectionSet: {
    kind: Kind.SELECTION_SET,
    selections: [ownerKindField, referenceKindField, containmentField, manyValuedField],
  },
};

const idField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'id',
  },
};

const kindField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'kind',
  },
};

const colorField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'color',
  },
};

const fontSizeField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'fontSize',
  },
};

const italicField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'italic',
  },
};

const boldField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'bold',
  },
};

const underlineField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'underline',
  },
};

const strikeThroughField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'strikeThrough',
  },
};

const referenceValuesField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'referenceValues',
  },
  selectionSet: {
    kind: Kind.SELECTION_SET,
    selections: [idField, labelField, kindField, iconURLField],
  },
};

const styleField: SelectionNode = {
  kind: Kind.FIELD,
  name: {
    kind: Kind.NAME,
    value: 'style',
  },
  selectionSet: {
    kind: Kind.SELECTION_SET,
    selections: [colorField, fontSizeField, italicField, boldField, underlineField, strikeThroughField],
  },
};

export const referenceWidgetDocumentTransform = new DocumentTransform((document) => {
  if (shouldTransform(document)) {
    return visit(document, {
      FragmentDefinition(node) {
        if (!isWidgetFragmentDefinition(node)) {
          return undefined;
        }
        const referenceWidgetInlineFragment: InlineFragmentNode = {
          kind: Kind.INLINE_FRAGMENT,
          selectionSet: {
            kind: Kind.SELECTION_SET,
            selections: [
              labelField,
              iconURLField,
              ownerIdField,
              descriptionIdField,
              referenceField,
              referenceValuesField,
              styleField,
            ],
          },
          typeCondition: {
            kind: Kind.NAMED_TYPE,
            name: {
              kind: Kind.NAME,
              value: 'ReferenceWidget',
            },
          },
        };

        return {
          ...node,
          selectionSet: {
            ...node.selectionSet,
            selections: [...node.selectionSet.selections, referenceWidgetInlineFragment],
          },
        };
      },
    });
  }
  return document;
});
